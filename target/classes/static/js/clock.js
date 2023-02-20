const deg = 6;
const hr = document.querySelector('#hr');
const mn = document.querySelector('#mn');
const sc = document.querySelector('#sc');

setInterval (()=> {
    let day = new Date();
    let hh = day.getHours() * 30;
    let mm = day.getMinutes() * deg;
    let ss = day.getSeconds() * deg;

    hr.style.transform = `rotateZ(${(hh)+(mm/12)}deg)`;
    mn.style.transform = `rotateZ(${mm}deg)`;
    sc.style.transform = `rotateZ(${ss}deg)`;
})

//출력
function GetCurDayTime(dateFormat,timeFormat) {
    return this.TimeString(dateFormat,timeFormat,new Date());
}

function TimeString (dateFormat='/',timeFormat=':',date) {
        
    let year = date.getFullYear();
    let month = date.getMonth() + 1; // 1월=0,12월=11이므로 1 더함
    let day = date.getDate();
    let hour = date.getHours();
    let min = date.getMinutes();

    if (("" + month).length == 1) {
        month = "0" + month;
    }
    if (("" + day).length == 1) {
        day = "0" + day;
    }
    if (("" + hour).length == 1) {
        hour = "0" + hour;
    }
    if (("" + min).length == 1) {
        min = "0" + min;
    }

    return ("" + year+ dateFormat + month+ dateFormat + day+ " " + hour+ timeFormat + min)
}


function getDate() {
    const week = new Array('SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY');
    const today = new Date();
    const dayName = week[today.getDay()];
    const hours = today.getHours() % 12 ? today.getHours() % 12 : 12;
    const minutes = today.getMinutes() < 10 ? '0' + today.getMinutes() : today.getMinutes();
    const ampm = today.getHours() >= 12 ? 'PM' : 'AM';
    const date = `${dayName} | ${ampm} ${hours}:${minutes}`;

    return date;
}

const update = () => {
    document.querySelector('.date').innerHTML = getDate();
    requestAnimationFrame(update);
};

update();