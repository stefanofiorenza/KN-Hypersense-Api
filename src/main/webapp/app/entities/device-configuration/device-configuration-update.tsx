import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { setFileData, openFile, byteSize, translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IConfigurationData } from 'app/shared/model/configuration-data.model';
import { getEntities as getConfigurationData } from 'app/entities/configuration-data/configuration-data.reducer';
import { IDevice } from 'app/shared/model/device.model';
import { getEntities as getDevices } from 'app/entities/device/device.reducer';
import { IUserData } from 'app/shared/model/user-data.model';
import { getEntities as getUserData } from 'app/entities/user-data/user-data.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './device-configuration.reducer';
import { IDeviceConfiguration } from 'app/shared/model/device-configuration.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IDeviceConfigurationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DeviceConfigurationUpdate = (props: IDeviceConfigurationUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { deviceConfigurationEntity, configurationData, devices, userData, loading, updating } = props;

  const { token, tokenContentType } = deviceConfigurationEntity;

  const handleClose = () => {
    props.history.push('/device-configuration');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getConfigurationData();
    props.getDevices();
    props.getUserData();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...deviceConfigurationEntity,
        ...values,
        configurationData: configurationData.find(it => it.id.toString() === values.configurationDataId.toString()),
        device: devices.find(it => it.id.toString() === values.deviceId.toString()),
        userData: userData.find(it => it.id.toString() === values.userDataId.toString()),
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
          <h2 id="coreplatformApp.deviceConfiguration.home.createOrEditLabel" data-cy="DeviceConfigurationCreateUpdateHeading">
            Create or edit a DeviceConfiguration
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : deviceConfigurationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="device-configuration-id">ID</Label>
                  <AvInput id="device-configuration-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="uUIDLabel" for="device-configuration-uUID">
                  U UID
                </Label>
                <AvField id="device-configuration-uUID" data-cy="uUID" type="text" name="uUID" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="tokenLabel" for="token">
                    Token
                  </Label>
                  <br />
                  {token ? (
                    <div>
                      {tokenContentType ? <a onClick={openFile(tokenContentType, token)}>Open</a> : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {tokenContentType}, {byteSize(token)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('token')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_token" data-cy="token" type="file" onChange={onBlobChange(false, 'token')} />
                  <AvInput type="hidden" name="token" value={token} />
                </AvGroup>
              </AvGroup>
              <AvGroup>
                <Label for="device-configuration-configurationData">Configuration Data</Label>
                <AvInput
                  id="device-configuration-configurationData"
                  data-cy="configurationData"
                  type="select"
                  className="form-control"
                  name="configurationDataId"
                >
                  <option value="" key="0" />
                  {configurationData
                    ? configurationData.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="device-configuration-device">Device</Label>
                <AvInput id="device-configuration-device" data-cy="device" type="select" className="form-control" name="deviceId">
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
              <AvGroup>
                <Label for="device-configuration-userData">User Data</Label>
                <AvInput id="device-configuration-userData" data-cy="userData" type="select" className="form-control" name="userDataId">
                  <option value="" key="0" />
                  {userData
                    ? userData.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/device-configuration" replace color="info">
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
  configurationData: storeState.configurationData.entities,
  devices: storeState.device.entities,
  userData: storeState.userData.entities,
  deviceConfigurationEntity: storeState.deviceConfiguration.entity,
  loading: storeState.deviceConfiguration.loading,
  updating: storeState.deviceConfiguration.updating,
  updateSuccess: storeState.deviceConfiguration.updateSuccess,
});

const mapDispatchToProps = {
  getConfigurationData,
  getDevices,
  getUserData,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DeviceConfigurationUpdate);
