import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './software.reducer';
import { ISoftware } from 'app/shared/model/software.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISoftwareProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Software = (props: ISoftwareProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { softwareList, match, loading } = props;
  return (
    <div>
      <h2 id="software-heading">
        <Translate contentKey="customerManagerApp.software.home.title">Software</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="customerManagerApp.software.home.createLabel">Create new Software</Translate>
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('customerManagerApp.software.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {softwareList && softwareList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="customerManagerApp.software.technology">Technology</Translate>
                </th>
                <th>
                  <Translate contentKey="customerManagerApp.software.amount">Amount</Translate>
                </th>
                <th>
                  <Translate contentKey="customerManagerApp.software.details">Details</Translate>
                </th>
                <th>
                  <Translate contentKey="customerManagerApp.software.serviceCategory">Service Category</Translate>
                </th>
                <th>
                  <Translate contentKey="customerManagerApp.software.services">Services</Translate>
                </th>
                <th>
                  <Translate contentKey="customerManagerApp.software.profile">Profile</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {softwareList.map((software, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${software.id}`} color="link" size="sm">
                      {software.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`customerManagerApp.Technology.${software.technology}`} />
                  </td>
                  <td>{software.amount}</td>
                  <td>{software.details}</td>
                  <td>
                    {software.serviceCategoryName ? (
                      <Link to={`service-category/${software.serviceCategoryId}`}>{software.serviceCategoryName}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {software.servicesDescription ? <Link to={`services/${software.servicesId}`}>{software.servicesDescription}</Link> : ''}
                  </td>
                  <td>
                    {software.profilePhoneNumber ? <Link to={`profile/${software.profileId}`}>{software.profilePhoneNumber}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${software.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${software.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${software.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="customerManagerApp.software.home.notFound">No Software found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ software }: IRootState) => ({
  softwareList: software.entities,
  loading: software.loading,
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Software);
