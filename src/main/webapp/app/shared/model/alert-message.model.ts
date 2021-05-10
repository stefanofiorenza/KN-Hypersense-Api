import { IAlertConfiguration } from 'app/shared/model/alert-configuration.model';
import { IDevice } from 'app/shared/model/device.model';

export interface IAlertMessage {
  id?: number;
  name?: string | null;
  description?: string | null;
  dataType?: string | null;
  value?: string | null;
  alertConfiguration?: IAlertConfiguration | null;
  device?: IDevice | null;
}

export const defaultValue: Readonly<IAlertMessage> = {};
