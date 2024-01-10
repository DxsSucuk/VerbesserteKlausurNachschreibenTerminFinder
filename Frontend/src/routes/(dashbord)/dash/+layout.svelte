<script lang="ts">
	import '../../+layout.svelte';

	import { AppShell } from '@skeletonlabs/skeleton';
	import { Avatar } from '@skeletonlabs/skeleton';
	import { AppBar } from '@skeletonlabs/skeleton';
	import { localStorageStore } from '@skeletonlabs/skeleton'; // Backs up our data
	import type { Writable } from 'svelte/store'; // Handles our data
	import type { Settings } from '$lib/client';

	import { initializeStores } from '@skeletonlabs/skeleton';
	import { Toast, getToastStore } from '@skeletonlabs/skeleton';
	import type { ToastSettings, ToastStore } from '@skeletonlabs/skeleton';

initializeStores();
const toastStore = getToastStore();

const t: ToastSettings = {
	message: 'Lorem ipsum dolor sit amet consectetur adipisicing elit...',
	autohide:false
};
toastStore.trigger(t);



	// Floating UI for Popups
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

	onMount(() => {
		if (!getSetting('profile')) {
			let setting = {
				name: 'profile',
				value:
					'https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/0325da39-2b46-4334-909f-5be445287d5b/de6rxcp-fac48cdd-4a90-4abd-a1b6-82702ab85e0d.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzAzMjVkYTM5LTJiNDYtNDMzNC05MDlmLTViZTQ0NTI4N2Q1YlwvZGU2cnhjcC1mYWM0OGNkZC00YTkwLTRhYmQtYTFiNi04MjcwMmFiODVlMGQucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.09EyMJkdd0TZIFnJwIugt1_K3Cpbw3lf41fZtWkDVeA'
			};

			saveSetting(setting);
		}
		profilePicture = getSetting('profile').value;
	});
</script>
<Toast />
<AppShell>
	<svelte:fragment slot="header">
		<AppBar gridColumns="grid-cols-3" slotDefault="place-self-center" slotTrail="place-content-end">
			<svelte:fragment slot="lead"><Avatar src={icon} /></svelte:fragment>
			VerbesserteKlausurNachreibenTerminFinder
			<svelte:fragment slot="trail">
				{#if profilePicture}
					<Avatar src={profilePicture} width="w-16" rounded="rounded-full" />
				{/if}
			</svelte:fragment>
		</AppBar>
	</svelte:fragment>
	<svelte:fragment slot="sidebarLeft">Sidebar Left</svelte:fragment>
	<slot />
	<svelte:fragment slot="footer">Datum</svelte:fragment>
</AppShell>
