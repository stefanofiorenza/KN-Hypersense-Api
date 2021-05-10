import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './rule.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRuleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RuleDetail = (props: IRuleDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { ruleEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ruleDetailsHeading">Rule</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{ruleEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{ruleEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{ruleEntity.description}</dd>
          <dt>
            <span id="alias">Alias</span>
          </dt>
          <dd>{ruleEntity.alias}</dd>
          <dt>Device</dt>
          <dd>{ruleEntity.device ? ruleEntity.device.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/rule" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/rule/${ruleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ rule }: IRootState) => ({
  ruleEntity: rule.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RuleDetail);
