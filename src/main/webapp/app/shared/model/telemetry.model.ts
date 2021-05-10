export interface ITelemetry {
  id?: number;
  name?: string | null;
  data?: string | null;
}

export const defaultValue: Readonly<ITelemetry> = {};
