<?php
    include("connect.php");
	$u = $_REQUEST["id"];
	$api = $_REQUEST["api_key"];
	if($api == "KDXWS4z")
	{
		$query = "SELECT * FROM scores where uid = '".$u."'";
		$result = pg_query($query) or die('Error message: ' . pg_last_error());
		
		while ($row = pg_fetch_assoc($result)) 
		{
			$tojson[] = $row;
		}
	   
		echo json_encode($tojson);
	}
	pg_close($dbconn);
?>