import { Module } from 'vuex';
import { RootState } from '../types';
import { Activity, fetchActivitiesService } from '@/services/activitiesService';

export interface ActivitiesState {
  activities: Activity[];
  isLoading: boolean;
  error: string | null;
};

const state: ActivitiesState = {
  activities: [],
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
    }
  },
  getters: {
    getActivities: (state): Activity[] => state.activities,
    isLoading: (state): boolean => state.isLoading,
    error: (state): string | null => state.error
  }
};
