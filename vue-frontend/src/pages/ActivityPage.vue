<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import ActivityRating from "@/components/ActivityRating.vue";

const store = useStore();
const route = useRoute();
const activityId = Number(route.params.id);

const activity = computed(() => store.getters["activities/getSelectedActivity"]);
const isLoading = computed(() => store.getters["activities/isLoading"]);
const error = computed(() => store.getters["activities/error"]);

onMounted(() => {
  if (!isNaN(activityId)) {
    store.dispatch("activities/fetchActivity", activityId);
  } else {
    store.commit("activities/SET_ERROR", "Invalid activity ID");
  }
});
</script>

<template>
  <div>
    <!-- Skeleton for loading -->
    <div v-if="isLoading" class="space-y-6">
      <div class="animate-pulse bg-gray-200 w-full h-64 rounded-t-lg" />
      <div class="animate-pulse bg-gray-200 h-8 w-4/5 mx-auto rounded-lg" />
      <div class="animate-pulse bg-gray-200 h-6 w-1/4 mx-auto rounded-lg" />
      <div class="animate-pulse bg-gray-200 h-6 w-2/5 mx-auto rounded-lg" />
      <div class="animate-pulse bg-gray-200 h-6 w-3/5 mx-auto rounded-lg" />
      <div class="animate-pulse bg-gray-200 h-6 w-3/5 mx-auto rounded-lg" />
    </div>

    <!-- Error message -->
    <div v-else-if="error" class="text-lg text-red-500">
      Error: {{ error }}
    </div>

    <!-- Activity details -->
    <div v-else-if="activity" class="bg-white shadow-lg overflow-hidden rounded">
      <img
        src="@/assets/dummy-image.webp"
        alt="Activity Image"
        class="w-full h-64 object-cover"
      />

      <div class="p-6">
        <h1 class="text-3xl font-semibold text-gray-800 mb-4">{{ activity.title }}</h1>

        <ActivityRating :rating="activity.rating" />

        <p v-if="activity.specialOffer" class="text-green-600 font-semibold mb-4">
          Special Offer Available!
        </p>

        <p class="text-gray-700 text-lg mb-4">
          <span class="font-semibold text-xl">From ${{ activity.price }}</span> per person
        </p>

        <div class="text-md text-gray-600 mb-4">
          <p class="font-semibold">Supplier: {{ activity.supplier.name }}</p>
          <p>{{ activity.supplier.address }}, {{ activity.supplier.zip }}</p>
          <p>{{ activity.supplier.city }}, {{ activity.supplier.country }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Optional custom styles */
</style>
