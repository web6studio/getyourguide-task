<script setup lang="ts">
import { computed, onMounted } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';

const store = useStore();
const router = useRouter();
const activities = computed(() => store.getters['activities/getActivities']);
const isLoading = computed(() => store.getters['activities/isLoading']);
const error = computed(() => store.getters['activities/error']);

onMounted(() => {
  store.dispatch('activities/fetchActivities');
});

const goToActivityPage = (activityId: number) => {
  router.push({ name: 'activity', params: { id: activityId } });
};
</script>

<template>
  <div class="activities__container">
    <div v-if="isLoading">
      Loading...
    </div>

    <div v-if="error">
      Error: {{ error }}
    </div>
  
    <div v-if="!isLoading && !error && activities.length === 0">
      No activities found.
    </div>
    
    <div v-else>
      <div
        v-for="activity in activities"
        :key="activity.id"
        class="activities__activity"
        @click="goToActivityPage(activity.id)"
      >
        <h3>{{ activity.title }}</h3>
        <p>Price: {{ activity.price }}</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Container styles for the activities list */
.activities__container {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  justify-content: center;
}

/* Styles for each individual activity card */
.activities__activity {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 20px;
  width: 300px;
  text-align: center;
  transition: box-shadow 0.3s ease;
  cursor: pointer;
}

.activities__activity:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}
</style>
