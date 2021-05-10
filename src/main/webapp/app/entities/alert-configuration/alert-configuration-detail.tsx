import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './alert-configuration.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAlertConfigurationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AlertConfigurationDetail = (props: IAlertConfigurationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { alertConfigurationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="alertConfigurationDetailsHeading">AlertConfiguration</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{alertConfigurationEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{alertConfigurationEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{alertConfigurationEntity.description}</dd>
          <dt>
            <span id="configuration">Configuration</span>
          </dt>
          <dd>{alertConfigurationEntity.configuration}</dd>
        </dl>
        <Button tag={Link} to="/alert-configuration" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/alert-configuration/${alertConfigurationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ alertConfiguration }: IRootState) => ({
  alertConfigurationEntity: alertConfiguration.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AlertConfigurationDetail);
