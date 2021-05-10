import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IThing } from 'app/shared/model/thing.model';
import { getEntities as getThings } from 'app/entities/thing/thing.reducer';
import { getEntity, updateEntity, createEntity, reset } from './state.reducer';
import { IState } from 'app/shared/model/state.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IStateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const StateUpdate = (props: IStateUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { stateEntity, things, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/state');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getThings();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...stateEntity,
        ...values,
        thing: things.find(it => it.id.toString() === values.thingId.toString()),
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
          <h2 id="coreplatformApp.state.home.createOrEditLabel" data-cy="StateCreateUpdateHeading">
            Create or edit a State
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : stateEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="state-id">ID</Label>
                  <AvInput id="state-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="state-name">
                  Name
                </Label>
                <AvField id="state-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="state-description">
                  Description
                </Label>
                <AvField id="state-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="uUIDLabel" for="state-uUID">
                  U UID
                </Label>
                <AvField id="state-uUID" data-cy="uUID" type="text" name="uUID" />
              </AvGroup>
              <AvGroup>
                <Label for="state-thing">Thing</Label>
                <AvInput id="state-thing" data-cy="thing" type="select" className="form-control" name="thingId">
                  <option value="" key="0" />
                  {things
                    ? things.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/state" replace color="info">
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
  things: storeState.thing.entities,
  stateEntity: storeState.state.entity,
  loading: storeState.state.loading,
  updating: storeState.state.updating,
  updateSuccess: storeState.state.updateSuccess,
});

const mapDispatchToProps = {
  getThings,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(StateUpdate);
