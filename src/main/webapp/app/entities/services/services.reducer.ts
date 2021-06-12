import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction,
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServices, defaultValue } from 'app/shared/model/services.model';

export const ACTION_TYPES = {
  SEARCH_SERVICES: 'services/SEARCH_SERVICES',
  FETCH_SERVICES_LIST: 'services/FETCH_SERVICES_LIST',
  FETCH_SERVICES: 'services/FETCH_SERVICES',
  CREATE_SERVICES: 'services/CREATE_SERVICES',
  UPDATE_SERVICES: 'services/UPDATE_SERVICES',
  DELETE_SERVICES: 'services/DELETE_SERVICES',
  RESET: 'services/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServices>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ServicesState = Readonly<typeof initialState>;

// Reducer

export default (state: ServicesState = initialState, action): ServicesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SERVICES):
    case REQUEST(ACTION_TYPES.FETCH_SERVICES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICES):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICES):
    case REQUEST(ACTION_TYPES.DELETE_SERVICES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_SERVICES):
    case FAILURE(ACTION_TYPES.FETCH_SERVICES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICES):
    case FAILURE(ACTION_TYPES.CREATE_SERVICES):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICES):
    case FAILURE(ACTION_TYPES.DELETE_SERVICES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SERVICES):
    case SUCCESS(ACTION_TYPES.FETCH_SERVICES_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_SERVICES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICES):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICES):
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

const apiUrl = 'api/services';
const apiSearchUrl = 'api/_search/services';

// Actions

export const getSearchEntities: ICrudSearchAction<IServices> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SERVICES,
  payload: axios.get<IServices>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IServices> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICES_LIST,
    payload: axios.get<IServices>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IServices> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICES,
    payload: axios.get<IServices>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IServices> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICES,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IServices> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICES,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServices> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICES,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
