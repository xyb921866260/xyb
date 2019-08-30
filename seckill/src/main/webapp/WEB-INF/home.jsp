<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">

<title>首页</title>

<link href=" AmazeUI-2.4.2/assets/css/amazeui.css" rel="stylesheet"
	type="text/css" />
<link href=" AmazeUI-2.4.2/assets/css/admin.css" rel="stylesheet"
	type="text/css" />

<link href=" basic/css/demo.css" rel="stylesheet" type="text/css" />

<link href=" css/hmstyle.css" rel="stylesheet" type="text/css" />
<script src=" AmazeUI-2.4.2/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="js/timeCountDown.js"></script>
<script type="text/javascript" src="js/main.js"></script>

</head>

<body>
	<div class="am-container">

		<div class="sale-mt">
			<i></i> <em class="sale-title">限时秒杀</em>
			<div class="s-time" id="countdown">
				<span id="hh" class="hh"></span> <span id="mm" class="mm"></span> <span
					id="ss" class="ss"></span> <span style="display:none" id="time">${time }</span>
			</div>
		</div>


		<div class="am-g am-g-fixed sale">
			<c:forEach var="sku" items="${list}">
				<div class="am-u-sm-3 sale-item">
					<div class="s-img">
						<a href="# "><img src=" ${sku.picture }" /></a>
					</div>
					<div class="s-info">
						<a href="javascript:void(0)" ><p class="s-title">${sku.goodsname}</p></a>
						<div class="s-price">
							￥<b>${sku.price }</b> <a class="s-buy" href="javascript:void(0)"
								 onclick="goseckill(${sku.id })">秒杀${sku.count }</a>
						</div>
					</div>
				</div>
			</c:forEach>

		</div>
		<div style="width:auto;height:auto">
		<button id="button" style="width:100px;height:50px;background:gray;font-size:30px">登录</button>
		<button id="button2" style="width:100px;height:50px;background:gray;font-size:20px">清空缓存</button>
	</div>
	</div>
	
</body>
<script>
$("#button").click(function(){
location="login"

})
$("#button2").click(function(){
location="remove"

})
function goseckill(a){
//这是setting比较多的请求
$.ajax({
       type:"post",
       url:"seckill?id="+a,
       processData: false,
       success:function (data) {
       if(data.data=="notLogin"){
        alert("请登录");
       }if(data.data=="success"){
       }else{
       alert(data.data)
       }
       },
       error:function(e){
           console.log(e)//请求失败是执行这里的函数
       }
   });


}




</script>

</html>