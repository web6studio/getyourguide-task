<script setup lang="ts">
import { ref, computed, watch } from "vue";
import { useStore } from "vuex";
import SearchInput from "@/components/SearchInput.vue";
import ActivitiesList from "@/components/ActivityList.vue";
import PaginationComponent from "@/components/PaginationComponent.vue";
import { DEFAULT_LIMIT } from "@/constants";

const store = useStore();

// Reactive state for filters
const searchQuery = ref("");
const minPrice = ref<number | null>(null);
const maxPrice = ref<number | null>(null);
const minRating = ref<number | null>(null);
const specialOffer = ref<boolean | null>(null);
const sortBy = ref<string>("title");
const sortOrder = ref<string>("asc");

const activities = computed(() => store.getters["activities/getActivities"]);
const total = computed(() => store.getters["activities/total"]);
const offset = computed(() => store.getters["activities/offset"]);
const limit = computed(() => store.getters["activities/limit"]);
const loading = computed(() => store.getters["activities/isLoading"]);
const error = computed(() => store.getters["activities/error"]);

// Fetch activities from store
const fetchActivities = () => {
  store.dispatch("activities/fetchActivities", {
    title: searchQuery.value,
    offset: offset.value,
    limit: limit.value,
    minPrice: minPrice.value,
    maxPrice: maxPrice.value,
    minRating: minRating.value,
    specialOffer: specialOffer.value,
    sortBy: sortBy.value,
    sortOrder: sortOrder.value
  });
};

// Debounce search input
watch(searchQuery, fetchActivities);

// Pagination handlers
const nextPage = () => {
  if (offset.value + limit.value < total.value) {
    store.dispatch("activities/fetchActivities", {
      title: searchQuery.value,
      offset: offset.value + limit.value,
      limit: limit.value,
      minPrice: minPrice.value,
      maxPrice: maxPrice.value,
      minRating: minRating.value,
      specialOffer: specialOffer.value,
      sortBy: sortBy.value,
      sortOrder: sortOrder.value
    });
  }
};

const prevPage = () => {
  if (offset.value > 0) {
    store.dispatch("activities/fetchActivities", {
      title: searchQuery.value,
      offset: Math.max(offset.value - limit.value, 0),
      limit: limit.value,
      minPrice: minPrice.value,
      maxPrice: maxPrice.value,
      minRating: minRating.value,
      specialOffer: specialOffer.value,
      sortBy: sortBy.value,
      sortOrder: sortOrder.value
    });
  }
};

// Handle filter changes
const applyFilters = () => {
  store.dispatch("activities/fetchActivities", {
    title: searchQuery.value,
    offset: 0, // Reset pagination when applying filters
    limit: limit.value,
    minPrice: minPrice.value,
    maxPrice: maxPrice.value,
    minRating: minRating.value,
    specialOffer: specialOffer.value,
    sortBy: sortBy.value,
    sortOrder: sortOrder.value
  });
};

// Initial fetch
fetchActivities();
</script>

<template>
  <div>
    <h1 class="text-4xl font-semibold text-gray-900 mb-6">
      Activities
    </h1>

    <!-- Search Input -->
    <SearchInput
      class="mb-4 w-full max-w-lg"
      @update:search="fetchActivities"
    />

    <!-- Filters -->
    <div class="flex flex-wrap gap-4 mb-6">
      <input v-model.number="minPrice" type="number" placeholder="Min Price" class="input-field">
      <input v-model.number="maxPrice" type="number" placeholder="Max Price" class="input-field">
      <input v-model.number="minRating" type="number" step="0.1" min="0" max="5" placeholder="Min Rating" class="input-field">

      <select v-model="specialOffer" class="input-field">
        <option :value="null">All Offers</option>
        <option :value="true">Only Special Offers</option>
      </select>

      <select v-model="sortBy" class="input-field">
        <option value="title">Sort by Title</option>
        <option value="price">Sort by Price</option>
        <option value="rating">Sort by Rating</option>
      </select>

      <select v-model="sortOrder" class="input-field">
        <option value="asc">Ascending</option>
        <option value="desc">Descending</option>
      </select>

      <button @click="applyFilters" class="btn-primary">Apply Filters</button>
    </div>

    <!-- Activities List -->
    <ActivitiesList
      :activities="activities"
      :loading="loading"
      :error="error"
    />

    <!-- Pagination Component (Hidden if no activities) -->
    <div
      v-if="activities.length > 0 && !loading && total > limit"
      class="mt-6"
    >
      <PaginationComponent
        :offset="offset"
        :limit="limit"
        :total="total"
        @prev="prevPage"
        @next="nextPage"
      />
    </div>
  </div>
</template>

<style scoped>
.input-field {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: white;
}

.btn-primary {
  background-color: #ff542f;
  color: white;
  padding: 8px 12px;
  border-radius: 8px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #e04b27;
}
</style>
