import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceCategory from './service-category';
import ServiceCategoryDetail from './service-category-detail';
import ServiceCategoryUpdate from './service-category-update';
import ServiceCategoryDeleteDialog from './service-category-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceCategoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceCategoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceCategory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ServiceCategoryDeleteDialog} />
  </>
);

export default Routes;
