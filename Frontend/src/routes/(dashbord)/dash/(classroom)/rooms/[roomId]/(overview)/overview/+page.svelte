<script lang="ts">
    import Icon from '@iconify/svelte';
    import { goto } from '$app/navigation';
    import { checkSession, type Exam, getMissedExamsForClass, getNoticeFromExam } from '$lib/client';
    import { onMount } from 'svelte';
    import { page } from '$app/stores';

    let classRoomId = $page.params.roomId;

    var missedExams: Exam[] = [];

    // Verpasste Prüfungen hier
    onMount(async () => {
        var result = await checkSession();
        if (!result) {
            goto('/');
        } else {
            await retrieveMissedExams();
        }
    });

    async function retrieveMissedExams() {
        missedExams = await getMissedExamsForClass(classRoomId);
    }

    async function redirectToNotice(examId: string, studentId: string) {
        let notice:Notice = await getNoticeFromExam(examId, studentId)
        if (notice != null) {await
            goto("/dash/notice/" + notice.id + '/view')
        }
    }
</script>

<div class="table-container ">
    <table class="table table-interactive" role="grid">
        <thead class="table-head ">
        <tr>
            <th class="" role="columnheader">Schüler</th>
            <th class="" role="columnheader">Datum</th>
            <th class="" role="columnheader">Nachweis Vorhanden</th>
            <th class="" role="columnheader">Actionen</th>
        </tr>
        </thead>
        <tbody class="table-body ">
        {#each missedExams as obj,index}
            <tr aria-rowindex="{index}">
                <td class="" role="gridcell" aria-colindex="1" tabindex="0">{obj.studentId}</td>
                <td class="" role="gridcell" aria-colindex="2" tabindex="-1">{obj.missedAt}</td>
                <td class="" role="gridcell" aria-colindex="3" tabindex="-1">
                    {#if obj.hasNotice}
                        <Icon icon="mdi-light:check-circle" width="24" height="24" />
                    {:else}
                        <Icon icon="material-symbols-light:cancel-outline" width="24" height="24" />
                    {/if}
                </td>
                <td class="flex" role="gridcell" aria-colindex="4" tabindex="-1">
                    <!-- SEND TO NEW PAGE RELATE TO USER SPECIFIC NOTICE VIEWING -->
                    <button on:click={() => redirectToNotice(obj.id, obj.studentId)}>
                        <Icon icon="mdi-light:file" width="35" height="35" />
                    </button>
                </td>
            </tr>
        {/each}
        </tbody>
        <tfoot class="table-foot ">
        <tr>
            <td class="">Total</td>
            <td class=""></td>
            <td class=""><code class="code">{missedExams.length}</code></td>
        </tr>
        </tfoot>
    </table>
</div>
