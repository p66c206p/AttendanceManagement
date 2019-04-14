function set2fig(num) {
   // 桁数が1桁だったら先頭に0を加えて2桁に調整する
   var ret;
   if( num < 10 ) { ret = "0" + num; }
   else { ret = num; }
   return ret;
}
function showClock() {
   var nowTime = new Date();
   var now   = new Date();       
   var year  = now.getFullYear();
   var month = now.getMonth() + 1;
   var day   = now.getDate();
   var hour = set2fig(nowTime.getHours());
   var min  = set2fig(nowTime.getMinutes());
   var sec  = set2fig(nowTime.getSeconds());
   var time = year + "年" + month + "月" + day + "日 " + hour + ":" + min + ":" + sec;
   document.getElementById("RealtimeClockArea").innerHTML = time;
}
setInterval('showClock()',1000);