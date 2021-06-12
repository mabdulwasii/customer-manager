import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IHardware } from 'app/shared/model/hardware.model';
import { getEntities as getHardware } from 'app/entities/hardware/hardware.reducer';
import { ITraining } from 'app/shared/model/training.model';
import { getEntities as getTrainings } from 'app/entities/training/training.reducer';
import { ISoftware } from 'app/shared/model/software.model';
import { getEntities as getSoftware } from 'app/entities/software/software.reducer';
import { getEntity, updateEntity, createEntity, reset } from './services.reducer';
import { IServices } from 'app/shared/model/services.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServicesUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServicesUpdate = (props: IServicesUpdateProps) => {
  const [hardwareId, setHardwareId] = useState('0');
  const [trainingId, setTrainingId] = useState('0');
  const [softwareId, setSoftwareId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { servicesEntity, hardware, trainings, software, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/services');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getHardware();
    props.getTrainings();
    props.getSoftware();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);

    if (errors.length === 0) {
      const entity = {
        ...servicesEntity,
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
          <h2 id="customerManagerApp.services.home.createOrEditLabel">
            <Translate contentKey="customerManagerApp.services.home.createOrEditLabel">Create or edit a Services</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : servicesEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="services-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="services-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="descriptionLabel" for="services-description">
                  <Translate contentKey="customerManagerApp.services.description">Description</Translate>
                </Label>
                <AvField id="services-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="startDateLabel" for="services-startDate">
                  <Translate contentKey="customerManagerApp.services.startDate">Start Date</Translate>
                </Label>
                <AvInput
                  id="services-startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.servicesEntity.startDate)}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="agreeLabel">
                  <AvInput id="services-agree" type="checkbox" className="form-check-input" name="agree" />
                  <Translate contentKey="customerManagerApp.services.agree">Agree</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="signDocUrlLabel" for="services-signDocUrl">
                  <Translate contentKey="customerManagerApp.services.signDocUrl">Sign Doc Url</Translate>
                </Label>
                <AvField id="services-signDocUrl" type="text" name="signDocUrl" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/services" replace color="info">
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
  hardware: storeState.hardware.entities,
  trainings: storeState.training.entities,
  software: storeState.software.entities,
  servicesEntity: storeState.services.entity,
  loading: storeState.services.loading,
  updating: storeState.services.updating,
  updateSuccess: storeState.services.updateSuccess,
});

const mapDispatchToProps = {
  getHardware,
  getTrainings,
  getSoftware,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServicesUpdate);
