import { Moment } from 'moment';

export interface IServices {
  id?: number;
  description?: string;
  startDate?: string;
  agree?: boolean;
  signDocUrl?: string;
  hardwareId?: number;
  trainingId?: number;
  softwareId?: number;
}

export const defaultValue: Readonly<IServices> = {
  agree: false,
};
