import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IOrganisation, defaultValue } from 'app/shared/model/organisation.model';

export const ACTION_TYPES = {
  FETCH_ORGANISATION_LIST: 'organisation/FETCH_ORGANISATION_LIST',
  FETCH_ORGANISATION: 'organisation/FETCH_ORGANISATION',
  CREATE_ORGANISATION: 'organisation/CREATE_ORGANISATION',
  UPDATE_ORGANISATION: 'organisation/UPDATE_ORGANISATION',
  PARTIAL_UPDATE_ORGANISATION: 'organisation/PARTIAL_UPDATE_ORGANISATION',
  DELETE_ORGANISATION: 'organisation/DELETE_ORGANISATION',
  RESET: 'organisation/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IOrganisation>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type OrganisationState = Readonly<typeof initialState>;

// Reducer

export default (state: OrganisationState = initialState, action): OrganisationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ORGANISATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ORGANISATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_ORGANISATION):
    case REQUEST(ACTION_TYPES.UPDATE_ORGANISATION):
    case REQUEST(ACTION_TYPES.DELETE_ORGANISATION):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_ORGANISATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_ORGANISATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ORGANISATION):
    case FAILURE(ACTION_TYPES.CREATE_ORGANISATION):
    case FAILURE(ACTION_TYPES.UPDATE_ORGANISATION):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_ORGANISATION):
    case FAILURE(ACTION_TYPES.DELETE_ORGANISATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANISATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_ORGANISATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_ORGANISATION):
    case SUCCESS(ACTION_TYPES.UPDATE_ORGANISATION):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_ORGANISATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_ORGANISATION):
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

const apiUrl = 'api/organisations';

// Actions

export const getEntities: ICrudGetAllAction<IOrganisation> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ORGANISATION_LIST,
  payload: axios.get<IOrganisation>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IOrganisation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ORGANISATION,
    payload: axios.get<IOrganisation>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IOrganisation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ORGANISATION,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IOrganisation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ORGANISATION,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IOrganisation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_ORGANISATION,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IOrganisation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ORGANISATION,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
