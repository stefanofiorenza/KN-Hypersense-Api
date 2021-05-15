export interface IState {
  id?: number;
  name?: string | null;
  description?: string | null;
  uUID?: string | null;
}

export const defaultValue: Readonly<IState> = {};
