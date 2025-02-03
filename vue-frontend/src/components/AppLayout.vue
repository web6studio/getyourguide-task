<template>
  <div class="min-h-screen bg-gray-100">
    <!-- Header -->
    <header class="w-full bg-white shadow-md">
      <div class="max-w-6xl mx-auto px-4 py-2 flex items-center">
        <!-- Logo -->
        <router-link
          to="/"
          class="flex items-center"
        >
          <img
            src="@/assets/logo.png"
            alt="Logo"
            class="h-16"
          >
        </router-link>
      </div>
    </header>

    <!-- Main content wrapper -->
    <main class="max-w-6xl mx-auto px-4 py-8">
      <slot />
    </main>
  </div>
</template>
