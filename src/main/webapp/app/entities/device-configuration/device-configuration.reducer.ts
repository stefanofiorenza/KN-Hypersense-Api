import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDeviceConfiguration, defaultValue } from 'app/shared/model/device-configuration.model';

export const ACTION_TYPES = {
  FETCH_DEVICECONFIGURATION_LIST: 'deviceConfiguration/FETCH_DEVICECONFIGURATION_LIST',
  FETCH_DEVICECONFIGURATION: 'deviceConfiguration/FETCH_DEVICECONFIGURATION',
  CREATE_DEVICECONFIGURATION: 'deviceConfiguration/CREATE_DEVICECONFIGURATION',
  UPDATE_DEVICECONFIGURATION: 'deviceConfiguration/UPDATE_DEVICECONFIGURATION',
  PARTIAL_UPDATE_DEVICECONFIGURATION: 'deviceConfiguration/PARTIAL_UPDATE_DEVICECONFIGURATION',
  DELETE_DEVICECONFIGURATION: 'deviceConfiguration/DELETE_DEVICECONFIGURATION',
  SET_BLOB: 'deviceConfiguration/SET_BLOB',
  RESET: 'deviceConfiguration/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDeviceConfiguration>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type DeviceConfigurationState = Readonly<typeof initialState>;

// Reducer

export default (state: DeviceConfigurationState = initialState, action): DeviceConfigurationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEVICECONFIGURATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEVICECONFIGURATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DEVICECONFIGURATION):
    case REQUEST(ACTION_TYPES.UPDATE_DEVICECONFIGURATION):
    case REQUEST(ACTION_TYPES.DELETE_DEVICECONFIGURATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DEVICECONFIGURATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DEVICECONFIGURATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEVICECONFIGURATION):
    case FAILURE(ACTION_TYPES.CREATE_DEVICECONFIGURATION):
    case FAILURE(ACTION_TYPES.UPDATE_DEVICECONFIGURATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DEVICECONFIGURATION):
    case FAILURE(ACTION_TYPES.DELETE_DEVICECONFIGURATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEVICECONFIGURATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEVICECONFIGURATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEVICECONFIGURATION):
    case SUCCESS(ACTION_TYPES.UPDATE_DEVICECONFIGURATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DEVICECONFIGURATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEVICECONFIGURATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/device-configurations';

// Actions

export const getEntities: ICrudGetAllAction<IDeviceConfiguration> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DEVICECONFIGURATION_LIST,
  payload: axios.get<IDeviceConfiguration>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IDeviceConfiguration> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEVICECONFIGURATION,
    payload: axios.get<IDeviceConfiguration>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDeviceConfiguration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEVICECONFIGURATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDeviceConfiguration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEVICECONFIGURATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDeviceConfiguration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DEVICECONFIGURATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDeviceConfiguration> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEVICECONFIGURATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
