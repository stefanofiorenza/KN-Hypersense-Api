import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConfigurationData from './configuration-data';
import ConfigurationDataDetail from './configuration-data-detail';
import ConfigurationDataUpdate from './configuration-data-update';
import ConfigurationDataDeleteDialog from './configuration-data-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConfigurationDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConfigurationDataUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConfigurationDataDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConfigurationData} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConfigurationDataDeleteDialog} />
  </>
);

export default Routes;
