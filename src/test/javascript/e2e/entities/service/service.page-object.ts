import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import ServiceUpdatePage from './service-update.page-object';

const expect = chai.expect;
export class ServiceDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('customerManagerApp.service.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-service'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class ServiceComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('service-heading'));
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
    await navBarPage.getEntityPage('service');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateService() {
    await this.createButton.click();
    return new ServiceUpdatePage();
  }

  async deleteService() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const serviceDeleteDialog = new ServiceDeleteDialog();
    await waitUntilDisplayed(serviceDeleteDialog.deleteModal);
    expect(await serviceDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/customerManagerApp.service.delete.question/);
    await serviceDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(serviceDeleteDialog.deleteModal);

    expect(await isVisible(serviceDeleteDialog.deleteModal)).to.be.false;
  }
}
