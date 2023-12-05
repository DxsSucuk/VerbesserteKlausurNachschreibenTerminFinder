<script lang="ts">
	import Navbar from '$lib/components/+navbar.svelte';
	import { faPlusCircle } from '@fortawesome/free-solid-svg-icons'
  	import Fa from 'svelte-fa/src/fa.svelte'
	import Modal from '$lib/components/+modal.svelte'
	import { createEventDispatcher } from 'svelte';
	import { onMount } from 'svelte';
	import { AppShell } from '@skeletonlabs/skeleton';
	import { closeModal, Modals, openModal } from 'svelte-modals';
	import { writable } from 'svelte/store';

	let username:string;
	let isOpen = writable(false);

	onMount(async () => {
		username = localStorage.getItem('name') ?? 'Loading...';
	});

	function handleClick() {
    	isOpen.set(true);
		console.log(isOpen);
  	}
	function handleCloseModal() {
    	isOpen.set(false);
  	}

</script>

<AppShell>
	<svelte:fragment slot="pageHeader">
		<Navbar />
	</svelte:fragment>

	<slot/>
		<div class="absolute top-0 right-0 mt-16 mr-4 cursor-pointer" on:click={handleClick}>
      		<Fa icon={faPlusCircle} class="text-4xl" />
    	</div>

		<Modals bind:isOpen>
		  <div slot="backdrop" class="backdrop" on:click={handleCloseModal} />
		  <Modal {isOpen} title="Your Title" message="Your Message" />
		</Modals>
	<svelte:fragment slot ="pageFooter">FOOTER</svelte:fragment>

</AppShell>

<style>
	
	.body {
		background-color: #333;
		color: #fff;
		font-family: 'Arial', sans-serif;
	}
	  .backdrop {
    position: fixed;
    top: 0;
    bottom: 0;
    right: 0;
    left: 0;
    background: rgba(0,0,0,0.50)
  }
</style>
