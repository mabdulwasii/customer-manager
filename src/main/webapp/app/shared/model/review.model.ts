export interface IReview {
  id?: number;
  rating?: number;
  comment?: string;
  profilePhoneNumber?: string;
  profileId?: number;
  hardwareGadget?: string;
  hardwareId?: number;
  trainingName?: string;
  trainingId?: number;
  softwareTechnology?: string;
  softwareId?: number;
}

export const defaultValue: Readonly<IReview> = {};
