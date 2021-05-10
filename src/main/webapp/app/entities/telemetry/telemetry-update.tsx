import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './telemetry.reducer';
import { ITelemetry } from 'app/shared/model/telemetry.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITelemetryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TelemetryUpdate = (props: ITelemetryUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { telemetryEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/telemetry');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...telemetryEntity,
        ...values,
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
          <h2 id="coreplatformApp.telemetry.home.createOrEditLabel" data-cy="TelemetryCreateUpdateHeading">
            Create or edit a Telemetry
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : telemetryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="telemetry-id">ID</Label>
                  <AvInput id="telemetry-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="telemetry-name">
                  Name
                </Label>
                <AvField id="telemetry-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="dataLabel" for="telemetry-data">
                  Data
                </Label>
                <AvField id="telemetry-data" data-cy="data" type="text" name="data" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/telemetry" replace color="info">
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
  telemetryEntity: storeState.telemetry.entity,
  loading: storeState.telemetry.loading,
  updating: storeState.telemetry.updating,
  updateSuccess: storeState.telemetry.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TelemetryUpdate);
