import { IDevice } from 'app/shared/model/device.model';

export interface IStatus {
  id?: number;
  name?: string | null;
  description?: string | null;
  uUID?: string | null;
  device?: IDevice | null;
}

export const defaultValue: Readonly<IStatus> = {};
