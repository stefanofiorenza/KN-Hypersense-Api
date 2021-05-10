import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Thing from './thing';
import ThingDetail from './thing-detail';
import ThingUpdate from './thing-update';
import ThingDeleteDialog from './thing-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ThingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ThingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ThingDetail} />
      <ErrorBoundaryRoute path={match.url} component={Thing} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ThingDeleteDialog} />
  </>
);

export default Routes;
