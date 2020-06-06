<?php
    include("connect.php");
	$uid = $_REQUEST["uid"];
	$score = $_REQUEST["score"];
	$subject = $_REQUEST["subject"];
	$api = $_REQUEST["api_key"];
	if($api == "KDXWS4z")
	{
		$time = date("Y-m-d H:i:s",time());
		$queryx = "INSERT INTO scores(uid, subject, score, timestamp)
			VALUES ('$uid', '$subject', '$score', '$time')";
		$result = pg_query($queryx); 
	 
	}
	pg_close($dbconn);
?>