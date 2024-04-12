<script lang="ts">
	import type { SvelteComponent } from 'svelte';

	// Stores
	import { FileDropzone, getModalStore } from '@skeletonlabs/skeleton';

	// Props
	/** Exposes parent props to this component. */
	export let parent: SvelteComponent;
	let files: FileList;
	let uploadedFileNames: string[] = [];

	const modalStore = getModalStore();

	// We've created a custom submit function to pass the response and close the modal.
	function onFormSubmit(): void {
		if ($modalStore[0].response) $modalStore[0].response(files);
		modalStore.close();
		console.log(files);
	}

	function updateUploadedFileNames() {
        uploadedFileNames = Array.from(files).map(file => file.name);
    }

	// Base Classes
	const cBase = 'card p-4 w-modal shadow-xl space-y-4';
	const cHeader = 'text-2xl font-bold';
	const cForm = 'border border-surface-500 p-4 space-y-4 rounded-container-token';
</script>

<!-- @component This example creates a simple form modal. -->

{#if $modalStore[0]}
	<div class="modal-example-form {cBase}">
		<header class={cHeader}>{$modalStore[0].title ?? 'Upload'}</header>
		<article>{$modalStore[0].body ?? 'Bitte laden sie im Nachfolgenden Feld ihre Krankmeldung hoch (Nur Bilder(png,jpg, etc.))'}</article>
		<!-- Enable for debugging: -->
		<form class="modal-form {cForm}">
			<label class="label">
				<span>Name</span>
				<FileDropzone name="files" bind:files={files} on:change={updateUploadedFileNames} accept="image/*">
					<svelte:fragment slot="meta"></svelte:fragment>
				</FileDropzone>
			</label>
			{#if uploadedFileNames.length > 0}
                <p>Uploaded files:</p>
                <ul>
                    {#each uploadedFileNames as fileName}
                        <li>{fileName}</li>
                    {/each}
                </ul>
            {/if}
		</form>
		<!-- prettier-ignore -->
		<footer class="modal-footer {parent.regionFooter}">
			<button class="btn {parent.buttonNeutral}" on:click={parent.onClose}>{parent.buttonTextCancel}</button>
			<button class="btn {parent.buttonPositive}" on:click={onFormSubmit}>Submit Form</button>
		</footer>
	</div>
{/if}