import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ServiceCategoryUpdatePage {
  pageTitle: ElementFinder = element(by.id('customerManagerApp.serviceCategory.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameInput: ElementFinder = element(by.css('input#service-category-name'));
  fixedAmountInput: ElementFinder = element(by.css('input#service-category-fixedAmount'));
  hasFixedPriceInput: ElementFinder = element(by.css('input#service-category-hasFixedPrice'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameInput(name) {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput() {
    return this.nameInput.getAttribute('value');
  }

  async setFixedAmountInput(fixedAmount) {
    await this.fixedAmountInput.sendKeys(fixedAmount);
  }

  async getFixedAmountInput() {
    return this.fixedAmountInput.getAttribute('value');
  }

  getHasFixedPriceInput() {
    return this.hasFixedPriceInput;
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
    await this.setFixedAmountInput('5');
    expect(await this.getFixedAmountInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    const selectedHasFixedPrice = await this.getHasFixedPriceInput().isSelected();
    if (selectedHasFixedPrice) {
      await this.getHasFixedPriceInput().click();
      expect(await this.getHasFixedPriceInput().isSelected()).to.be.false;
    } else {
      await this.getHasFixedPriceInput().click();
      expect(await this.getHasFixedPriceInput().isSelected()).to.be.true;
    }
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
