const goBackBtn = document.querySelector("#go_back_btn");

goBackBtn.addEventListener("click", goBack);

function goBack(){
    window.location.replace("index.php");
}