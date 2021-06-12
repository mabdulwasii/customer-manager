import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './hardware.reducer';
import { IHardware } from 'app/shared/model/hardware.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHardwareDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HardwareDetail = (props: IHardwareDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { hardwareEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="customerManagerApp.hardware.detail.title">Hardware</Translate> [<b>{hardwareEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="gadget">
              <Translate contentKey="customerManagerApp.hardware.gadget">Gadget</Translate>
            </span>
          </dt>
          <dd>{hardwareEntity.gadget}</dd>
          <dt>
            <span id="model">
              <Translate contentKey="customerManagerApp.hardware.model">Model</Translate>
            </span>
          </dt>
          <dd>{hardwareEntity.model}</dd>
          <dt>
            <span id="brandName">
              <Translate contentKey="customerManagerApp.hardware.brandName">Brand Name</Translate>
            </span>
          </dt>
          <dd>{hardwareEntity.brandName}</dd>
          <dt>
            <span id="serialNumber">
              <Translate contentKey="customerManagerApp.hardware.serialNumber">Serial Number</Translate>
            </span>
          </dt>
          <dd>{hardwareEntity.serialNumber}</dd>
          <dt>
            <span id="imeiNumber">
              <Translate contentKey="customerManagerApp.hardware.imeiNumber">Imei Number</Translate>
            </span>
          </dt>
          <dd>{hardwareEntity.imeiNumber}</dd>
          <dt>
            <span id="state">
              <Translate contentKey="customerManagerApp.hardware.state">State</Translate>
            </span>
          </dt>
          <dd>{hardwareEntity.state}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.hardware.services">Services</Translate>
          </dt>
          <dd>{hardwareEntity.servicesDescription ? hardwareEntity.servicesDescription : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.hardware.serviceCategory">Service Category</Translate>
          </dt>
          <dd>{hardwareEntity.serviceCategoryName ? hardwareEntity.serviceCategoryName : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.hardware.profile">Profile</Translate>
          </dt>
          <dd>{hardwareEntity.profilePhoneNumber ? hardwareEntity.profilePhoneNumber : ''}</dd>
        </dl>
        <Button tag={Link} to="/hardware" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/hardware/${hardwareEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ hardware }: IRootState) => ({
  hardwareEntity: hardware.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HardwareDetail);
