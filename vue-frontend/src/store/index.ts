import { createStore } from "vuex";
import { activitiesModule } from "./modules/activities"

const store = createStore<RootState>({
  modules: {
    activities: activitiesModule
  }
});

export default store;
