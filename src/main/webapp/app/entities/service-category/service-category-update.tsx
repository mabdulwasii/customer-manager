import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './service-category.reducer';
import { IServiceCategory } from 'app/shared/model/service-category.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IServiceCategoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceCategoryUpdate = (props: IServiceCategoryUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serviceCategoryEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/service-category');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...serviceCategoryEntity,
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
          <h2 id="customerManagerApp.serviceCategory.home.createOrEditLabel">
            <Translate contentKey="customerManagerApp.serviceCategory.home.createOrEditLabel">Create or edit a ServiceCategory</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : serviceCategoryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="service-category-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="service-category-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="service-category-name">
                  <Translate contentKey="customerManagerApp.serviceCategory.name">Name</Translate>
                </Label>
                <AvField id="service-category-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="fixedAmountLabel" for="service-category-fixedAmount">
                  <Translate contentKey="customerManagerApp.serviceCategory.fixedAmount">Fixed Amount</Translate>
                </Label>
                <AvField id="service-category-fixedAmount" type="text" name="fixedAmount" />
              </AvGroup>
              <AvGroup check>
                <Label id="hasFixedPriceLabel">
                  <AvInput id="service-category-hasFixedPrice" type="checkbox" className="form-check-input" name="hasFixedPrice" />
                  <Translate contentKey="customerManagerApp.serviceCategory.hasFixedPrice">Has Fixed Price</Translate>
                </Label>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/service-category" replace color="info">
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
  serviceCategoryEntity: storeState.serviceCategory.entity,
  loading: storeState.serviceCategory.loading,
  updating: storeState.serviceCategory.updating,
  updateSuccess: storeState.serviceCategory.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceCategoryUpdate);
