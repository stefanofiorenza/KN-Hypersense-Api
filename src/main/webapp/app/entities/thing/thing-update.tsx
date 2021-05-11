import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ILocation } from 'app/shared/model/location.model';
import { getEntities as getLocations } from 'app/entities/location/location.reducer';
import { IThingCategory } from 'app/shared/model/thing-category.model';
import { getEntities as getThingCategories } from 'app/entities/thing-category/thing-category.reducer';
import { IApplication } from 'app/shared/model/application.model';
import { getEntities as getApplications } from 'app/entities/application/application.reducer';
import { getEntity, updateEntity, createEntity, reset } from './thing.reducer';
import { IThing } from 'app/shared/model/thing.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IThingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ThingUpdate = (props: IThingUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { thingEntity, locations, thingCategories, applications, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/thing');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getLocations();
    props.getThingCategories();
    props.getApplications();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...thingEntity,
        ...values,
        location: locations.find(it => it.id.toString() === values.locationId.toString()),
        thingCategory: thingCategories.find(it => it.id.toString() === values.thingCategoryId.toString()),
        application: applications.find(it => it.id.toString() === values.applicationId.toString()),
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
          <h2 id="coreplatformApp.thing.home.createOrEditLabel" data-cy="ThingCreateUpdateHeading">
            Create or edit a Thing
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : thingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="thing-id">ID</Label>
                  <AvInput id="thing-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="thing-name">
                  Name
                </Label>
                <AvField id="thing-name" data-cy="name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="uUIDLabel" for="thing-uUID">
                  U UID
                </Label>
                <AvField id="thing-uUID" data-cy="uUID" type="text" name="uUID" />
              </AvGroup>
              <AvGroup>
                <Label for="thing-location">Location</Label>
                <AvInput id="thing-location" data-cy="location" type="select" className="form-control" name="locationId">
                  <option value="" key="0" />
                  {locations
                    ? locations.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="thing-thingCategory">Thing Category</Label>
                <AvInput id="thing-thingCategory" data-cy="thingCategory" type="select" className="form-control" name="thingCategoryId">
                  <option value="" key="0" />
                  {thingCategories
                    ? thingCategories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="thing-application">Application</Label>
                <AvInput id="thing-application" data-cy="application" type="select" className="form-control" name="applicationId">
                  <option value="" key="0" />
                  {applications
                    ? applications.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/thing" replace color="info">
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
  locations: storeState.location.entities,
  thingCategories: storeState.thingCategory.entities,
  applications: storeState.application.entities,
  thingEntity: storeState.thing.entity,
  loading: storeState.thing.loading,
  updating: storeState.thing.updating,
  updateSuccess: storeState.thing.updateSuccess,
});

const mapDispatchToProps = {
  getLocations,
  getThingCategories,
  getApplications,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ThingUpdate);
