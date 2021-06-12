import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class TrainingUpdatePage {
  pageTitle: ElementFinder = element(by.id('customerManagerApp.training.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#training-name'));
  amountInput: ElementFinder = element(by.css('input#training-amount'));
  profileSelect: ElementFinder = element(by.css('select#training-profile'));
  serviceCategorySelect: ElementFinder = element(by.css('select#training-serviceCategory'));
  servicesSelect: ElementFinder = element(by.css('select#training-services'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setAmountInput(amount) {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput() {
    return this.amountInput.getAttribute('value');
  }

  async profileSelectLastOption() {
    await this.profileSelect.all(by.tagName('option')).last().click();
  }

  async profileSelectOption(option) {
    await this.profileSelect.sendKeys(option);
  }

  getProfileSelect() {
    return this.profileSelect;
  }

  async getProfileSelectedOption() {
    return this.profileSelect.element(by.css('option:checked')).getText();
  }

  async serviceCategorySelectLastOption() {
    await this.serviceCategorySelect.all(by.tagName('option')).last().click();
  }

  async serviceCategorySelectOption(option) {
    await this.serviceCategorySelect.sendKeys(option);
  }

  getServiceCategorySelect() {
    return this.serviceCategorySelect;
  }

  async getServiceCategorySelectedOption() {
    return this.serviceCategorySelect.element(by.css('option:checked')).getText();
  }

  async servicesSelectLastOption() {
    await this.servicesSelect.all(by.tagName('option')).last().click();
  }

  async servicesSelectOption(option) {
    await this.servicesSelect.sendKeys(option);
  }

  getServicesSelect() {
    return this.servicesSelect;
  }

  async getServicesSelectedOption() {
    return this.servicesSelect.element(by.css('option:checked')).getText();
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
    await this.setNameInput('name');
    expect(await this.getNameInput()).to.match(/name/);
    await waitUntilDisplayed(this.saveButton);
    await this.setAmountInput('5');
    expect(await this.getAmountInput()).to.eq('5');
    await this.profileSelectLastOption();
    await this.serviceCategorySelectLastOption();
    await this.servicesSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
