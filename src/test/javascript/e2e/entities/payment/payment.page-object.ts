import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import PaymentUpdatePage from './payment-update.page-object';

const expect = chai.expect;
export class PaymentDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('customerManagerApp.payment.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-payment'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class PaymentComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('payment-heading'));
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
    await navBarPage.getEntityPage('payment');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreatePayment() {
    await this.createButton.click();
    return new PaymentUpdatePage();
  }

  async deletePayment() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const paymentDeleteDialog = new PaymentDeleteDialog();
    await waitUntilDisplayed(paymentDeleteDialog.deleteModal);
    expect(await paymentDeleteDialog.getDialogTitle().getAttribute('id')).to.match(/customerManagerApp.payment.delete.question/);
    await paymentDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(paymentDeleteDialog.deleteModal);

    expect(await isVisible(paymentDeleteDialog.deleteModal)).to.be.false;
  }
}
