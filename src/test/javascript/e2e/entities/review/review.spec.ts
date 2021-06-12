import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ReviewComponentsPage from './review.page-object';
import ReviewUpdatePage from './review-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('Review e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reviewComponentsPage: ReviewComponentsPage;
  let reviewUpdatePage: ReviewUpdatePage;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();

    await signInPage.username.sendKeys('admin');
    await signInPage.password.sendKeys('admin');
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    reviewComponentsPage = new ReviewComponentsPage();
    reviewComponentsPage = await reviewComponentsPage.goToPage(navBarPage);
  });

  it('should load Reviews', async () => {
    expect(await reviewComponentsPage.title.getText()).to.match(/Reviews/);
    expect(await reviewComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Reviews', async () => {
    const beforeRecordsCount = (await isVisible(reviewComponentsPage.noRecords)) ? 0 : await getRecordsCount(reviewComponentsPage.table);
    reviewUpdatePage = await reviewComponentsPage.goToCreateReview();
    await reviewUpdatePage.enterData();

    expect(await reviewComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(reviewComponentsPage.table);
    await waitUntilCount(reviewComponentsPage.records, beforeRecordsCount + 1);
    expect(await reviewComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await reviewComponentsPage.deleteReview();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(reviewComponentsPage.records, beforeRecordsCount);
      expect(await reviewComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(reviewComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
