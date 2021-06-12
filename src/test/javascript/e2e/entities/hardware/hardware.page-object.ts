import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import HardwareUpdatePage from './hardware-update.page-object';

const expect = chai.expect;
export class HardwareDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('customerManagerApp.hardware.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-hardware'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class HardwareComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('hardware-heading'));
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
    await navBarPage.getEntityPage('hardware');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateHardware() {
    await this.createButton.click();
    return new HardwareUpdatePage();
  }

  async deleteHardware() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const hardwareDeleteDialog = new HardwareDeleteDialog();
    await waitUntilDisplayed(hardwareDeleteDialog.deleteModal);
    expect(await hardwareDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/customerManagerApp.hardware.delete.question/);
    await hardwareDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(hardwareDeleteDialog.deleteModal);

    expect(await isVisible(hardwareDeleteDialog.deleteModal)).to.be.false;
  }
}
