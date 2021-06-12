import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './profile.reducer';
import { IProfile } from 'app/shared/model/profile.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProfileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ProfileDetail = (props: IProfileDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { profileEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="customerManagerApp.profile.detail.title">Profile</Translate> [<b>{profileEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="customerManagerApp.profile.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{profileEntity.phoneNumber}</dd>
          <dt>
            <span id="dateOfBirth">
              <Translate contentKey="customerManagerApp.profile.dateOfBirth">Date Of Birth</Translate>
            </span>
          </dt>
          <dd>
            {profileEntity.dateOfBirth ? <TextFormat value={profileEntity.dateOfBirth} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="profileId">
              <Translate contentKey="customerManagerApp.profile.profileId">Profile Id</Translate>
            </span>
          </dt>
          <dd>{profileEntity.profileId}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="customerManagerApp.profile.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{profileEntity.gender}</dd>
          <dt>
            <span id="validId">
              <Translate contentKey="customerManagerApp.profile.validId">Valid Id</Translate>
            </span>
          </dt>
          <dd>{profileEntity.validId}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.profile.user">User</Translate>
          </dt>
          <dd>{profileEntity.userLogin ? profileEntity.userLogin : ''}</dd>
          <dt>
            <Translate contentKey="customerManagerApp.profile.address">Address</Translate>
          </dt>
          <dd>{profileEntity.addressStreetAddress ? profileEntity.addressStreetAddress : ''}</dd>
        </dl>
        <Button tag={Link} to="/profile" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profile/${profileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ profile }: IRootState) => ({
  profileEntity: profile.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ProfileDetail);
