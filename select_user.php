<?php
	$response = array();
    include("connect.php");
	$u = $_POST["username"];
	$p = $_POST["password"];
	$query = "SELECT * FROM public.users where username = '".$u."' and password = '".$p."'";
    $result = pg_query($query) or die('Error message: ' . pg_last_error());
	if (pg_num_rows($result) > 0) {
		while ($row = pg_fetch_assoc($result)) { //pg_fetch_assoc = name , pg_fetch_row = number
			$response[] = $row;
	   }
	   echo json_encode($response);
	}else
	{
		echo "false";
	}
		
	/*if (isset($_GET["username"]) && isset($_GET["password"])) {
		$user = $_GET['username'];
		$pass = $_GET['password'];
		$result = pg_query("SELECT * FROM users WHERE username = '".$user."' and password = '".$pass."'");
		if (pg_num_rows($result) > 0) {
			$users = array();
			while ($row = pg_fetch_assoc($result)) 
			{
				$users["id"] = $row['id'];
				$users["username"] = $row['username'];
				$users["password"] = $row['password'];
				$users["isAdmin"] = $row['isAdmin'];
			}
			
			
			
            $response["success"] = 1;
			$response["message"] = "Login Success";
 
            $response["users"] = array();
			
			array_push($response["users"], $users);
			
			echo json_encode($response); // for json android
		}
		else 
		{
            $response["success"] = 0;
            $response["message"] = "Not found users or password !!";
 
            
            echo json_encode($response); // for json android
        }
	
	}*/
	pg_close($dbconn);
?>