<script lang="ts">
	import { goto } from '$app/navigation';


	let username:string, password:string;

	async function loginForm() {
		var result = true;

		if (result) {
            localStorage.setItem("username",username)
			goto('dash');
		} else {
			console.error('Login failed!');
		}
	}
</script>

<div class="body">
	<div class="login-container">
		<div class="login-box">
			<h2>Login</h2>
			<form on:submit|preventDefault={loginForm}>
				<div class="input-container">
					<i class="fas fa-user" />
					<input
						type="text"
						id="username"
						bind:value={username}
						placeholder="Username"
						class="rounded-input"
					/>
				</div>
				<div class="input-container">
					<i class="fas fa-lock" />
					<input
						type="password"
						id="password"
						bind:value={password}
						placeholder="Password"
						class="rounded-input"
					/>
				</div>
				<button type="submit">Log In</button>
			</form>
		</div>
	</div>
</div>

<style lang="scss">
	.login-container {
		margin: auto;
		background-color: #222;
		border-radius: 10px;
		box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
		padding: 20px;
		text-align: center;
		width: 100%;
		max-width: 400px;
	}

	.login-box {
		display: flex;
		flex-direction: column;
		align-items: center;
	}

	h2 {
		font-size: 24px;
		color: #fff;
	}

	form {
		margin-top: 20px;
	}

	.input-container {
		position: relative;
		margin-bottom: 20px;
	}

	i {
		position: absolute;
		top: 50%;
		transform: translateY(-50%);
		left: 10px;
		color: #888;
	}

	.rounded-input {
		padding: 10px;
		border: none;
		border-radius: 25px;
		background-color: #444;
		color: #fff;
		outline: none;
		font-family: 'Arial', sans-serif;
	}

	button {
		width: 100%;
		padding: 10px;
		border: none;
		border-radius: 25px;
		background-color: #007bff;
		color: #fff;
		cursor: pointer;
		font-weight: bold;
		font-family: 'Arial', sans-serif;
	}

	button:hover {
		background-color: #0056b3;
	}

	.body {
		background-color: #333;
		color: #fff;
		font-family: 'Arial', sans-serif;
		display: flex;
		justify-content: center;
		align-items: center;
		height: 100vh;
		margin: 0;
	}
</style>