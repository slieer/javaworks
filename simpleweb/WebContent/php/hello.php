<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Language" content="en" />
	<title>php JSON</title>
</head>
<body>

<?php echo hello_test("World") ?>
<script type="text/javascript">
 
   <?php
     $array = array("a"=>"Caucho", "b"=>"Resin", "c"=>"Quercus");
     $json = json_encode($array);
     echo "var data = $json;";
   ?>
   
   console.log(data.c + " at work.");
   //alert(data.c + " at work.");
   
   var txt =document.createElement('TextNode');
   txt.innerHTML = "console has start. End.";
   document.body.appendChild(txt)
 </script>
 
 
 
 <a href="index.php">first php</a>
</body>
</html>
