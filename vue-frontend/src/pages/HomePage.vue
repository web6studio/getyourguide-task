<script setup lang="ts">
import { ref, computed, watch } from "vue";
import { useStore } from "vuex";

import { debounce } from "@/utils";
import { DEFAULT_LIMIT } from "@/constants";
import ActivitiesList from "@/components/ActivityList.vue";

// Vuex store
const store = useStore();

// Reactive state for search input
const searchQuery = ref("");

// Computed properties from Vuex
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
    offset: 0, // Reload Pagination on new search
    limit: DEFAULT_LIMIT,
  });
};

// Debounce search (300ms delay)
const debouncedFetch = debounce(fetchActivities, 300);
watch(searchQuery, debouncedFetch);

// Pagination handlers
const nextPage = () => {
  if (offset.value + limit.value < total.value) {
    store.dispatch("activities/fetchActivities", {
      title: searchQuery.value,
      offset: offset.value + limit.value,
      limit: limit.value,
    });
  }
};

const prevPage = () => {
  if (offset.value > 0) {
    store.dispatch("activities/fetchActivities", {
      title: searchQuery.value,
      offset: Math.max(offset.value - limit.value, 0),
      limit: limit.value,
    });
  }
};

// Function to reset the search input
const resetSearch = () => {
  searchQuery.value = "";
  fetchActivities();
};

// Initial data fetch
fetchActivities();
</script>

<template>
  <div class="home">
    <h1 class="title">Activities</h1>

    <!-- Search Input with Reset Button -->
    <div class="search-input-wrapper">
      <input
        v-model="searchQuery"
        type="text"
        placeholder="Search activities..."
        class="search-input"
      />
      <!-- Reset button appears only when searchQuery is non-empty -->
      <button v-if="searchQuery" @click="resetSearch" class="reset-button">X</button>
    </div>

    <!-- Activities List -->
    <ActivitiesList
      :activities="activities"
      :loading="loading"
      :error="error"
    />

    <!-- Pagination -->
    <div class="pagination">
      <button
        :disabled="offset === 0"
        @click="prevPage"
      >
        Prev
      </button>
      <span>{{ offset + 1 }} - {{ Math.min(offset + limit, total) }} of {{ total }}</span>
      <button
        :disabled="offset + limit >= total"
        @click="nextPage"
      >
        Next
      </button>
    </div>
  </div>
</template>

<style scoped>
.title {
  font-size: 2rem;
  margin-bottom: 20px;
}

.search-input-wrapper {
  position: relative;
  width: 100%;
  margin-bottom: 20px;
}

.search-input {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  box-sizing: border-box;
}

.reset-button {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  background: transparent;
  border: none;
  font-size: 1rem;
  cursor: pointer;
  color: #888;
}

.reset-button:hover {
  color: #333;
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
