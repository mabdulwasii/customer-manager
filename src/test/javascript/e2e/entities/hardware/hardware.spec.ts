import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import HardwareComponentsPage from './hardware.page-object';
import HardwareUpdatePage from './hardware-update.page-object';
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

describe('Hardware e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let hardwareComponentsPage: HardwareComponentsPage;
  let hardwareUpdatePage: HardwareUpdatePage;

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
    hardwareComponentsPage = new HardwareComponentsPage();
    hardwareComponentsPage = await hardwareComponentsPage.goToPage(navBarPage);
  });

  it('should load Hardware', async () => {
    expect(await hardwareComponentsPage.title.getText()).to.match(/Hardware/);
    expect(await hardwareComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete Hardware', async () => {
        const beforeRecordsCount = await isVisible(hardwareComponentsPage.noRecords) ? 0 : await getRecordsCount(hardwareComponentsPage.table);
        hardwareUpdatePage = await hardwareComponentsPage.goToCreateHardware();
        await hardwareUpdatePage.enterData();

        expect(await hardwareComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(hardwareComponentsPage.table);
        await waitUntilCount(hardwareComponentsPage.records, beforeRecordsCount + 1);
        expect(await hardwareComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await hardwareComponentsPage.deleteHardware();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(hardwareComponentsPage.records, beforeRecordsCount);
          expect(await hardwareComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(hardwareComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
