export interface IAlertConfiguration {
  id?: number;
  name?: string | null;
  description?: string | null;
  configuration?: string | null;
}

export const defaultValue: Readonly<IAlertConfiguration> = {};
