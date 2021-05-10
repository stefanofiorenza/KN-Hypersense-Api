import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDeviceGroup, defaultValue } from 'app/shared/model/device-group.model';

export const ACTION_TYPES = {
  FETCH_DEVICEGROUP_LIST: 'deviceGroup/FETCH_DEVICEGROUP_LIST',
  FETCH_DEVICEGROUP: 'deviceGroup/FETCH_DEVICEGROUP',
  CREATE_DEVICEGROUP: 'deviceGroup/CREATE_DEVICEGROUP',
  UPDATE_DEVICEGROUP: 'deviceGroup/UPDATE_DEVICEGROUP',
  PARTIAL_UPDATE_DEVICEGROUP: 'deviceGroup/PARTIAL_UPDATE_DEVICEGROUP',
  DELETE_DEVICEGROUP: 'deviceGroup/DELETE_DEVICEGROUP',
  RESET: 'deviceGroup/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDeviceGroup>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type DeviceGroupState = Readonly<typeof initialState>;

// Reducer

export default (state: DeviceGroupState = initialState, action): DeviceGroupState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_DEVICEGROUP_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEVICEGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DEVICEGROUP):
    case REQUEST(ACTION_TYPES.UPDATE_DEVICEGROUP):
    case REQUEST(ACTION_TYPES.DELETE_DEVICEGROUP):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_DEVICEGROUP):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_DEVICEGROUP_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEVICEGROUP):
    case FAILURE(ACTION_TYPES.CREATE_DEVICEGROUP):
    case FAILURE(ACTION_TYPES.UPDATE_DEVICEGROUP):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_DEVICEGROUP):
    case FAILURE(ACTION_TYPES.DELETE_DEVICEGROUP):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEVICEGROUP_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEVICEGROUP):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEVICEGROUP):
    case SUCCESS(ACTION_TYPES.UPDATE_DEVICEGROUP):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_DEVICEGROUP):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEVICEGROUP):
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

const apiUrl = 'api/device-groups';

// Actions

export const getEntities: ICrudGetAllAction<IDeviceGroup> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DEVICEGROUP_LIST,
  payload: axios.get<IDeviceGroup>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IDeviceGroup> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEVICEGROUP,
    payload: axios.get<IDeviceGroup>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDeviceGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEVICEGROUP,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDeviceGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEVICEGROUP,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IDeviceGroup> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_DEVICEGROUP,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDeviceGroup> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEVICEGROUP,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
