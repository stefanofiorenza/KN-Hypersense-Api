import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './alert-message.reducer';
import { IAlertMessage } from 'app/shared/model/alert-message.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAlertMessageProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const AlertMessage = (props: IAlertMessageProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { alertMessageList, match, loading } = props;
  return (
    <div>
      <h2 id="alert-message-heading" data-cy="AlertMessageHeading">
        Alert Messages
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Alert Message
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {alertMessageList && alertMessageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Data Type</th>
                <th>Value</th>
                <th>Alert Configuration</th>
                <th>Device</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {alertMessageList.map((alertMessage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${alertMessage.id}`} color="link" size="sm">
                      {alertMessage.id}
                    </Button>
                  </td>
                  <td>{alertMessage.name}</td>
                  <td>{alertMessage.description}</td>
                  <td>{alertMessage.dataType}</td>
                  <td>{alertMessage.value}</td>
                  <td>
                    {alertMessage.alertConfiguration ? (
                      <Link to={`alert-configuration/${alertMessage.alertConfiguration.id}`}>{alertMessage.alertConfiguration.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{alertMessage.device ? <Link to={`device/${alertMessage.device.id}`}>{alertMessage.device.id}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${alertMessage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${alertMessage.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${alertMessage.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Alert Messages found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ alertMessage }: IRootState) => ({
  alertMessageList: alertMessage.entities,
  loading: alertMessage.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AlertMessage);
