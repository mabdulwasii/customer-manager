import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class HardwareUpdatePage {
  pageTitle: ElementFinder = element(by.id('customerManagerApp.hardware.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  gadgetSelect: ElementFinder = element(by.css('select#hardware-gadget'));
  modelInput: ElementFinder = element(by.css('input#hardware-model'));
  brandNameInput: ElementFinder = element(by.css('input#hardware-brandName'));
  serialNumberInput: ElementFinder = element(by.css('input#hardware-serialNumber'));
  imeiNumberInput: ElementFinder = element(by.css('input#hardware-imeiNumber'));
  stateInput: ElementFinder = element(by.css('input#hardware-state'));
  servicesSelect: ElementFinder = element(by.css('select#hardware-services'));
  serviceCategorySelect: ElementFinder = element(by.css('select#hardware-serviceCategory'));
  profileSelect: ElementFinder = element(by.css('select#hardware-profile'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setGadgetSelect(gadget) {
    await this.gadgetSelect.sendKeys(gadget);
  }

  async getGadgetSelect() {
    return this.gadgetSelect.element(by.css('option:checked')).getText();
  }

  async gadgetSelectLastOption() {
    await this.gadgetSelect.all(by.tagName('option')).last().click();
  }
  async setModelInput(model) {
    await this.modelInput.sendKeys(model);
  }

  async getModelInput() {
    return this.modelInput.getAttribute('value');
  }

  async setBrandNameInput(brandName) {
    await this.brandNameInput.sendKeys(brandName);
  }

  async getBrandNameInput() {
    return this.brandNameInput.getAttribute('value');
  }

  async setSerialNumberInput(serialNumber) {
    await this.serialNumberInput.sendKeys(serialNumber);
  }

  async getSerialNumberInput() {
    return this.serialNumberInput.getAttribute('value');
  }

  async setImeiNumberInput(imeiNumber) {
    await this.imeiNumberInput.sendKeys(imeiNumber);
  }

  async getImeiNumberInput() {
    return this.imeiNumberInput.getAttribute('value');
  }

  async setStateInput(state) {
    await this.stateInput.sendKeys(state);
  }

  async getStateInput() {
    return this.stateInput.getAttribute('value');
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
    await this.gadgetSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setModelInput('model');
    expect(await this.getModelInput()).to.match(/model/);
    await waitUntilDisplayed(this.saveButton);
    await this.setBrandNameInput('brandName');
    expect(await this.getBrandNameInput()).to.match(/brandName/);
    await waitUntilDisplayed(this.saveButton);
    await this.setSerialNumberInput('serialNumber');
    expect(await this.getSerialNumberInput()).to.match(/serialNumber/);
    await waitUntilDisplayed(this.saveButton);
    await this.setImeiNumberInput('imeiNumber');
    expect(await this.getImeiNumberInput()).to.match(/imeiNumber/);
    await waitUntilDisplayed(this.saveButton);
    await this.setStateInput('state');
    expect(await this.getStateInput()).to.match(/state/);
    await this.servicesSelectLastOption();
    await this.serviceCategorySelectLastOption();
    await this.profileSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
