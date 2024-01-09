<script lang='ts'>
    import '../app.postcss';

    import { AppShell } from '@skeletonlabs/skeleton';
    import { Avatar } from '@skeletonlabs/skeleton';
    import { AppBar } from '@skeletonlabs/skeleton';
    import { localStorageStore } from '@skeletonlabs/skeleton'; // Backs up our data
    import type { Writable } from 'svelte/store'; // Handles our data
    import type { Settings } from '$lib/client';

    // Floating UI for Popups
    import { computePosition, autoUpdate, flip, shift, offset, arrow } from '@floating-ui/dom';
    import { storePopup } from '@skeletonlabs/skeleton';
    import { onMount } from 'svelte';

    storePopup.set({ computePosition, autoUpdate, flip, shift, offset, arrow });

    const settingStore: Writable<Settings[]> = localStorageStore('settingsStore', []);

    function saveSetting(setting: Settings) {
        $settingStore = [
            setting,
            ...$settingStore
        ];
    }

    function getSetting(name: string) : Settings {
        return $settingStore.filter(setting => setting.name === name)[0];
    }

	function deleteSetting(name: string) {
        $settingStore = $settingStore.filter(setting => setting.name !== name);
    }


    let profilePicture: string | undefined;

    onMount(() => {
        if (!getSetting("profile")) {
            let setting = {
                name: "profile",
                value: "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/0325da39-2b46-4334-909f-5be445287d5b/de6rxcp-fac48cdd-4a90-4abd-a1b6-82702ab85e0d.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7InBhdGgiOiJcL2ZcLzAzMjVkYTM5LTJiNDYtNDMzNC05MDlmLTViZTQ0NTI4N2Q1YlwvZGU2cnhjcC1mYWM0OGNkZC00YTkwLTRhYmQtYTFiNi04MjcwMmFiODVlMGQucG5nIn1dXSwiYXVkIjpbInVybjpzZXJ2aWNlOmZpbGUuZG93bmxvYWQiXX0.09EyMJkdd0TZIFnJwIugt1_K3Cpbw3lf41fZtWkDVeA"
            };

            saveSetting(setting);
        }
        profilePicture = getSetting("profile").value;
    });
</script>

<AppShell>
    <AppBar gridColumns='grid-cols-3' slotDefault='place-self-center' slotTrail='place-content-end'>
        <svelte:fragment slot='lead'>(icon)</svelte:fragment>
        VerbesserteKlausurNachreibenTerminFinder
        <svelte:fragment slot='trail'>
            {#if profilePicture}
                <Avatar src={profilePicture} width='w-16' rounded='rounded-full' />
            {/if}
        </svelte:fragment>
    </AppBar>
    <slot />
    <svelte:fragment slot='footer'>Datum</svelte:fragment>
</AppShell>