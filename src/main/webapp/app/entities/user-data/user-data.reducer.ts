import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserData, defaultValue } from 'app/shared/model/user-data.model';

export const ACTION_TYPES = {
  FETCH_USERDATA_LIST: 'userData/FETCH_USERDATA_LIST',
  FETCH_USERDATA: 'userData/FETCH_USERDATA',
  CREATE_USERDATA: 'userData/CREATE_USERDATA',
  UPDATE_USERDATA: 'userData/UPDATE_USERDATA',
  PARTIAL_UPDATE_USERDATA: 'userData/PARTIAL_UPDATE_USERDATA',
  DELETE_USERDATA: 'userData/DELETE_USERDATA',
  RESET: 'userData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserData>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type UserDataState = Readonly<typeof initialState>;

// Reducer

export default (state: UserDataState = initialState, action): UserDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_USERDATA):
    case REQUEST(ACTION_TYPES.UPDATE_USERDATA):
    case REQUEST(ACTION_TYPES.DELETE_USERDATA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_USERDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_USERDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERDATA):
    case FAILURE(ACTION_TYPES.CREATE_USERDATA):
    case FAILURE(ACTION_TYPES.UPDATE_USERDATA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_USERDATA):
    case FAILURE(ACTION_TYPES.DELETE_USERDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_USERDATA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_USERDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERDATA):
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

const apiUrl = 'api/user-data';

// Actions

export const getEntities: ICrudGetAllAction<IUserData> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_USERDATA_LIST,
  payload: axios.get<IUserData>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IUserData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERDATA,
    payload: axios.get<IUserData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IUserData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERDATA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IUserData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_USERDATA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
