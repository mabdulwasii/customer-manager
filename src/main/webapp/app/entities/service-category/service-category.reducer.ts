import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceCategory, defaultValue } from 'app/shared/model/service-category.model';

export const ACTION_TYPES = {
  SEARCH_SERVICECATEGORIES: 'serviceCategory/SEARCH_SERVICECATEGORIES',
  FETCH_SERVICECATEGORY_LIST: 'serviceCategory/FETCH_SERVICECATEGORY_LIST',
  FETCH_SERVICECATEGORY: 'serviceCategory/FETCH_SERVICECATEGORY',
  CREATE_SERVICECATEGORY: 'serviceCategory/CREATE_SERVICECATEGORY',
  UPDATE_SERVICECATEGORY: 'serviceCategory/UPDATE_SERVICECATEGORY',
  DELETE_SERVICECATEGORY: 'serviceCategory/DELETE_SERVICECATEGORY',
  RESET: 'serviceCategory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceCategory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ServiceCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceCategoryState = initialState, action): ServiceCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SERVICECATEGORIES):
    case REQUEST(ACTION_TYPES.FETCH_SERVICECATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICECATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICECATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICECATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_SERVICECATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_SERVICECATEGORIES):
    case FAILURE(ACTION_TYPES.FETCH_SERVICECATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICECATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_SERVICECATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICECATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_SERVICECATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SERVICECATEGORIES):
    case SUCCESS(ACTION_TYPES.FETCH_SERVICECATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICECATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICECATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICECATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICECATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/service-categories';
const apiSearchUrl = 'api/_search/service-categories';

// Actions

export const getSearchEntities: ICrudSearchAction<IServiceCategory> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SERVICECATEGORIES,
  payload: axios.get<IServiceCategory>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IServiceCategory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SERVICECATEGORY_LIST,
  payload: axios.get<IServiceCategory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IServiceCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICECATEGORY,
    payload: axios.get<IServiceCategory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServiceCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICECATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICECATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICECATEGORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
