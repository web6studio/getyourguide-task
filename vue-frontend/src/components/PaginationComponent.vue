<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';

const props = defineProps<{
  offset: number;
  limit: number;
  total: number;
}>();

const emit = defineEmits<{
  (e: 'prev'): void;
  (e: 'next'): void;
}>();
</script>

<template>
  <div class="flex items-center justify-center space-x-4 mt-8 mb-3">
    <button
      :disabled="props.offset === 0"
      class="px-4 py-2 text-white rounded-lg shadow-md transition
             disabled:bg-gray-300 disabled:cursor-not-allowed 
             bg-[#ff542f] hover:bg-[#e04829] active:bg-[#c13d22] cursor-pointer"
      @click="$emit('prev')"
    >
      Prev
    </button>

    <span class="text-gray-700 font-medium">
      {{ props.offset + 1 }} - {{ Math.min(props.offset + props.limit, props.total) }} of {{ props.total }}
    </span>

    <button
      :disabled="props.offset + props.limit >= props.total"
      class="px-4 py-2 text-white rounded-lg shadow-md transition
             disabled:bg-gray-300 disabled:cursor-not-allowed 
             bg-[#ff542f] hover:bg-[#e04829] active:bg-[#c13d22] cursor-pointer"
      @click="$emit('next')"
    >
      Next
    </button>
  </div>
</template>
