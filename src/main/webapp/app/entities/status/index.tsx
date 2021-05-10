import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Status from './status';
import StatusDetail from './status-detail';
import StatusUpdate from './status-update';
import StatusDeleteDialog from './status-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={StatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={StatusUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={StatusDetail} />
      <ErrorBoundaryRoute path={match.url} component={Status} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={StatusDeleteDialog} />
  </>
);

export default Routes;
