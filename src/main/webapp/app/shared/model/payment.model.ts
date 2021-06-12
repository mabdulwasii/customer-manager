import { Moment } from 'moment';

export interface IPayment {
  id?: number;
  date?: string;
  amount?: number;
  paymentType?: string;
  balance?: number;
  softwareTechnology?: string;
  softwareId?: number;
  trainingName?: string;
  trainingId?: number;
  hardwareGadget?: string;
  hardwareId?: number;
}

export const defaultValue: Readonly<IPayment> = {};
