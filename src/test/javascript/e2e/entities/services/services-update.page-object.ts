import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ServicesUpdatePage {
  pageTitle: ElementFinder = element(by.id('customerManagerApp.services.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  descriptionInput: ElementFinder = element(by.css('input#services-description'));
  startDateInput: ElementFinder = element(by.css('input#services-startDate'));
  agreeInput: ElementFinder = element(by.css('input#services-agree'));
  signDocUrlInput: ElementFinder = element(by.css('input#services-signDocUrl'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setStartDateInput(startDate) {
    await this.startDateInput.sendKeys(startDate);
  }

  async getStartDateInput() {
    return this.startDateInput.getAttribute('value');
  }

  getAgreeInput() {
    return this.agreeInput;
  }
  async setSignDocUrlInput(signDocUrl) {
    await this.signDocUrlInput.sendKeys(signDocUrl);
  }

  async getSignDocUrlInput() {
    return this.signDocUrlInput.getAttribute('value');
  }

  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    expect(await this.getDescriptionInput()).to.match(/description/);
    await waitUntilDisplayed(this.saveButton);
    await this.setStartDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getStartDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    const selectedAgree = await this.getAgreeInput().isSelected();
    if (selectedAgree) {
      await this.getAgreeInput().click();
      expect(await this.getAgreeInput().isSelected()).to.be.false;
    } else {
      await this.getAgreeInput().click();
      expect(await this.getAgreeInput().isSelected()).to.be.true;
    }
    await waitUntilDisplayed(this.saveButton);
    await this.setSignDocUrlInput('signDocUrl');
    expect(await this.getSignDocUrlInput()).to.match(/signDocUrl/);
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
