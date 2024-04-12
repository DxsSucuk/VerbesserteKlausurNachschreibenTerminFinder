<script lang="ts">
    import Icon from '@iconify/svelte';
    import { goto } from '$app/navigation';
    import { type Appointment, checkSession, getPendingAppointments, getProposedAppointments, getAcceptedAppointments, createNewAppointment } from '$lib/client';
    import { onMount } from 'svelte';

    import { getModalStore } from '@skeletonlabs/skeleton';
    import type { ModalSettings, ModalComponent } from '@skeletonlabs/skeleton';
    import DatepickerModal from '../../../(classroom)/rooms/[roomId]/(appointment)/apo/datepickerModal.svelte';

	import { Toast, getToastStore } from '@skeletonlabs/skeleton';
	import type { ToastSettings } from '@skeletonlabs/skeleton';

	import { page } from '$app/stores';

	const toastStore = getToastStore();
    const modalStore = getModalStore();

    let teacherId = $page.params.teacherId;

    const modalComponent: ModalComponent = { ref: DatepickerModal };
    const modal: ModalSettings = {
        type: 'component',
        component: modalComponent,
        response: (r: Date) => {
            submitAppointment(r);
        },
    };

    function submitAppointment(date: Date) {
        console.log($page.params)
        console.log(classRoomId)
        if (createNewAppointment(date, classRoomId, [])) {
            console.log(date)
            const t: ToastSettings = {
                message: 'Appointment wurde erstellt!',
                autohide: true,
                background: 'variant-filled-success',
            };
            toastStore.trigger(t);
        } else {
            const t: ToastSettings = {
                message: 'Fehler!',
                autohide: true,
                background: 'variant-filled-error',
                
            };
            toastStore.trigger(t);
        }
    }

    var appointments: Appointment[] = [];

    function openDatePickerModal() {
        console.log('Called');
        modalStore.trigger(modal);
    }

    onMount(async () => {
        var result = await checkSession();
        if (result) {
            await refreshAppointments()
        } else {
            goto('/');
        }
    });

    export async function refreshAppointments() {
        appointments = await getPendingAppointments();
        appointments = [...await getAcceptedAppointments(), appointments];
        appointments = [...await getProposedAppointments(), appointments];
    }
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
        {#each appointments as obj, index}
            <tr aria-rowindex={index}>
                <td class="" role="gridcell" aria-colindex="1" tabindex="0">{obj.date}
                </td>
                <td class="" role="gridcell" aria-colindex="2" tabindex="-1">1</td>
                <td class="" role="gridcell" aria-colindex="3" tabindex="-1">
                    {#if obj.acceptedByTeacher}
                        <Icon icon="mdi-light:check-circle" width="24" height="24" />
                    {:else}
                        <Icon icon="material-symbols-light:cancel-outline" width="24" height="24" />
                    {/if}
                </td>
            </tr>
        {/each}
        </tbody>
        <tfoot class="table-foot">
        <tr>
            <td class="">Total</td>
            <td class=""></td>
            <td class=""><code class="code">{appointments.length}</code></td>
        </tr>
        </tfoot>
    </table>
</div>
