import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Hardware from './hardware';
import HardwareDetail from './hardware-detail';
import HardwareUpdate from './hardware-update';
import HardwareDeleteDialog from './hardware-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={HardwareUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={HardwareUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={HardwareDetail} />
      <ErrorBoundaryRoute path={match.url} component={Hardware} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={HardwareDeleteDialog} />
  </>
);

export default Routes;
