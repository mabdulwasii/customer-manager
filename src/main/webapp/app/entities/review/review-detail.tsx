import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './review.reducer';
import { IReview } from 'app/shared/model/review.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IReviewDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ReviewDetail = (props: IReviewDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { reviewEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="customerManagerApp.review.detail.title">Review</Translate> [<b>{reviewEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="rating">
              <Translate contentKey="customerManagerApp.review.rating">Rating</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.rating}</dd>
          <dt>
            <span id="comment">
              <Translate contentKey="customerManagerApp.review.comment">Comment</Translate>
            </span>
          </dt>
          <dd>{reviewEntity.comment}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.review.profile">Profile</Translate>
          </dt>
          <dd>{reviewEntity.profilePhoneNumber ? reviewEntity.profilePhoneNumber : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.review.hardware">Hardware</Translate>
          </dt>
          <dd>{reviewEntity.hardwareGadget ? reviewEntity.hardwareGadget : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.review.training">Training</Translate>
          </dt>
          <dd>{reviewEntity.trainingName ? reviewEntity.trainingName : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.review.software">Software</Translate>
          </dt>
          <dd>{reviewEntity.softwareTechnology ? reviewEntity.softwareTechnology : ''}</dd>
        </dl>
        <Button tag={Link} to="/review" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/review/${reviewEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ review }: IRootState) => ({
  reviewEntity: review.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ReviewDetail);
