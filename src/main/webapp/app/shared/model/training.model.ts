import { IReview } from 'app/shared/model/review.model';
import { IPayment } from 'app/shared/model/payment.model';

export interface ITraining {
  id?: number;
  name?: string;
  amount?: number;
  profilePhoneNumber?: string;
  profileId?: number;
  reviews?: IReview[];
  payments?: IPayment[];
  serviceCategoryName?: string;
  serviceCategoryId?: number;
  servicesDescription?: string;
  servicesId?: number;
}

export const defaultValue: Readonly<ITraining> = {};
