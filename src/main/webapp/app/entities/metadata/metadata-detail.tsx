import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './metadata.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMetadataDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MetadataDetail = (props: IMetadataDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { metadataEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="metadataDetailsHeading">Metadata</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{metadataEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{metadataEntity.name}</dd>
          <dt>
            <span id="data">Data</span>
          </dt>
          <dd>{metadataEntity.data}</dd>
          <dt>Device</dt>
          <dd>{metadataEntity.device ? metadataEntity.device.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/metadata" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/metadata/${metadataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ metadata }: IRootState) => ({
  metadataEntity: metadata.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MetadataDetail);
