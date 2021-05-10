import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Rule from './rule';
import RuleDetail from './rule-detail';
import RuleUpdate from './rule-update';
import RuleDeleteDialog from './rule-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RuleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RuleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RuleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Rule} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RuleDeleteDialog} />
  </>
);

export default Routes;
