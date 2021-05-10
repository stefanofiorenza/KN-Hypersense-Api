import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Supplier from './supplier';
import SupplierDetail from './supplier-detail';
import SupplierUpdate from './supplier-update';
import SupplierDeleteDialog from './supplier-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SupplierUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SupplierUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SupplierDetail} />
      <ErrorBoundaryRoute path={match.url} component={Supplier} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SupplierDeleteDialog} />
  </>
);

export default Routes;
