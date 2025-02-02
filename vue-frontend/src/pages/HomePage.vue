<script setup lang="ts">
import { ref, computed } from "vue";
import { useStore } from "vuex";
import SearchInput from "@/components/SearchInput.vue";
import ActivitiesList from "@/components/ActivityList.vue";
import PaginationComponent from "@/components/PaginationComponent.vue";
import { DEFAULT_LIMIT } from "@/constants";

const store = useStore();
// Reactive state for search input
const searchQuery = ref("");

const activities = computed(() => store.getters["activities/getActivities"]);
const total = computed(() => store.getters["activities/total"]);
const offset = computed(() => store.getters["activities/offset"]);
const limit = computed(() => store.getters["activities/limit"]);
const loading = computed(() => store.getters["activities/isLoading"]);
const error = computed(() => store.getters["activities/error"]);

// Fetch activities from store
const fetchActivities = (query: string, offset: number, limit: number) => {
  store.dispatch("activities/fetchActivities", {
    title: query,
    offset,
    limit,
  });
};

// Pagination handlers
const nextPage = () => {
  if (offset.value + limit.value < total.value) {
    fetchActivities(searchQuery.value, offset.value + limit.value, limit.value);
  }
};

const prevPage = () => {
  if (offset.value > 0) {
    fetchActivities(searchQuery.value, Math.max(offset.value - limit.value, 0), limit.value);
  }
};

// When the search is updated, reset pagination and fetch activities
const onSearchUpdate = (query: string) => {
  fetchActivities(query, 0, limit.value);
};

// Initial data fetch
fetchActivities("", 0, DEFAULT_LIMIT);
</script>

<template>
  <div class="home">
    <h1 class="title">
      Activities
    </h1>

    <!-- Search Input with Reset Button -->
    <SearchInput @update:search="onSearchUpdate" />

    <!-- Activities List -->
    <ActivitiesList
      :activities="activities"
      :loading="loading"
      :error="error"
    />

    <!-- Pagination Component -->
    <PaginationComponent :offset="offset" :limit="limit" :total="total" @prev="prevPage" @next="nextPage" />
  </div>
</template>

<style scoped>
.title {
  font-size: 2rem;
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: center;
  gap: 10px;
  margin-top: 20px;
}

.pagination button {
  padding: 10px 20px;
  font-size: 1rem;
  cursor: pointer;
}
</style>
