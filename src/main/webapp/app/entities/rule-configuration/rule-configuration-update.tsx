import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IRule } from 'app/shared/model/rule.model';
import { getEntities as getRules } from 'app/entities/rule/rule.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rule-configuration.reducer';
import { IRuleConfiguration } from 'app/shared/model/rule-configuration.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRuleConfigurationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RuleConfigurationUpdate = (props: IRuleConfigurationUpdateProps) => {
  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const { ruleConfigurationEntity, rules, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/rule-configuration');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getRules();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...ruleConfigurationEntity,
        ...values,
        rule: rules.find(it => it.id.toString() === values.ruleId.toString()),
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
          <h2 id="coreplatformApp.ruleConfiguration.home.createOrEditLabel" data-cy="RuleConfigurationCreateUpdateHeading">
            Create or edit a RuleConfiguration
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : ruleConfigurationEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="rule-configuration-id">ID</Label>
                  <AvInput id="rule-configuration-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label for="rule-configuration-rule">Rule</Label>
                <AvInput id="rule-configuration-rule" data-cy="rule" type="select" className="form-control" name="ruleId">
                  <option value="" key="0" />
                  {rules
                    ? rules.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/rule-configuration" replace color="info">
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
  rules: storeState.rule.entities,
  ruleConfigurationEntity: storeState.ruleConfiguration.entity,
  loading: storeState.ruleConfiguration.loading,
  updating: storeState.ruleConfiguration.updating,
  updateSuccess: storeState.ruleConfiguration.updateSuccess,
});

const mapDispatchToProps = {
  getRules,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RuleConfigurationUpdate);
