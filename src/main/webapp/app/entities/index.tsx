import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Organisation from './organisation';
import UserData from './user-data';
import DeviceConfiguration from './device-configuration';
import ConfigurationData from './configuration-data';
import Application from './application';
import ThingCategory from './thing-category';
import Thing from './thing';
import DeviceGroup from './device-group';
import AlertConfiguration from './alert-configuration';
import Telemetry from './telemetry';
import Supplier from './supplier';
import Device from './device';
import DeviceModel from './device-model';
import Location from './location';
import Rule from './rule';
import RuleConfiguration from './rule-configuration';
import Metadata from './metadata';
import AlertMessage from './alert-message';
import State from './state';
import Status from './status';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}organisation`} component={Organisation} />
      <ErrorBoundaryRoute path={`${match.url}user-data`} component={UserData} />
      <ErrorBoundaryRoute path={`${match.url}device-configuration`} component={DeviceConfiguration} />
      <ErrorBoundaryRoute path={`${match.url}configuration-data`} component={ConfigurationData} />
      <ErrorBoundaryRoute path={`${match.url}application`} component={Application} />
      <ErrorBoundaryRoute path={`${match.url}thing-category`} component={ThingCategory} />
      <ErrorBoundaryRoute path={`${match.url}thing`} component={Thing} />
      <ErrorBoundaryRoute path={`${match.url}device-group`} component={DeviceGroup} />
      <ErrorBoundaryRoute path={`${match.url}alert-configuration`} component={AlertConfiguration} />
      <ErrorBoundaryRoute path={`${match.url}telemetry`} component={Telemetry} />
      <ErrorBoundaryRoute path={`${match.url}supplier`} component={Supplier} />
      <ErrorBoundaryRoute path={`${match.url}device`} component={Device} />
      <ErrorBoundaryRoute path={`${match.url}device-model`} component={DeviceModel} />
      <ErrorBoundaryRoute path={`${match.url}location`} component={Location} />
      <ErrorBoundaryRoute path={`${match.url}rule`} component={Rule} />
      <ErrorBoundaryRoute path={`${match.url}rule-configuration`} component={RuleConfiguration} />
      <ErrorBoundaryRoute path={`${match.url}metadata`} component={Metadata} />
      <ErrorBoundaryRoute path={`${match.url}alert-message`} component={AlertMessage} />
      <ErrorBoundaryRoute path={`${match.url}state`} component={State} />
      <ErrorBoundaryRoute path={`${match.url}status`} component={Status} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
