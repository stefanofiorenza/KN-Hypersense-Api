import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IDevice } from 'app/shared/model/device.model';
import { getEntities as getDevices } from 'app/entities/device/device.reducer';
import { getEntity, updateEntity, createEntity, reset } from './metadata.reducer';
import { IMetadata } from 'app/shared/model/metadata.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMetadataUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MetadataUpdate = (props: IMetadataUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { metadataEntity, devices, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/metadata');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...metadataEntity,
        ...values,
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
          <h2 id="coreplatformApp.metadata.home.createOrEditLabel" data-cy="MetadataCreateUpdateHeading">
            Create or edit a Metadata
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : metadataEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="metadata-id">ID</Label>
                  <AvInput id="metadata-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="metadata-name">
                  Name
                </Label>
                <AvField id="metadata-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="dataLabel" for="metadata-data">
                  Data
                </Label>
                <AvField id="metadata-data" data-cy="data" type="text" name="data" />
              </AvGroup>
              <AvGroup>
                <Label for="metadata-device">Device</Label>
                <AvInput id="metadata-device" data-cy="device" type="select" className="form-control" name="deviceId">
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
              <Button tag={Link} id="cancel-save" to="/metadata" replace color="info">
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
  devices: storeState.device.entities,
  metadataEntity: storeState.metadata.entity,
  loading: storeState.metadata.loading,
  updating: storeState.metadata.updating,
  updateSuccess: storeState.metadata.updateSuccess,
});

const mapDispatchToProps = {
  getDevices,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MetadataUpdate);
