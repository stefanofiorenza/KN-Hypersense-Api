import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AlertMessage from './alert-message';
import AlertMessageDetail from './alert-message-detail';
import AlertMessageUpdate from './alert-message-update';
import AlertMessageDeleteDialog from './alert-message-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AlertMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AlertMessageUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AlertMessageDetail} />
      <ErrorBoundaryRoute path={match.url} component={AlertMessage} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AlertMessageDeleteDialog} />
  </>
);

export default Routes;
