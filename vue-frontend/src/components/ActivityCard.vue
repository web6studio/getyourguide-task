<script setup lang="ts">
import { useRouter } from "vue-router";

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
  <div class="activity-card" @click="goToActivityPage">
    <h3>{{ activity.title }}</h3>
    <p>Price: {{ activity.price }} {{ activity.currency }}</p>
    <p>Rating: {{ activity.rating }}</p>
    <p v-if="activity.specialOffer" class="special-offer">Special Offer Available!</p>
    <p>Supplier: {{ activity.supplier.name }}</p>
    <p>Location: {{ activity.supplier.city }}, {{ activity.supplier.country }}</p>
  </div>
</template>

<style scoped>
.activity-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  max-width: 300px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  text-align: center;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.activity-card:hover {
  border-color: #000;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}

.special-offer {
  color: green;
  font-weight: bold;
}

.no-offer {
  color: #555;
}
</style>
