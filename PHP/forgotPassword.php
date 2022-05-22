<?php
include_once "kompassConnect.php";
include "sendEmail.php";

date_default_timezone_set('Europe/Paris');

function email() {
	global $keyHashed, $email;
	$subject = "reinitialisation du mot de passe de loginApp";
	$body = "lien: https://resetpassword.kompass.com/" . $keyHashed . " ou: https://mobile.jouvefamily.ch/kompass/resetPasswordNav.php?key=" . $keyHashed;
	sendEmail($email, $subject, $body);
}

$email = $_POST["email"];
$str = "abcdefghijklmnopqrstuvwxyz123456789()$#";
$randString = substr(str_shuffle($str), 0, rand(1, 40));
$currentDate = date("Y-m-d H:i:s");
$key = $email . $randString . $currentDate;
$keyHashed = password_hash($key, PASSWORD_DEFAULT);
$keyHashed = str_replace("/", "-", $keyHashed);
$expDateFormat = mktime(date("H"), date("i"), date("s"), date("m") ,date("d") + 1, date("Y"));
$expDate = date("Y-m-d H:i:s", $expDateFormat);

$sqlQuery = "SELECT actived FROM user_table WHERE email = '" . $email . "';";
$result = mysqli_query($conn, $sqlQuery);
$resultLength = mysqli_num_rows($result);
$row = mysqli_fetch_assoc($result);
$actived = $row["actived"];
if ($resultLength == 1 and $actived == 1) {
	$sqlQuery = "SELECT * FROM reset_password WHERE reset_email = '" . $email . "';";
	$result = mysqli_query($conn, $sqlQuery);
	$resultLength = mysqli_num_rows($result);

	if ($resultLength == 1) {
		$sqlQuery = "UPDATE reset_password SET reset_key = '" . $keyHashed . "', expDate = '" . $expDate . "' WHERE reset_email = '" . $email . "';";
		$result = mysqli_query($conn, $sqlQuery);
		if (!mysqli_error($conn)) {
			email();
			echo "l'email de recuperation de votre mot de passe a bien ete envoye !";
		}
	}
	if ($resultLength == 0) {
		$sqlQuery = "INSERT INTO reset_password(reset_email, reset_key, expDate) VALUES('" . $email . "', '" . $keyHashed . "', '" . $expDate . "');";
		$result = mysqli_query($conn, $sqlQuery);
		if (!mysqli_error($conn)) {
			email();
			echo "l'email de recuperation de votre mot de passe a bien ete envoye !";
		}
	}
}
else {
	echo "compte inconnu ou verifiez l'email";
}

mysqli_close($conn);
?>
