import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProfile } from 'app/shared/model/profile.model';
import { getEntities as getProfiles } from 'app/entities/profile/profile.reducer';
import { IServiceCategory } from 'app/shared/model/service-category.model';
import { getEntities as getServiceCategories } from 'app/entities/service-category/service-category.reducer';
import { IServices } from 'app/shared/model/services.model';
import { getEntities as getServices } from 'app/entities/services/services.reducer';
import { getEntity, updateEntity, createEntity, reset } from './training.reducer';
import { ITraining } from 'app/shared/model/training.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITrainingUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TrainingUpdate = (props: ITrainingUpdateProps) => {
  const [profileId, setProfileId] = useState('0');
  const [serviceCategoryId, setServiceCategoryId] = useState('0');
  const [servicesId, setServicesId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { trainingEntity, profiles, serviceCategories, services, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/training');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getProfiles();
    props.getServiceCategories();
    props.getServices();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...trainingEntity,
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
          <h2 id="customerManagerApp.training.home.createOrEditLabel">
            <Translate contentKey="customerManagerApp.training.home.createOrEditLabel">Create or edit a Training</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : trainingEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="training-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="training-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="training-name">
                  <Translate contentKey="customerManagerApp.training.name">Name</Translate>
                </Label>
                <AvField id="training-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="training-amount">
                  <Translate contentKey="customerManagerApp.training.amount">Amount</Translate>
                </Label>
                <AvField
                  id="training-amount"
                  type="text"
                  name="amount"
                  validate={{
                    min: { value: 0, errorMessage: translate('entity.validation.min', { min: 0 }) },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label for="training-profile">
                  <Translate contentKey="customerManagerApp.training.profile">Profile</Translate>
                </Label>
                <AvInput id="training-profile" type="select" className="form-control" name="profileId" required>
                  {profiles
                    ? profiles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.phoneNumber}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="training-serviceCategory">
                  <Translate contentKey="customerManagerApp.training.serviceCategory">Service Category</Translate>
                </Label>
                <AvInput id="training-serviceCategory" type="select" className="form-control" name="serviceCategoryId" required>
                  {serviceCategories
                    ? serviceCategories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>
                  <Translate contentKey="entity.validation.required">This field is required.</Translate>
                </AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="training-services">
                  <Translate contentKey="customerManagerApp.training.services">Services</Translate>
                </Label>
                <AvInput id="training-services" type="select" className="form-control" name="servicesId">
                  <option value="" key="0" />
                  {services
                    ? services.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.description}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/training" replace color="info">
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
  profiles: storeState.profile.entities,
  serviceCategories: storeState.serviceCategory.entities,
  services: storeState.services.entities,
  trainingEntity: storeState.training.entity,
  loading: storeState.training.loading,
  updating: storeState.training.updating,
  updateSuccess: storeState.training.updateSuccess,
});

const mapDispatchToProps = {
  getProfiles,
  getServiceCategories,
  getServices,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TrainingUpdate);
