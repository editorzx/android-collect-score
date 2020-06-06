<?php
    include("connect.php");
	$user = $_REQUEST["username"];
	$password = $_REQUEST["password"];
	$name = $_REQUEST["name"];
	$api = $_REQUEST["api_key"];
	if($api == "KDXWS4z")
	{
		$queryx = "INSERT INTO users(username, password, name)
			VALUES ('$user', '$password', '$name')";
		$result = pg_query($queryx); 
	 
	}
	pg_close($dbconn);
?>