<script lang="ts">
	import { tableMapperValues } from '@skeletonlabs/skeleton';
	import type { TableSource } from '@skeletonlabs/skeleton';
	import Icon from '@iconify/svelte';
	let visible: boolean = true;

	const sourceData = [
		{ position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H', code: '<a href="#" class="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800">Smash</a>' },
		{ position: 2, name: 'Helium', weight: 4.0026, symbol: 'He' },
		{ position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li' },
		{ position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be' },
		{ position: 5, name: 'Boron', weight: 10.811, symbol: 'B' }
	];

	function setTableSource(): TableSource {
		return {
			// A list of heading labels.
			head: ['Lernfeld', 'Datum', 'Anzahl', 'Actionen'],
			// The data visibly shown in your table body UI.
			body: tableMapperValues(sourceData, ['name', 'symbol', 'weight', 'code']), 
			// Optional: The data returned when interactive is enabled and a row is clicked.
			meta: tableMapperValues(sourceData, ['position', 'name', 'symbol', 'weight']),
			// Optional: A list of footer labels.
			foot: ['Total', '', '<code class="code">5</code>']
		};
	}
	$: tableSimple = sourceData ? setTableSource() : undefined;

	function onSelected(meta: unknown): void {
		console.log('on:selected', meta);
	}
</script>

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
				<td class="flex" role="gridcell" aria-colindex="4" tabindex="-1"><a href="#smash"><Icon icon="mdi-light:home" width="35"height="35" /></a><a href="#smash"><Icon icon="mdi-light:alarm" width="35"height="35" /></a></td>
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
