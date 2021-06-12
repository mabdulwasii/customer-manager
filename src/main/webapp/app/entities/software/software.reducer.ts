import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISoftware, defaultValue } from 'app/shared/model/software.model';

export const ACTION_TYPES = {
  SEARCH_SOFTWARE: 'software/SEARCH_SOFTWARE',
  FETCH_SOFTWARE_LIST: 'software/FETCH_SOFTWARE_LIST',
  FETCH_SOFTWARE: 'software/FETCH_SOFTWARE',
  CREATE_SOFTWARE: 'software/CREATE_SOFTWARE',
  UPDATE_SOFTWARE: 'software/UPDATE_SOFTWARE',
  DELETE_SOFTWARE: 'software/DELETE_SOFTWARE',
  RESET: 'software/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISoftware>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SoftwareState = Readonly<typeof initialState>;

// Reducer

export default (state: SoftwareState = initialState, action): SoftwareState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_SOFTWARE):
    case REQUEST(ACTION_TYPES.FETCH_SOFTWARE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SOFTWARE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SOFTWARE):
    case REQUEST(ACTION_TYPES.UPDATE_SOFTWARE):
    case REQUEST(ACTION_TYPES.DELETE_SOFTWARE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_SOFTWARE):
    case FAILURE(ACTION_TYPES.FETCH_SOFTWARE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SOFTWARE):
    case FAILURE(ACTION_TYPES.CREATE_SOFTWARE):
    case FAILURE(ACTION_TYPES.UPDATE_SOFTWARE):
    case FAILURE(ACTION_TYPES.DELETE_SOFTWARE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_SOFTWARE):
    case SUCCESS(ACTION_TYPES.FETCH_SOFTWARE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SOFTWARE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SOFTWARE):
    case SUCCESS(ACTION_TYPES.UPDATE_SOFTWARE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SOFTWARE):
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

const apiUrl = 'api/software';
const apiSearchUrl = 'api/_search/software';

// Actions

export const getSearchEntities: ICrudSearchAction<ISoftware> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_SOFTWARE,
  payload: axios.get<ISoftware>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<ISoftware> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SOFTWARE_LIST,
  payload: axios.get<ISoftware>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISoftware> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SOFTWARE,
    payload: axios.get<ISoftware>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISoftware> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SOFTWARE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISoftware> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SOFTWARE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISoftware> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SOFTWARE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
