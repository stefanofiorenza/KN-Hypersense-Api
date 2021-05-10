import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './telemetry.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITelemetryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TelemetryDetail = (props: ITelemetryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { telemetryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="telemetryDetailsHeading">Telemetry</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{telemetryEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{telemetryEntity.name}</dd>
          <dt>
            <span id="data">Data</span>
          </dt>
          <dd>{telemetryEntity.data}</dd>
        </dl>
        <Button tag={Link} to="/telemetry" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/telemetry/${telemetryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ telemetry }: IRootState) => ({
  telemetryEntity: telemetry.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TelemetryDetail);
