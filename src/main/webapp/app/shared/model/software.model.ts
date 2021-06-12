import { IPayment } from 'app/shared/model/payment.model';
import { IReview } from 'app/shared/model/review.model';
import { Technology } from 'app/shared/model/enumerations/technology.model';

export interface ISoftware {
  id?: number;
  technology?: Technology;
  amount?: number;
  details?: string;
  serviceCategoryName?: string;
  serviceCategoryId?: number;
  payments?: IPayment[];
  servicesDescription?: string;
  servicesId?: number;
  reviews?: IReview[];
  profilePhoneNumber?: string;
  profileId?: number;
}

export const defaultValue: Readonly<ISoftware> = {};
