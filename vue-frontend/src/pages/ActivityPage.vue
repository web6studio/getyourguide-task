<script setup lang="ts">
import { computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { useStore } from "vuex";

const store = useStore();
const route = useRoute();
const activityId = Number(route.params.id);

const activity = computed(() => store.getters["activities/getSelectedActivity"]);
const isLoading = computed(() => store.getters["activities/isLoading"]);
const error = computed(() => store.getters["activities/error"]);

// When the component mounts, dispatch the action to fetch the activity details
onMounted(() => {
  if (!isNaN(activityId)) {
    store.dispatch("activities/fetchActivity", activityId);
  } else {
    store.commit("activities/SET_ERROR", "Invalid activity ID");
  }
});
</script>

<template>
  <div class="activity-page">
    <!-- Loading indicator -->
    <div
      v-if="isLoading"
      class="loading"
    >
      Loading activity data...
    </div>

    <!-- Error message -->
    <div
      v-else-if="error"
      class="error"
    >
      Error: {{ error }}
    </div>

    <!-- Activity details -->
    <div
      v-else-if="activity"
      class="activity-details"
    >
      <h1>{{ activity.title }}</h1>
      <p>Price: ${{ activity.price }}</p>
      <p>Rating: {{ activity.rating }}</p>
      <p
        v-if="activity.specialOffer"
        class="special-offer"
      >
        Special Offer Available!
      </p>

      <!-- Supplier details -->
      <div
        v-if="activity.supplier"
        class="supplier-details"
      >
        <p>Supplier: {{ activity.supplier.name }}</p>
        <p>Address: {{ activity.supplier.address }}, {{ activity.supplier.zip }}, {{ activity.supplier.city }}, {{ activity.supplier.country }}</p>
      </div>
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
  margin-bottom: 10px;
}

.special-offer {
  color: green;
  font-weight: bold;
}

.no-offer {
  color: #555;
}

.supplier-details p {
  font-size: 1rem;
  margin: 5px 0;
}
</style>
