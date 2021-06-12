import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import TrainingUpdatePage from './training-update.page-object';

const expect = chai.expect;
export class TrainingDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('customerManagerApp.training.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-training'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class TrainingComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('training-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('training');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateTraining() {
    await this.createButton.click();
    return new TrainingUpdatePage();
  }

  async deleteTraining() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const trainingDeleteDialog = new TrainingDeleteDialog();
    await waitUntilDisplayed(trainingDeleteDialog.deleteModal);
    expect(await trainingDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/customerManagerApp.training.delete.question/);
    await trainingDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(trainingDeleteDialog.deleteModal);

    expect(await isVisible(trainingDeleteDialog.deleteModal)).to.be.false;
  }
}
