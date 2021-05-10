import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITelemetry, defaultValue } from 'app/shared/model/telemetry.model';

export const ACTION_TYPES = {
  FETCH_TELEMETRY_LIST: 'telemetry/FETCH_TELEMETRY_LIST',
  FETCH_TELEMETRY: 'telemetry/FETCH_TELEMETRY',
  CREATE_TELEMETRY: 'telemetry/CREATE_TELEMETRY',
  UPDATE_TELEMETRY: 'telemetry/UPDATE_TELEMETRY',
  PARTIAL_UPDATE_TELEMETRY: 'telemetry/PARTIAL_UPDATE_TELEMETRY',
  DELETE_TELEMETRY: 'telemetry/DELETE_TELEMETRY',
  RESET: 'telemetry/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITelemetry>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type TelemetryState = Readonly<typeof initialState>;

// Reducer

export default (state: TelemetryState = initialState, action): TelemetryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TELEMETRY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TELEMETRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TELEMETRY):
    case REQUEST(ACTION_TYPES.UPDATE_TELEMETRY):
    case REQUEST(ACTION_TYPES.DELETE_TELEMETRY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_TELEMETRY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TELEMETRY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TELEMETRY):
    case FAILURE(ACTION_TYPES.CREATE_TELEMETRY):
    case FAILURE(ACTION_TYPES.UPDATE_TELEMETRY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_TELEMETRY):
    case FAILURE(ACTION_TYPES.DELETE_TELEMETRY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TELEMETRY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TELEMETRY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TELEMETRY):
    case SUCCESS(ACTION_TYPES.UPDATE_TELEMETRY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_TELEMETRY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TELEMETRY):
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

const apiUrl = 'api/telemetries';

// Actions

export const getEntities: ICrudGetAllAction<ITelemetry> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_TELEMETRY_LIST,
  payload: axios.get<ITelemetry>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ITelemetry> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TELEMETRY,
    payload: axios.get<ITelemetry>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITelemetry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TELEMETRY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITelemetry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TELEMETRY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ITelemetry> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_TELEMETRY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITelemetry> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TELEMETRY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
