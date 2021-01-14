<?php
include('userstorage.php');
include('auth.php');

session_start();
$auth = new Auth(new UserStorage());
$auth->logout();
header("Location: ../php/login.php");
exit();

?>