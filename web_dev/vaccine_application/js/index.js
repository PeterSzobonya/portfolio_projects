//elements
const calendar = document.querySelector("#calendar");
const yearPerMonth = document.querySelector("#yearPmonth");
const newAppointment = document.querySelector("#new_appointment");
const logoutBtn = document.querySelector("#logout_btn");

//event listeners
calendar.addEventListener('click', dateSelected);
newAppointment.addEventListener('click', createNewAppointment);
logoutBtn.addEventListener('click', logout);

//functions
function dateSelected(e){
    
    if(e.target.tagName === "TD" && e.target.innerHTML != ""){
        const year = yearPerMonth.innerHTML.split(" / ")[0];
        const month = yearPerMonth.innerHTML.split(" / ")[1];
        window.location.replace(`date.php?year=${year}&month=${month}&day=${e.target.innerHTML.split("<p>")[0]}`);
    }
}

function createNewAppointment() {
    window.location.replace(`newAppointment.php`);
}

function logout(){
    window.location.replace(`../services/logout.php`);
}