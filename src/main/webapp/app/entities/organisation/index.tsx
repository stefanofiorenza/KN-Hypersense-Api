import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Organisation from './organisation';
import OrganisationDetail from './organisation-detail';
import OrganisationUpdate from './organisation-update';
import OrganisationDeleteDialog from './organisation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={OrganisationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={OrganisationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={OrganisationDetail} />
      <ErrorBoundaryRoute path={match.url} component={Organisation} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={OrganisationDeleteDialog} />
  </>
);

export default Routes;
