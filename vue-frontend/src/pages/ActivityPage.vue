<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useStore } from 'vuex';

const store = useStore();
const route = useRoute();
const activityId = Number(route.params.id);

const activity = computed(() => store.getters['activities/getSelectedActivity']);
const isLoading = computed(() => store.getters['activities/isLoading']);
const error = computed(() => store.getters['activities/error']);

onMounted(() => {
  if (!isNaN(activityId)) {
    store.dispatch('activities/fetchActivity', activityId);
  } else {
    store.commit('activities/SET_ERROR', 'Invalid activity ID');
  }
});
</script>

<template>
  <div
    class="activity-page"
  >
    <div
      v-if="isLoading"
      class="loading"
    >
      Loading activity data...
    </div>

    <div
      v-else-if="error"
      class="error"
    >
      Error: {{ error }}
    </div>

    <div
      v-else-if="activity"
      class="activity-details"
    >
      <h1>{{ activity.title }}</h1>
      <p>Price: ${{ activity.price }}</p>
    </div>
  </div>
</template>

<style scoped>
.activity-page {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

.loading {
  font-size: 1.2rem;
  color: #007bff;
}

.error {
  font-size: 1.2rem;
  color: red;
}

.activity-details h1 {
  font-size: 2rem;
  margin-bottom: 15px;
}

.activity-details p {
  font-size: 1.2rem;
}
</style>
