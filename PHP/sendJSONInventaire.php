<?php
ob_start();
include "validateToken.php";
include "kompassConnect.php";
ob_end_clean();

$json = $_POST["json"];
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
	$file = fopen($fileName, "w+") or die ("*Fichier inaccessible");
	fwrite($file, $json);
	fclose($file);
	echo "a";
}
else {
	echo "*Erreur: token de connection invalide";
}
?>
