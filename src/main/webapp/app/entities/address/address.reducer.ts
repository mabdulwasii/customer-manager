import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAddress, defaultValue } from 'app/shared/model/address.model';

export const ACTION_TYPES = {
  SEARCH_ADDRESSES: 'address/SEARCH_ADDRESSES',
  FETCH_ADDRESS_LIST: 'address/FETCH_ADDRESS_LIST',
  FETCH_ADDRESS: 'address/FETCH_ADDRESS',
  CREATE_ADDRESS: 'address/CREATE_ADDRESS',
  UPDATE_ADDRESS: 'address/UPDATE_ADDRESS',
  DELETE_ADDRESS: 'address/DELETE_ADDRESS',
  RESET: 'address/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAddress>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AddressState = Readonly<typeof initialState>;

// Reducer

export default (state: AddressState = initialState, action): AddressState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ADDRESSES):
    case REQUEST(ACTION_TYPES.FETCH_ADDRESS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ADDRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ADDRESS):
    case REQUEST(ACTION_TYPES.UPDATE_ADDRESS):
    case REQUEST(ACTION_TYPES.DELETE_ADDRESS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_ADDRESSES):
    case FAILURE(ACTION_TYPES.FETCH_ADDRESS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ADDRESS):
    case FAILURE(ACTION_TYPES.CREATE_ADDRESS):
    case FAILURE(ACTION_TYPES.UPDATE_ADDRESS):
    case FAILURE(ACTION_TYPES.DELETE_ADDRESS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ADDRESSES):
    case SUCCESS(ACTION_TYPES.FETCH_ADDRESS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ADDRESS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ADDRESS):
    case SUCCESS(ACTION_TYPES.UPDATE_ADDRESS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ADDRESS):
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

const apiUrl = 'api/addresses';
const apiSearchUrl = 'api/_search/addresses';

// Actions

export const getSearchEntities: ICrudSearchAction<IAddress> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ADDRESSES,
  payload: axios.get<IAddress>(`${apiSearchUrl}?query=${query}`),
});

export const getEntities: ICrudGetAllAction<IAddress> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ADDRESS_LIST,
  payload: axios.get<IAddress>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAddress> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ADDRESS,
    payload: axios.get<IAddress>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAddress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ADDRESS,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAddress> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ADDRESS,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAddress> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ADDRESS,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
