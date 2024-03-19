<script lang="ts">
	import Icon from '@iconify/svelte';
	import { goto } from '$app/navigation';
	import { checkSession } from '$lib/client';
	import { onMount } from 'svelte';
	import { Datepicker } from 'svelte-calendar';

	import { getModalStore } from '@skeletonlabs/skeleton';
	import type { ModalSettings, ModalComponent } from '@skeletonlabs/skeleton';
	import { initializeStores } from '@skeletonlabs/skeleton';
	import DatepickerModal from './datepickerModal.svelte';

	const modalComponent: ModalComponent = { ref: DatepickerModal };
	const modal: ModalSettings = {
		type: 'component',
		component: modalComponent
	};

	const modalStore = getModalStore();

    function openDatePickerModal() {
		console.log("Called")
        modalStore.trigger(modal);
    }

	onMount(async () => {
		var result = await checkSession();
		if (!result) {
			goto('/');
		}
	});
</script>

<div class="table-container">
	<table class="table table-interactive" role="grid">
		<thead class="table-head">
			<tr>
				<th class="" role="columnheader">Datum</th>
				<th class="" role="columnheader">Zustimmungen</th>
				<th class="" role="columnheader">Lehrer Zustimmung</th>
			</tr>
			<button on:click="{openDatePickerModal}">Add</button>
		</thead>
		<tbody class="table-body">
			{#each [9, 23, 53, 64] as obj, index}
				<tr aria-rowindex={index}>
					<td class="" role="gridcell" aria-colindex="1" tabindex="0">Hydrogen</td>
					<td class="" role="gridcell" aria-colindex="2" tabindex="-1">H</td>
					<td class="" role="gridcell" aria-colindex="3" tabindex="-1"><Icon icon="mdi-light:check-circle" width="24" height="24" /><Icon icon="material-symbols-light:cancel-outline" width="24" height="24" /></td>
				</tr>
			{/each}
		</tbody>
		<tfoot class="table-foot">
			<tr>
				<td class="">Total</td>
				<td class=""></td>
				<td class=""><code class="code">5</code></td>
			</tr>
		</tfoot>
	</table>
</div>
