<?php
	$bool = True;
	$str = "foo";
	$int = 12;
	
	$arr = 
	
	echo gettype($bool);
	echo  gettype($str);
	
	if(is_int($int)){
		$int += 4;
	}
	
	if(is_string($bool)){
		echo "String : $bool"
	}else{
		echo  "not boolean."
	}
	
	$str1 = "this is test";
	$first = $str1{0};
	$second = $str1{3};
	$last =$str1{strleng($str1) - 1};
	
	$a = "Hello ";
	$b = $a . "World!"; // now $b contains "Hello World!"
	$a = "Hello ";
	$a .= "World!";     // now $a contains "Hello World!"

	
	?>
