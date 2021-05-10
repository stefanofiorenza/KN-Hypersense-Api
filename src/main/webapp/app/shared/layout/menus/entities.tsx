import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" data-cy="entity" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/organisation">
      Organisation
    </MenuItem>
    <MenuItem icon="asterisk" to="/user-data">
      User Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/device-configuration">
      Device Configuration
    </MenuItem>
    <MenuItem icon="asterisk" to="/configuration-data">
      Configuration Data
    </MenuItem>
    <MenuItem icon="asterisk" to="/application">
      Application
    </MenuItem>
    <MenuItem icon="asterisk" to="/thing-category">
      Thing Category
    </MenuItem>
    <MenuItem icon="asterisk" to="/thing">
      Thing
    </MenuItem>
    <MenuItem icon="asterisk" to="/device-group">
      Device Group
    </MenuItem>
    <MenuItem icon="asterisk" to="/alert-configuration">
      Alert Configuration
    </MenuItem>
    <MenuItem icon="asterisk" to="/telemetry">
      Telemetry
    </MenuItem>
    <MenuItem icon="asterisk" to="/supplier">
      Supplier
    </MenuItem>
    <MenuItem icon="asterisk" to="/device">
      Device
    </MenuItem>
    <MenuItem icon="asterisk" to="/device-model">
      Device Model
    </MenuItem>
    <MenuItem icon="asterisk" to="/location">
      Location
    </MenuItem>
    <MenuItem icon="asterisk" to="/rule">
      Rule
    </MenuItem>
    <MenuItem icon="asterisk" to="/rule-configuration">
      Rule Configuration
    </MenuItem>
    <MenuItem icon="asterisk" to="/metadata">
      Metadata
    </MenuItem>
    <MenuItem icon="asterisk" to="/alert-message">
      Alert Message
    </MenuItem>
    <MenuItem icon="asterisk" to="/state">
      State
    </MenuItem>
    <MenuItem icon="asterisk" to="/status">
      Status
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
