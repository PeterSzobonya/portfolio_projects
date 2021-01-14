<?php
include_once('storage.php');

class AppointmentStorage extends Storage {
  public function __construct() {
    parent::__construct(new JsonIO('../database/appointments.json'));
  }
}