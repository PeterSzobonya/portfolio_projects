<?php
include('../services/userstorage.php');
include('../services/auth.php');

//functions
function validate($post, &$data, &$errors){
    if($_POST['email'] != "" && $_POST['password'] != "" ){
        if (!filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)) {
            array_push($errors,"invalid_email_format");
        } else {
            $data = $post;
        }
    }
    if(!$_POST['email']){
        array_push($errors, "missing_email");
    }
    if(!$_POST['password']){
        array_push($errors, "missing_password");
    }

    return count($errors) === 0;
}

//main
session_start();
$user_storage = new UserStorage();
$auth = new Auth($user_storage);
$data = [];
$errors = [];

if($_POST) {
    if(validate($_POST,$data,$errors)){
        $auth_user = $auth->authenticate($data['email'], $data['password']);
        if(!$auth_user) {
            $errors['global'] = "Login error";
        } else {
            $auth->login($auth_user);
            if($_GET['goto']){
                $goto = $_GET['goto'] . '&month=' . $_GET['month'] . '&day=' .$_GET['day'];
                header("Location: $goto");
                exit();
            } else {
                header("Location: index.php");
                exit();
            }
        }
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
    <title>NatCoVid-login</title>
</head>
<body>
    <h1 style="text-align:center; padding-bottom:30px;">Login</h1>
    <div class="container">
        <form action="" method="post">
            <div class="row">
                <div class="col-sm-5 text-right">
                    <label style="padding-top:10px;" for="email_login">Email</label>
                </div>
                <div class="col-sm-7">
                    <?php if(isset($_POST['email'])) : ?>
                        <input type="email" id="email_login" name="email" value="<?= $_POST['email'] ?>">
                    <?php else : ?>
                        <input type="email" id="email_login" name="email">
                    <?php endif; ?>
                    <?php if(in_array("missing_email", $errors)) : ?>
                        <p style="color:red;">Please provide your email adress</p>
                    <?php endif; ?>
                    <?php if(in_array("invalid_email_format", $errors)) : ?>
                        <p style="color:red;">Invalid email format</p>
                    <?php endif; ?>
                    </br>
                    <span></span>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-5 text-right">
                    <label style="padding-top:10px;" for="password_login">Password</label>
                </div>
                <div class="col-sm-7">
                    <input type="password" id="password_login" name="password">
                    <?php if(in_array("missing_password", $errors)) : ?>
                        <p style="color:red;">Please provide your password</p>
                    <?php endif; ?>
                    </br>
                    <span></span>
                </div>
            </div>
            <div style="text-align:center; padding-top:30px;">
                <button type="submit">Login</button>
            </div>
        </form>
        <div style="text-align:center; padding-top:30px;">
            <p>Not a member yet?</p>
            <a href="register.php">Register</a>
        </div>
    </div>
</body>
</html>