import { IRule } from 'app/shared/model/rule.model';

export interface IRuleConfiguration {
  id?: number;
  rule?: IRule | null;
}

export const defaultValue: Readonly<IRuleConfiguration> = {};
