<script>
  import { faUserCircle } from '@fortawesome/free-solid-svg-icons'
  import Fa from 'svelte-fa/src/fa.svelte'
</script>



<nav class="flex items-center justify-between bg-gray-800 text-white p-4">
  <div class="flex items-center">
    <!-- Title on the left -->
    <h1 class="text-lg font-semibold">VKN Termin Finder</h1>
  </div>

  <div class="flex items-center">
    <!-- Account icon on the right -->
    <i class="fas fa-user-circle text-2xl mr-2"></i>
    <Fa icon={faUserCircle} />
  </div>
</nav>
