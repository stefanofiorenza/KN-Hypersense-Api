import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './alert-message.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAlertMessageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AlertMessageDetail = (props: IAlertMessageDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { alertMessageEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alertMessageDetailsHeading">AlertMessage</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{alertMessageEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{alertMessageEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{alertMessageEntity.description}</dd>
          <dt>
            <span id="dataType">Data Type</span>
          </dt>
          <dd>{alertMessageEntity.dataType}</dd>
          <dt>
            <span id="value">Value</span>
          </dt>
          <dd>{alertMessageEntity.value}</dd>
          <dt>Alert Configuration</dt>
          <dd>{alertMessageEntity.alertConfiguration ? alertMessageEntity.alertConfiguration.id : ''}</dd>
          <dt>Device</dt>
          <dd>{alertMessageEntity.device ? alertMessageEntity.device.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/alert-message" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alert-message/${alertMessageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ alertMessage }: IRootState) => ({
  alertMessageEntity: alertMessage.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AlertMessageDetail);
