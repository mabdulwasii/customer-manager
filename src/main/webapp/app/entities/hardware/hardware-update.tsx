import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServices } from 'app/shared/model/services.model';
import { getEntities as getServices } from 'app/entities/services/services.reducer';
import { IServiceCategory } from 'app/shared/model/service-category.model';
import { getEntities as getServiceCategories } from 'app/entities/service-category/service-category.reducer';
import { IProfile } from 'app/shared/model/profile.model';
import { getEntities as getProfiles } from 'app/entities/profile/profile.reducer';
import { getEntity, updateEntity, createEntity, reset } from './hardware.reducer';
import { IHardware } from 'app/shared/model/hardware.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHardwareUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HardwareUpdate = (props: IHardwareUpdateProps) => {
  const [servicesId, setServicesId] = useState('0');
  const [serviceCategoryId, setServiceCategoryId] = useState('0');
  const [profileId, setProfileId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { hardwareEntity, services, serviceCategories, profiles, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/hardware');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getServices();
    props.getServiceCategories();
    props.getProfiles();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...hardwareEntity,
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
          <h2 id="customerManagerApp.hardware.home.createOrEditLabel">
            <Translate contentKey="customerManagerApp.hardware.home.createOrEditLabel">Create or edit a Hardware</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : hardwareEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="hardware-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="hardware-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="gadgetLabel" for="hardware-gadget">
                  <Translate contentKey="customerManagerApp.hardware.gadget">Gadget</Translate>
                </Label>
                <AvInput
                  id="hardware-gadget"
                  type="select"
                  className="form-control"
                  name="gadget"
                  value={(!isNew && hardwareEntity.gadget) || 'PHONE'}
                >
                  <option value="PHONE">{translate('customerManagerApp.Gadget.PHONE')}</option>
                  <option value="TABLET">{translate('customerManagerApp.Gadget.TABLET')}</option>
                  <option value="LAPTOP">{translate('customerManagerApp.Gadget.LAPTOP')}</option>
                  <option value="OTHERS">{translate('customerManagerApp.Gadget.OTHERS')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="modelLabel" for="hardware-model">
                  <Translate contentKey="customerManagerApp.hardware.model">Model</Translate>
                </Label>
                <AvField id="hardware-model" type="text" name="model" />
              </AvGroup>
              <AvGroup>
                <Label id="brandNameLabel" for="hardware-brandName">
                  <Translate contentKey="customerManagerApp.hardware.brandName">Brand Name</Translate>
                </Label>
                <AvField id="hardware-brandName" type="text" name="brandName" />
              </AvGroup>
              <AvGroup>
                <Label id="serialNumberLabel" for="hardware-serialNumber">
                  <Translate contentKey="customerManagerApp.hardware.serialNumber">Serial Number</Translate>
                </Label>
                <AvField id="hardware-serialNumber" type="text" name="serialNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="imeiNumberLabel" for="hardware-imeiNumber">
                  <Translate contentKey="customerManagerApp.hardware.imeiNumber">Imei Number</Translate>
                </Label>
                <AvField id="hardware-imeiNumber" type="text" name="imeiNumber" />
              </AvGroup>
              <AvGroup>
                <Label id="stateLabel" for="hardware-state">
                  <Translate contentKey="customerManagerApp.hardware.state">State</Translate>
                </Label>
                <AvField id="hardware-state" type="text" name="state" />
              </AvGroup>
              <AvGroup>
                <Label for="hardware-services">
                  <Translate contentKey="customerManagerApp.hardware.services">Services</Translate>
                </Label>
                <AvInput id="hardware-services" type="select" className="form-control" name="servicesId">
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
              <AvGroup>
                <Label for="hardware-serviceCategory">
                  <Translate contentKey="customerManagerApp.hardware.serviceCategory">Service Category</Translate>
                </Label>
                <AvInput id="hardware-serviceCategory" type="select" className="form-control" name="serviceCategoryId">
                  <option value="" key="0" />
                  {serviceCategories
                    ? serviceCategories.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="hardware-profile">
                  <Translate contentKey="customerManagerApp.hardware.profile">Profile</Translate>
                </Label>
                <AvInput id="hardware-profile" type="select" className="form-control" name="profileId" required>
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
              <Button tag={Link} id="cancel-save" to="/hardware" replace color="info">
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
  services: storeState.services.entities,
  serviceCategories: storeState.serviceCategory.entities,
  profiles: storeState.profile.entities,
  hardwareEntity: storeState.hardware.entity,
  loading: storeState.hardware.loading,
  updating: storeState.hardware.updating,
  updateSuccess: storeState.hardware.updateSuccess,
});

const mapDispatchToProps = {
  getServices,
  getServiceCategories,
  getProfiles,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HardwareUpdate);
