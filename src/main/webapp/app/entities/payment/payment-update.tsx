import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ISoftware } from 'app/shared/model/software.model';
import { getEntities as getSoftware } from 'app/entities/software/software.reducer';
import { ITraining } from 'app/shared/model/training.model';
import { getEntities as getTrainings } from 'app/entities/training/training.reducer';
import { IHardware } from 'app/shared/model/hardware.model';
import { getEntities as getHardware } from 'app/entities/hardware/hardware.reducer';
import { getEntity, updateEntity, createEntity, reset } from './payment.reducer';
import { IPayment } from 'app/shared/model/payment.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPaymentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PaymentUpdate = (props: IPaymentUpdateProps) => {
  const [softwareId, setSoftwareId] = useState('0');
  const [trainingId, setTrainingId] = useState('0');
  const [hardwareId, setHardwareId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { paymentEntity, software, trainings, hardware, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/payment');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getSoftware();
    props.getTrainings();
    props.getHardware();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...paymentEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="customerManagerApp.payment.home.createOrEditLabel">
            <Translate contentKey="customerManagerApp.payment.home.createOrEditLabel">Create or edit a Payment</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : paymentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="payment-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="payment-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="payment-date">
                  <Translate contentKey="customerManagerApp.payment.date">Date</Translate>
                </Label>
                <AvInput
                  id="payment-date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.paymentEntity.date)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="payment-amount">
                  <Translate contentKey="customerManagerApp.payment.amount">Amount</Translate>
                </Label>
                <AvField
                  id="payment-amount"
                  type="text"
                  name="amount"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="paymentTypeLabel" for="payment-paymentType">
                  <Translate contentKey="customerManagerApp.payment.paymentType">Payment Type</Translate>
                </Label>
                <AvField id="payment-paymentType" type="text" name="paymentType" />
              </AvGroup>
              <AvGroup>
                <Label id="balanceLabel" for="payment-balance">
                  <Translate contentKey="customerManagerApp.payment.balance">Balance</Translate>
                </Label>
                <AvField
                  id="payment-balance"
                  type="text"
                  name="balance"
                  validate={{
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="payment-software">
                  <Translate contentKey="customerManagerApp.payment.software">Software</Translate>
                </Label>
                <AvInput id="payment-software" type="select" className="form-control" name="softwareId">
                  <option value="" key="0" />
                  {software
                    ? software.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.technology}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="payment-training">
                  <Translate contentKey="customerManagerApp.payment.training">Training</Translate>
                </Label>
                <AvInput id="payment-training" type="select" className="form-control" name="trainingId">
                  <option value="" key="0" />
                  {trainings
                    ? trainings.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="payment-hardware">
                  <Translate contentKey="customerManagerApp.payment.hardware">Hardware</Translate>
                </Label>
                <AvInput id="payment-hardware" type="select" className="form-control" name="hardwareId">
                  <option value="" key="0" />
                  {hardware
                    ? hardware.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.gadget}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/payment" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  software: storeState.software.entities,
  trainings: storeState.training.entities,
  hardware: storeState.hardware.entities,
  paymentEntity: storeState.payment.entity,
  loading: storeState.payment.loading,
  updating: storeState.payment.updating,
  updateSuccess: storeState.payment.updateSuccess,
});

const mapDispatchToProps = {
  getSoftware,
  getTrainings,
  getHardware,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PaymentUpdate);
