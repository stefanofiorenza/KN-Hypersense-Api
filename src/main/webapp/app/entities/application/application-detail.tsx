import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './application.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IApplicationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ApplicationDetail = (props: IApplicationDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { applicationEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="applicationDetailsHeading">Application</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{applicationEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{applicationEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{applicationEntity.description}</dd>
          <dt>
            <span id="isAuthorized">Is Authorized</span>
          </dt>
          <dd>{applicationEntity.isAuthorized ? 'true' : 'false'}</dd>
          <dt>Organisation</dt>
          <dd>{applicationEntity.organisation ? applicationEntity.organisation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/application" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/application/${applicationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ application }: IRootState) => ({
  applicationEntity: application.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ApplicationDetail);
