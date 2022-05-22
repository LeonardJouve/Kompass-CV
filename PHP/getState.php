<?php
ob_start();
include "validateToken.php";
include "kompassConnect.php";
ob_end_clean();

$token = $_POST["token"];

$sqlQuery = "SELECT username FROM user_table WHERE token = '" . $token . "';";
$result = mysqli_query($conn, $sqlQuery);
$resultLength = mysqli_num_rows($result);
if ($resultLength == 1) {
	$row = mysqli_fetch_assoc($result);
	$username = $row["username"];
	$sqlQuery = "SELECT vie, faim, endurance FROM user_state WHERE username = '" . $username . "';";
	$result = mysqli_query($conn, $sqlQuery);
	if ($resultLength == 1) {
		$row = mysqli_fetch_assoc($result);
		$vie = $row["vie"];
		$faim = $row["faim"];
		$endurance = $row["endurance"];
		echo $vie . " " . $faim . " " . $endurance;
	}
}
else {
	echo "token invalide";
}

mysqli_close($conn);
?>
