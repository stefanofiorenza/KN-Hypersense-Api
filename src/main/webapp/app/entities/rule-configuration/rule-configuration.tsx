import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './rule-configuration.reducer';
import { IRuleConfiguration } from 'app/shared/model/rule-configuration.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRuleConfigurationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const RuleConfiguration = (props: IRuleConfigurationProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { ruleConfigurationList, match, loading } = props;
  return (
    <div>
      <h2 id="rule-configuration-heading" data-cy="RuleConfigurationHeading">
        Rule Configurations
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Rule Configuration
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ruleConfigurationList && ruleConfigurationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Rule</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ruleConfigurationList.map((ruleConfiguration, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${ruleConfiguration.id}`} color="link" size="sm">
                      {ruleConfiguration.id}
                    </Button>
                  </td>
                  <td>{ruleConfiguration.rule ? <Link to={`rule/${ruleConfiguration.rule.id}`}>{ruleConfiguration.rule.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${ruleConfiguration.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${ruleConfiguration.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${ruleConfiguration.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Rule Configurations found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ ruleConfiguration }: IRootState) => ({
  ruleConfigurationList: ruleConfiguration.entities,
  loading: ruleConfiguration.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RuleConfiguration);
