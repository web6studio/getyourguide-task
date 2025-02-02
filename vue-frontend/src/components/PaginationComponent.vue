<script setup lang="ts">
import { defineProps } from 'vue';

const props = defineProps<{
  offset: number;
  limit: number;
  total: number;
}>();

</script>

<template>
  <div class="pagination">
    <button @click="$emit('prev')" :disabled="props.offset === 0">
      Prev
    </button>

    <span>
      {{ props.offset + 1 }} - {{ Math.min(props.offset + props.limit, props.total) }} of {{ props.total }}
    </span>

    <button @click="$emit('next')" :disabled="props.offset + props.limit >= props.total">
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
