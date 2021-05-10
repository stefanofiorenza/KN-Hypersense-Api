import { IDevice } from 'app/shared/model/device.model';

export interface IDeviceGroup {
  id?: number;
  name?: string | null;
  description?: string | null;
  devices?: IDevice[] | null;
}

export const defaultValue: Readonly<IDeviceGroup> = {};
