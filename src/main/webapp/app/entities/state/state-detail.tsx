import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './state.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IStateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StateDetail = (props: IStateDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { stateEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="stateDetailsHeading">State</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{stateEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{stateEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{stateEntity.description}</dd>
          <dt>
            <span id="uUID">U UID</span>
          </dt>
          <dd>{stateEntity.uUID}</dd>
          <dt>Thing</dt>
          <dd>{stateEntity.thing ? stateEntity.thing.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/state" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/state/${stateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ state }: IRootState) => ({
  stateEntity: state.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StateDetail);
