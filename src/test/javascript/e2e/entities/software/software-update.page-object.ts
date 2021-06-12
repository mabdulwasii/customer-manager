import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class SoftwareUpdatePage {
  pageTitle: ElementFinder = element(by.id('customerManagerApp.software.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  technologySelect: ElementFinder = element(by.css('select#software-technology'));
  amountInput: ElementFinder = element(by.css('input#software-amount'));
  detailsInput: ElementFinder = element(by.css('input#software-details'));
  serviceCategorySelect: ElementFinder = element(by.css('select#software-serviceCategory'));
  servicesSelect: ElementFinder = element(by.css('select#software-services'));
  profileSelect: ElementFinder = element(by.css('select#software-profile'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTechnologySelect(technology) {
    await this.technologySelect.sendKeys(technology);
  }

  async getTechnologySelect() {
    return this.technologySelect.element(by.css('option:checked')).getText();
  }

  async technologySelectLastOption() {
    await this.technologySelect.all(by.tagName('option')).last().click();
  }
  async setAmountInput(amount) {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput() {
    return this.amountInput.getAttribute('value');
  }

  async setDetailsInput(details) {
    await this.detailsInput.sendKeys(details);
  }

  async getDetailsInput() {
    return this.detailsInput.getAttribute('value');
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
    await this.technologySelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setAmountInput('5');
    expect(await this.getAmountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setDetailsInput('details');
    expect(await this.getDetailsInput()).to.match(/details/);
    await this.serviceCategorySelectLastOption();
    await this.servicesSelectLastOption();
    await this.profileSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
