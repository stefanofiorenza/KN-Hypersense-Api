import { IThing } from 'app/shared/model/thing.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IApplication {
  id?: number;
  name?: string | null;
  description?: string | null;
  isAuthorized?: boolean | null;
  things?: IThing[] | null;
  organisation?: IOrganisation | null;
}

export const defaultValue: Readonly<IApplication> = {
  isAuthorized: false,
};
