import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Profile from './profile';
import ServiceCategory from './service-category';
import Address from './address';
import Review from './review';
import Payment from './payment';
import Service from './service';
import Hardware from './hardware';
import Training from './training';
import Software from './software';
import Services from './services';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}profile`} component={Profile} />
      <ErrorBoundaryRoute path={`${match.url}service-category`} component={ServiceCategory} />
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}review`} component={Review} />
      <ErrorBoundaryRoute path={`${match.url}payment`} component={Payment} />
      <ErrorBoundaryRoute path={`${match.url}service`} component={Service} />
      <ErrorBoundaryRoute path={`${match.url}hardware`} component={Hardware} />
      <ErrorBoundaryRoute path={`${match.url}training`} component={Training} />
      <ErrorBoundaryRoute path={`${match.url}software`} component={Software} />
      <ErrorBoundaryRoute path={`${match.url}services`} component={Services} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
