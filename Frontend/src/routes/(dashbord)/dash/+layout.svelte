<script lang="ts">
	import '../../+layout.svelte';

	import { AppShell } from '@skeletonlabs/skeleton';
	import { Avatar } from '@skeletonlabs/skeleton';
	import { AppBar } from '@skeletonlabs/skeleton';
	import { localStorageStore } from '@skeletonlabs/skeleton'; // Backs up our data
	import type { Writable } from 'svelte/store'; // Handles our data
	import type { Settings } from '$lib/client';
	import { goto } from '$app/navigation';

	import { initializeStores } from '@skeletonlabs/skeleton';
	import { Toast, getToastStore } from '@skeletonlabs/skeleton';
	import type { ToastSettings } from '@skeletonlabs/skeleton';
	import { AppRail, AppRailAnchor } from '@skeletonlabs/skeleton';
	import { page } from '$app/stores';
	import Icon from '@iconify/svelte';

	let currentTile: number = 0;

	initializeStores();
	const toastStore = getToastStore();

	const t: ToastSettings = {
		message: 'Lorem ipsum dolor sit amet consectetur adipisicing elit...',
		autohide: false
	};
	toastStore.trigger(t);

	// Floating UI for Popups
	import { popup } from '@skeletonlabs/skeleton';
	import type { PopupSettings } from '@skeletonlabs/skeleton';
	import { computePosition, autoUpdate, flip, shift, offset, arrow } from '@floating-ui/dom';
	import { storePopup } from '@skeletonlabs/skeleton';
	import { onMount } from 'svelte';

	storePopup.set({ computePosition, autoUpdate, flip, shift, offset, arrow });

	const settingStore: Writable<Settings[]> = localStorageStore('settingsStore', []);

	function saveSetting(setting: Settings) {
		$settingStore = [setting, ...$settingStore];
	}

	function getSetting(name: string): Settings {
		return $settingStore.filter((setting) => setting.name === name)[0];
	}

	function deleteSetting(name: string) {
		$settingStore = $settingStore.filter((setting) => setting.name !== name);
	}

	let icon =
		'https://cdn.discordapp.com/attachments/1173914026066071602/1194236332067786802/full-length-portrait-busy-businessperson-running-late-wall-clock-briefcase-isolated-white-background-32275306.jpg?ex=65af9e41&is=659d2941&hm=f5a81517489a2dfa5e719e8509ec7dfc438469bc3add83d0c32b0ad80463572c&';

	let profilePicture: string | undefined;
	let username: string | undefined;

	onMount(() => {
		if (!getSetting('profile')) {
			let setting = {
				name: 'profile',
				value:
					'https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/0325da39-2b46-4334-909f-5be445287d5b/de6rxcp-fac48cdd-4a90-4abd-a1b6-82702ab85e0d.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzAzMjVkYTM5LTJiNDYtNDMzNC05MDlmLTViZTQ0NTI4N2Q1YlwvZGU2cnhjcC1mYWM0OGNkZC00YTkwLTRhYmQtYTFiNi04MjcwMmFiODVlMGQucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.09EyMJkdd0TZIFnJwIugt1_K3Cpbw3lf41fZtWkDVeA'
			};

			saveSetting(setting);
		}

		if (!getSetting('username')) {
			let setting = {
				name: 'username',
				value: 'ILoveBoobies'
			};

			saveSetting(setting);
		}
		profilePicture = getSetting('profile').value;
		username = getSetting('username').value;
	});

	const popupClick: PopupSettings = {
		event: 'click',
		target: 'popupFeatured',
		placement: 'bottom'
	};

	function directLogin() {
		goto("/")
	}

	function directHome() {
		goto("/dash")
	}

	function directClassroom() {
		goto("/dash/rooms")
	}

	function directPerson() {
		goto("/dash/pers")
	}
</script>

<Toast />
<AppShell>
	<svelte:fragment slot="header">
		<AppBar gridColumns="grid-cols-3" slotDefault="place-self-center" slotTrail="place-content-end">
			<svelte:fragment slot="lead"><Avatar src={icon} /></svelte:fragment>
			VerbesserteKlausurNachreibenTerminFinder
			<svelte:fragment slot="trail">
				{#if profilePicture}
					<button use:popup={popupClick}>
						<Avatar src={profilePicture} width="w-16" rounded="rounded-full" />
					</button>
				{/if}
			</svelte:fragment>
		</AppBar>
	</svelte:fragment>

	<svelte:fragment slot="sidebarLeft">
		<AppRail>
			<AppRailAnchor on:click={directHome} selected={$page.url.pathname === '/dash'}>
				<svelte:fragment slot="lead"
					><Icon icon="mdi-light:home" width="35" height="35" /></svelte:fragment
				>
				<span>Übersicht</span>
			</AppRailAnchor>
			<AppRailAnchor on:click={directClassroom} selected={$page.url.pathname === '/dash/rooms'}>
				<svelte:fragment slot="lead"
					><Icon icon="material-symbols-light:local-library"  width="35" height="35" /></svelte:fragment
				>
				<span>Klassen</span>
			</AppRailAnchor>
			<AppRailAnchor on:click={directPerson} selected={$page.url.pathname === '/dash/pers'}>
				<svelte:fragment slot="lead"
					><Icon icon="material-symbols-light:accessible-forward-sharp" width="35" height="35" /></svelte:fragment
				>
				<span>Person</span>
			</AppRailAnchor>
		</AppRail>
	</svelte:fragment>
	<slot/>
</AppShell>

<div class="card p-4 w-52 shadow-xl" data-popup="popupFeatured">
	<div><p>{username}</p></div>
	<div class="arrow bg-surface-100-800-token" />
	<div><a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ/"><p>Settings</p></a></div>
	<div><button><p>Logout</p></button></div>
</div>
