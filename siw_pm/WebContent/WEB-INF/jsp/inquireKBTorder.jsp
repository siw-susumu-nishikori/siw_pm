<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.Iterator"
	import="java.util.List"
    import="jp.siw.pm.edi.bean.KBTItemBean"
    import="jp.siw.pm.edi.util.PropertyLoader"
    import="jp.siw.pm.edi.util.Cast"
    import="java.util.Calendar"
    import="java.sql.Timestamp"
	import="java.text.SimpleDateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>クボタ受注照会</title>
	<link href="./css/common.css" rel="stylesheet" type="text/css">
	<link href="./css/table.css" rel="stylesheet" type="text/css">
	<link href="./css/juchu.css" rel="stylesheet" type="text/css">
</head>

<body>
<h2>クボタ受注照会</h2>
<% Calendar cal = Calendar.getInstance(); //カレンダーオブジェクトを取得
int year = cal.get(Calendar.YEAR); //現在の年を取得
int month = cal.get(Calendar.MONTH) + 1; //現在の月を取得
int day = cal.get(Calendar.DATE); //現在の日を取得
%>

<% Timestamp nowTime= new Timestamp(System.currentTimeMillis());
SimpleDateFormat timeStampNowDay = new SimpleDateFormat("MM/dd");
SimpleDateFormat timeStampNowTime = new SimpleDateFormat("HH:mm:ss");
String ymd  = timeStampNowDay.format(nowTime);
%>

<% List<KBTItemBean> itemListOrder = Cast.castList(request.getAttribute("itemListOrder"));
Iterator<KBTItemBean> iterator = itemListOrder.iterator();
Iterator<KBTItemBean> iterator2 = itemListOrder.iterator();
Iterator<KBTItemBean> iterator3 = itemListOrder.iterator();
Iterator<KBTItemBean> iterator4 = itemListOrder.iterator();
Iterator<KBTItemBean> iterator5 = itemListOrder.iterator();
Iterator<KBTItemBean> iterator6 = itemListOrder.iterator();%>

<%request.setCharacterEncoding("UTF-8");
String hinban = request.getParameter("hinban");
String day1 = request.getParameter("day1");
String day2 = request.getParameter("day2");
String day3 = request.getParameter("day3");
%>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.KBTorderManager") %>'">戻る</button>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.TopMenuManager") %>'">トップメニューへ</button>
<article>
	<section>
		<table>
			<tr>
				<td style="border-style: none;">品番：<%out.println(hinban); %></td>
			</tr>
		</table>

		<table class="juchu_waku">
			<tr>
				<td>納入指示日</td>
				<% while (iterator.hasNext()) {KBTItemBean item = iterator.next();%>
				<td><%=item.getNounyushiji_ymd()%></td>
				<%} %>
			</tr>
			<tr>
				<td>納入場所</td>
				<% while (iterator2.hasNext()) {KBTItemBean item2 = iterator2.next();%>
				<td><%=item2.getNoba_cd()%></td>
				<%} %>
			</tr>
			<tr>
				<td id="day1_qty"><%out.println(day1); %>数量</td>
				<% while (iterator3.hasNext()) {KBTItemBean item3 = iterator3.next();%>
				<td id="day1_qty"><%=item3.getQty1()%></td>
				<%} %>
			</tr>
			<tr>
				<td><%out.println(day2); %>数量</td>
				<% while (iterator4.hasNext()) {KBTItemBean item4 = iterator4.next();%>
				<td><%=item4.getQty2()%></td>
				<%} %>
			</tr>
			<tr>
				<td id="day3_qty"><%out.println(day3); %>数量</td>
				<% while (iterator5.hasNext()) {KBTItemBean item5 = iterator5.next();%>
				<td id="day3_qty"><%=item5.getQty3()%></td>
				<%} %>
			</tr>
			<tr>
				<td>確定数量</td>
				<% while (iterator6.hasNext()) {KBTItemBean item6 = iterator6.next();%>
				<td><%=item6.getFinal_qty()%></td>
				<%} %>
			</tr>
		</table>
	</section>
</article>
</body>
</html>