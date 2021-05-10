import { IDevice } from 'app/shared/model/device.model';

export interface IMetadata {
  id?: number;
  name?: string | null;
  data?: string | null;
  device?: IDevice | null;
}

export const defaultValue: Readonly<IMetadata> = {};
