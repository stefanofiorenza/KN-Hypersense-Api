export interface ISupplier {
  id?: number;
  name?: string | null;
  contactDetails?: string | null;
}

export const defaultValue: Readonly<ISupplier> = {};
