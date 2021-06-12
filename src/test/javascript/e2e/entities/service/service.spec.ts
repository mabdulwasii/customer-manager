import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServiceComponentsPage from './service.page-object';
import ServiceUpdatePage from './service-update.page-object';
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

describe('Service e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceComponentsPage: ServiceComponentsPage;
  let serviceUpdatePage: ServiceUpdatePage;

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
    serviceComponentsPage = new ServiceComponentsPage();
    serviceComponentsPage = await serviceComponentsPage.goToPage(navBarPage);
  });

  it('should load Services', async () => {
    expect(await serviceComponentsPage.title.getText()).to.match(/Services/);
    expect(await serviceComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete Services', async () => {
    const beforeRecordsCount = (await isVisible(serviceComponentsPage.noRecords)) ? 0 : await getRecordsCount(serviceComponentsPage.table);
    serviceUpdatePage = await serviceComponentsPage.goToCreateService();
    await serviceUpdatePage.enterData();

    expect(await serviceComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(serviceComponentsPage.table);
    await waitUntilCount(serviceComponentsPage.records, beforeRecordsCount + 1);
    expect(await serviceComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await serviceComponentsPage.deleteService();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(serviceComponentsPage.records, beforeRecordsCount);
      expect(await serviceComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(serviceComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
