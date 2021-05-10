import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConfigurationData, defaultValue } from 'app/shared/model/configuration-data.model';

export const ACTION_TYPES = {
  FETCH_CONFIGURATIONDATA_LIST: 'configurationData/FETCH_CONFIGURATIONDATA_LIST',
  FETCH_CONFIGURATIONDATA: 'configurationData/FETCH_CONFIGURATIONDATA',
  CREATE_CONFIGURATIONDATA: 'configurationData/CREATE_CONFIGURATIONDATA',
  UPDATE_CONFIGURATIONDATA: 'configurationData/UPDATE_CONFIGURATIONDATA',
  PARTIAL_UPDATE_CONFIGURATIONDATA: 'configurationData/PARTIAL_UPDATE_CONFIGURATIONDATA',
  DELETE_CONFIGURATIONDATA: 'configurationData/DELETE_CONFIGURATIONDATA',
  RESET: 'configurationData/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConfigurationData>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type ConfigurationDataState = Readonly<typeof initialState>;

// Reducer

export default (state: ConfigurationDataState = initialState, action): ConfigurationDataState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONFIGURATIONDATA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONFIGURATIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONFIGURATIONDATA):
    case REQUEST(ACTION_TYPES.UPDATE_CONFIGURATIONDATA):
    case REQUEST(ACTION_TYPES.DELETE_CONFIGURATIONDATA):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CONFIGURATIONDATA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONFIGURATIONDATA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONFIGURATIONDATA):
    case FAILURE(ACTION_TYPES.CREATE_CONFIGURATIONDATA):
    case FAILURE(ACTION_TYPES.UPDATE_CONFIGURATIONDATA):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CONFIGURATIONDATA):
    case FAILURE(ACTION_TYPES.DELETE_CONFIGURATIONDATA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFIGURATIONDATA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONFIGURATIONDATA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONFIGURATIONDATA):
    case SUCCESS(ACTION_TYPES.UPDATE_CONFIGURATIONDATA):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CONFIGURATIONDATA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONFIGURATIONDATA):
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

const apiUrl = 'api/configuration-data';

// Actions

export const getEntities: ICrudGetAllAction<IConfigurationData> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CONFIGURATIONDATA_LIST,
  payload: axios.get<IConfigurationData>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<IConfigurationData> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONFIGURATIONDATA,
    payload: axios.get<IConfigurationData>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConfigurationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONFIGURATIONDATA,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConfigurationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONFIGURATIONDATA,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<IConfigurationData> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CONFIGURATIONDATA,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConfigurationData> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONFIGURATIONDATA,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
