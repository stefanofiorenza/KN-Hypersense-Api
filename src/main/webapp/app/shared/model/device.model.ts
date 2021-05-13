import { ITelemetry } from 'app/shared/model/telemetry.model';
import { ISupplier } from 'app/shared/model/supplier.model';
import { IDeviceModel } from 'app/shared/model/device-model.model';
import { IRule } from 'app/shared/model/rule.model';
import { IAlertMessage } from 'app/shared/model/alert-message.model';
import { IMetadata } from 'app/shared/model/metadata.model';
import { IDeviceConfiguration } from 'app/shared/model/device-configuration.model';
import { IThing } from 'app/shared/model/thing.model';
import { IDeviceGroup } from 'app/shared/model/device-group.model';
import { IStatus } from 'app/shared/model/status.model';

export interface IDevice {
  id?: number;
  name?: string | null;
  serialNumber?: string | null;
  manufacturer?: string | null;
  telemetry?: ITelemetry | null;
  supplier?: ISupplier | null;
  deviceModel?: IDeviceModel | null;
  rules?: IRule[] | null;
  alertMessages?: IAlertMessage[] | null;
  metaData?: IMetadata[] | null;
  deviceConfigurations?: IDeviceConfiguration[] | null;
  thing?: IThing | null;
  deviceGroup?: IDeviceGroup | null;
  status?: IStatus | null;
}

export const defaultValue: Readonly<IDevice> = {};
