import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import SoftwareComponentsPage from './software.page-object';
import SoftwareUpdatePage from './software-update.page-object';
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

describe('Software e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let softwareComponentsPage: SoftwareComponentsPage;
  let softwareUpdatePage: SoftwareUpdatePage;

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
    softwareComponentsPage = new SoftwareComponentsPage();
    softwareComponentsPage = await softwareComponentsPage.goToPage(navBarPage);
  });

  it('should load Software', async () => {
    expect(await softwareComponentsPage.title.getText()).to.match(/Software/);
    expect(await softwareComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete Software', async () => {
        const beforeRecordsCount = await isVisible(softwareComponentsPage.noRecords) ? 0 : await getRecordsCount(softwareComponentsPage.table);
        softwareUpdatePage = await softwareComponentsPage.goToCreateSoftware();
        await softwareUpdatePage.enterData();

        expect(await softwareComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(softwareComponentsPage.table);
        await waitUntilCount(softwareComponentsPage.records, beforeRecordsCount + 1);
        expect(await softwareComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await softwareComponentsPage.deleteSoftware();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(softwareComponentsPage.records, beforeRecordsCount);
          expect(await softwareComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(softwareComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
