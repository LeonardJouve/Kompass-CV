<?php
include_once "kompassConnect.php";
include "sendEmail.php";

function email() {
	global $email, $activate_key;
	$subject = "Activation de votre compte loginApp";
	$body = "Afin d'activer votre compte loginApp, veuillez cliquer sur le lien ci-dessous: https://mobile.jouvefamily.ch/kompass/validateEmail.php?key=" . $activate_key;
	sendEmail($email, $subject, $body);
}

$password = $_POST["password"];
if (isset($_POST["username"])) {
	$log = $_POST["username"];
	$sqlQuery = mysqli_prepare($conn, "SELECT email, password, actived, activate_key FROM user_table WHERE username = ?");
	$sqlQuery2 = mysqli_prepare($conn, "UPDATE user_table SET token = ? WHERE username = ?");
}
elseif (isset($_POST["email"])) {
	$log = $_POST["email"];
	$sqlQuery = mysqli_prepare($conn, "SELECT email, password, actived, activate_key FROM user_table WHERE email = ?");
	$sqlQuery2 = mysqli_prepare($conn, "UPDATE user_table SET token = ? WHERE email = ?");
}

date_default_timezone_set('Europe/Paris');
$str = "abcdefghijklmnopqrstuvwxyz123456789()$#";
$randString = substr(str_shuffle($str), 0, rand(1, 40));
$currentDate = date("Y-m-d H:i:s");
$token = $log . $randString . $currentDate;
$tokenHashed = password_hash($token, PASSWORD_DEFAULT);
$tokenHashed = str_replace("/", "-", $tokenHashed);
$tokenHashed = substr_replace($tokenHashed, "a", 0, 1);

mysqli_stmt_bind_param($sqlQuery, "s", $log);
mysqli_stmt_bind_param($sqlQuery2, "ss", $tokenHashed, $log);
mysqli_stmt_execute($sqlQuery);
$result = mysqli_stmt_get_result($sqlQuery);
$resultLength = mysqli_stmt_affected_rows($sqlQuery);
if ($resultLength > 0) {
	$row = mysqli_fetch_array($result, MYSQLI_ASSOC);
	$actived = $row["actived"];
	if ($actived == 1) {
		$passwordHashed = $row["password"];
        	if (password_verify($password, $passwordHashed)) {
			mysqli_stmt_execute($sqlQuery2);
			if (!mysqli_error($conn)) {
				echo $tokenHashed;
			}
        	}
		else {
			echo "*mauvais mot de passe";
		}
	}
	else {
		$email = $row["email"];
		$activate_key = $row["activate_key"];
		echo "*veuillez verifier votre adresse email";
		email();
	}
}
else {
	echo "*email ou nom d'utilisateur invalide";
}

mysqli_stmt_close($sqlQuery);
mysqli_stmt_close($sqlQuery2);
mysqli_close($conn);
?>
