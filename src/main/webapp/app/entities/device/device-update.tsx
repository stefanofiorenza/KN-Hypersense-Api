import React, { useEffect, useState } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities as getTelemetries } from 'app/entities/telemetry/telemetry.reducer';
import { getEntities as getSuppliers } from 'app/entities/supplier/supplier.reducer';
import { getEntities as getDeviceModels } from 'app/entities/device-model/device-model.reducer';
import { getEntities as getThings } from 'app/entities/thing/thing.reducer';
import { getEntities as getDeviceGroups } from 'app/entities/device-group/device-group.reducer';
import { getEntities as getStatuses } from 'app/entities/status/status.reducer';
import { createEntity, getEntity, reset, updateEntity } from './device.reducer';

export interface IDeviceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const DeviceUpdate = (props: IDeviceUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { deviceEntity, telemetries, suppliers, deviceModels, things, deviceGroups, statuses, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/device');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTelemetries();
    props.getSuppliers();
    props.getDeviceModels();
    props.getThings();
    props.getDeviceGroups();
    props.getStatuses();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...deviceEntity,
        ...values,
        telemetry: telemetries.find(it => it.id.toString() === values.telemetryId.toString()),
        supplier: suppliers.find(it => it.id.toString() === values.supplierId.toString()),
        deviceModel: deviceModels.find(it => it.id.toString() === values.deviceModelId.toString()),
        thing: things.find(it => it.id.toString() === values.thingId.toString()),
        deviceGroup: deviceGroups.find(it => it.id.toString() === values.deviceGroupId.toString()),
        status: statuses.find(it => it.id.toString() === values.statusId.toString()),
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
          <h2 id="coreplatformApp.device.home.createOrEditLabel" data-cy="DeviceCreateUpdateHeading">
            Create or edit a Device
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : deviceEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="device-id">ID</Label>
                  <AvInput id="device-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="device-name">
                  Name
                </Label>
                <AvField id="device-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="serialNumberLabel" for="device-serialNumber">
                  Serial Number
                </Label>
                <AvField id="device-serialNumber" data-cy="serialNumber" type="text" name="serialNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="manufacturerLabel" for="device-manufacturer">
                  Manufacturer
                </Label>
                <AvField id="device-manufacturer" data-cy="manufacturer" type="text" name="manufacturer" />
              </AvGroup>
              <AvGroup>
                <Label for="device-telemetry">Telemetry</Label>
                <AvInput id="device-telemetry" data-cy="telemetry" type="select" className="form-control" name="telemetryId">
                  <option value="" key="0" />
                  {telemetries
                    ? telemetries.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="device-supplier">Supplier</Label>
                <AvInput id="device-supplier" data-cy="supplier" type="select" className="form-control" name="supplierId">
                  <option value="" key="0" />
                  {suppliers
                    ? suppliers.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="device-deviceModel">Device Model</Label>
                <AvInput id="device-deviceModel" data-cy="deviceModel" type="select" className="form-control" name="deviceModelId">
                  <option value="" key="0" />
                  {deviceModels
                    ? deviceModels.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="device-thing">Thing</Label>
                <AvInput id="device-thing" data-cy="thing" type="select" className="form-control" name="thingId">
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
              <AvGroup>
                <Label for="device-deviceGroup">Device Group</Label>
                <AvInput id="device-deviceGroup" data-cy="deviceGroup" type="select" className="form-control" name="deviceGroupId">
                  <option value="" key="0" />
                  {deviceGroups
                    ? deviceGroups.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="device-status">Status</Label>
                <AvInput id="device-status" data-cy="status" type="select" className="form-control" name="statusId">
                  <option value="" key="0" />
                  {statuses
                    ? statuses.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/device" replace color="info">
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
  telemetries: storeState.telemetry.entities,
  suppliers: storeState.supplier.entities,
  deviceModels: storeState.deviceModel.entities,
  things: storeState.thing.entities,
  deviceGroups: storeState.deviceGroup.entities,
  statuses: storeState.status.entities,
  deviceEntity: storeState.device.entity,
  loading: storeState.device.loading,
  updating: storeState.device.updating,
  updateSuccess: storeState.device.updateSuccess,
});

const mapDispatchToProps = {
  getTelemetries,
  getSuppliers,
  getDeviceModels,
  getThings,
  getDeviceGroups,
  getStatuses,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DeviceUpdate);
