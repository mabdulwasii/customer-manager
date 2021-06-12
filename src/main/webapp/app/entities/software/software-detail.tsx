import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './software.reducer';
import { ISoftware } from 'app/shared/model/software.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISoftwareDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareDetail = (props: ISoftwareDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { softwareEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="customerManagerApp.software.detail.title">Software</Translate> [<b>{softwareEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="technology">
              <Translate contentKey="customerManagerApp.software.technology">Technology</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.technology}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="customerManagerApp.software.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.amount}</dd>
          <dt>
            <span id="details">
              <Translate contentKey="customerManagerApp.software.details">Details</Translate>
            </span>
          </dt>
          <dd>{softwareEntity.details}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.software.serviceCategory">Service Category</Translate>
          </dt>
          <dd>{softwareEntity.serviceCategoryName ? softwareEntity.serviceCategoryName : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.software.services">Services</Translate>
          </dt>
          <dd>{softwareEntity.servicesDescription ? softwareEntity.servicesDescription : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.software.profile">Profile</Translate>
          </dt>
          <dd>{softwareEntity.profilePhoneNumber ? softwareEntity.profilePhoneNumber : ''}</dd>
        </dl>
        <Button tag={Link} to="/software" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/software/${softwareEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ software }: IRootState) => ({
  softwareEntity: software.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareDetail);
