import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './device-configuration.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeviceConfigurationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DeviceConfigurationDetail = (props: IDeviceConfigurationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { deviceConfigurationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="deviceConfigurationDetailsHeading">DeviceConfiguration</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{deviceConfigurationEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{deviceConfigurationEntity.name}</dd>
          <dt>
            <span id="uUID">U UID</span>
          </dt>
          <dd>{deviceConfigurationEntity.uUID}</dd>
          <dt>
            <span id="token">Token</span>
          </dt>
          <dd>
            {deviceConfigurationEntity.token ? (
              <div>
                {deviceConfigurationEntity.tokenContentType ? (
                  <a onClick={openFile(deviceConfigurationEntity.tokenContentType, deviceConfigurationEntity.token)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {deviceConfigurationEntity.tokenContentType}, {byteSize(deviceConfigurationEntity.token)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Configuration Data</dt>
          <dd>{deviceConfigurationEntity.configurationData ? deviceConfigurationEntity.configurationData.id : ''}</dd>
          <dt>User Data</dt>
          <dd>{deviceConfigurationEntity.userData ? deviceConfigurationEntity.userData.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/device-configuration" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/device-configuration/${deviceConfigurationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ deviceConfiguration }: IRootState) => ({
  deviceConfigurationEntity: deviceConfiguration.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DeviceConfigurationDetail);
