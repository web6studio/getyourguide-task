declare global {
  // Define the shape of the RootState. Extend this as your app grows.
  export interface RootState {}

  export interface Activity {
    id: number;
    title: string;
    price: number;
    currency: string;
  }

  interface PaginatedResponse<T> {
    data: T[];
    total: number;
    offset: number;
    limit: number;
  }

  interface ActivitiesState {
    activities: Activity[];
    total: number;
    offset: number;
    limit: number;
    selectedActivity: Activity | null;
    isLoading: boolean;
    error: string | null;
    abortController: AbortController | null
  }
}

export {};