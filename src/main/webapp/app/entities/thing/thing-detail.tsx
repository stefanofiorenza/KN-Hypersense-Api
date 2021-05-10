import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './thing.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IThingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ThingDetail = (props: IThingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { thingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="thingDetailsHeading">Thing</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{thingEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{thingEntity.name}</dd>
          <dt>Location</dt>
          <dd>{thingEntity.location ? thingEntity.location.id : ''}</dd>
          <dt>Thing Category</dt>
          <dd>{thingEntity.thingCategory ? thingEntity.thingCategory.id : ''}</dd>
          <dt>Application</dt>
          <dd>{thingEntity.application ? thingEntity.application.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/thing" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/thing/${thingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ thing }: IRootState) => ({
  thingEntity: thing.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ThingDetail);
