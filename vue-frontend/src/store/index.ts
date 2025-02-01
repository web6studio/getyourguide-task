import { createStore } from 'vuex';
import { activitiesModule } from './modules/activities'
import { RootState } from './types';

const store = createStore<RootState>({
  modules: {
    activities: activitiesModule
  }
});

export default store;
