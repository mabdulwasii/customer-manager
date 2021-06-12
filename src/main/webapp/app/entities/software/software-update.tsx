import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IServiceCategory } from 'app/shared/model/service-category.model';
import { getEntities as getServiceCategories } from 'app/entities/service-category/service-category.reducer';
import { IServices } from 'app/shared/model/services.model';
import { getEntities as getServices } from 'app/entities/services/services.reducer';
import { IProfile } from 'app/shared/model/profile.model';
import { getEntities as getProfiles } from 'app/entities/profile/profile.reducer';
import { getEntity, updateEntity, createEntity, reset } from './software.reducer';
import { ISoftware } from 'app/shared/model/software.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISoftwareUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SoftwareUpdate = (props: ISoftwareUpdateProps) => {
  const [serviceCategoryId, setServiceCategoryId] = useState('0');
  const [servicesId, setServicesId] = useState('0');
  const [profileId, setProfileId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { softwareEntity, serviceCategories, services, profiles, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/software');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getServiceCategories();
    props.getServices();
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
        ...softwareEntity,
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
          <h2 id="customerManagerApp.software.home.createOrEditLabel">
            <Translate contentKey="customerManagerApp.software.home.createOrEditLabel">Create or edit a Software</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : softwareEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="software-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="software-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="technologyLabel" for="software-technology">
                  <Translate contentKey="customerManagerApp.software.technology">Technology</Translate>
                </Label>
                <AvInput
                  id="software-technology"
                  type="select"
                  className="form-control"
                  name="technology"
                  value={(!isNew && softwareEntity.technology) || 'WEB_DESIGN'}
                >
                  <option value="WEB_DESIGN">{translate('customerManagerApp.Technology.WEB_DESIGN')}</option>
                  <option value="WEB_DEVELOPMENT">{translate('customerManagerApp.Technology.WEB_DEVELOPMENT')}</option>
                  <option value="PHP">{translate('customerManagerApp.Technology.PHP')}</option>
                  <option value="JAVA">{translate('customerManagerApp.Technology.JAVA')}</option>
                  <option value="JAVASCRIPT">{translate('customerManagerApp.Technology.JAVASCRIPT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="amountLabel" for="software-amount">
                  <Translate contentKey="customerManagerApp.software.amount">Amount</Translate>
                </Label>
                <AvField id="software-amount" type="text" name="amount" />
              </AvGroup>
              <AvGroup>
                <Label id="detailsLabel" for="software-details">
                  <Translate contentKey="customerManagerApp.software.details">Details</Translate>
                </Label>
                <AvField id="software-details" type="text" name="details" />
              </AvGroup>
              <AvGroup>
                <Label for="software-serviceCategory">
                  <Translate contentKey="customerManagerApp.software.serviceCategory">Service Category</Translate>
                </Label>
                <AvInput id="software-serviceCategory" type="select" className="form-control" name="serviceCategoryId">
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
                <Label for="software-services">
                  <Translate contentKey="customerManagerApp.software.services">Services</Translate>
                </Label>
                <AvInput id="software-services" type="select" className="form-control" name="servicesId">
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
                <Label for="software-profile">
                  <Translate contentKey="customerManagerApp.software.profile">Profile</Translate>
                </Label>
                <AvInput id="software-profile" type="select" className="form-control" name="profileId" required>
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
              <Button tag={Link} id="cancel-save" to="/software" replace color="info">
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
  serviceCategories: storeState.serviceCategory.entities,
  services: storeState.services.entities,
  profiles: storeState.profile.entities,
  softwareEntity: storeState.software.entity,
  loading: storeState.software.loading,
  updating: storeState.software.updating,
  updateSuccess: storeState.software.updateSuccess,
});

const mapDispatchToProps = {
  getServiceCategories,
  getServices,
  getProfiles,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SoftwareUpdate);
