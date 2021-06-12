export interface IAddress {
  id?: number;
  streetNumber?: string;
  streetAddress?: string;
  city?: string;
  state?: string;
}

export const defaultValue: Readonly<IAddress> = {};
