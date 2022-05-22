<?php
include_once "kompassConnect.php";

$token = $_POST["token"];
$sqlQuery = "SELECT username FROM user_table WHERE token = '" . $token . "' AND actived = (1);";
$result = mysqli_query($conn, $sqlQuery);
$resultLength = mysqli_num_rows($result);
if ($resultLength > 0) {
	$status = "1";
}
else {
	$status = "*erreur de connection, veuillez reessayer s'il vous plait";
}
echo $status;

mysqli_close($conn);
?>
