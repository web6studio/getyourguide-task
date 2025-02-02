declare global {
  // Define the shape of the RootState. Extend this as your app grows.
  type RootState = {}

  type Supplier = {
    id: number;
    name: string;
    address: string;
    zip: number;
    city: string;
    country: string;
  }

  type Activity = {
    id: number;
    title: string;
    price: number;
    currency: string;
    rating: number;
    specialOffer: boolean;
    supplier: Supplier;
  }

  type PaginatedResponse<T> = {
    data: T[];
    total: number;
    offset: number;
    limit: number;
  }

  type ActivitiesState = {
    activities: Activity[];
    total: number;
    offset: number;
    limit: number;
    selectedActivity: Activity | null;
    isLoading: boolean;
    error: string | null;
    abortController: AbortController | null;
  }
}

export {};
