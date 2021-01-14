<?php
include('../services/userstorage.php');
include('../services/appointmentstorage.php');
include('../services/auth.php');


session_start();
$user_storage = new UserStorage();
$auth = new Auth($user_storage);
$appointments = new AppointmentStorage();
$user = $auth->authenticated_user();
$hiddenFromUnsigned = $user!=NULL ? "" : "hidden";
$hiddenFromSigned = $user!=NULL ? "hidden" : "";
$hiddenButAdmin = $user['admin'] ? "" : "hidden";




//Calendar
date_default_timezone_set('Europe/Budapest');

// Get prev & next month
if (isset($_GET['ym'])) {
    $ym = $_GET['ym'];
} else {
    // This month
    $ym = date('Y-m');
}

// Check format
$timestamp = strtotime($ym . '-01');
if ($timestamp === false) {
    $ym = date('Y-m');
    $timestamp = strtotime($ym . '-01');
}

// Today
$today = date('Y-m-j', time());

// For H3 title
$html_title = date('Y / m', $timestamp);

// Create prev & next month link     mktime(hour,minute,second,month,day,year)
$prev = date('Y-m', mktime(0, 0, 0, date('m', $timestamp)-1, 1, date('Y', $timestamp)));
$next = date('Y-m', mktime(0, 0, 0, date('m', $timestamp)+1, 1, date('Y', $timestamp)));
// You can also use strtotime!
// $prev = date('Y-m', strtotime('-1 month', $timestamp));
// $next = date('Y-m', strtotime('+1 month', $timestamp));

// Number of days in the month
$day_count = date('t', $timestamp);
 
// 0:Sun 1:Mon 2:Tue ...
$str = date('w', mktime(0, 0, 0, date('m', $timestamp), 1, date('Y', $timestamp)));
$str = date('w', $timestamp);


// Create Calendar!!
$weeks = array();
$week = '';

// Add empty cell
$week .= str_repeat('<td></td>', $str-1);

for ( $day = 1; $day <= $day_count; $day++, $str++) {
     
    $date = $ym . '-' . $day;

    if($appointments->findAll(['date'=>$date]) != NULL){
        $there_is_free = false;
        $intervals = "<p>";
        $curr_day = $appointments->findAll(['date'=>$date]);
        foreach($curr_day as $record) {
            if(count($record['participants']) < $record['max_participants']){
                $there_is_free = true;  
            }
            $intervals .= $record['start_time'];
            $intervals .= "-";
            $intervals .= $record['end_time'];
            $intervals .= "</p>";
        }
        if($there_is_free){
            $week .= '<td class="free">' . $day;
        } else {
            $week .= '<td class="full">' . $day;
        }
        $week .= $intervals;

    }
    else if ($today == $date) {
        $week .= '<td class="today">' . $day;
    } else {
        $week .= '<td>' . $day;
    }
    $week .= '</td>';
     
    // End of the week OR End of the month
    if ($str % 7 == 0 || $day == $day_count) {

        if ($day == $day_count) {
            // Add empty cell
            $week .= str_repeat('<td></td>', 0 - ($str % 7));
        }

        $weeks[] = '<tr>' . $week . '</tr>';

        // Prepare for new week
        $week = '';
    }

}

$user = $user_storage->findOne(["id"=>$user['id']]);

$there_is_appointment = count($user['appointments']) != 0 ? "" : "hidden";

if(isset($_POST['delete'])){
    $curr_app = $appointments->findOne($user['appointments'][0]);
    $curr_app['participants'] = [];
    $user['appointments'] = [];
    $user_storage->update($user['id'], $user);
    $appointments->update($curr_app['id'],$curr_app);
    header("Location: index.php");
    exit();
}



?>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;700&display=swap" rel="stylesheet"> 
    <link rel="stylesheet" href="../css/index.css">
    <title>NatCoVid</title>

    <!---TODO put this to css  --->
    <style>
        
    </style>

</head>
<body>
    <div class="container">
        <h1>National Covid-19 Depot</h1>
        <p>Welcome to the <b>National Covid-19 Depot webpage</b>. Where we are fighting the Covid-19 pandemic. To help humanity and further the research for the cure <b>please apply for vaccination</b>.</p>
        <p>To apply please select one of the green fields from the calendar.</p>
        <span class="<?= $hiddenFromSigned ?>" id="login_status">You are not logged in! Please <a href="login.php">log in</a> or <a href="register.php">register!</a></span>
        <div class="user-message <?= $hiddenFromUnsigned ?>">
            <h3>Hello <?= $user['name'] ?>!</h3>
            <?php if(isset($_GET['error'])) : ?>
                <h4 style="color: red;"><?= $_GET['error'] ?></h4>
            <?php endif; ?>
            <form class="<?= $there_is_appointment ?>" action="" method="post">
                <input class="hidden" type="text" name="delete" value="true">
                <h4>You have an appointment:&nbsp;&nbsp; <span class="appointment-date"><?= $user['appointments'][0]['date'] ?> &nbsp;&nbsp;&nbsp;&nbsp;<?= $user['appointments'][0]['start_time']?> - <?= $user['appointments'][0]['end_time']?></span></h4>
                <button type="submit">Remove Appointment</button>
            </form>

            <button id="new_appointment" class="<?= $hiddenButAdmin ?>">Create new appointment</button>
        </div>
    </div>
    <div class="container">
        <div class="date-next"><h3><a href="?ym=<?php echo $prev; ?>">&lt; </a><h3 id="yearPmonth"><?php echo $html_title; ?></h3><h3><a href="?ym=<?php echo $next; ?>"> &gt;</a></h3></div>
        <table class="table table-bordered" id="calendar">
            <tr>
                <th>M</th>
                <th>T</th>
                <th>W</th>
                <th>T</th>
                <th>F</th>
                <th>S</th>
                <th>S</th>
            </tr>
            <?php
                foreach ($weeks as $week) {
                    echo $week;
                }
            ?>
        </table>
    </div>

    <button id="logout_btn">Logout</button>

    <script src="../js/index.js"></script>
</body>
</html>