<?php
include_once "kompassConnect.php";
include "sendEmail.php";

function email() {
	global $email;
	$subject = "reinitialisation du mot de passe de loginApp";
	$body = "Votre mot de passe loginApp a ete reinitialise avec succes !";
	sendEmail($email, $subject, $body);
}

date_default_timezone_set('Europe/Paris');
$currentDate = date("Y-m-d H:i:s");

$key = $_POST["key"];
$password = $_POST["password"];
$passwordHashed = password_hash($password, PASSWORD_DEFAULT);

$sqlQuery = "SELECT reset_email, expDate FROM reset_password WHERE reset_key = '" . $key . "';";
$result = mysqli_query($conn, $sqlQuery);
$resultLength = mysqli_num_rows($result);
if ($resultLength == 1) {
	$row = mysqli_fetch_assoc($result);
	$expDate = $row["expDate"];
	$email = $row["reset_email"];
	if ($expDate >= $currentDate) {
		$sqlQuery = "UPDATE user_table SET password = '" . $passwordHashed . "' WHERE email = '" . $email . "';";
		$result = mysqli_query($conn, $sqlQuery);
		$sqlQuery = "DELETE FROM reset_password WHERE reset_key = '" . $key . "';";
		$result = mysqli_query($conn, $sqlQuery);
		if (!mysqli_error()) {
			email();
			if (isset($_POST["resetPassword"])) {
				echo "mot de passe reinitinalise avec succes !";
			}
			else {
				echo $email;
			}
		}
	}
	else {
		echo "*le lien de réinitialisation du mot de passe a expiré";
	}
}
else {
	echo "*le lien de réinitialisation du mot de passe est invalide";
}

mysqli_close($conn);
?>
