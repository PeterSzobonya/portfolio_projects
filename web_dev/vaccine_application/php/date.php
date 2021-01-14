<?php
include('../services/userstorage.php');
include('../services/appointmentstorage.php');
include('../services/auth.php');

$hidden = "hidden";

if(isset($_GET["year"])){
    $year = $_GET["year"];
}
if(isset($_GET["month"])){
    $month = $_GET["month"];
}
if(isset($_GET["day"])){
    $day = $_GET["day"];
}

if(isset($_GET["year"]) && isset($_GET["month"]) && isset($_GET["day"])){
    $date = "";
    $date .= $year;
    $date .= "-";
    $date .= $month;
    $date .= "-";
    $date .= $day;
}

session_start();
$user_storage = new UserStorage();
$auth = new Auth($user_storage);
$appointments = new AppointmentStorage();
$user = $auth->authenticated_user();
$hiddenFromUnsigned = $user!=NULL ? "" : "hidden";
$hiddenFromSigned = $user!=NULL ? "hidden" : "";
$intervals = $appointments->findAll(['date' => $date]) ? $appointments->findAll(['date' => $date]) : NULL;

$hiddenButAdmin = $user['admin'] ? "" : "hidden";
$agreed = NULL;
$selected = NULL;

if($_POST){
    $start_time = $_POST['interval'];
    $params = [
        "date" => $date,
        "start_time" => $start_time
    ];
    $selected = $appointments->findOne($params);
    if(isset($_POST['agree'])){
        $agreed = $_POST['agree'];
        $hidden = "hidden";
    } else {
        $hidden = "";
    }
}

if($selected && $agreed){
    if(!in_array($user, $selected['participants']) && count($user['appointments']) == 0 && $selected['max_participants'] - count($selected['participants']) > 0){
        $selected['participants'] = array_merge($selected['participants'], [count($selected['participants'])+1 => $user ]);
        $appointments->update($selected['id'],$selected);

        $user['appointments'] = array_merge($user['appointments'],[1=>$selected]);
        $user_storage->update($user['id'],$user);

        header("Location: index.php");
        exit();
    } else if(in_array($user, $selected['participants'])){
        //already applied
        header("Location: index.php?error=You have already apllied for this appointment!");
        exit();
    } else if(count($user['appointments']) != 0){
        //have other appointment
        header("Location: index.php?error=You have an other appointment!");
        exit();
    } else if($interval['max_participants'] - count($interval['participants']) <= 0) {
        //no space left
        header("Location: index.php?error=There is no space left for this time interval!");
        exit();
    }
} else if(!$agreed){
    $hidden = "";
}

?>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/date.css">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet"> 
    <title>NatCoVid</title>

    <style>
        .full{
            color: red;
        }
    
    </style>
</head>
<body>
<div class="container">    
    <h1><?= $date ?> </h1> 
        </br>
        <h3>Available appointments</h3>
        <span class="<?= $hiddenFromSigned ?>" style="color:red; font-weight:bold;" id="login_status">You are not logged in! Please <a href="login.php?goto=date.php?year=<?= $year ?>&month=<?= $month ?>&day=<?= $day ?>">log in</a> or <a href="register.php?goto=date.php?year=<?= $year ?>&month=<?= $month ?>&day=<?= $day ?>">register!</a></span>
        <h4 class="<?= $hiddenFromUnsigned ?> user-data">Welcome! </br> User: <?= $user['name'] ?> </br> Adress: <?= $user['adress'] ?></br>TAJ number: <?= $user['taj'] ?></h4>
            <h4 class="<?= $hidden ?>" style="color:red;">You havent agreed to the terms!</h4>

        <?php if($intervals != NULL) :?>
        <ul style="list-style-type: none;">

        <form action="" method="post">
        <label for="agree">With this checkbox I agree to show up in the selected time interval</label>
        <input type="checkbox" name="agree" id="agree" value=true>
            <?php foreach($intervals as $interval) : ?>
                <?php if($interval['max_participants'] - count($interval['participants']) == 0) : ?>
                    <li class="full">
                <?php else : ?>
                    <li class="">
                <?php endif; ?>
                        <input class="hidden" type="text" name="interval" id="" value="<?=$interval["start_time"] ?>">
                        <p> <?= $interval["start_time"] ?> - <?= $interval['end_time'] ?>
                        </br>
                        Places left: <?= $interval['max_participants'] - count($interval['participants']) ?>
                        <button type="submit" style="margin-left:30px">Apply</button>
                        </p>
                        <p class="<?= $hiddenButAdmin ?>">Participants:</p>
                        <?php foreach($interval['participants'] as $participant) : ?>
                            <p class="<?= $hiddenButAdmin ?>">
                                <?= $participant['name'] ?>
                                <?= $participant['taj'] ?>
                                <?= $participant['email'] ?>
                            </p>
                        <?php endforeach; ?>
                    </form>
                </li>
            <?php endforeach; ?>
        </ul>
        <?php endif; ?>

        <button id="go_back_btn">Go back</button>

        <script src="../js/date.js"></script>
     </div>
</body>
</html>