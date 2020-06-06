<?php
    include("connect.php");
	$id = $_REQUEST["id"];
	$api = $_REQUEST["api_key"];
	if($api == "KDXWS4z")
	{
		$queryx = "DELETE FROM scores WHERE id = '".$id."'";
		$result = pg_query($queryx); 
	 
	}
	pg_close($dbconn);
?>