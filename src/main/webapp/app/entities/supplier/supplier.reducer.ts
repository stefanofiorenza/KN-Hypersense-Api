import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISupplier, defaultValue } from 'app/shared/model/supplier.model';

export const ACTION_TYPES = {
  FETCH_SUPPLIER_LIST: 'supplier/FETCH_SUPPLIER_LIST',
  FETCH_SUPPLIER: 'supplier/FETCH_SUPPLIER',
  CREATE_SUPPLIER: 'supplier/CREATE_SUPPLIER',
  UPDATE_SUPPLIER: 'supplier/UPDATE_SUPPLIER',
  PARTIAL_UPDATE_SUPPLIER: 'supplier/PARTIAL_UPDATE_SUPPLIER',
  DELETE_SUPPLIER: 'supplier/DELETE_SUPPLIER',
  RESET: 'supplier/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISupplier>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type SupplierState = Readonly<typeof initialState>;

// Reducer

export default (state: SupplierState = initialState, action): SupplierState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SUPPLIER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SUPPLIER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_SUPPLIER):
    case REQUEST(ACTION_TYPES.UPDATE_SUPPLIER):
    case REQUEST(ACTION_TYPES.DELETE_SUPPLIER):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_SUPPLIER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_SUPPLIER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SUPPLIER):
    case FAILURE(ACTION_TYPES.CREATE_SUPPLIER):
    case FAILURE(ACTION_TYPES.UPDATE_SUPPLIER):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_SUPPLIER):
    case FAILURE(ACTION_TYPES.DELETE_SUPPLIER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUPPLIER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_SUPPLIER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_SUPPLIER):
    case SUCCESS(ACTION_TYPES.UPDATE_SUPPLIER):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_SUPPLIER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_SUPPLIER):
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

const apiUrl = 'api/suppliers';

// Actions

export const getEntities: ICrudGetAllAction<ISupplier> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SUPPLIER_LIST,
  payload: axios.get<ISupplier>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ISupplier> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SUPPLIER,
    payload: axios.get<ISupplier>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ISupplier> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SUPPLIER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISupplier> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SUPPLIER,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ISupplier> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_SUPPLIER,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISupplier> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SUPPLIER,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
