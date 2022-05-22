<?php
include_once "kompassConnect.php";
include "sendEmail.php";

function email() {
	global $email;
	$subject = "Activation de votre compte loginApp";
        $body = "Votre compte a ete active avec succes !";
	sendEmail($email, $subject, $body);
}

$key = $_GET["key"];
if ($key != NULL) {
	$sqlQuery = "SELECT email FROM user_table WHERE activate_key = '" . $key . "';";
	$result = mysqli_query($conn, $sqlQuery);
	$resultLength = mysqli_num_rows($result);
	if ($resultLength == 1) {
		$row = mysqli_fetch_assoc($result);
		$email = $row["email"];
		$actived = $row["actived"];
		if ($actived == 0) {
			$sqlQuery = "UPDATE user_table SET actived = 1, activate_key = NULL WHERE activate_key = '" . $key . "';";
			$result = mysqli_query($conn, $sqlQuery);
			if (!mysqli_error()) {
				email();
				echo "email verifie !";
			}
			else {
				echo "erreur, veuillez reessayer";
			}
		}
		else {
			echo "le compte est deja actif";
		}
	}
	else {
		echo "Cle d'activation invalide";
	}
}
else {
	echo "la cle d'activation ne peut pas etre vide";
}

mysqli_close($conn);
?>
