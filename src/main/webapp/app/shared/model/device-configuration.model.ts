import { IConfigurationData } from 'app/shared/model/configuration-data.model';
import { IDevice } from 'app/shared/model/device.model';
import { IUserData } from 'app/shared/model/user-data.model';

export interface IDeviceConfiguration {
  id?: number;
  uUID?: string | null;
  tokenContentType?: string | null;
  token?: string | null;
  configurationData?: IConfigurationData | null;
  device?: IDevice | null;
  userData?: IUserData | null;
}

export const defaultValue: Readonly<IDeviceConfiguration> = {};
