<script lang='ts'>
    import Icon from '@iconify/svelte';
    import { goto } from '$app/navigation';

    let istLehrer: boolean = false;
    import { onMount } from 'svelte';
    import { getClassrooms, checkSession, type Exam, getMissedExams } from '$lib/client';
    import type { Classroom } from '$lib/client';

    import { getModalStore } from '@skeletonlabs/skeleton';
    import type { ModalSettings, ModalComponent } from '@skeletonlabs/skeleton';
    import FileUploadModal from './fileUploadModal.svelte'

    var classrooms: Classroom[] = [];
    var missedExams: Exam[] = [];

    const modalStore = getModalStore();

    const modalComponent: ModalComponent = { ref: FileUploadModal };
    const modal: ModalSettings = {
        type: 'component',
        component: modalComponent,
        response: (r: File) => {
            submitNotice(r);
        },
    };

    function submitNotice(file: File) {
        /*if (createNewAppointment(date, classRoomId, [])) {
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
        }*/
    }

    function openNoticeModal() {
        console.log('Called');
        modalStore.trigger(modal);
    }

    onMount(async () => {
        var result = await checkSession();
        if (!result) {
            goto('/');
        } else {
            istLehrer = localStorage.getItem("name")?.endsWith('Arslan')
            if (istLehrer) {
                refreshClassroom();
            } else {
                missedExams = await getMissedExams();
            }
        }
    });

    async function refreshClassroom() {
        classrooms = await getClassrooms();
    }

    function gotoAppointment(room: string) {
        goto('dash/rooms/' + room + '/apo');
    }

    function gotoStudentAppointment(teacher: string) {
        goto('dash/apo/' + teacher);
    }

    function gotoNoticeOverview(room: string) {
        goto('dash/rooms/' + room + '/overview');
    }
</script>

{#if istLehrer}
    <div class='table-container '>
        <table class='table table-interactive' role='grid'>
            <thead class='table-head '>
            <tr>
                <th class='' role='columnheader'>Klasse</th>
                <th class='' role='columnheader'>Schüleranzahl</th>
                <th class='' role='columnheader'>Lernfelder</th>
                <th class='' role='columnheader'>Actionen</th>
            </tr>
            </thead>
            <tbody class='table-body '>
            {#each classrooms as obj,index}
                <tr aria-rowindex='{index}'>
                    <td class='' role='gridcell' aria-colindex='1' tabindex='0'>{obj.name}</td>
                    <td class='' role='gridcell' aria-colindex='2' tabindex='-1'>{obj.abteilung}</td>
                    <td class='' role='gridcell' aria-colindex='3' tabindex='-1'>{obj}</td>
                    <td class='flex' role='gridcell' aria-colindex='4' tabindex='-1'>
                        <button on:click={() => gotoNoticeOverview(obj.name)}>
                            <Icon icon='mdi-light:file' width='35' height='35' />
                        </button>
                        <button on:click={() => gotoAppointment(obj.name)}>
                            <Icon icon='mdi-light:calendar' width='35' height='35' />
                        </button>
                    </td>
                </tr>
            {/each}
            </tbody>
            <tfoot class='table-foot '>
            <tr>
                <td class=''>Total</td>
                <td class=''></td>
                <td class=''><code class='code'>{classrooms.length}</code></td>
                <td>
                    <button on:click={refreshClassroom}><span>Refresh</span></button>
                </td>
            </tr>
            </tfoot>
        </table>
    </div>
{:else}
    <div class='table-container '>
        <table class='table table-interactive' role='grid'>
            <thead class='table-head '>
            <tr>
                <th class='' role='columnheader'>Lehrer</th>
                <th class='' role='columnheader'>Datum</th>
                <th class='' role='columnheader'>Genehmigte Krankmeldung</th>
                <th class='' role='columnheader'>Actionen</th>
            </tr>
            </thead>
            <tbody class='table-body '>
            {#each missedExams as obj,index}
                <tr aria-rowindex='{index}'>
                    <td class='' role='gridcell' aria-colindex='1' tabindex='0'>{obj.teacherId}</td>
                    <td class='' role='gridcell' aria-colindex='2' tabindex='-1'>{obj.missedAt}/td>
                    <td class='' role='gridcell' aria-colindex='3' tabindex='-1'>
                        {#if obj.hasNotice}
                            <Icon icon='mdi-light:check-circle' width='24' height='24' />
                        {:else}
                            <Icon icon='material-symbols-light:cancel-outline' width='24' height='24' />
                        {/if}
                    </td>
                    <td class='flex' role='gridcell' aria-colindex='4' tabindex='-1'>
                        <button on:click={openNoticeModal} disabled={obj.hasNotice}>
                            <Icon icon='mdi-light:file' width='35' height='35' />
                        </button>
                        <button on:click={() => gotoStudentAppointment(obj.teacherId)}>
                            <Icon icon='mdi-light:calendar' width='35' height='35' />
                        </button>
                    </td>
                </tr>
            {/each}
            </tbody>
            <tfoot class='table-foot '>
            <tr>
                <td class=''>Total</td>
                <td class=''></td>
                <td class=''><code class='code'>5</code></td>
            </tr>
            </tfoot>
        </table>
    </div>
{/if}