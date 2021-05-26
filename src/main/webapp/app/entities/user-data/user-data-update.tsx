import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-data.reducer';
import { IUserData } from 'app/shared/model/user-data.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserDataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserDataUpdate = (props: IUserDataUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { userDataEntity, users, organisations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/user-data');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getOrganisations();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...userDataEntity,
        ...values,
        internalUser: users.find(it => it.id.toString() === values.internalUserId.toString()),
        organisation: organisations.find(it => it.id.toString() === values.organisationId.toString()),
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="coreplatformApp.userData.home.createOrEditLabel" data-cy="UserDataCreateUpdateHeading">
            Create or edit a UserData
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : userDataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="user-data-id">ID</Label>
                  <AvInput id="user-data-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="uuidLabel" for="user-data-uuid">
                  Uuid
                </Label>
                <AvField id="user-data-uuid" data-cy="uuid" type="text" name="uuid" />
              </AvGroup>
              <AvGroup>
                <Label for="user-data-internalUser">Internal User</Label>
                <AvInput id="user-data-internalUser" data-cy="internalUser" type="select" className="form-control" name="internalUserId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="user-data-organisation">Organisation</Label>
                <AvInput id="user-data-organisation" data-cy="organisation" type="select" className="form-control" name="organisationId">
                  <option value="" key="0" />
                  {organisations
                    ? organisations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/user-data" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  organisations: storeState.organisation.entities,
  userDataEntity: storeState.userData.entity,
  loading: storeState.userData.loading,
  updating: storeState.userData.updating,
  updateSuccess: storeState.userData.updateSuccess,
});

const mapDispatchToProps = {
  getUsers,
  getOrganisations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserDataUpdate);
