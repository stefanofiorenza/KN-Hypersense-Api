import { IConfigurationData } from 'app/shared/model/configuration-data.model';
import { IUserData } from 'app/shared/model/user-data.model';

export interface IDeviceConfiguration {
  id?: number;
  name?: string | null;
  uUID?: string | null;
  tokenContentType?: string | null;
  token?: string | null;
  configurationData?: IConfigurationData | null;
  userData?: IUserData | null;
}

export const defaultValue: Readonly<IDeviceConfiguration> = {};
