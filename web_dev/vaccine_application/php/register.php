<?php
include('../services/userstorage.php');
include('../services/auth.php');

//functions
function validate($post, &$data, &$errors, $user_storage) {
    if(isset($post['email']) && isset($post['password']) && isset($post['taj']) && isset($post['adress']) && isset($post['re_password']) && isset($post['name'])){
        if($post['email'] != "" && $post['password'] != "" && $post['taj'] != "" && $post['adress'] != "" && $post['re_password'] != "" && $post['name'] != ""){
            if($user_storage->findOne(["email"=>$post['email']]) != NULL){
                array_push($errors,"user_already_uses_email");
            }
            $data = $post;
        }
        if (!filter_var($post['email'], FILTER_VALIDATE_EMAIL)) {
            array_push($errors,"invalid_email_format");
        } 
        if($post['re_password'] != $post['password']){
            array_push($errors, "passwords_mismatch");
        } 
        if(!preg_match("/([0-9]{8})/",$post[taj])){
            array_push($errors, "incorrect_taj_format");
            echo "preg error";
        }
        if(!$post['email']){
            array_push($errors, "missing_email");
        }
        if(!$post['password']){
            array_push($errors, "missing_password");
        }
        if(!$post['taj']){
            array_push($errors, "missing_taj");
        }
        if(!$post['adress']){
            array_push($errors, "missing_adress");
        }
        if(!$post['re_password']){
            array_push($errors, "missing_re_password");
        }
        if(!$post['name']){
            array_push($errors, "missing_name");
        }
    } else {
        array_push($errors, "data_not set");
    }

    return count($errors) === 0;
}

//main
$user_storage = new UserStorage();
$auth = new Auth($user_storage);
$errors = [];
$data = [];
if(count($_POST > 0)) {
    if(validate($_POST, $data, $errors,$user_storage)) {
        if($auth->user_exists($data['name'])){
            $errors['global'] = "User already exists";
        } else {
            array_push($data);
            $auth->register($data);
            header("Location: login.php");
            exit();
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
    <title>NatCoVid-register</title>
</head>
<body>
<h1 style="text-align:center; padding-bottom:30px;">Registration</h1>
<?php if(in_array("user_already_uses_email",$errors)) : ?>
    <h3 style="color:red">This email address is already taken</h3>
<?php endif; ?>
    <div class="container-fluid">
        <form action="" method="post">
            <div class="row">
                <div class="col-sm-5 text-right">
                    <label style="padding-top:10px;" for="fullname_register">Full name</label>
                </div>
                <div class="col-sm-7">
                <?php if(isset($_POST['email'])) : ?>
                    <input type="text" id="fullname_register" name="name" value="<?= $_POST['name'] ?>">
                    <?php else : ?>
                        <input type="text" id="fullname_register" name="name">
                    <?php endif; ?>
                    
                    <?php if(in_array("missing_name", $errors)) : ?>
                        <p style="color:red;">Please provide your name</p>
                    <?php endif; ?>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-5 text-right">
                    <label style="padding-top:10px;" for="TAJ_register">TAJ number</label>
                </div>
                <div class="col-sm-7">
                <?php if(isset($_POST['taj'])) : ?>
                    <input type="text" id="TAJ_register" name="taj" value="<?= $_POST['taj'] ?>">
                    <?php else : ?>
                        <input type="text" id="TAJ_register" name="taj">
                    <?php endif; ?>
                    
                    <?php if(in_array("missing_taj", $errors)) : ?>
                        <p style="color:red;">Please provide your TAJ number</p>
                    <?php endif; ?>
                    <?php if(in_array("incorrect_taj_format", $errors)) : ?>
                        <p style="color:red;">Invalid taj format</p>
                    <?php endif; ?>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-5 text-right">
                    <label style="padding-top:10px;" for="adress_register">Adress</label>
                </div>
                <div class="col-sm-7">
                <?php if(isset($_POST['adress'])) : ?>
                    <input type="text" id="adress_register" name="adress" value="<?= $_POST['adress'] ?>">
                    <?php else : ?>
                        <input type="text" id="adress_register" name="adress">
                    <?php endif; ?>
                    
                    <?php if(in_array("missing_adress", $errors)) : ?>
                        <p style="color:red;">Please provide your adress</p>
                    <?php endif; ?>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-5 text-right">
                    <label style="padding-top:10px;" for="email_register">Email</label>
                </div>
                <div class="col-sm-7">
                <?php if(isset($_POST['email'])) : ?>
                    <input type="email" id="email_register" name="email" value="<?= $_POST['email'] ?>">
                    <?php else : ?>
                        <input type="email" id="email_register" name="email">
                    <?php endif; ?>
                    
                    <?php if(in_array("missing_email", $errors)) : ?>
                        <p style="color:red;">Please provide your email adress</p>
                    <?php endif; ?>
                    <?php if(in_array("invalid_email_format", $errors)) : ?>
                        <p style="color:red;">Invalid email format</p>
                    <?php endif; ?>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-5 text-right">
                    <label style="padding-top:10px;" for="password_register">Password</label>
                </div>
                <div class="col-sm-7">
                    <input type="password" id="password_register" name="password">
                    <?php if(in_array("missing_password", $errors)) : ?>
                        <p style="color:red;">Please provide your password</p>
                    <?php endif; ?>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-5 text-right">
                    <label style="padding-top:10px;" for="re_password_register">Password Confirm</label>
                </div>
                <div class="col-sm-7">
                    <input type="password" id="re_password_register" name="re_password">
                    <?php if(in_array("missing_re_password", $errors)) : ?>
                        <p style="color:red;">Please confirm your password</p>
                    <?php endif; ?>
                    <?php if(in_array("passwords_mismatch", $errors)) : ?>
                        <p style="color:red;">Your passwords are not the same</p>
                    <?php endif; ?>
                </div>
            </div>
            <div style="text-align:center; padding-top:30px;">
                <button type="submit">Register</button>
            </div>
        </form>
        <div style="text-align:center; padding-top:30px;">
            <p>Already have a user?</p>
            <a href="login.php">Login</a>
        </div>
    </div>
</body>
</html>