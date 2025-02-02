import { createRouter, createWebHistory } from "vue-router"
import HomePage from "@/pages/HomePage.vue"
import ActivityPage from "@/pages/ActivityPage.vue"

const routes = [
  { path: "/", component: HomePage },
  { path: "/activity/:id", name: "activity", component: ActivityPage, props: true }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
