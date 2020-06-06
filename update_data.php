<?php
    include("connect.php");
	$id = $_REQUEST["id"];
	$score = $_REQUEST["score"];
	$subject = $_REQUEST["subject"];
	$api = $_REQUEST["api_key"];
	if($api == "KDXWS4z")
	{
		$time = date("Y-m-d H:i:s",time());
		$queryx = "UPDATE scores set score = '".$score."' , subject = '".$subject."' , timestamp = '".$time."' where id = '".$id."'";
		$result = pg_query($queryx); 
	 
	}
	pg_close($dbconn);
?>