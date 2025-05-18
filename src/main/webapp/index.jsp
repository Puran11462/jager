<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Add speed and turn</title>
<script>
function sendData(){
	//Create a new Javascript object
	var robot=new Object();
	robot.speed=document.getElementById("speed").value;
	robot.turn=document.getElementById("turn").value;
	robot.duration=document.getElementById("duration").value;
	robot.task=document.getElementById("task").value;
	
	var jsonRobot=JSON.stringify(robot);
	var xhttp = new XMLHttpRequest();
	
	xhttp.onreadystatechange = function() {
	  if (this.readyState == 4 && this.status == 200) {
	   document.getElementById("responseView").innerHTML = this.responseText;
	   console.log("Raw response:", this.responseText);
	   var returned=JSON.parse(this.responseText);
	   document.getElementById("inparts").innerHTML="ID="+returned.id+" speed="+returned.speed +" turn="+returned.turn +
	   " duration="+returned.duration+" task="+returned.task;
	  }
	};
	
	xhttp.open("POST","./rest/robotservice/addrobot",true);
	xhttp.setRequestHeader("Content-type","application/json");
	xhttp.send(jsonRobot);
}
</script>
</head>

<body>
<h2>Fill in - this form uses AJAX</h2>
<form action="#" method='post' onsubmit='return false;'>
	Speed: <input id='speed' type='text' name='speed' value='' placeholder='New robot speed'><br>
	Turn: <input id='turn' type='text' name='turn' value='' placeholder='New turn'><br>
	Duration (seconds): <input id='duration' type='number' name='duration' value='' placeholder='Duration'><br>
	Task Name:
	<select id='task'>
    <option value="FORWARD">Forward</option>
    <option value="BACK">Back</option>
    <option value="LEFT">Left</option>
    <option value="RIGHT">Right</option>
</select><br>
	<input type='button' name='ok' value='Send' onclick='sendData();'><br>
</form>
<p id='responseView'>The response will be shown here!
</p>
<p id='inparts'>The response in parts will be shown here!
</p>
</body>
</html>