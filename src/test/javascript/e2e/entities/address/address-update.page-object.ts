import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class AddressUpdatePage {
  pageTitle: ElementFinder = element(by.id('customerManagerApp.address.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  streetNumberInput: ElementFinder = element(by.css('input#address-streetNumber'));
  streetAddressInput: ElementFinder = element(by.css('input#address-streetAddress'));
  cityInput: ElementFinder = element(by.css('input#address-city'));
  stateInput: ElementFinder = element(by.css('input#address-state'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setStreetNumberInput(streetNumber) {
    await this.streetNumberInput.sendKeys(streetNumber);
  }

  async getStreetNumberInput() {
    return this.streetNumberInput.getAttribute('value');
  }

  async setStreetAddressInput(streetAddress) {
    await this.streetAddressInput.sendKeys(streetAddress);
  }

  async getStreetAddressInput() {
    return this.streetAddressInput.getAttribute('value');
  }

  async setCityInput(city) {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput() {
    return this.cityInput.getAttribute('value');
  }

  async setStateInput(state) {
    await this.stateInput.sendKeys(state);
  }

  async getStateInput() {
    return this.stateInput.getAttribute('value');
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
    await this.setStreetNumberInput('streetNumber');
    expect(await this.getStreetNumberInput()).to.match(/streetNumber/);
    await waitUntilDisplayed(this.saveButton);
    await this.setStreetAddressInput('streetAddress');
    expect(await this.getStreetAddressInput()).to.match(/streetAddress/);
    await waitUntilDisplayed(this.saveButton);
    await this.setCityInput('city');
    expect(await this.getCityInput()).to.match(/city/);
    await waitUntilDisplayed(this.saveButton);
    await this.setStateInput('state');
    expect(await this.getStateInput()).to.match(/state/);
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
