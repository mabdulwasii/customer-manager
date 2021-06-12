import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-category.reducer';
import { IServiceCategory } from 'app/shared/model/service-category.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ServiceCategoryDetail = (props: IServiceCategoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serviceCategoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="customerManagerApp.serviceCategory.detail.title">ServiceCategory</Translate> [
          <b>{serviceCategoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="customerManagerApp.serviceCategory.name">Name</Translate>
            </span>
          </dt>
          <dd>{serviceCategoryEntity.name}</dd>
          <dt>
            <span id="fixedAmount">
              <Translate contentKey="customerManagerApp.serviceCategory.fixedAmount">Fixed Amount</Translate>
            </span>
          </dt>
          <dd>{serviceCategoryEntity.fixedAmount}</dd>
          <dt>
            <span id="hasFixedPrice">
              <Translate contentKey="customerManagerApp.serviceCategory.hasFixedPrice">Has Fixed Price</Translate>
            </span>
          </dt>
          <dd>{serviceCategoryEntity.hasFixedPrice ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/service-category" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-category/${serviceCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serviceCategory }: IRootState) => ({
  serviceCategoryEntity: serviceCategory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ServiceCategoryDetail);
