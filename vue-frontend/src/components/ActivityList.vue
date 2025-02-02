<script setup lang="ts">
import ActivityCard from "@/components/ActivityCard.vue";

// Props from parent (HomePage.vue)
defineProps<{
  activities: Activity[];
  loading: boolean;
  error: string | null;
}>();
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
      v-else
      class="activities-list"
    >
      <ActivityCard 
        v-for="activity in activities" 
        :key="activity.id" 
        :activity="activity"
      />
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
