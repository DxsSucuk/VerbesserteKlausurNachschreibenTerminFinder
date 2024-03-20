<script lang='ts'>
    import Icon from '@iconify/svelte';
    import { goto } from '$app/navigation';
    import { checkSession, getNoticeImage, approveNotice } from '$lib/client';
    import { onMount } from 'svelte';
    import { page } from '$app/stores';

    let noticeId = $page.params.noticeId;
    let noticeImage: string;

    onMount(async () => {
        var result = await checkSession();
        if (!result) {
            goto('/');
        } else {
            noticeImage = await getNoticeImage(noticeId);
        }
    });
</script>

<!-- Make a div with a image and two buttons -->
<!-- One to approve it and one to deny it -->
<div>
    <image src={noticeImage} />
    <button on:click={() => approveNotice(noticeId)}>
        <Icon icon='mdi-light:check-circle' width='24' height='24' />
    </button>
    <button>
        <Icon icon='material-symbols-light:cancel-outline' width='24' height='24' />
    </button>
</div>
