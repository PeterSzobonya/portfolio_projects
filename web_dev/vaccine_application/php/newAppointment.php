<?php
include('../services/userstorage.php');
include('../services/appointmentstorage.php');
include('../services/auth.php');

//functions
function validate($post, &$data, &$errors){
    if($_POST['date'] != "" && $_POST['start_time'] != "" && $_POST['end_time'] != "" && $_POST['max_participants'] != ""){
        if($_POST['start_time'] < $_POST['end_time']){
            $data = $post;
        }else {
            //invalid start and end time
            array_push($errors, "start_and_end_time_mismatch");
        }
    } if(!$_POST['date'] != "") {
        //no selected date
        array_push($errors, "no_selected_date");
    }  
    if(!$_POST['start_time'] != ""){
        //no selected start time
        array_push($errors, "no_selected_start_time");
    }  
    if(!$_POST['end_time']) {
        //no selected end time
        array_push($errors, "no_selected_end_time");
    }  
    if(!$_POST['max_participants'] != ""){
        //no selected max participants
        array_push($errors, "no_selected_maxparticipants");
    }
    return count($errors) === 0;
}

session_start();
$user_storage = new UserStorage();
$auth = new Auth($user_storage);
$appointments = new AppointmentStorage();
$data = [];
$errors = [];

$user = $auth->authenticated_user();

if(!$user['admin']) {
    header("Location: index.php");
    exit();
}

if($_POST){
    if(validate($_POST,$data,$errors)){
        $participants = ['participants'=>[]];
        $data = array_merge($data,$participants);

        $appointments->add($data);

        header("Location: index.php");
        exit();
    }
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet"> 
    <link rel="stylesheet" href="../css/login.css">
    <title>NatCoVid-new appointment</title>
</head>
<body>
    <div class="container">
        <h1>Create new appointment</h1>
        <h3>Please fill the form below</h3>
        </br></br></br>

        <?php if(in_array("start_and_end_time_mismatch",$errors)) : ?>
            <p style="color:red;"> There is a mismatch between start and end time</p>
        <?php endif; ?>

        <form action="" method="post">
            <div class="row">
                <div class="col-sm-6 text-right">
                    <label for="date">Date</label>
                </div>
                <div class="col-sm-6">
                    <?php if(isset($_POST['date'])) : ?>
                        <input type="date" name="date" id="date" value="<?= $_POST['date']?>">
                    <?php else : ?>
                        <input type="date" name="date" id="date">
                    <?php endif; ?>
                    <?php if(in_array("no_selected_date",$errors)) : ?>
                        <p style="color:red;"> There is no date selected </p>
                    <?php endif; ?>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 text-right">
                    <label for="start_time">Start time</label>
                </div>
                <div class="col-sm-6">
                    <?php if(isset($_POST['date'])) : ?>
                        <input type="time" name="start_time" id="start_time" value="<?= $_POST['start_time']?>">
                    <?php else : ?>
                        <input type="time" name="start_time" id="start_time">
                    <?php endif; ?><?php if(in_array("no_selected_start_time",$errors)) : ?>
                        <p style="color:red;"> There is no start time selected </p>
                    <?php endif; ?>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 text-right">
                    <label for="end_time">End time</label>
                </div>
                <div class="col-sm-6">
                    <?php if(isset($_POST['date'])) : ?>
                        <input type="time" name="end_time" id="end_time" value="<?= $_POST['end_time']?>">
                    <?php else : ?>
                        <input type="time" name="end_time" id="end_time">
                    <?php endif; ?>
                    <?php if(in_array("no_selected_end_time",$errors)) : ?>
                        <p style="color:red;"> There is no end time selected </p>
                    <?php endif; ?>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 text-right">
                    <label style="padding-top:10px;" for="max_participants">Max participants</label>
                </div>
                <div class="col-sm-6">
                    <?php if(isset($_POST['date'])) : ?>
                        <input type="number" name="max_participants" value="<?= $_POST['max_participants']?>">
                    <?php else : ?>
                        <input type="number" name="max_participants" id="max_participants">
                    <?php endif; ?>
                    <?php if(in_array("no_selected_maxparticipants",$errors)) : ?>
                        <p style="color:red;"> There is no maximum participants limit addded </p>
                    <?php endif; ?>
                </div>
            </div>
            <div style="text-align:center; padding-top:30px;">
                <button type="submit">Create</button>
            </div>
        </form>
    </div>
</body>
</html>