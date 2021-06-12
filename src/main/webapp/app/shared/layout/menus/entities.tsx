import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/profile">
      <Translate contentKey="global.menu.entities.profile" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/service-category">
      <Translate contentKey="global.menu.entities.serviceCategory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/address">
      <Translate contentKey="global.menu.entities.address" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/review">
      <Translate contentKey="global.menu.entities.review" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/payment">
      <Translate contentKey="global.menu.entities.payment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/service">
      <Translate contentKey="global.menu.entities.service" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/hardware">
      <Translate contentKey="global.menu.entities.hardware" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/training">
      <Translate contentKey="global.menu.entities.training" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/software">
      <Translate contentKey="global.menu.entities.software" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/services">
      <Translate contentKey="global.menu.entities.services" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
