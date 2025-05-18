<!--<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Add Robot Action</title>
    <script>
        function sendRobotData() {
            // Create an object to hold the robot task
            var task = {};
            task.taskName = document.getElementById("taskName").value;
            task.duration = document.getElementById("duration").value;

            // Convert to JSON
            var jsonTask = JSON.stringify(task);

            // Create the HTTP request
            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    // Full response display
                    document.getElementById("robotResponse").innerHTML = this.responseText;

                    // Parsed response display
                    var returned = JSON.parse(this.responseText);
                    document.getElementById("robotParts").innerHTML =
                        "ID: " + returned.id +
                        " | Task: " + returned.taskName +
                        " | Duration: " + returned.duration + " sec";
                }
            };

            // Send JSON to robot REST service
            xhttp.open("POST", "./rest/robotservice/addtask", true);
            xhttp.setRequestHeader("Content-type", "application/json");
            xhttp.send(jsonTask);
        }
    </script>
</head>

<body>
    <h2>Add Task to Robot</h2>

    <!-- AJAX Form -->
   <!--  <form onsubmit="return false;">
        Task Name: <input id="taskName" type="text" placeholder="e.g., Move Forward"><br><br>
        Duration (sec): <input id="duration" type="text" placeholder="e.g., 5"><br><br>
        <input type="button" value="Send (AJAX)" onclick="sendRobotData();">
    </form>

    <p id="robotResponse">Full response will appear here.</p>
    <p id="robotParts">Parsed response will appear here.</p>

    <hr>

    <!-- Traditional HTML Form -->
    <!--<h3>Alternative: Use normal form submission</h3>
    <form action="./rest/robotservice/addtask" method="post">
        Task Name: <input name="taskName" type="text"><br><br>
        Duration (sec): <input name="duration" type="text"><br><br>
        <input type="submit" value="Send (Form)">
    </form>
</body>
</html>  -->
