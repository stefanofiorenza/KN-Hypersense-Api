import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAlertConfiguration, defaultValue } from 'app/shared/model/alert-configuration.model';

export const ACTION_TYPES = {
  FETCH_ALERTCONFIGURATION_LIST: 'alertConfiguration/FETCH_ALERTCONFIGURATION_LIST',
  FETCH_ALERTCONFIGURATION: 'alertConfiguration/FETCH_ALERTCONFIGURATION',
  CREATE_ALERTCONFIGURATION: 'alertConfiguration/CREATE_ALERTCONFIGURATION',
  UPDATE_ALERTCONFIGURATION: 'alertConfiguration/UPDATE_ALERTCONFIGURATION',
  PARTIAL_UPDATE_ALERTCONFIGURATION: 'alertConfiguration/PARTIAL_UPDATE_ALERTCONFIGURATION',
  DELETE_ALERTCONFIGURATION: 'alertConfiguration/DELETE_ALERTCONFIGURATION',
  RESET: 'alertConfiguration/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAlertConfiguration>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AlertConfigurationState = Readonly<typeof initialState>;

// Reducer

export default (state: AlertConfigurationState = initialState, action): AlertConfigurationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ALERTCONFIGURATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ALERTCONFIGURATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ALERTCONFIGURATION):
    case REQUEST(ACTION_TYPES.UPDATE_ALERTCONFIGURATION):
    case REQUEST(ACTION_TYPES.DELETE_ALERTCONFIGURATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ALERTCONFIGURATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ALERTCONFIGURATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ALERTCONFIGURATION):
    case FAILURE(ACTION_TYPES.CREATE_ALERTCONFIGURATION):
    case FAILURE(ACTION_TYPES.UPDATE_ALERTCONFIGURATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ALERTCONFIGURATION):
    case FAILURE(ACTION_TYPES.DELETE_ALERTCONFIGURATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALERTCONFIGURATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALERTCONFIGURATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ALERTCONFIGURATION):
    case SUCCESS(ACTION_TYPES.UPDATE_ALERTCONFIGURATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ALERTCONFIGURATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ALERTCONFIGURATION):
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

const apiUrl = 'api/alert-configurations';

// Actions

export const getEntities: ICrudGetAllAction<IAlertConfiguration> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ALERTCONFIGURATION_LIST,
  payload: axios.get<IAlertConfiguration>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAlertConfiguration> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ALERTCONFIGURATION,
    payload: axios.get<IAlertConfiguration>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAlertConfiguration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ALERTCONFIGURATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAlertConfiguration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ALERTCONFIGURATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAlertConfiguration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ALERTCONFIGURATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAlertConfiguration> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ALERTCONFIGURATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
