import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RuleConfiguration from './rule-configuration';
import RuleConfigurationDetail from './rule-configuration-detail';
import RuleConfigurationUpdate from './rule-configuration-update';
import RuleConfigurationDeleteDialog from './rule-configuration-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RuleConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RuleConfigurationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RuleConfigurationDetail} />
      <ErrorBoundaryRoute path={match.url} component={RuleConfiguration} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RuleConfigurationDeleteDialog} />
  </>
);

export default Routes;
