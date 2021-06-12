import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import SoftwareUpdatePage from './software-update.page-object';

const expect = chai.expect;
export class SoftwareDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('customerManagerApp.software.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-software'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class SoftwareComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('software-heading'));
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
    await navBarPage.getEntityPage('software');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateSoftware() {
    await this.createButton.click();
    return new SoftwareUpdatePage();
  }

  async deleteSoftware() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const softwareDeleteDialog = new SoftwareDeleteDialog();
    await waitUntilDisplayed(softwareDeleteDialog.deleteModal);
    expect(await softwareDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/customerManagerApp.software.delete.question/);
    await softwareDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(softwareDeleteDialog.deleteModal);

    expect(await isVisible(softwareDeleteDialog.deleteModal)).to.be.false;
  }
}
