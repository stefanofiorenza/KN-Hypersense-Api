import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRuleConfiguration, defaultValue } from 'app/shared/model/rule-configuration.model';

export const ACTION_TYPES = {
  FETCH_RULECONFIGURATION_LIST: 'ruleConfiguration/FETCH_RULECONFIGURATION_LIST',
  FETCH_RULECONFIGURATION: 'ruleConfiguration/FETCH_RULECONFIGURATION',
  CREATE_RULECONFIGURATION: 'ruleConfiguration/CREATE_RULECONFIGURATION',
  UPDATE_RULECONFIGURATION: 'ruleConfiguration/UPDATE_RULECONFIGURATION',
  PARTIAL_UPDATE_RULECONFIGURATION: 'ruleConfiguration/PARTIAL_UPDATE_RULECONFIGURATION',
  DELETE_RULECONFIGURATION: 'ruleConfiguration/DELETE_RULECONFIGURATION',
  RESET: 'ruleConfiguration/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRuleConfiguration>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type RuleConfigurationState = Readonly<typeof initialState>;

// Reducer

export default (state: RuleConfigurationState = initialState, action): RuleConfigurationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RULECONFIGURATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RULECONFIGURATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RULECONFIGURATION):
    case REQUEST(ACTION_TYPES.UPDATE_RULECONFIGURATION):
    case REQUEST(ACTION_TYPES.DELETE_RULECONFIGURATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_RULECONFIGURATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RULECONFIGURATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RULECONFIGURATION):
    case FAILURE(ACTION_TYPES.CREATE_RULECONFIGURATION):
    case FAILURE(ACTION_TYPES.UPDATE_RULECONFIGURATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_RULECONFIGURATION):
    case FAILURE(ACTION_TYPES.DELETE_RULECONFIGURATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RULECONFIGURATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RULECONFIGURATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RULECONFIGURATION):
    case SUCCESS(ACTION_TYPES.UPDATE_RULECONFIGURATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_RULECONFIGURATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RULECONFIGURATION):
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

const apiUrl = 'api/rule-configurations';

// Actions

export const getEntities: ICrudGetAllAction<IRuleConfiguration> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RULECONFIGURATION_LIST,
  payload: axios.get<IRuleConfiguration>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IRuleConfiguration> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RULECONFIGURATION,
    payload: axios.get<IRuleConfiguration>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRuleConfiguration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RULECONFIGURATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRuleConfiguration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RULECONFIGURATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IRuleConfiguration> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_RULECONFIGURATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRuleConfiguration> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RULECONFIGURATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
