<script setup lang="ts">
import { ref, watch, defineEmits } from "vue";
import { debounce } from "@/utils";

// Define events that this component can emit
const emit = defineEmits<{
  (e: "update:search", value: string): void;
}>();

// Local reactive state for the search query
const searchQuery = ref("");

// Debounce the update event (300ms delay)
const debouncedEmit = debounce(() => {
  emit("update:search", searchQuery.value);
}, 300);

// Watch for changes in the search query and emit the update
watch(searchQuery, debouncedEmit);

// Function to clear the search input and emit an update
const clearSearch = () => {
  searchQuery.value = "";
};
</script>

<template>
  <div class="search-input-wrapper">
    <input
      v-model="searchQuery"
      type="text"
      placeholder="Search activities..."
      class="search-input"
    >
    <!-- Reset button appears only when searchQuery is non-empty -->
    <button
      v-if="searchQuery"
      class="reset-button"
      @click="clearSearch"
    >
      X
    </button>
  </div>
</template>

<style scoped>
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
</style>
