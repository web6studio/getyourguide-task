<script setup lang="ts">
import { useRouter } from "vue-router";
import ActivityRating from "@/components/ActivityRating.vue";

const props = defineProps<{
  activity: Activity;
}>();

const router = useRouter();

// Navigate to the activity detail page when the card is clicked
const goToActivityPage = () => {
  router.push({ name: "activity", params: { id: props.activity.id } });
};
</script>

<template>
  <div
    class="w-full border border-gray-300 rounded shadow-md hover:shadow-lg cursor-pointer transition-transform transform hover:scale-105 bg-white"
    @click="goToActivityPage"
  >
    <img
      src="@/assets/dummy-image.webp"
      alt="Activity Image"
      class="w-full h-48 object-cover rounded-t"
    >

    <div class="p-4">
      <h3 class="text-xl font-semibold text-gray-800 mb-2">
        {{ activity.title }}
      </h3>

      <ActivityRating :rating="activity.rating" />

      <div class="text-sm text-gray-600 mb-2">
        Supplier: {{ activity.supplier.name }}, {{ activity.supplier.city }}, {{ activity.supplier.country }}
      </div>

      <p
        v-if="activity.specialOffer"
        class="text-green-600 font-bold mb-2"
      >
        Special Offer Available!
      </p>

      <p class="text-gray-600">
        <span class="font-semibold">From ${{ activity.price }}</span> per person
      </p>
    </div>
  </div>
</template>
