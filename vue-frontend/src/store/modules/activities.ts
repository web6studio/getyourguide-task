import { Module } from "vuex";
import { fetchActivitiesService, fetchActivityService } from "@/services/activitiesService";
import { DEFAULT_LIMIT } from "@/constants";

const state: ActivitiesState = {
  activities: [],
  total: 0,
  offset: 0,
  limit: DEFAULT_LIMIT,
  selectedActivity: null,
  isLoading: false,
  error: null,
  abortController: null,
};

export const activitiesModule: Module<ActivitiesState, RootState> = {
  namespaced: true,
  state,
  mutations: {
    SET_ACTIVITIES(state, payload: PaginatedResponse<Activity>) {
      state.activities = payload.data;
      state.total = payload.total;
      state.offset = payload.offset;
      state.limit = payload.limit;
      state.isLoading = false;
    },
    SET_SELECTED_ACTIVITY(state, activity: Activity) {
      state.selectedActivity = activity;
      state.isLoading = false;
    },
    SET_LOADING(state, isLoading: boolean) {
      state.isLoading = isLoading;
    },
    SET_ERROR(state, error: string) {
      state.error = error;
      state.isLoading = false;
    },
    SET_ABORT_CONTROLLER(state, controller: AbortController | null) {
      state.abortController = controller;
    },
  },
  actions: {
    async fetchActivities({ commit }, payload = {}) {
      const { title = "", offset = 0, limit = DEFAULT_LIMIT } = payload;

      // Fix Race Conditions
      // If the request is still not completed, cancel it.
      if (state.abortController) {
        state.abortController.abort();
      }
      const controller = new AbortController();
      commit("SET_ABORT_CONTROLLER", controller);

      commit("SET_LOADING", true);
      commit("SET_ERROR", null);
      try {
        const response = await fetchActivitiesService(title, offset, limit);
        commit("SET_ACTIVITIES", response);
      } catch (error) {
        commit("SET_ERROR", "Error fetching activities");
        console.error("Error fetching activities:", error);
      }
    },
    async fetchActivity({ commit }, id: number) {
      commit("SET_LOADING", true);
      commit("SET_ERROR", null);
      try {
        const activity = await fetchActivityService(id);
        commit("SET_SELECTED_ACTIVITY", activity);
      } catch (error) {
        commit("SET_ERROR", `Error fetching activity with ID: ${id}`);
        console.error("Error fetching activity:", error);
      }
    },
  },
  getters: {
    getActivities: (state): Activity[] => state.activities,
    total: (state) => state.total,
    offset: (state) => state.offset,
    limit: (state) => state.limit,
    getSelectedActivity: (state): Activity | null => state.selectedActivity,
    isLoading: (state): boolean => state.isLoading,
    error: (state): string | null => state.error,
  },
};
