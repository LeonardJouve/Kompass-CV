<?php
ob_start();
include "kompassConnect.php";
ob_end_clean();

$json = $_POST["json"];
$token = $_POST["token"];
$isFouille = $_POST["isFouille"];

$sqlQuery = "SELECT * FROM poi_user WHERE token = '" . $token . "';";
$result = mysqli_query($conn, $sqlQuery);
$row = mysqli_fetch_assoc($result);
$amount = $row["amount"];
if ($isFouille == "true") {
	$amount += 1;
}

$sqlQuery = "UPDATE poi_user SET json = '" . $json . "', amount = '" . $amount . "' WHERE token = '" . $token . "';";
$result = mysqli_query($conn, $sqlQuery);
if (mysqli_sqlstate($conn) == "00000") {
	echo "a";
}
else {
	echo "*erreur SQL: " . mysqli_error($conn);
	echo "<br>" . $sqlQuery;
}

mysqli_close($conn);
?>
