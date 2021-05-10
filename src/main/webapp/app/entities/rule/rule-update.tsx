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
import { getEntity, updateEntity, createEntity, reset } from './rule.reducer';
import { IRule } from 'app/shared/model/rule.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRuleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RuleUpdate = (props: IRuleUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { ruleEntity, devices, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/rule');
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
        ...ruleEntity,
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
          <h2 id="coreplatformApp.rule.home.createOrEditLabel" data-cy="RuleCreateUpdateHeading">
            Create or edit a Rule
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : ruleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="rule-id">ID</Label>
                  <AvInput id="rule-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="rule-name">
                  Name
                </Label>
                <AvField id="rule-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="rule-description">
                  Description
                </Label>
                <AvField id="rule-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="aliasLabel" for="rule-alias">
                  Alias
                </Label>
                <AvField id="rule-alias" data-cy="alias" type="text" name="alias" />
              </AvGroup>
              <AvGroup>
                <Label for="rule-device">Device</Label>
                <AvInput id="rule-device" data-cy="device" type="select" className="form-control" name="deviceId">
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
              <Button tag={Link} id="cancel-save" to="/rule" replace color="info">
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
  ruleEntity: storeState.rule.entity,
  loading: storeState.rule.loading,
  updating: storeState.rule.updating,
  updateSuccess: storeState.rule.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(RuleUpdate);
