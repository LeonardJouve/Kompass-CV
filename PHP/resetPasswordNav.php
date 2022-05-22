<!DOCTYPE html>
<?php
if($_SERVER["REQUEST_METHOD"] == "POST" and isset($_POST["resetPassword"])) {
	if (strcmp($_POST["password"], $_POST["confirmPassword"]) == 0) {
		if (isset($_POST["key"]) and isset($_POST["password"])) {
			include "resetPassword.php";
		}
		else {
			echo "erreur, veuillez reessayer s'il vous plaît";
		}
	}
	else {
		echo "les 2 mots de passe ne sont pas identiques";
	}
}
?>
<html>
	<head>
		<meta content="width=device-width, initial-scale=1">
		<style>
			body {font-family: Arial, Helvetica, sans-serif;}

			input[type=password], input[type=text]{
  				width: 100%;
  				padding: 12px 15px;
  				margin: 8px 0;
				border: 1px solid #ccc;
			}

			button {
        		width: 100%;
  				background-color: #04AA6D;
  				color: white;
  				padding: 14px;
  				margin: 8px 0;
  				border: none;
	           		cursor: pointer;
			}
        		button:hover {
  				opacity: 0.8;
			}
		</style>
	</head>
	<h2>Mot de passe oublié</h2>
	<body>
    	<form action="resetPasswordNav.php" method="post">
    		<div>
    			<label><b>Mot de passe</b></label>
    			<input type="password" id="password" name="password" placeholder="Nouveau mot de passe" maxlength="50" required>
			<label><b>Confirmer le mot de passe</b></label>
    			<input type="password" id="confirmPassword" name="confirmPassword" placeholder="Nouveau mot de passe" maxlength="50" required>
    			<input type="hidden" id="key" name="key">
			<label><input type="checkbox" onclick="showPassword()">Afficher le mot de passe</label>
			<button type="submit" name="resetPassword">Réinitialiser le mot de passe</button>
			<script>
				const parameters = new URLSearchParams(window.location.search);
				document.getElementById("key").value = parameters.get("key");

				function showPassword() {
					var inputPassword = document.getElementById("password");
					var inputConfirmPassword = document.getElementById("confirmPassword");
					if (inputPassword.type == "password") {
						inputPassword.type = "text";
						inputConfirmPassword.type = "text";
					}
					else {
						inputPassword.type = "password";
						inputConfirmPassword.type = "password";
					}
				}
			</script>
		</div>
        </form>
	</body>
</html>
