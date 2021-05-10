import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAlertConfiguration } from 'app/shared/model/alert-configuration.model';
import { getEntities as getAlertConfigurations } from 'app/entities/alert-configuration/alert-configuration.reducer';
import { IDevice } from 'app/shared/model/device.model';
import { getEntities as getDevices } from 'app/entities/device/device.reducer';
import { getEntity, updateEntity, createEntity, reset } from './alert-message.reducer';
import { IAlertMessage } from 'app/shared/model/alert-message.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAlertMessageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AlertMessageUpdate = (props: IAlertMessageUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { alertMessageEntity, alertConfigurations, devices, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/alert-message');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getAlertConfigurations();
    props.getDevices();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...alertMessageEntity,
        ...values,
        alertConfiguration: alertConfigurations.find(it => it.id.toString() === values.alertConfigurationId.toString()),
        device: devices.find(it => it.id.toString() === values.deviceId.toString()),
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
          <h2 id="coreplatformApp.alertMessage.home.createOrEditLabel" data-cy="AlertMessageCreateUpdateHeading">
            Create or edit a AlertMessage
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : alertMessageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="alert-message-id">ID</Label>
                  <AvInput id="alert-message-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="alert-message-name">
                  Name
                </Label>
                <AvField id="alert-message-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="alert-message-description">
                  Description
                </Label>
                <AvField id="alert-message-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="dataTypeLabel" for="alert-message-dataType">
                  Data Type
                </Label>
                <AvField id="alert-message-dataType" data-cy="dataType" type="text" name="dataType" />
              </AvGroup>
              <AvGroup>
                <Label id="valueLabel" for="alert-message-value">
                  Value
                </Label>
                <AvField id="alert-message-value" data-cy="value" type="text" name="value" />
              </AvGroup>
              <AvGroup>
                <Label for="alert-message-alertConfiguration">Alert Configuration</Label>
                <AvInput
                  id="alert-message-alertConfiguration"
                  data-cy="alertConfiguration"
                  type="select"
                  className="form-control"
                  name="alertConfigurationId"
                >
                  <option value="" key="0" />
                  {alertConfigurations
                    ? alertConfigurations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="alert-message-device">Device</Label>
                <AvInput id="alert-message-device" data-cy="device" type="select" className="form-control" name="deviceId">
                  <option value="" key="0" />
                  {devices
                    ? devices.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/alert-message" replace color="info">
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
  alertConfigurations: storeState.alertConfiguration.entities,
  devices: storeState.device.entities,
  alertMessageEntity: storeState.alertMessage.entity,
  loading: storeState.alertMessage.loading,
  updating: storeState.alertMessage.updating,
  updateSuccess: storeState.alertMessage.updateSuccess,
});

const mapDispatchToProps = {
  getAlertConfigurations,
  getDevices,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AlertMessageUpdate);
