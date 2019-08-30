 $(document).ready(function(){ 
	 
	 
		var a = $("#time").html();
		var gmt = getTaskTime(a)

		var now = new Date(gmt);
		var year = now.getFullYear(); //年
		var month = now.getMonth() + 1; //月
		var day = now.getDate(); //日
		var hh = now.getHours(); //时
		var mm = now.getMinutes(); //分

		var d = Date.UTC(year, month, day, hh, mm);
		var obj = {
			sec : document.getElementById("ss"),
			mini : document.getElementById("mm"),
			hour : document.getElementById("hh")
		};
		fnTimeCountDown(d, obj);

		function getTaskTime(strDate) {
			if (null == strDate || "" == strDate) {
				return "";
			}
			var dateStr = strDate.trim().split(" ");
			var strGMT = dateStr[0] + " " + dateStr[1] + " " + dateStr[2] + " " + dateStr[5] + " " + dateStr[3] + " GMT+0800";
			var date = new Date(Date.parse(strGMT));
			var y = date.getFullYear();
			var m = date.getMonth() + 1;
			m = m < 10 ? ('0' + m) : m;
			var d = date.getDate();
			d = d < 10 ? ('0' + d) : d;
			var h = date.getHours();
			var minute = date.getMinutes();
			minute = minute < 10 ? ('0' + minute) : minute;
			var second = date.getSeconds();
			second = second < 10 ? ('0' + second) : second;

			return y + "-" + m + "-" + d + " " + h + ":" + minute + ":" + second;
		};
	 
	 
	 
 })