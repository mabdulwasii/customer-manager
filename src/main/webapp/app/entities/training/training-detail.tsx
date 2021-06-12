import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './training.reducer';
import { ITraining } from 'app/shared/model/training.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITrainingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TrainingDetail = (props: ITrainingDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { trainingEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="customerManagerApp.training.detail.title">Training</Translate> [<b>{trainingEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="customerManagerApp.training.name">Name</Translate>
            </span>
          </dt>
          <dd>{trainingEntity.name}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="customerManagerApp.training.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{trainingEntity.amount}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.training.profile">Profile</Translate>
          </dt>
          <dd>{trainingEntity.profilePhoneNumber ? trainingEntity.profilePhoneNumber : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.training.serviceCategory">Service Category</Translate>
          </dt>
          <dd>{trainingEntity.serviceCategoryName ? trainingEntity.serviceCategoryName : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.training.services">Services</Translate>
          </dt>
          <dd>{trainingEntity.servicesDescription ? trainingEntity.servicesDescription : ''}</dd>
        </dl>
        <Button tag={Link} to="/training" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/training/${trainingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ training }: IRootState) => ({
  trainingEntity: training.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TrainingDetail);
