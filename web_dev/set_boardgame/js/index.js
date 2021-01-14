// ui elements
const gameDescToggle = document.querySelector('#expandable')
const gameDesc = document.querySelector('#game_description')
const form = document.querySelector('form')
const gameMode = form.mode
const difficulty = form.difficulty
const additionalSettings = document.querySelector('.additional_settings')
const playerCount = form.player_counter
const playerNames = document.querySelector('#player_names')
const startGameButton = document.querySelector('#start_game')
const shownCards = document.querySelector('#shown_cards')
const cardsInDeckUI = document.querySelector('#cards_indeck')
const addNewCards = document.querySelector('#add_new')
const gameBoard = document.querySelector('#game_board')
const playerScores = document.querySelector('#players')
const setExistBtn = document.querySelector('#set_exist_btn')
const setExistLabel = document.querySelector('#set_exist_label')
const showSetBtn = document.querySelector('#show_set_btn')
const createSetExist = document.querySelector('#set_exist')
const createShowSet = document.querySelector('#show_set')
const createAddThree = document.querySelector('#add_three')
const timeLeft = document.querySelector('#time_left_selected')
const gameOverGoHome = document.querySelector('#game_over_btn')
const endGoHome = document.querySelector('#end_go_home')
const timer = document.querySelector('#timer')
const results = document.querySelector('#results')

// event listeners
gameDescToggle.addEventListener('click', toggleGameDesc)
for(let i=0; i<gameMode.length; i++){
    gameMode[i].addEventListener('click', toggleGameMode)
}
playerCount.addEventListener('change', playerCountChange)
startGameButton.addEventListener('click', startGame)
shownCards.addEventListener('click', cardSelected)
addNewCards.addEventListener('click', addCards)
setExistBtn.addEventListener('click', setExistUI)
showSetBtn.addEventListener('click', showPreparedSet)
playerScores.addEventListener('click', playerSelected)
endGoHome.addEventListener('click', goToHome)

//call on start
playerNames.innerHTML = setPlayerNames(1)

// ui functions
function toggleGameDesc(){
    if(gameDesc.classList.contains('hidden')){
        gameDesc.classList.remove('hidden')
    } else {
        gameDesc.classList.add('hidden')
    }
}

function toggleGameMode(e){
    if(e.target.checked){
        if(e.target.value === 'practice'){
            additionalSettings.classList.remove('hidden')
        } else {
            additionalSettings.classList.add('hidden')
        }
    }   
}

function setPlayerNames(n){
    let tempInner = ""
    for(let i=0; i<n; i++){
        tempInner += `<input type="text" id="name" style="margin: 5px 48px" value="${i+1}. player">`
    }
    return tempInner
}

function playerCountChange(e){
    playerNames.innerHTML = setPlayerNames(e.target.value)
}

function startGame(){
    gameBoard.classList.remove('hidden')
    document.querySelector('.menu').classList.add('hidden')
    loadDeck(difficulty.value)
    loadPlayers()
    shuffleDeck()
    for(let i=0; i<shuffledDeck.length; i++){
        console.log(deck[shuffledDeck[i]])
    }
    initialCards()
    updateCardsInDeck()
    showPlayers()
    document.querySelector('#advanced_buttons').classList.remove('hidden')
    shownCards.classList.add('col-lg-8')
    shownCards.classList.remove('col-0')
    playerScores.parentElement.classList.add('col-lg-4')
    playerScores.parentElement.classList.remove('col-12')
    document.querySelector('#main_banner').classList.add('hidden')
    
    if(!createSetExist.checked){
        setExistBtn.disabled = true
        setExistBtn.classList.add('hidden')
    } else {
        setExistBtn.disabled = false
        setExistBtn.classList.remove('hidden')
    } 
    if(!createShowSet.checked){
        showSetBtn.disabled = true
        showSetBtn.classList.add('hidden')
    } else {
        showSetBtn.disabled = false
        showSetBtn.classList.remove('hidden')
    }
    if(!createAddThree.checked){
        addNewCards.disabled = true
        addNewCards.classList.add('hidden')
    } else {
        addNewCards.disabled = false
        addNewCards.classList.remove('hidden')
    }
    if(players.length == 1){
        playerScores.childNodes[0].childNodes[1].childNodes[2].childNodes[0].disabled = true
        timer.classList.remove('hidden')
        startTimer()
    } else {
        timer.classList.add('hidden')
    }
}

function initialCards(){
    let innerHTML = `<table cellspacing="0" cellpadding="0"><tr>`
    for(let i=0; i<12; i++){
        if((i+1)%4 === 0){
            innerHTML += `<td cell-padding><img alt="${cardsInDeck}" src="../icons/${deck[shuffledDeck[cardsInDeck]].fileName}"/></td></tr><tr>`
        } else {
            innerHTML += `<td><img alt="${cardsInDeck}" src="../icons/${deck[shuffledDeck[cardsInDeck]].fileName}"/></td>`
        }
        cardsInDeck -= 1
    }
    innerHTML += `</table>`
    shownCards.innerHTML = innerHTML
}

//Game variables
const players = []
const deck = []
let cardsInDeck = 0
let shuffledDeck = new Set()
const number = [1,2,3]
const fill = ['H', 'O', 'S']
const color = ['g', 'p', 'r']
const shape = ['D', 'P', 'S']
let selectedCards = []
let selectedPlayerId = 0
let timeRemaining = 9
let timeTaken

//load deck
function loadDeck(type){
    if(deck.length != 0){
        resetDeck()
    }
    for(let i=0; i<number.length; i++){
        for(let j=0; j<shape.length; j++){
            for(let k=0; k<color.length; k++){
                if(type === 'expert'){
                    for(let l=0; l<fill.length; l++){
                        deck.push({
                            number: number[i],
                            fill: fill[l],
                            color:color[k],
                            shape: shape[j],
                            fileName: '' + number[i] + fill[l] + color[k] + shape[j] +'.svg'
                        })
                    }
                } else {
                    deck.push({
                        number: number[i],
                        fill: 'S',
                        color:color[k],
                        shape: shape[j],
                        fileName: '' + number[i] + 'S' + color[k] + shape[j] +'.svg'
                    })
                }
            }
        }
    }
    cardsInDeck = deck.length-1
}

//create player pool
function loadPlayers(){
    document.querySelectorAll('#player_names input').forEach(player => 
        players.push({
            name: player.value,
            score: 0
        })
    )
}

//create Shuffled order
function shuffleDeck(){
    while(shuffledDeck.size != deck.length){
        let temp = getRandomBetween(0,deck.length)
        if(!shuffledDeck.has(temp)){
            shuffledDeck.add(temp)
        }
    }
    shuffledDeck = Array.from(shuffledDeck)
}

//card is selected
function cardSelected(e){
    if(players.length <= 1 || selectedPlayerId != 0){
        if(players.length == 1){
            selectedPlayerId = 1
        }

        if(e.target.tagName === 'IMG'){
            e.target.classList.remove('suggested_card')
    
            if(selectedCards.length < 3){
    
                let tempCard = {
                    attrib: deck[shuffledDeck[e.target.alt]],
                    col: e.target.parentElement.cellIndex,
                    row: e.target.parentElement.parentElement.rowIndex
                }
                let inSelected = false
                let matchingId = 0;
                for(let i=0; i<selectedCards.length; i++){
                    if(selectedCards[i].col == tempCard.col && selectedCards[i].row == tempCard.row){
                        inSelected = true
                        matchingId = i
                    }
                }
                if(!inSelected){
                    selectedCards.push(tempCard)
                    e.target.classList.add('selected_card')
                } else {
                    selectedCards.splice(matchingId,1)
                    e.target.classList.remove('selected_card')
                }
            }
            if(selectedCards.length == 3){
                if(isSet(selectedCards[0].attrib,selectedCards[1].attrib,selectedCards[2].attrib)){
                    correctSet(selectedPlayerId)
                    //there is a set
                    for(let card of selectedCards){
                        shownCards.childNodes[0].childNodes[0].childNodes[card.row].childNodes[card.col].childNodes[0].classList.remove('selected_card')
                    }
                    if(shownCards.childNodes[0].childNodes[0].childNodes[0].childNodes.length===4 && cardsInDeck > 0){
                        for(let card of selectedCards){
                            shownCards.childNodes[0].childNodes[0].childNodes[card.row].childNodes[card.col].childNodes[0].src = `../icons/${deck[shuffledDeck[cardsInDeck]].fileName}`
                            shownCards.childNodes[0].childNodes[0].childNodes[card.row].childNodes[card.col].childNodes[0].alt = `${cardsInDeck}`
                            cardsInDeck -= 1
                        }
                        updateCardsInDeck()
                    } else {
                        let a=0
                        shownCards.childNodes[0].childNodes[0].childNodes.forEach(elem => a+=elem.children.length)
                        a = ((a)/3)-1
                        let moveableCards = []
                        for(let i=0; i<3; i++){
                            let tempPlus = shownCards.childNodes[0].childNodes[0].childNodes[i].childNodes[a]
                            let tempPlusCol = tempPlus.cellIndex
                            let tempPlusRow = tempPlus.parentElement.rowIndex
                            let selected = false
                            for(let j=0; j<3; j++){
                                if(selectedCards[j].col == tempPlusCol && selectedCards[j].row == tempPlusRow){
                                    selected = true
                                }
                            }
                            if(!selected){
                                moveableCards.push(tempPlus)
                            }
                        }
                        let moveableId = 0;
                        for(let i=0; i<3; i++){
                            if(selectedCards[i].col < a){
                                shownCards.childNodes[0].childNodes[0].childNodes[selectedCards[i].row].childNodes[selectedCards[i].col].childNodes[0].src = moveableCards[moveableId].childNodes[0].src
                                shownCards.childNodes[0].childNodes[0].childNodes[selectedCards[i].row].childNodes[selectedCards[i].col].childNodes[0].alt = moveableCards[moveableId].childNodes[0].alt
                                moveableId += 1
                            } else {
                                removeFromInner(shownCards.childNodes[0].childNodes[0].childNodes[selectedCards[i].row].innerHTML,selectedCards[i].col)
                            }
                        }
                        moveableCards.length=0
                        for(let i=0; i<3; i++) {
                            shownCards.childNodes[0].childNodes[0].childNodes[i].innerHTML = removeFromInner(shownCards.childNodes[0].childNodes[0].childNodes[i].innerHTML, a)
                        }
                    }
                    if(gameIsOver()){
                        gameOver()
                    }
                    selectedPlayerId = 0
                } else{
                    incorrectSet(selectedPlayerId)
                    for(let card of selectedCards){
                        shownCards.childNodes[0].childNodes[0].childNodes[card.row].childNodes[card.col].childNodes[0].classList.remove('selected_card')
                    }
                    selectedPlayerId = 0
                }
                selectedCards.length = 0
                while(setExist().length<3 && !createAddThree.checked && cardsInDeck>=0){
                    addCards()
                }
            }
        }
    }
}

//add cards
function addCards(){
    for(let i=0; i<3; i++){
        shownCards.childNodes[0].childNodes[0].childNodes[i].innerHTML += 
            `<td><img alt="${cardsInDeck}" src="../icons/${deck[shuffledDeck[cardsInDeck]].fileName}"/></td>`
            cardsInDeck -= 1
            updateCardsInDeck()
    }
    console.log(shownCards.childNodes[0].childNodes[0].childNodes[0].innerHTML)
}

//check if the selected is a set
function isSet(a,b,c){
    let numEq = a.number == b.number && b.number == c.number
    let numDif = a.number != b.number && b.number != c.number && a.number != c.number
    let num = numEq || numDif
    let fillEq = a.fill == b.fill && b.fill == c.fill
    let fillDif = a.fill != b.fill && b.fill != c.fill && a.fill != c.fill
    let fil = fillEq || fillDif
    let colEq = a.color == b.color && b.color == c.color
    let colDif = a.color != b.color && b.color != c.color && a.color != c.color
    let col = colEq || colDif
    let shapeEq = a.shape == b.shape && b.shape == c.shape
    let shapeDif = a.shape != b.shape && b.shape != c.shape && a.shape != c.shape
    let sha = shapeEq || shapeDif
    return num && fil && col && sha
}

//clear deck
function resetDeck() {
    deck.length = 0
}

function getRandomBetween(min, max){
    return Math.floor(Math.random() * (max-min)) + min
}

//update cards in deck
function updateCardsInDeck(){
    cardsInDeckUI.innerHTML = `<p style="color: white">remaining cards: ${cardsInDeck+1}</p>`
}

//remove from inner
function removeFromInner(inner,id){
    let tds = inner.split('</td>')
    let newInner = ''
    for(let i=0; i<tds.length; i++){
        if(i != id){
            newInner += tds[i] + '</td>'
        }
    }
    return newInner
}

function showPlayers(){
    let inner = '<tr><th>Name</th><th>Points</th><th>Set</th><th>Correct</th></tr>'
    for(let pl of players){
        inner += `<tr><td><p>${pl.name}</p></td><td><p>${pl.score}</p></td><td><button class="set_btn">SET</button></td><td><i style="color: green; font-size: 1.6em;" class="fas fa-check-circle hidden"></i><i style="color: red; font-size: 1.6em;" class="fas fa-times-circle hidden"></i></td></tr>`
    }
    playerScores.innerHTML = inner
}

function setExistUI(){
    if(setExist().length < 3){
        setExistLabel.innerHTML = `<h2>There is no set on this board</h2>`
        setTimeout(function(){setExistLabel.innerHTML =''},2000)
    } else {
        setExistLabel.innerHTML = `<h2>There is a set on this board</h2>`
        setTimeout(function(){setExistLabel.innerHTML =''},2000)
    }
}

function setExist(){
    let elements = createListOfVisible()
    let firstFoundSet = []
    let setFound = false
    for(let i=0; i<elements.length-2; i++){
        for(let j=i+1; j<elements.length-1; j++){
            for(let k=j+1; k<elements.length; k++){
                if(isSet(elements[i].card,elements[j].card,elements[k].card) && !setFound){
                    firstFoundSet.push(elements[i])
                    firstFoundSet.push(elements[j])
                    firstFoundSet.push(elements[k])
                    setFound = true
                }
            }   
        }
    }
    return firstFoundSet
}

function createListOfVisible(){
    if(shownCards.childNodes.length > 0){
        let rows = shownCards.childNodes[0].childNodes[0].childNodes
    let elements = []
    for(let i=0; i<rows.length; i++){
        for(let j=0; j<rows[i].childNodes.length; j++){
            elements.push({
                card: deck[shuffledDeck[rows[i].childNodes[j].childNodes[0].alt]],
                row: i,
                col: j
            })
        }
    }
    return elements
    } else {
        return []
    }
    
}

function showPreparedSet(){
    if(setExist().length === 3){
        let points = setExist()
        for(let p of points){
            shownCards.childNodes[0].childNodes[0].childNodes[p.row].childNodes[p.col].childNodes[0].classList.add('suggested_card')
        }
    }
}

function gameIsOver(){
    return cardsInDeck <= 0 && setExist().length == 0 
}

function playerSelected(e){
    if(e.target.tagName == 'BUTTON' && selectedPlayerId == 0){
        e.target.classList.remove('set_btn')
        e.target.classList.add('selected_btn')
        selectedPlayerId = e.target.parentElement.parentElement.rowIndex
        let lastPlayer = selectedPlayerId
        setTimeout(function(){
            e.target.classList.add('set_btn')
            e.target.classList.remove('selected_btn')
            selectedPlayerId = 0
        },10000)
        timeLeft.innerHTML=9
        timeLeft.classList.remove('hidden')
        timeRemaining = 9
        var timeInterval = setInterval(function(){
            if(timeRemaining <= 0) {
                clearInterval(timeInterval)
                timeLeft.classList.add('hidden')
                if(selectedCards.length<3 && selectedCards.length != 0){
                    for(let c of selectedCards){
                        shownCards.childNodes[0].childNodes[0].childNodes[c.row].childNodes[c.col].childNodes[0].classList.remove('selected_card')
                    }
                    selectedCards.length = 0
                    incorrectSet(lastPlayer)
                }
            }
            timeLeft.innerHTML = timeRemaining
            timeRemaining -= 1
        },1000)
    }
}

function correctSet(playerId) {
    playerScores.childNodes[0].childNodes[playerId].childNodes[3].childNodes[0].classList.remove('hidden')
    playerScores.childNodes[0].childNodes[playerId].childNodes[2].childNodes[0].classList.remove('selected_btn')
    playerScores.childNodes[0].childNodes[playerId].childNodes[2].childNodes[0].classList.add('set_btn')
    players[playerId-1].score += 1
    timeRemaining = 0
    selectedPlayerId = 0
    playerScores.childNodes[0].childNodes[playerId].childNodes[1].childNodes[0].innerHTML = players[playerId-1].score
    setTimeout(function(){playerScores.childNodes[0].childNodes[playerId].childNodes[3].childNodes[0].classList.add('hidden')}, 600)
}

function incorrectSet(playerId){
    playerScores.childNodes[0].childNodes[playerId].childNodes[3].childNodes[1].classList.remove('hidden')
    playerScores.childNodes[0].childNodes[playerId].childNodes[2].childNodes[0].classList.remove('selected_btn')
    playerScores.childNodes[0].childNodes[playerId].childNodes[2].childNodes[0].classList.add('set_btn')
    players[playerId-1].score -= 1
    players[playerId-1].score = players[playerId-1].score < 0 ? 0 : players[playerId-1].score 
    timeRemaining = 0
    selectedPlayerId = 0
    playerScores.childNodes[0].childNodes[playerId].childNodes[1].childNodes[0].innerHTML = players[playerId-1].score
    setTimeout(function(){playerScores.childNodes[0].childNodes[playerId].childNodes[3].childNodes[1].classList.add('hidden')}, 600)
}

function gameOver(){
    timeTaken = elapsedTime
    document.querySelector('#advanced_buttons').classList.add('hidden')
    cardsInDeckUI.classList.add('hidden')
    for(let i=0; i<=players.length; i++){
        playerScores.childNodes[0].childNodes[i].childNodes[2].classList.add('hidden')
        playerScores.childNodes[0].childNodes[i].childNodes[3].classList.add('hidden')
    }
    document.querySelector('#main_banner').classList.remove('hidden')
    shownCards.innerHTML = shownCards.innerHTML.split('</table>')[1]
    shownCards.classList.remove('col-lg-8')
    shownCards.classList.add('col-0')
    playerScores.parentElement.classList.remove('col-lg-4')
    playerScores.parentElement.classList.add('col-12')
    endGoHome.classList.remove('hidden')
    if(players.length == 1 && gameMode.value === 'race'){
        if(difficulty.value === 'expert'){
            updateTopTenAdvanced(timer.innerHTML, players[0].name)
        } else {
            updateTopTenBeginner(timer.innerHTML, players[0].name)
        }
    } else if(players.length > 1){
        updateLastTenGroup(players)
    }
    resetTimer()
    showLeaderBoards()
}

function goToHome(){
    gameBoard.classList.add('hidden')
    document.querySelector('.menu').classList.remove('hidden')
    shuffledDeck = new Set()
    players.length = 0
    endGoHome.classList.add('hidden')
}

//stopwatch
let startTime
let elapsedTime = 0
let timerInterval

function startTimer(){
    startTime = Date.now() - elapsedTime
    timerInterval = setInterval(function printTime(){
        elapsedTime = Date.now() - startTime
        timer.innerHTML = timeToString(elapsedTime)
    },10)
}

function timeToString(time) {
    let diffInHrs = time / 3600000
    let hh = Math.floor(diffInHrs)
  
    let diffInMin = (diffInHrs - hh) * 60
    let mm = Math.floor(diffInMin)
  
    let diffInSec = (diffInMin - mm) * 60
    let ss = Math.floor(diffInSec)
  
    let diffInMs = (diffInSec - ss) * 100
    let ms = Math.floor(diffInMs)
  
    let formattedMM = mm.toString().padStart(2, "0")
    let formattedSS = ss.toString().padStart(2, "0")
    let formattedMS = ms.toString().padStart(2, "0")
  
    return `${formattedMM}:${formattedSS}:${formattedMS}`
}

function resetTimer(){
    clearInterval(timerInterval)
    elapsedTime = 0
}

//leaderboards
let topTenBeginner = []
let topTenAdvanced = []
let lastTenGroup = []

function updateTopTenAdvanced(currentTime,n) {
    if(topTenAdvanced[topTenAdvanced.length-1] > currentTime || topTenBeginner.length < 10){
        if(topTenAdvanced.length == 10){
            topTenAdvanced.push({name: n,time:currentTime})
            topTenAdvanced.sort((a,b) => (a.time > b.time) ? 1 : ((b.time > a.time) ? -1 : 0))
            topTenAdvanced.pop()
        } else {
            topTenAdvanced.push({name: n,time:currentTime})
            topTenAdvanced.sort((a,b) => (a.time > b.time) ? 1 : ((b.time > a.time) ? -1 : 0))
        }
    }
    saveLeaderBoard()
}

function updateTopTenBeginner(currentTime,n) {
    if(topTenBeginner[topTenBeginner.length-1] > currentTime || topTenBeginner.length < 10){
        if(topTenBeginner.length == 10){
            topTenBeginner.push({name: n,time:currentTime})
            topTenBeginner.sort((a,b) => (a.time > b.time) ? 1 : ((b.time > a.time) ? -1 : 0))
            topTenBeginner.pop()
        } else {
            topTenBeginner.push({name: n,time:currentTime})
            topTenBeginner.sort((a,b) => (a.time > b.time) ? 1 : ((b.time > a.time) ? -1 : 0))
        }
    }
    saveLeaderBoard()
}

function updateLastTenGroup(p) {
    if(lastTenGroup.length == 10){
        p.sort((a,b) => (a.score < b.score) ? 1 : ((b.score < a.score) ? -1 : 0))
        lastTenGroup.push(p)
        lastTenGroup.shift()
    } else {
        p.sort((a,b) => (a.score < b.score) ? 1 : ((b.score > a.score) ? -1 : 0))
        lastTenGroup.push(p)      
    }
    saveLeaderBoard()
}

function showLeaderBoards() {
    results.classList.remove('hidden')
    let inner = ''
    for(let p of topTenBeginner){
        inner += `<li>${p.name} - ${p.time}</li>`
    }
    results.firstElementChild.firstElementChild.nextElementSibling.innerHTML = inner

    inner = ''
    for(let p of topTenAdvanced){
        inner += `<li>${p.name} - ${p.time}</li>`
    }
    results.firstElementChild.nextElementSibling.firstElementChild.nextElementSibling.innerHTML = inner

    inner = ''
    for(let i=lastTenGroup.length-1; i>=0; i--){
        inner += '<li><ol>'
        for(let p of lastTenGroup[i]){
            inner += `<li>${p.name} - ${p.score}</li>`
        }
        inner += '</ol></li>'
    }
    results.firstElementChild.nextElementSibling.nextElementSibling.firstElementChild.nextElementSibling.innerHTML = inner
}

function saveLeaderBoard(){
    localStorage.setItem("single_beginner", JSON.stringify(topTenBeginner))
    localStorage.setItem("single_expert", JSON.stringify(topTenAdvanced))
    localStorage.setItem("last_ten_multi", JSON.stringify(lastTenGroup))
}

function loadLeaderboards(){
    topTenBeginner = JSON.parse(localStorage.getItem("single_beginner"));
    topTenAdvanced = JSON.parse(localStorage.getItem("single_expert"));
    lastTenGroup = JSON.parse(localStorage.getItem("last_ten_multi"));
    topTenBeginner = topTenBeginner == null ? [] : topTenBeginner;
    topTenAdvanced = topTenAdvanced == null ? [] : topTenAdvanced;
    lastTenGroup = lastTenGroup == null ? [] : lastTenGroup;
}
loadLeaderboards()
showLeaderBoards()