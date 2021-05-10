import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './device-configuration.reducer';
import { IDeviceConfiguration } from 'app/shared/model/device-configuration.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IDeviceConfigurationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const DeviceConfiguration = (props: IDeviceConfigurationProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { deviceConfigurationList, match, loading } = props;
  return (
    <div>
      <h2 id="device-configuration-heading" data-cy="DeviceConfigurationHeading">
        Device Configurations
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Device Configuration
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {deviceConfigurationList && deviceConfigurationList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>U UID</th>
                <th>Token</th>
                <th>Configuration Data</th>
                <th>Device</th>
                <th>User Data</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {deviceConfigurationList.map((deviceConfiguration, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${deviceConfiguration.id}`} color="link" size="sm">
                      {deviceConfiguration.id}
                    </Button>
                  </td>
                  <td>{deviceConfiguration.uUID}</td>
                  <td>
                    {deviceConfiguration.token ? (
                      <div>
                        {deviceConfiguration.tokenContentType ? (
                          <a onClick={openFile(deviceConfiguration.tokenContentType, deviceConfiguration.token)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {deviceConfiguration.tokenContentType}, {byteSize(deviceConfiguration.token)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {deviceConfiguration.configurationData ? (
                      <Link to={`configuration-data/${deviceConfiguration.configurationData.id}`}>
                        {deviceConfiguration.configurationData.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {deviceConfiguration.device ? (
                      <Link to={`device/${deviceConfiguration.device.id}`}>{deviceConfiguration.device.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {deviceConfiguration.userData ? (
                      <Link to={`user-data/${deviceConfiguration.userData.id}`}>{deviceConfiguration.userData.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${deviceConfiguration.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${deviceConfiguration.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${deviceConfiguration.id}/delete`}
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
          !loading && <div className="alert alert-warning">No Device Configurations found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ deviceConfiguration }: IRootState) => ({
  deviceConfigurationList: deviceConfiguration.entities,
  loading: deviceConfiguration.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(DeviceConfiguration);
