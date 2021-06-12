import { element, by, ElementFinder, protractor } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class PaymentUpdatePage {
  pageTitle: ElementFinder = element(by.id('customerManagerApp.payment.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  dateInput: ElementFinder = element(by.css('input#payment-date'));
  amountInput: ElementFinder = element(by.css('input#payment-amount'));
  paymentTypeInput: ElementFinder = element(by.css('input#payment-paymentType'));
  balanceInput: ElementFinder = element(by.css('input#payment-balance'));
  softwareSelect: ElementFinder = element(by.css('select#payment-software'));
  trainingSelect: ElementFinder = element(by.css('select#payment-training'));
  hardwareSelect: ElementFinder = element(by.css('select#payment-hardware'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setDateInput(date) {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput() {
    return this.dateInput.getAttribute('value');
  }

  async setAmountInput(amount) {
    await this.amountInput.sendKeys(amount);
  }

  async getAmountInput() {
    return this.amountInput.getAttribute('value');
  }

  async setPaymentTypeInput(paymentType) {
    await this.paymentTypeInput.sendKeys(paymentType);
  }

  async getPaymentTypeInput() {
    return this.paymentTypeInput.getAttribute('value');
  }

  async setBalanceInput(balance) {
    await this.balanceInput.sendKeys(balance);
  }

  async getBalanceInput() {
    return this.balanceInput.getAttribute('value');
  }

  async softwareSelectLastOption() {
    await this.softwareSelect.all(by.tagName('option')).last().click();
  }

  async softwareSelectOption(option) {
    await this.softwareSelect.sendKeys(option);
  }

  getSoftwareSelect() {
    return this.softwareSelect;
  }

  async getSoftwareSelectedOption() {
    return this.softwareSelect.element(by.css('option:checked')).getText();
  }

  async trainingSelectLastOption() {
    await this.trainingSelect.all(by.tagName('option')).last().click();
  }

  async trainingSelectOption(option) {
    await this.trainingSelect.sendKeys(option);
  }

  getTrainingSelect() {
    return this.trainingSelect;
  }

  async getTrainingSelectedOption() {
    return this.trainingSelect.element(by.css('option:checked')).getText();
  }

  async hardwareSelectLastOption() {
    await this.hardwareSelect.all(by.tagName('option')).last().click();
  }

  async hardwareSelectOption(option) {
    await this.hardwareSelect.sendKeys(option);
  }

  getHardwareSelect() {
    return this.hardwareSelect;
  }

  async getHardwareSelectedOption() {
    return this.hardwareSelect.element(by.css('option:checked')).getText();
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
    await this.setDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM');
    expect(await this.getDateInput()).to.contain('2001-01-01T02:30');
    await waitUntilDisplayed(this.saveButton);
    await this.setAmountInput('5');
    expect(await this.getAmountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setPaymentTypeInput('paymentType');
    expect(await this.getPaymentTypeInput()).to.match(/paymentType/);
    await waitUntilDisplayed(this.saveButton);
    await this.setBalanceInput('5');
    expect(await this.getBalanceInput()).to.eq('5');
    await this.softwareSelectLastOption();
    await this.trainingSelectLastOption();
    await this.hardwareSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
