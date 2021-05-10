import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAlertMessage, defaultValue } from 'app/shared/model/alert-message.model';

export const ACTION_TYPES = {
  FETCH_ALERTMESSAGE_LIST: 'alertMessage/FETCH_ALERTMESSAGE_LIST',
  FETCH_ALERTMESSAGE: 'alertMessage/FETCH_ALERTMESSAGE',
  CREATE_ALERTMESSAGE: 'alertMessage/CREATE_ALERTMESSAGE',
  UPDATE_ALERTMESSAGE: 'alertMessage/UPDATE_ALERTMESSAGE',
  PARTIAL_UPDATE_ALERTMESSAGE: 'alertMessage/PARTIAL_UPDATE_ALERTMESSAGE',
  DELETE_ALERTMESSAGE: 'alertMessage/DELETE_ALERTMESSAGE',
  RESET: 'alertMessage/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAlertMessage>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type AlertMessageState = Readonly<typeof initialState>;

// Reducer

export default (state: AlertMessageState = initialState, action): AlertMessageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ALERTMESSAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ALERTMESSAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ALERTMESSAGE):
    case REQUEST(ACTION_TYPES.UPDATE_ALERTMESSAGE):
    case REQUEST(ACTION_TYPES.DELETE_ALERTMESSAGE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ALERTMESSAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ALERTMESSAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ALERTMESSAGE):
    case FAILURE(ACTION_TYPES.CREATE_ALERTMESSAGE):
    case FAILURE(ACTION_TYPES.UPDATE_ALERTMESSAGE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ALERTMESSAGE):
    case FAILURE(ACTION_TYPES.DELETE_ALERTMESSAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALERTMESSAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ALERTMESSAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ALERTMESSAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_ALERTMESSAGE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ALERTMESSAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ALERTMESSAGE):
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

const apiUrl = 'api/alert-messages';

// Actions

export const getEntities: ICrudGetAllAction<IAlertMessage> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ALERTMESSAGE_LIST,
  payload: axios.get<IAlertMessage>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IAlertMessage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ALERTMESSAGE,
    payload: axios.get<IAlertMessage>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IAlertMessage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ALERTMESSAGE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAlertMessage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ALERTMESSAGE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IAlertMessage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ALERTMESSAGE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAlertMessage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ALERTMESSAGE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
