import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import organisation, {
  OrganisationState
} from 'app/entities/organisation/organisation.reducer';
// prettier-ignore
import userData, {
  UserDataState
} from 'app/entities/user-data/user-data.reducer';
// prettier-ignore
import deviceConfiguration, {
  DeviceConfigurationState
} from 'app/entities/device-configuration/device-configuration.reducer';
// prettier-ignore
import configurationData, {
  ConfigurationDataState
} from 'app/entities/configuration-data/configuration-data.reducer';
// prettier-ignore
import application, {
  ApplicationState
} from 'app/entities/application/application.reducer';
// prettier-ignore
import thingCategory, {
  ThingCategoryState
} from 'app/entities/thing-category/thing-category.reducer';
// prettier-ignore
import thing, {
  ThingState
} from 'app/entities/thing/thing.reducer';
// prettier-ignore
import deviceGroup, {
  DeviceGroupState
} from 'app/entities/device-group/device-group.reducer';
// prettier-ignore
import alertConfiguration, {
  AlertConfigurationState
} from 'app/entities/alert-configuration/alert-configuration.reducer';
// prettier-ignore
import telemetry, {
  TelemetryState
} from 'app/entities/telemetry/telemetry.reducer';
// prettier-ignore
import supplier, {
  SupplierState
} from 'app/entities/supplier/supplier.reducer';
// prettier-ignore
import device, {
  DeviceState
} from 'app/entities/device/device.reducer';
// prettier-ignore
import deviceModel, {
  DeviceModelState
} from 'app/entities/device-model/device-model.reducer';
// prettier-ignore
import location, {
  LocationState
} from 'app/entities/location/location.reducer';
// prettier-ignore
import rule, {
  RuleState
} from 'app/entities/rule/rule.reducer';
// prettier-ignore
import ruleConfiguration, {
  RuleConfigurationState
} from 'app/entities/rule-configuration/rule-configuration.reducer';
// prettier-ignore
import metadata, {
  MetadataState
} from 'app/entities/metadata/metadata.reducer';
// prettier-ignore
import alertMessage, {
  AlertMessageState
} from 'app/entities/alert-message/alert-message.reducer';
// prettier-ignore
import state, {
  StateState
} from 'app/entities/state/state.reducer';
// prettier-ignore
import status, {
  StatusState
} from 'app/entities/status/status.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly organisation: OrganisationState;
  readonly userData: UserDataState;
  readonly deviceConfiguration: DeviceConfigurationState;
  readonly configurationData: ConfigurationDataState;
  readonly application: ApplicationState;
  readonly thingCategory: ThingCategoryState;
  readonly thing: ThingState;
  readonly deviceGroup: DeviceGroupState;
  readonly alertConfiguration: AlertConfigurationState;
  readonly telemetry: TelemetryState;
  readonly supplier: SupplierState;
  readonly device: DeviceState;
  readonly deviceModel: DeviceModelState;
  readonly location: LocationState;
  readonly rule: RuleState;
  readonly ruleConfiguration: RuleConfigurationState;
  readonly metadata: MetadataState;
  readonly alertMessage: AlertMessageState;
  readonly state: StateState;
  readonly status: StatusState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  organisation,
  userData,
  deviceConfiguration,
  configurationData,
  application,
  thingCategory,
  thing,
  deviceGroup,
  alertConfiguration,
  telemetry,
  supplier,
  device,
  deviceModel,
  location,
  rule,
  ruleConfiguration,
  metadata,
  alertMessage,
  state,
  status,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
