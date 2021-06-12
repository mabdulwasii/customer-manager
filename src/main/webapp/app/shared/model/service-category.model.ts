export interface IServiceCategory {
  id?: number;
  name?: string;
  fixedAmount?: number;
  hasFixedPrice?: boolean;
}

export const defaultValue: Readonly<IServiceCategory> = {
  hasFixedPrice: false,
};
