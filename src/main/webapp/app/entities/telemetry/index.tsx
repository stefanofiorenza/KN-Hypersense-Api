import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Telemetry from './telemetry';
import TelemetryDetail from './telemetry-detail';
import TelemetryUpdate from './telemetry-update';
import TelemetryDeleteDialog from './telemetry-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TelemetryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TelemetryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TelemetryDetail} />
      <ErrorBoundaryRoute path={match.url} component={Telemetry} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TelemetryDeleteDialog} />
  </>
);

export default Routes;
