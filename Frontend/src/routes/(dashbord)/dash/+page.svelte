<script lang="ts">
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	import logo from '$lib/assets/9.png';
	import { Avatar } from '@skeletonlabs/skeleton';
	import { AppBar } from '@skeletonlabs/skeleton';
	import { AppShell } from '@skeletonlabs/skeleton';

	onMount(async () => {
		username = localStorage.getItem('name') ?? 'Loading...';
	});
</script>
<AppShell>
	<svelte:fragment slot="pageHeader">Page Header</svelte:fragment>
	<slot/>
	<svelte:fragment slot ="pageFooter">FOOTER</svelte:fragment>
</AppShell>

<style>
	
	.body {
		background-color: #333;
		color: #fff;
		font-family: 'Arial', sans-serif;
	}
</style>
