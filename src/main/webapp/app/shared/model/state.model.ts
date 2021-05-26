import { IThing } from 'app/shared/model/thing.model';

export interface IState {
  id?: number;
  name?: string | null;
  description?: string | null;
  uUID?: string | null;
  thing?: IThing | null;
}

export const defaultValue: Readonly<IState> = {};
