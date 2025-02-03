<script setup lang="ts">
defineProps({
  rating: {
    type: Number,
    required: true,
  },
});
</script>

<template>
  <div class="flex items-center mb-4">
    <span class="flex items-center text-yellow-500">
      <template v-for="n in 5" :key="n">
        <!-- Full star -->
        <span v-if="n <= Math.floor(rating)" class="text-yellow-500">★</span>
        <!-- Half star -->
        <span v-else-if="n === Math.ceil(rating) && (rating % 1) >= 0.5" class="text-yellow-500">★</span>
        <!-- Empty star -->
        <span v-else class="text-gray-300">☆</span>
      </template>
    </span>
    <span class="ml-2 text-gray-500">{{ rating }} / 5</span>
  </div>
</template>
