import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import TrainingComponentsPage from './training.page-object';
import TrainingUpdatePage from './training-update.page-object';
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

describe('Training e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let trainingComponentsPage: TrainingComponentsPage;
  let trainingUpdatePage: TrainingUpdatePage;

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
    trainingComponentsPage = new TrainingComponentsPage();
    trainingComponentsPage = await trainingComponentsPage.goToPage(navBarPage);
  });

  it('should load Trainings', async () => {
    expect(await trainingComponentsPage.title.getText()).to.match(/Trainings/);
    expect(await trainingComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete Trainings', async () => {
        const beforeRecordsCount = await isVisible(trainingComponentsPage.noRecords) ? 0 : await getRecordsCount(trainingComponentsPage.table);
        trainingUpdatePage = await trainingComponentsPage.goToCreateTraining();
        await trainingUpdatePage.enterData();

        expect(await trainingComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(trainingComponentsPage.table);
        await waitUntilCount(trainingComponentsPage.records, beforeRecordsCount + 1);
        expect(await trainingComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);
        
        await trainingComponentsPage.deleteTraining();
        if(beforeRecordsCount !== 0) { 
          await waitUntilCount(trainingComponentsPage.records, beforeRecordsCount);
          expect(await trainingComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(trainingComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
