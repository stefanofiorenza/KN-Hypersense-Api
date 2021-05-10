import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AlertConfiguration from './alert-configuration';
import AlertConfigurationDetail from './alert-configuration-detail';
import AlertConfigurationUpdate from './alert-configuration-update';
import AlertConfigurationDeleteDialog from './alert-configuration-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AlertConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AlertConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AlertConfigurationDetail} />
      <ErrorBoundaryRoute path={match.url} component={AlertConfiguration} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AlertConfigurationDeleteDialog} />
  </>
);

export default Routes;
