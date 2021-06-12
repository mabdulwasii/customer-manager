import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServiceCategoryComponentsPage from './service-category.page-object';
import ServiceCategoryUpdatePage from './service-category-update.page-object';
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

describe('ServiceCategory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let serviceCategoryComponentsPage: ServiceCategoryComponentsPage;
  let serviceCategoryUpdatePage: ServiceCategoryUpdatePage;

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
    serviceCategoryComponentsPage = new ServiceCategoryComponentsPage();
    serviceCategoryComponentsPage = await serviceCategoryComponentsPage.goToPage(navBarPage);
  });

  it('should load ServiceCategories', async () => {
    expect(await serviceCategoryComponentsPage.title.getText()).to.match(/Service Categories/);
    expect(await serviceCategoryComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServiceCategories', async () => {
    const beforeRecordsCount = (await isVisible(serviceCategoryComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(serviceCategoryComponentsPage.table);
    serviceCategoryUpdatePage = await serviceCategoryComponentsPage.goToCreateServiceCategory();
    await serviceCategoryUpdatePage.enterData();

    expect(await serviceCategoryComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(serviceCategoryComponentsPage.table);
    await waitUntilCount(serviceCategoryComponentsPage.records, beforeRecordsCount + 1);
    expect(await serviceCategoryComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await serviceCategoryComponentsPage.deleteServiceCategory();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(serviceCategoryComponentsPage.records, beforeRecordsCount);
      expect(await serviceCategoryComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(serviceCategoryComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
