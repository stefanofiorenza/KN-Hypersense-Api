import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-data.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserDataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserDataDetail = (props: IUserDataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userDataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="userDataDetailsHeading">UserData</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{userDataEntity.id}</dd>
          <dt>
            <span id="uuid">Uuid</span>
          </dt>
          <dd>{userDataEntity.uuid}</dd>
          <dt>Internal User</dt>
          <dd>{userDataEntity.internalUser ? userDataEntity.internalUser.id : ''}</dd>
          <dt>Organisation</dt>
          <dd>{userDataEntity.organisation ? userDataEntity.organisation.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-data/${userDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userData }: IRootState) => ({
  userDataEntity: userData.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserDataDetail);
