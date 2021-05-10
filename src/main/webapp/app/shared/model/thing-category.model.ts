import { IThing } from 'app/shared/model/thing.model';

export interface IThingCategory {
  id?: number;
  name?: string | null;
  description?: string | null;
  things?: IThing[] | null;
}

export const defaultValue: Readonly<IThingCategory> = {};
