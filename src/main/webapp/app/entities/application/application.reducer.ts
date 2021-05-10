import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IApplication, defaultValue } from 'app/shared/model/application.model';

export const ACTION_TYPES = {
  FETCH_APPLICATION_LIST: 'application/FETCH_APPLICATION_LIST',
  FETCH_APPLICATION: 'application/FETCH_APPLICATION',
  CREATE_APPLICATION: 'application/CREATE_APPLICATION',
  UPDATE_APPLICATION: 'application/UPDATE_APPLICATION',
  PARTIAL_UPDATE_APPLICATION: 'application/PARTIAL_UPDATE_APPLICATION',
  DELETE_APPLICATION: 'application/DELETE_APPLICATION',
  RESET: 'application/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IApplication>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ApplicationState = Readonly<typeof initialState>;

// Reducer

export default (state: ApplicationState = initialState, action): ApplicationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_APPLICATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_APPLICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_APPLICATION):
    case REQUEST(ACTION_TYPES.UPDATE_APPLICATION):
    case REQUEST(ACTION_TYPES.DELETE_APPLICATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_APPLICATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_APPLICATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_APPLICATION):
    case FAILURE(ACTION_TYPES.CREATE_APPLICATION):
    case FAILURE(ACTION_TYPES.UPDATE_APPLICATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_APPLICATION):
    case FAILURE(ACTION_TYPES.DELETE_APPLICATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_APPLICATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_APPLICATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_APPLICATION):
    case SUCCESS(ACTION_TYPES.UPDATE_APPLICATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_APPLICATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_APPLICATION):
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

const apiUrl = 'api/applications';

// Actions

export const getEntities: ICrudGetAllAction<IApplication> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_APPLICATION_LIST,
  payload: axios.get<IApplication>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IApplication> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_APPLICATION,
    payload: axios.get<IApplication>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IApplication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_APPLICATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IApplication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_APPLICATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IApplication> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_APPLICATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IApplication> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_APPLICATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
