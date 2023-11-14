<script lang="ts">
	import { goto } from '$app/navigation';
	import { onMount } from 'svelte';
	import logo from '$lib/assets/dalek.png';

	let username: string, password: string;

	onMount(async () => {
		username = localStorage.getItem('username') ?? 'Loading...';
	});

	async function loginForm() {
		var result = false;

		if (result) {
			localStorage.goto('dash');
		} else {
			console.error('Login failed!');
		}
	}
</script>

<div class="body">
	<div class="user">
		{username} <picture> <img src={logo} alt="Dalek" /> </picture>
		<br /><button type="submit">Logout</button>
	</div>
	<div class="class">
		<div class="list">
			<ul>
				<li>List item 1</li>
				<li>List item 2</li>
				<li>List item 3</li>
				<li>List item 4</li>
				<li>List item 5</li>
			</ul>
		</div>
	</div>
</div>

<style>
	.body {
		background-color: #333;
		color: #fff;
		font-family: 'Arial', sans-serif;
		display: flex;

		height: 100vh;
		margin: 0;
	}
	.user {
		position: absolute;
		top: 0;
		right: 0;
		text-align: right;
	}
	.class {
		margin: auto;
		background-color: #222;
		border-radius: 10px;
		box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
		padding: 20px;
		text-align: center;
		width: 100%;
		max-width: 400px;
	}
	ul {
		padding: 0;
		margin: 0;
		list-style: none;
		background-color: white;
	}

	li {
		position: relative;
		padding-left: 30px;
		height: 50px;
		line-height: 50px;
		margin: 5px;
		border: 1px solid #aaa;
		border-radius: 8px;
		width: 200px;
		background: #cccccc; /* Old browsers */
		background: -moz-linear-gradient(
			top,
			#cccccc 0%,
			#ffffff 25%,
			#ffffff 75%,
			#cccccc 100%
		); /* FF3.6+ */
		background: -webkit-gradient(
			linear,
			left top,
			left bottom,
			color-stop(0%, #cccccc),
			color-stop(25%, #ffffff),
			color-stop(75%, #ffffff),
			color-stop(100%, #cccccc)
		); /* Chrome,Safari4+ */
		background: -webkit-linear-gradient(
			top,
			#cccccc 0%,
			#ffffff 25%,
			#ffffff 75%,
			#cccccc 100%
		); /* Chrome10+,Safari5.1+ */
		background: -o-linear-gradient(
			top,
			#cccccc 0%,
			#ffffff 25%,
			#ffffff 75%,
			#cccccc 100%
		); /* Opera 11.10+ */
		background: -ms-linear-gradient(
			top,
			#cccccc 0%,
			#ffffff 25%,
			#ffffff 75%,
			#cccccc 100%
		); /* IE10+ */
		background: linear-gradient(
			to bottom,
			#cccccc 0%,
			#ffffff 25%,
			#ffffff 75%,
			#cccccc 100%
		); /* W3C */
		cursor: pointer;

		box-shadow: 2px 2px 3px #666;
		user-select: none;
	}

	li:before {
		position: absolute;
		content: '';
		left: 4px;
		top: 50%;
		margin-top: -10px;
		border-left: 20px solid #ccc;
		border-top: 10px solid transparent;
		border-bottom: 10px solid transparent;
	}

	li:after {
		position: absolute;
		content: '';
		left: 4px;
		top: 50%;
		margin-top: -10px;
		border-left: 5px solid white;
		border-top: 10px solid transparent;
		border-bottom: 10px solid transparent;
	}

	li:hover {
		border-color: #777;
		box-shadow: 3px 3px 5px #333;

		background: #eeeeee; /* Old browsers */
		background: -moz-linear-gradient(
			top,
			#dddddd 0%,
			#ffffff 25%,
			#ffffff 75%,
			#dddddd 100%
		); /* FF3.6+ */
		background: -webkit-gradient(
			linear,
			left top,
			left bottom,
			color-stop(0%, #dddddd),
			color-stop(25%, #ffffff),
			color-stop(75%, #ffffff),
			color-stop(100%, #dddddd)
		); /* Chrome,Safari4+ */
		background: -webkit-linear-gradient(
			top,
			#dddddd 0%,
			#ffffff 25%,
			#ffffff 75%,
			#dddddd 100%
		); /* Chrome10+,Safari5.1+ */
		background: -o-linear-gradient(
			top,
			#dddddd 0%,
			#ffffff 25%,
			#ffffff 75%,
			#dddddd 100%
		); /* Opera 11.10+ */
		background: -ms-linear-gradient(
			top,
			#dddddd 0%,
			#ffffff 25%,
			#ffffff 75%,
			#dddddd 100%
		); /* IE10+ */
		background: linear-gradient(
			to bottom,
			#dddddd 0%,
			#ffffff 25%,
			#ffffff 75%,
			#dddddd 100%
		); /* W3C */
	}

	li:hover:before {
		border-left-color: #777;
	}

	li:active {
		background: #eee;
		box-shadow: inset 0 1px 1px #666;
	}
</style>
