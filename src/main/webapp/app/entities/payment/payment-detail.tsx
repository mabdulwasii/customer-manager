import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './payment.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPaymentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaymentDetail = (props: IPaymentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { paymentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="customerManagerApp.payment.detail.title">Payment</Translate> [<b>{paymentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="customerManagerApp.payment.date">Date</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.date ? <TextFormat value={paymentEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="amount">
              <Translate contentKey="customerManagerApp.payment.amount">Amount</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.amount}</dd>
          <dt>
            <span id="paymentType">
              <Translate contentKey="customerManagerApp.payment.paymentType">Payment Type</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.paymentType}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="customerManagerApp.payment.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{paymentEntity.balance}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.payment.software">Software</Translate>
          </dt>
          <dd>{paymentEntity.softwareTechnology ? paymentEntity.softwareTechnology : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.payment.training">Training</Translate>
          </dt>
          <dd>{paymentEntity.trainingName ? paymentEntity.trainingName : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.payment.hardware">Hardware</Translate>
          </dt>
          <dd>{paymentEntity.hardwareGadget ? paymentEntity.hardwareGadget : ''}</dd>
        </dl>
        <Button tag={Link} to="/payment" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payment/${paymentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ payment }: IRootState) => ({
  paymentEntity: payment.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentDetail);
