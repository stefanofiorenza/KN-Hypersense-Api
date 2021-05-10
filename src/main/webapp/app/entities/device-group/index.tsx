import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DeviceGroup from './device-group';
import DeviceGroupDetail from './device-group-detail';
import DeviceGroupUpdate from './device-group-update';
import DeviceGroupDeleteDialog from './device-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DeviceGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DeviceGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DeviceGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={DeviceGroup} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DeviceGroupDeleteDialog} />
  </>
);

export default Routes;
