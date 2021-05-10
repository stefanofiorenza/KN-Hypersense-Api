import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDeviceModel, defaultValue } from 'app/shared/model/device-model.model';

export const ACTION_TYPES = {
  FETCH_DEVICEMODEL_LIST: 'deviceModel/FETCH_DEVICEMODEL_LIST',
  FETCH_DEVICEMODEL: 'deviceModel/FETCH_DEVICEMODEL',
  CREATE_DEVICEMODEL: 'deviceModel/CREATE_DEVICEMODEL',
  UPDATE_DEVICEMODEL: 'deviceModel/UPDATE_DEVICEMODEL',
  PARTIAL_UPDATE_DEVICEMODEL: 'deviceModel/PARTIAL_UPDATE_DEVICEMODEL',
  DELETE_DEVICEMODEL: 'deviceModel/DELETE_DEVICEMODEL',
  RESET: 'deviceModel/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDeviceModel>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type DeviceModelState = Readonly<typeof initialState>;

// Reducer

export default (state: DeviceModelState = initialState, action): DeviceModelState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEVICEMODEL_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEVICEMODEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DEVICEMODEL):
    case REQUEST(ACTION_TYPES.UPDATE_DEVICEMODEL):
    case REQUEST(ACTION_TYPES.DELETE_DEVICEMODEL):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DEVICEMODEL):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DEVICEMODEL_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEVICEMODEL):
    case FAILURE(ACTION_TYPES.CREATE_DEVICEMODEL):
    case FAILURE(ACTION_TYPES.UPDATE_DEVICEMODEL):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DEVICEMODEL):
    case FAILURE(ACTION_TYPES.DELETE_DEVICEMODEL):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEVICEMODEL_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEVICEMODEL):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEVICEMODEL):
    case SUCCESS(ACTION_TYPES.UPDATE_DEVICEMODEL):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DEVICEMODEL):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEVICEMODEL):
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

const apiUrl = 'api/device-models';

// Actions

export const getEntities: ICrudGetAllAction<IDeviceModel> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DEVICEMODEL_LIST,
  payload: axios.get<IDeviceModel>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IDeviceModel> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEVICEMODEL,
    payload: axios.get<IDeviceModel>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDeviceModel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEVICEMODEL,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDeviceModel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEVICEMODEL,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDeviceModel> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DEVICEMODEL,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDeviceModel> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEVICEMODEL,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
