import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ThingCategory from './thing-category';
import ThingCategoryDetail from './thing-category-detail';
import ThingCategoryUpdate from './thing-category-update';
import ThingCategoryDeleteDialog from './thing-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ThingCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ThingCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ThingCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={ThingCategory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ThingCategoryDeleteDialog} />
  </>
);

export default Routes;
