<script lang="ts">
	import Icon from '@iconify/svelte';
	let visible: boolean = true;
	import { goto } from '$app/navigation';
    import { checkSession } from '$lib/client';
	import { onMount } from 'svelte';

    onMount(async () => {
		var result = await checkSession()
        if (!result) {
            goto("/")
        }
    })
</script>

<div class="table-container ">
	<table class="table table-interactive" role="grid">
		<thead class="table-head ">
		<tr>
			<th class="" role="columnheader">Vorname</th>
			<th class="" role="columnheader">Nachname</th>
            <th class="" role="columnheader">Actionen</th>
		</tr>
		</thead>
		<tbody class="table-body ">
			{#each [2412,12315,235,124] as obj,index}
			<tr aria-rowindex="{index}">
				<td class="" role="gridcell" aria-colindex="1" tabindex="0">Hydrogen</td>
				<td class="" role="gridcell" aria-colindex="2" tabindex="-1">{obj}</td>
				<td class="flex" role="gridcell" aria-colindex="4" tabindex="-1"><a href="dash"><Icon icon="mdi-light:file" width="35"height="35" /></a><a href="dash"><Icon icon="mdi-light:calendar" width="35"height="35" /></a></td>
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
