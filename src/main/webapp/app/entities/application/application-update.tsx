import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IOrganisation } from 'app/shared/model/organisation.model';
import { getEntities as getOrganisations } from 'app/entities/organisation/organisation.reducer';
import { getEntity, updateEntity, createEntity, reset } from './application.reducer';
import { IApplication } from 'app/shared/model/application.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IApplicationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ApplicationUpdate = (props: IApplicationUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { applicationEntity, organisations, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/application');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

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
        ...applicationEntity,
        ...values,
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
          <h2 id="coreplatformApp.application.home.createOrEditLabel" data-cy="ApplicationCreateUpdateHeading">
            Create or edit a Application
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : applicationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="application-id">ID</Label>
                  <AvInput id="application-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="application-name">
                  Name
                </Label>
                <AvField id="application-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="application-description">
                  Description
                </Label>
                <AvField id="application-description" data-cy="description" type="text" name="description" />
              </AvGroup>
              <AvGroup check>
                <Label id="isAuthorizedLabel">
                  <AvInput
                    id="application-isAuthorized"
                    data-cy="isAuthorized"
                    type="checkbox"
                    className="form-check-input"
                    name="isAuthorized"
                  />
                  Is Authorized
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="application-organisation">Organisation</Label>
                <AvInput id="application-organisation" data-cy="organisation" type="select" className="form-control" name="organisationId">
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
              <Button tag={Link} id="cancel-save" to="/application" replace color="info">
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
  organisations: storeState.organisation.entities,
  applicationEntity: storeState.application.entity,
  loading: storeState.application.loading,
  updating: storeState.application.updating,
  updateSuccess: storeState.application.updateSuccess,
});

const mapDispatchToProps = {
  getOrganisations,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ApplicationUpdate);
