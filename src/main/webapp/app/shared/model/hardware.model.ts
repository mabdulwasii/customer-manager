import { IReview } from 'app/shared/model/review.model';
import { IPayment } from 'app/shared/model/payment.model';
import { Gadget } from 'app/shared/model/enumerations/gadget.model';

export interface IHardware {
  id?: number;
  gadget?: Gadget;
  model?: string;
  brandName?: string;
  serialNumber?: string;
  imeiNumber?: string;
  state?: string;
  servicesDescription?: string;
  servicesId?: number;
  reviews?: IReview[];
  payments?: IPayment[];
  serviceCategoryName?: string;
  serviceCategoryId?: number;
  profilePhoneNumber?: string;
  profileId?: number;
}

export const defaultValue: Readonly<IHardware> = {};
