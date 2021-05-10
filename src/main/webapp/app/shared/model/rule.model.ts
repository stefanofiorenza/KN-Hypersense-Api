import { IRuleConfiguration } from 'app/shared/model/rule-configuration.model';
import { IDevice } from 'app/shared/model/device.model';

export interface IRule {
  id?: number;
  name?: string | null;
  description?: string | null;
  alias?: string | null;
  ruleConfigurations?: IRuleConfiguration[] | null;
  device?: IDevice | null;
}

export const defaultValue: Readonly<IRule> = {};
