<script lang="ts">
	import Icon from '@iconify/svelte';
	import { goto } from '$app/navigation';
	let visible: boolean = true;
	let istLehrer: boolean = true; 
	import { onMount } from 'svelte';
	import { getClassrooms, checkSession } from '$lib/client';
	import type { Classroom } from '$lib/client';

	var classrooms: Classroom[] = []

    onMount(async () => {
		var result = await checkSession()
        if (!result) {
            goto("/")
        } else {
			classrooms = await getClassrooms()
		}
    })
</script>


{#if istLehrer}
<div class="table-container ">
	<table class="table table-interactive" role="grid">
		<thead class="table-head ">
		<tr>
			<th class="" role="columnheader">Klasse</th>
			<th class="" role="columnheader">Schüleranzahl</th>
			<th class="" role="columnheader">Lernfelder</th>
			<th class="" role="columnheader">Actionen</th>
		</tr>
		</thead>
		<tbody class="table-body ">
			{#each classrooms as obj,index}
			<tr aria-rowindex="{index}">
				<td class="" role="gridcell" aria-colindex="1" tabindex="0">{obj.name}</td>
				<td class="" role="gridcell" aria-colindex="2" tabindex="-1">{obj.abteilung}</td>
				<td class="" role="gridcell" aria-colindex="3" tabindex="-1">{obj}</td>
				<td class="flex" role="gridcell" aria-colindex="4" tabindex="-1"><a href="#smash"><a href="classroom"><Icon icon="mdi-light:calendar" width="35"height="35" /></a></td>
			</tr>
			{/each}
		</tbody>
		 	<tfoot class="table-foot ">
				<tr>
					<td class="">Total</td>
					<td class=""></td>
					<td class=""><code class="code">{classrooms.length}</code></td>
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
				<td class="flex" role="gridcell" aria-colindex="4" tabindex="-1"><a href="#smash"><Icon icon="mdi-light:file" width="35"height="35" /></a><a href="#smash"><Icon icon="mdi-light:calendar" width="35"height="35" /></a></td>
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