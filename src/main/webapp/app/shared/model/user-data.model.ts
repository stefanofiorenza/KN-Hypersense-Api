import { IUser } from 'app/shared/model/user.model';
import { IDeviceConfiguration } from 'app/shared/model/device-configuration.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IUserData {
  id?: number;
  uuid?: string | null;
  internalUser?: IUser | null;
  deviceConfigurations?: IDeviceConfiguration[] | null;
  organisation?: IOrganisation | null;
}

export const defaultValue: Readonly<IUserData> = {};
