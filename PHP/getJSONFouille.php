<?php
ob_start();
include "validateToken.php";
include "kompassConnect.php";
ob_end_clean();

$latitude = $_POST["latitude"];
$longitude = $_POST["longitude"];
$token = $_POST["token"];

$sqlQuery = "SELECT * FROM poi_user WHERE token = '" . $token . "';";
$result = mysqli_query($conn, $sqlQuery);
$resultLength = mysqli_num_rows($result);
if ($resultLength == 1) {
	$row = mysqli_fetch_assoc($result);
	$json = $row["json"];
	echo $json;
}
else if ($status == "1") {
	$sqlQuery = "INSERT INTO poi_user (token, json, amount) VALUES ('" . $token . "', '[]', 0);";
	$result = mysqli_query($conn, $sqlQuery);
	if (mysqli_sqlstate($conn) != "00000") {
		echo "erreur dans le SQL: " . mysqli_error($conn) . mysqli_sqlstate($conn);
	}
	else {
		echo "[]";
	}
}
else {
	echo "token invalide";
}

mysqli_close($conn);
?>
