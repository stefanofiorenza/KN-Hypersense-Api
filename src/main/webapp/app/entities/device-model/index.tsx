import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DeviceModel from './device-model';
import DeviceModelDetail from './device-model-detail';
import DeviceModelUpdate from './device-model-update';
import DeviceModelDeleteDialog from './device-model-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DeviceModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DeviceModelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DeviceModelDetail} />
      <ErrorBoundaryRoute path={match.url} component={DeviceModel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DeviceModelDeleteDialog} />
  </>
);

export default Routes;
