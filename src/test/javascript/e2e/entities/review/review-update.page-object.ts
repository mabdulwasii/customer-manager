import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class ReviewUpdatePage {
  pageTitle: ElementFinder = element(by.id('customerManagerApp.review.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  ratingInput: ElementFinder = element(by.css('input#review-rating'));
  commentInput: ElementFinder = element(by.css('input#review-comment'));
  profileSelect: ElementFinder = element(by.css('select#review-profile'));
  hardwareSelect: ElementFinder = element(by.css('select#review-hardware'));
  trainingSelect: ElementFinder = element(by.css('select#review-training'));
  softwareSelect: ElementFinder = element(by.css('select#review-software'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setRatingInput(rating) {
    await this.ratingInput.sendKeys(rating);
  }

  async getRatingInput() {
    return this.ratingInput.getAttribute('value');
  }

  async setCommentInput(comment) {
    await this.commentInput.sendKeys(comment);
  }

  async getCommentInput() {
    return this.commentInput.getAttribute('value');
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
    await this.setRatingInput('5');
    expect(await this.getRatingInput()).to.eq('5');
    await waitUntilDisplayed(this.saveButton);
    await this.setCommentInput('comment');
    expect(await this.getCommentInput()).to.match(/comment/);
    await this.profileSelectLastOption();
    await this.hardwareSelectLastOption();
    await this.trainingSelectLastOption();
    await this.softwareSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
    expect(await isVisible(this.saveButton)).to.be.false;
  }
}
