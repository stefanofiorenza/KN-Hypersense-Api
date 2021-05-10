import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IThingCategory, defaultValue } from 'app/shared/model/thing-category.model';

export const ACTION_TYPES = {
  FETCH_THINGCATEGORY_LIST: 'thingCategory/FETCH_THINGCATEGORY_LIST',
  FETCH_THINGCATEGORY: 'thingCategory/FETCH_THINGCATEGORY',
  CREATE_THINGCATEGORY: 'thingCategory/CREATE_THINGCATEGORY',
  UPDATE_THINGCATEGORY: 'thingCategory/UPDATE_THINGCATEGORY',
  PARTIAL_UPDATE_THINGCATEGORY: 'thingCategory/PARTIAL_UPDATE_THINGCATEGORY',
  DELETE_THINGCATEGORY: 'thingCategory/DELETE_THINGCATEGORY',
  RESET: 'thingCategory/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IThingCategory>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ThingCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: ThingCategoryState = initialState, action): ThingCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_THINGCATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_THINGCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_THINGCATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_THINGCATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_THINGCATEGORY):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_THINGCATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_THINGCATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_THINGCATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_THINGCATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_THINGCATEGORY):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_THINGCATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_THINGCATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_THINGCATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_THINGCATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_THINGCATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_THINGCATEGORY):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_THINGCATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_THINGCATEGORY):
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

const apiUrl = 'api/thing-categories';

// Actions

export const getEntities: ICrudGetAllAction<IThingCategory> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_THINGCATEGORY_LIST,
  payload: axios.get<IThingCategory>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IThingCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_THINGCATEGORY,
    payload: axios.get<IThingCategory>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IThingCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_THINGCATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IThingCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_THINGCATEGORY,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IThingCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_THINGCATEGORY,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IThingCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_THINGCATEGORY,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
