<?php
include_once "kompassConnect.php";
include "sendEmail.php";

function email() {
	global $email, $keyHashed;
	$subject = "Activation de votre compte loginApp";
	$body = "Afin d'activer votre compte loginApp, veuillez cliquer sur le lien ci-dessous: https://mobile.jouvefamily.ch/kompass/validateEmail.php?key=" . $keyHashed;
	sendEmail($email, $subject, $body);
}

date_default_timezone_set('Europe/Paris');

$password = $_POST["password"];
$passwordHashed = password_hash($password, PASSWORD_DEFAULT);
$username = $_POST["username"];
$email = $_POST["email"];

$str = "abcdefghijklmnopqrstuvwxyz123456789()$#";
$randString = substr(str_shuffle($str), 0, rand(1, 40));
$currentDate = date("Y-m-d H:i:s");
$key = $username . $randString . $currentDate;
$keyHashed = password_hash($key, PASSWORD_DEFAULT);
$keyHashed = str_replace("/", "-", $keyHashed);
$keyHashed = str_replace(".", "a", $keyHashed);

$sqlQuery = "SELECT username FROM user_table WHERE username = '" . $username . "' OR email = '" . $email . "';";
$result = mysqli_query($conn, $sqlQuery);
$resultLength = mysqli_num_rows($result);
if ($resultLength == 0) {
	$sqlQuery = "INSERT INTO user_table (email, username, password, actived, activate_key) VALUES ('" . $email . "', '" . $username . "', '" . $passwordHashed . "', 0, '" . $keyHashed ."');";
	mysqli_query($conn, $sqlQuery);
	if (mysqli_sqlstate($conn) != "00000") {
        	echo "*erreur de la creation du compte: " . mysqli_sqlstate($conn);
	}
	else {
		echo "compte cree avec succes ! Veuillez valider votre adresse email";
		email();
	}
	$sqlQuery = "INSERT INTO user_state (username, vie, faim, endurance) VALUES ('" . $username . "', 100, 100, 100);";
	mysqli_query($conn, $sqlQuery);
}
else {
	echo "*un compte utilisant le meme email ou nom d'utilisateur existe deja";
}

mysqli_close($conn);
?>
