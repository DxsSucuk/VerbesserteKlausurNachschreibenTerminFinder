<script lang="ts">
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	import logo from '$lib/assets/9.png';
	import { Avatar } from '@skeletonlabs/skeleton';
	import { AppBar } from '@skeletonlabs/skeleton';
	import { AppShell } from '@skeletonlabs/skeleton';
	let username: string, password: string;

	onMount(async () => {
		username = localStorage.getItem('username') ?? 'Loading...';
	});

	async function loginForm() {
		var result = false;

		if (result) {
			localStorage.goto('dash');
		} else {
			console.error('Login failed!');
		}
	}
</script>
<body class="body">
<AppShell>
	<svelte:fragment slot="header">HEADER</svelte:fragment>
	<slot/>
	<svelte:fragment slot ="footer">FOOTER</svelte:fragment>
</AppShell>
</body>

<style>
	
	.body {
		background-color: #333;
		color: #fff;
		font-family: 'Arial', sans-serif;
	}
</style>
