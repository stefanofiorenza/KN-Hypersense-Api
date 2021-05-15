import { ILocation } from 'app/shared/model/location.model';
import { IDevice } from 'app/shared/model/device.model';
import { IThingCategory } from 'app/shared/model/thing-category.model';
import { IApplication } from 'app/shared/model/application.model';
import { IState } from 'app/shared/model/state.model';

export interface IThing {
  id?: number;
  name?: string | null;
  uUID?: string | null;
  location?: ILocation | null;
  devices?: IDevice[] | null;
  thingCategory?: IThingCategory | null;
  application?: IApplication | null;
  states?: IState[] | null;
}

export const defaultValue: Readonly<IThing> = {};
