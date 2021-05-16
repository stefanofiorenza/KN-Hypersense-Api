import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './thing.reducer';
import { IThing } from 'app/shared/model/thing.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IThingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Thing = (props: IThingProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const handleSyncList = () => {
    props.getEntities();
  };

  const { thingList, match, loading } = props;
  return (
    <div>
      <h2 id="thing-heading" data-cy="ThingHeading">
        Things
        <div className="d-flex justify-content-end">
          <Button className="mr-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Thing
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {thingList && thingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>U UID</th>
                <th>Location</th>
                <th>Thing Category</th>
                <th>Application</th>
                <th>State</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {thingList.map((thing, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${thing.id}`} color="link" size="sm">
                      {thing.id}
                    </Button>
                  </td>
                  <td>{thing.name}</td>
                  <td>{thing.uUID}</td>
                  <td>{thing.location ? <Link to={`location/${thing.location.id}`}>{thing.location.id}</Link> : ''}</td>
                  <td>
                    {thing.thingCategory ? <Link to={`thing-category/${thing.thingCategory.id}`}>{thing.thingCategory.id}</Link> : ''}
                  </td>
                  <td>{thing.application ? <Link to={`application/${thing.application.id}`}>{thing.application.id}</Link> : ''}</td>
                  <td>
                    {thing.states
                      ? thing.states.map((val, j) => (
                          <span key={j}>
                            <Link to={`state/${val.id}`}>{val.name}</Link>
                            {j === thing.states.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${thing.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${thing.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${thing.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Things found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ thing }: IRootState) => ({
  thingList: thing.entities,
  loading: thing.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Thing);
