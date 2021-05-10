import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Application from './application';
import ApplicationDetail from './application-detail';
import ApplicationUpdate from './application-update';
import ApplicationDeleteDialog from './application-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ApplicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ApplicationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ApplicationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Application} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ApplicationDeleteDialog} />
  </>
);

export default Routes;
