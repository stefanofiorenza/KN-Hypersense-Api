import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IThing, defaultValue } from 'app/shared/model/thing.model';

export const ACTION_TYPES = {
  FETCH_THING_LIST: 'thing/FETCH_THING_LIST',
  FETCH_THING: 'thing/FETCH_THING',
  CREATE_THING: 'thing/CREATE_THING',
  UPDATE_THING: 'thing/UPDATE_THING',
  PARTIAL_UPDATE_THING: 'thing/PARTIAL_UPDATE_THING',
  DELETE_THING: 'thing/DELETE_THING',
  RESET: 'thing/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IThing>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ThingState = Readonly<typeof initialState>;

// Reducer

export default (state: ThingState = initialState, action): ThingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_THING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_THING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_THING):
    case REQUEST(ACTION_TYPES.UPDATE_THING):
    case REQUEST(ACTION_TYPES.DELETE_THING):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_THING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_THING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_THING):
    case FAILURE(ACTION_TYPES.CREATE_THING):
    case FAILURE(ACTION_TYPES.UPDATE_THING):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_THING):
    case FAILURE(ACTION_TYPES.DELETE_THING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_THING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_THING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_THING):
    case SUCCESS(ACTION_TYPES.UPDATE_THING):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_THING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_THING):
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

const apiUrl = 'api/things';

// Actions

export const getEntities: ICrudGetAllAction<IThing> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_THING_LIST,
  payload: axios.get<IThing>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IThing> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_THING,
    payload: axios.get<IThing>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IThing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_THING,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IThing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_THING,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IThing> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_THING,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IThing> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_THING,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
