import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DeviceConfiguration from './device-configuration';
import DeviceConfigurationDetail from './device-configuration-detail';
import DeviceConfigurationUpdate from './device-configuration-update';
import DeviceConfigurationDeleteDialog from './device-configuration-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DeviceConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DeviceConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DeviceConfigurationDetail} />
      <ErrorBoundaryRoute path={match.url} component={DeviceConfiguration} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DeviceConfigurationDeleteDialog} />
  </>
);

export default Routes;
