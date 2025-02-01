import { Module } from 'vuex';
import { RootState } from '../types';
import { Activity, fetchActivitiesService, fetchActivityService } from '@/services/activitiesService';

export interface ActivitiesState {
  activities: Activity[];
  selectedActivity: Activity | null;
  isLoading: boolean;
  error: string | null;
}

const state: ActivitiesState = {
  activities: [],
  selectedActivity: null,
  isLoading: false,
  error: null
};

export const activitiesModule: Module<ActivitiesState, RootState> = {
  namespaced: true,
  state,
  mutations: {
    SET_ACTIVITIES(state, activities: Activity[]) {
      state.activities = activities;
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
    }
  },
  actions: {
    async fetchActivities({ commit }) {
      commit('SET_LOADING', true);
      try {
        const data = await fetchActivitiesService();
        commit('SET_ACTIVITIES', data);
      } catch (error) {
        commit('SET_ERROR', 'Error fetching activities');
        console.error('Error fetching activities:', error);
      }
    },
    async fetchActivity({ commit }, id: number) {
      commit('SET_LOADING', true);
      try {
        const activity = await fetchActivityService(id);
        commit('SET_SELECTED_ACTIVITY', activity);
      } catch (error) {
        commit('SET_ERROR', `Error fetching activity with ID: ${id}`);
        console.error('Error fetching activity:', error);
      }
    }
  },
  getters: {
    getActivities: (state): Activity[] => state.activities,
    getSelectedActivity: (state): Activity | null => state.selectedActivity,
    isLoading: (state): boolean => state.isLoading,
    error: (state): string | null => state.error
  }
};
