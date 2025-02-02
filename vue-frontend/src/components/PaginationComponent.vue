<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';

// Define props for pagination controls
const props = defineProps<{
  offset: number;
  limit: number;
  total: number;
}>();

// Define the events to emit when user navigates pages
const emit = defineEmits<{
  (e: 'prev'): void;
  (e: 'next'): void;
}>();
</script>

<template>
  <div class="pagination">
    <button
      :disabled="props.offset === 0"
      @click="$emit('prev')"
    >
      Prev
    </button>

    <span>
      {{ props.offset + 1 }} - {{ Math.min(props.offset + props.limit, props.total) }} of {{ props.total }}
    </span>

    <button
      :disabled="props.offset + props.limit >= props.total"
      @click="$emit('next')"
    >
      Next
    </button>
  </div>
</template>

<style scoped>
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
