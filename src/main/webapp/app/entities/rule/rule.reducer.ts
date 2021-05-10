import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRule, defaultValue } from 'app/shared/model/rule.model';

export const ACTION_TYPES = {
  FETCH_RULE_LIST: 'rule/FETCH_RULE_LIST',
  FETCH_RULE: 'rule/FETCH_RULE',
  CREATE_RULE: 'rule/CREATE_RULE',
  UPDATE_RULE: 'rule/UPDATE_RULE',
  PARTIAL_UPDATE_RULE: 'rule/PARTIAL_UPDATE_RULE',
  DELETE_RULE: 'rule/DELETE_RULE',
  RESET: 'rule/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRule>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type RuleState = Readonly<typeof initialState>;

// Reducer

export default (state: RuleState = initialState, action): RuleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RULE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_RULE):
    case REQUEST(ACTION_TYPES.UPDATE_RULE):
    case REQUEST(ACTION_TYPES.DELETE_RULE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_RULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_RULE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RULE):
    case FAILURE(ACTION_TYPES.CREATE_RULE):
    case FAILURE(ACTION_TYPES.UPDATE_RULE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_RULE):
    case FAILURE(ACTION_TYPES.DELETE_RULE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RULE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_RULE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_RULE):
    case SUCCESS(ACTION_TYPES.UPDATE_RULE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_RULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_RULE):
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

const apiUrl = 'api/rules';

// Actions

export const getEntities: ICrudGetAllAction<IRule> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RULE_LIST,
  payload: axios.get<IRule>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IRule> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RULE,
    payload: axios.get<IRule>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IRule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RULE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RULE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IRule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_RULE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRule> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RULE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
