<script setup lang="ts">
import { useRouter } from "vue-router";

// Props from parent (HomePage.vue)
defineProps<{
  activities: Activity[];
  loading: boolean;
  error: string | null;
}>();

const router = useRouter();

// Routing
const goToActivityPage = (activityId: number) => {
  router.push({ name: "activity", params: { id: activityId } });
};
</script>

<template>
  <div class="activities__container">
    <p
      v-if="loading"
      class="loading"
    >
      Loading...
    </p>
    <p
      v-else-if="error"
      class="error"
    >
      Error: {{ error }}
    </p>
    <p v-else-if="activities?.length === 0">
      No activities found.
    </p>

    <div
      v-for="activity in activities"
      v-else
      :key="activity.id"
      class="activities__activity"
      @click="goToActivityPage(activity.id)"
    >
      <h3>{{ activity.title }}</h3>
      <p>Price: {{ activity.price }} {{ activity.currency }}</p>
    </div>
  </div>
</template>

<style scoped>
.activities__container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.activities__activity {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  max-width: 300px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.activities__activity:hover {
  border-color: #000;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.loading {
  color: blue;
}

.error {
  color: red;
}
</style>
