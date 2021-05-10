import { IApplication } from 'app/shared/model/application.model';
import { IUserData } from 'app/shared/model/user-data.model';

export interface IOrganisation {
  id?: number;
  name?: string | null;
  factories?: IApplication[] | null;
  userData?: IUserData[] | null;
}

export const defaultValue: Readonly<IOrganisation> = {};
