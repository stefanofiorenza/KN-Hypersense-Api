import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './alert-configuration.reducer';
import { IAlertConfiguration } from 'app/shared/model/alert-configuration.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAlertConfigurationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AlertConfigurationUpdate = (props: IAlertConfigurationUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { alertConfigurationEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/alert-configuration');
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
        ...alertConfigurationEntity,
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
          <h2 id="coreplatformApp.alertConfiguration.home.createOrEditLabel" data-cy="AlertConfigurationCreateUpdateHeading">
            Create or edit a AlertConfiguration
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : alertConfigurationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="alert-configuration-id">ID</Label>
                  <AvInput id="alert-configuration-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="alert-configuration-name">
                  Name
                </Label>
                <AvField id="alert-configuration-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="alert-configuration-description">
                  Description
                </Label>
                <AvField id="alert-configuration-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="configurationLabel" for="alert-configuration-configuration">
                  Configuration
                </Label>
                <AvField id="alert-configuration-configuration" data-cy="configuration" type="text" name="configuration" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/alert-configuration" replace color="info">
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
  alertConfigurationEntity: storeState.alertConfiguration.entity,
  loading: storeState.alertConfiguration.loading,
  updating: storeState.alertConfiguration.updating,
  updateSuccess: storeState.alertConfiguration.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AlertConfigurationUpdate);
