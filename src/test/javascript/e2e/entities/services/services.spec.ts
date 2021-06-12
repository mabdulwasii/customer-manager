import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServicesComponentsPage from './services.page-object';
import ServicesUpdatePage from './services-update.page-object';
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

describe('Services e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let servicesComponentsPage: ServicesComponentsPage;
  let servicesUpdatePage: ServicesUpdatePage;

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
    servicesComponentsPage = new ServicesComponentsPage();
    servicesComponentsPage = await servicesComponentsPage.goToPage(navBarPage);
  });

  it('should load Services', async () => {
    expect(await servicesComponentsPage.title.getText()).to.match(/Services/);
    expect(await servicesComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Services', async () => {
    const beforeRecordsCount = (await isVisible(servicesComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(servicesComponentsPage.table);
    servicesUpdatePage = await servicesComponentsPage.goToCreateServices();
    await servicesUpdatePage.enterData();

    expect(await servicesComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(servicesComponentsPage.table);
    await waitUntilCount(servicesComponentsPage.records, beforeRecordsCount + 1);
    expect(await servicesComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await servicesComponentsPage.deleteServices();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(servicesComponentsPage.records, beforeRecordsCount);
      expect(await servicesComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(servicesComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
