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

import { IHardware, defaultValue } from 'app/shared/model/hardware.model';

export const ACTION_TYPES = {
  SEARCH_HARDWARE: 'hardware/SEARCH_HARDWARE',
  FETCH_HARDWARE_LIST: 'hardware/FETCH_HARDWARE_LIST',
  FETCH_HARDWARE: 'hardware/FETCH_HARDWARE',
  CREATE_HARDWARE: 'hardware/CREATE_HARDWARE',
  UPDATE_HARDWARE: 'hardware/UPDATE_HARDWARE',
  DELETE_HARDWARE: 'hardware/DELETE_HARDWARE',
  RESET: 'hardware/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHardware>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type HardwareState = Readonly<typeof initialState>;

// Reducer

export default (state: HardwareState = initialState, action): HardwareState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_HARDWARE):
    case REQUEST(ACTION_TYPES.FETCH_HARDWARE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HARDWARE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_HARDWARE):
    case REQUEST(ACTION_TYPES.UPDATE_HARDWARE):
    case REQUEST(ACTION_TYPES.DELETE_HARDWARE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_HARDWARE):
    case FAILURE(ACTION_TYPES.FETCH_HARDWARE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HARDWARE):
    case FAILURE(ACTION_TYPES.CREATE_HARDWARE):
    case FAILURE(ACTION_TYPES.UPDATE_HARDWARE):
    case FAILURE(ACTION_TYPES.DELETE_HARDWARE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_HARDWARE):
    case SUCCESS(ACTION_TYPES.FETCH_HARDWARE_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_HARDWARE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_HARDWARE):
    case SUCCESS(ACTION_TYPES.UPDATE_HARDWARE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_HARDWARE):
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

const apiUrl = 'api/hardware';
const apiSearchUrl = 'api/_search/hardware';

// Actions

export const getSearchEntities: ICrudSearchAction<IHardware> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_HARDWARE,
  payload: axios.get<IHardware>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IHardware> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_HARDWARE_LIST,
    payload: axios.get<IHardware>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IHardware> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HARDWARE,
    payload: axios.get<IHardware>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IHardware> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HARDWARE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const updateEntity: ICrudPutAction<IHardware> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HARDWARE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHardware> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HARDWARE,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
