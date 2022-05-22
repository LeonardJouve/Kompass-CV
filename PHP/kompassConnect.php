<?php
$db_Server = "localhost";
$db_Username = "connect_kompass";
$db_Password = "connect_kompass";
$db_Name = "kompass";

$conn = mysqli_connect($db_Server, $db_Username, $db_Password, $db_Name);

if (mysqli_connect_errno()) {
	echo "*erreur de connection: " . mysqli_connect_error() . "<br>";
}
?>
