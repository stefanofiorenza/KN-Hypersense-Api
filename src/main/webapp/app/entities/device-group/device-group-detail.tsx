import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './device-group.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeviceGroupDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DeviceGroupDetail = (props: IDeviceGroupDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { deviceGroupEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="deviceGroupDetailsHeading">DeviceGroup</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{deviceGroupEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{deviceGroupEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{deviceGroupEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/device-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/device-group/${deviceGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ deviceGroup }: IRootState) => ({
  deviceGroupEntity: deviceGroup.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DeviceGroupDetail);
