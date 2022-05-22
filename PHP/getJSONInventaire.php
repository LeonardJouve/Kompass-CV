<?php
ob_start();
include "validateToken.php";
include "kompassConnect.php";
ob_end_clean();

$token = $_POST["token"];

if ($status == "1") {
	$sqlQuery = "SELECT username FROM user_table WHERE token = '" . $token . "';";
	$result = mysqli_query($conn, $sqlQuery);
	$resultLength = mysqli_num_rows($result);
	if ($resultLength == 1) {
        	$row = mysqli_fetch_assoc($result);
        	$username = $row["username"];
	}
	$fileName = "JSONInventaire/" . $username . ".txt";
	$file = fopen($fileName, "x+");
	$file = fopen($fileName, "r") or die ("*Fichier inaccessible");
	echo fread($file, filesize($fileName));
	fclose($file);
}
else {
	echo "Erreur lors de la connection a votre compte";
}
?>
