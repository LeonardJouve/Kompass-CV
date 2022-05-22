<?php
ob_start();
include "kompassConnect.php";
ob_end_clean();

$token = $_POST["token"];
$vie = $_POST["vie"];
$faim = $_POST["faim"];
$endurance = $_POST["endurance"];

$sqlQuery = "SELECT username FROM user_table WHERE token = '" . $token . "';";
$result = mysqli_query($conn, $sqlQuery);
$row = mysqli_fetch_assoc($result);
$username = $row["username"];

$sqlQuery = "UPDATE user_state SET vie = '" . $vie . "', faim = '" . $faim . "', endurance = '" . $endurance ."' WHERE username = '" . $username . "';";
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
