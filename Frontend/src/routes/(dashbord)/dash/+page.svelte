<script lang="ts">
    import Icon from '@iconify/svelte';
    import { openModal } from 'svelte-modals';
    import { getModalStore } from '@skeletonlabs/skeleton';
    import type { ModalSettings, ModalComponent } from '@skeletonlabs/skeleton';
    import { initializeStores } from '@skeletonlabs/skeleton';
    import { FileDropzone } from '@skeletonlabs/skeleton';
    import Modal from './modal.svelte'

    const modalComponent: ModalComponent = { ref: Modal };
    const modal: ModalSettings = {
	type: 'component',
	component: modalComponent,
};

    const modalStore = getModalStore();

    initializeStores();
    let istLehrer: boolean = false;

    function openFileUploadModal() {
        modalStore.trigger(modal);
    }

    //const modalComponent: ModalComponent = {
    //    ref: FileDropzone,
    //    props: { foo: 'bar' },
    //    slot: '<FileDropzone name="files" on:files={handleFileUpload}/>'
    //};


    //const modal: ModalSettings = {
    //    type: 'confirm',
    //    // Data
    //    title: 'Please Confirm',
    //    body: `
    //        <div>
    //            <p>Are you sure you wish to proceed?</p>
    //             <!-- Include FileDropzone component here -->
    //        </div>
    //    `,
    //    // TRUE if confirm pressed, FALSE if cancel pressed
    //    response: (r: boolean) => console.log('response:', r),
    //};

    // Function to handle file upload
    function handleFileUpload(files) {
        // Handle file upload logic here
        console.log('Uploaded files:', files);
    }
</script>

{#if istLehrer}
<div class="table-container ">
    <table class="table table-interactive" role="grid">
        <thead class="table-head ">
            <tr>
                <th class="" role="columnheader">Klasse</th>
                <th class="" role="columnheader">Schüler</th>
                <th class="" role="columnheader">Lernfelder</th>
                <th class="" role="columnheader">Actionen</th>
            </tr>
        </thead>
        <tbody class="table-body ">
            {#each [2412,12315,235,124] as obj,index}
            <tr aria-rowindex="{index}">
                <td class="" role="gridcell" aria-colindex="1" tabindex="0">Hydrogen</td>
                <td class="" role="gridcell" aria-colindex="2" tabindex="-1">H</td>
                <td class="" role="gridcell" aria-colindex="3" tabindex="-1">{obj}</td>
                <td class="flex" role="gridcell" aria-colindex="4" tabindex="-1">
                    <a href="#smash"><Icon icon="mdi-light:file" width="35"height="35" /></a>
                    <a href="#smash"><Icon icon="mdi-light:calendar" width="35"height="35" /></a>
                </td>
            </tr>
            {/each}
        </tbody>
        <tfoot class="table-foot ">
            <tr>
                <td class="">Total</td>
                <td class=""></td>
                <td class=""><code class="code">5</code></td>
            </tr>
        </tfoot>
    </table>
</div>
{:else}
<div class="table-container ">
    <table class="table table-interactive" role="grid">
        <thead class="table-head ">
            <tr>
                <th class="" role="columnheader">Lernfeld</th>
                <th class="" role="columnheader">Datum</th>
                <th class="" role="columnheader">Anzahl</th>
                <th class="" role="columnheader">Actionen</th>
            </tr>
        </thead>
        <tbody class="table-body ">
            {#each [2412,12315,235,124] as obj,index}
            <tr aria-rowindex="{index}">
                <td class="" role="gridcell" aria-colindex="1" tabindex="0">Hydrogen</td>
                <td class="" role="gridcell" aria-colindex="2" tabindex="-1">H</td>
                <td class="" role="gridcell" aria-colindex="3" tabindex="-1">{obj}</td>
                <td class="flex" role="gridcell" aria-colindex="4" tabindex="-1">
                    <a href="#smash" on:click={openFileUploadModal}><Icon icon="mdi-light:file" width="35"height="35" /></a>
                    <a href="#smash"><Icon icon="mdi-light:calendar" width="35"height="35" /></a>
                </td>
            </tr>
            {/each}
        </tbody>
        <tfoot class="table-foot ">
            <tr>
                <td class="">Total</td>
                <td class=""></td>
                <td class=""><code class="code">5</code></td>
            </tr>
        </tfoot>
    </table>
</div>
{/if}
