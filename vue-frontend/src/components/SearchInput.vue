<script setup lang="ts">
import { ref, watch, defineEmits } from "vue";
import { debounce } from "@/utils";
import { X, Search } from "lucide-vue-next";

const emit = defineEmits<{
  (e: "update:search", value: string): void;
}>();

const searchQuery = ref("");

// Debounce the update event (300ms delay)
const debouncedEmit = debounce(() => {
  emit("update:search", searchQuery.value);
}, 300);

// Watch for changes in the search query and emit the update
watch(searchQuery, debouncedEmit);

// Function to clear search input
const clearSearch = () => {
  searchQuery.value = "";
};
</script>

<template>
  <div class="relative w-full max-w-md">
    <div class="relative flex items-center bg-white border border-gray-300 rounded-full shadow-sm hover:shadow-md transition-all">
      <Search class="absolute left-3 text-gray-500 w-5 h-5" />
      <input
        v-model="searchQuery"
        type="text"
        placeholder="Search activities..."
        class="w-full py-2 pl-10 pr-10 text-gray-900 placeholder-gray-500 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-blue-500 rounded-full"
      >

      <!-- Clear button -->
      <button
        v-if="searchQuery"
        class="absolute right-3 text-gray-500 hover:text-gray-800 cursor-pointer"
        @click="clearSearch"
      >
        <X class="w-5 h-5" />
      </button>
    </div>
  </div>
</template>
