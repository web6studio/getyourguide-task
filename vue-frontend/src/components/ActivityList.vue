<script setup lang="ts">
import ActivityCard from "@/components/ActivityCard.vue";

defineProps<{
  activities: Activity[];
  loading: boolean;
  error: string | null;
}>();
</script>

<template>
  <div>
    <p
      v-if="error"
      class="text-red-500 text-lg text-center"
    >
      Error: {{ error }}
    </p>
    <p
      v-else-if="activities?.length === 0"
      class="text-gray-500 text-lg"
    >
      No activities found.
    </p>

    <!-- Skeleton Loader -->
    <div
      v-else-if="loading"
      class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6"
    >
      <div
        v-for="n in 4"
        :key="n"
        class="animate-pulse bg-gray-200 rounded-xl h-60"
      />
    </div>

    <!-- Activities List -->
    <div
      v-else
      class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6"
    >
      <ActivityCard 
        v-for="activity in activities" 
        :key="activity.id" 
        :activity="activity"
      />
    </div>
  </div>
</template>
