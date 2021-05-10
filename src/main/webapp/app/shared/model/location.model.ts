export interface ILocation {
  id?: number;
  coordinates?: string | null;
}

export const defaultValue: Readonly<ILocation> = {};
