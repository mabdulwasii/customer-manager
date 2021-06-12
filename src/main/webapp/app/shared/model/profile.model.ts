import { Moment } from 'moment';
import { IHardware } from 'app/shared/model/hardware.model';
import { ISoftware } from 'app/shared/model/software.model';
import { ITraining } from 'app/shared/model/training.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';

export interface IProfile {
  id?: number;
  phoneNumber?: string;
  dateOfBirth?: string;
  profileId?: string;
  gender?: Gender;
  validId?: string;
  userLogin?: string;
  userId?: number;
  addressStreetAddress?: string;
  addressId?: number;
  hardware?: IHardware[];
  software?: ISoftware[];
  trainings?: ITraining[];
}

export const defaultValue: Readonly<IProfile> = {};
