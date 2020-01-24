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

<%
String today = (String)request.getAttribute("today");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<%request.setCharacterEncoding("UTF-8");
		String hinban = request.getParameter("hinban");%>
			<title>受注照会 <%out.println(hinban); %></title>
				<link href="./css/common.css" rel="stylesheet" type="text/css">
				<link href="./css/table.css" rel="stylesheet" type="text/css">
				<link href="./css/juchu.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
				<h1>クボタ受注照会</h1>
				<p id="logo"><a href="index.html">
				<img src="../../siwportalsite/SIW-Portal-site/images/siw-logo.png" width="150" height="25" alt="シンバテッコウショ" class="auto-style1"/></a><%= today %></p>
			</header>
		</div>
		<!-- /ヘッダーエリアここまで -->

		<!-- グローバルナビゲーションエリアここから -->
		<div class="wrapper row2">
			<nav id="g_navi">
				<ul>
					<li><a href="#" onclick="history.back(); return false;">前に戻る</a></li>
					<li><a href="/siw_pm/TopMenuManager" >トップメニュー<br><span></span></a></li>
					<li><a href="/siw_pm/KBTorderManager">受注検索メニュー<br/><span></span></a></li>
				</ul>
			</nav>
		</div>
			<!-- /グローバルナビゲーションエリアここまで -->

		<% Calendar cal = Calendar.getInstance(); //カレンダーオブジェクトを取得
		int year = cal.get(Calendar.YEAR); //現在の年を取得
		int month = cal.get(Calendar.MONTH) + 1; //現在の月を取得
		int day = cal.get(Calendar.DATE); //現在の日を取得
		%>

		<% List<KBTItemBean> itemListOrder = Cast.castList(request.getAttribute("itemListOrder"));
		Iterator<KBTItemBean> iterator = itemListOrder.iterator();
		Iterator<KBTItemBean> iterator2 = itemListOrder.iterator();
		Iterator<KBTItemBean> iterator3 = itemListOrder.iterator();
		Iterator<KBTItemBean> iterator4 = itemListOrder.iterator();
		Iterator<KBTItemBean> iterator5 = itemListOrder.iterator();
		Iterator<KBTItemBean> iterator6 = itemListOrder.iterator();
		%>

		<%String day1 = request.getParameter("day1");
		String day2 = request.getParameter("day2");
		String day3 = request.getParameter("day3");
		%>

		<article>
			<section class="cntr, bottom" style="width:1350px;">
				<table class="center">
					<tr>
						<td class="hinban">品番：<%out.println(hinban); %></td>
					</tr>
				</table>

				<table class="juchu_waku" style="float:none;">
				<caption>《受注照会》</caption>
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
						<td class="day1_qty"><%out.println(day1); %>数量</td>
						<% while (iterator3.hasNext()) {KBTItemBean item3 = iterator3.next();%>
						<td class="day1_qty"><%=item3.getQty1()%></td>
						<%} %>
					</tr>
					<tr>
						<td><%out.println(day2); %>数量</td>
						<% while (iterator4.hasNext()) {KBTItemBean item4 = iterator4.next();%>
						<td><%=item4.getQty2()%></td>
						<%} %>
					</tr>
					<tr>
						<td class="day3_qty"><%out.println(day3); %>数量</td>
						<% while (iterator5.hasNext()) {KBTItemBean item5 = iterator5.next();%>
						<td class="day3_qty"><%=item5.getQty3()%></td>
						<%} %>
					</tr>
					<tr>
						<td class="comfirm">確定数量</td>
						<% while (iterator6.hasNext()) {KBTItemBean item6 = iterator6.next();%>
						<td class="comfirm"><%=item6.getFinal_qty()%></td>
						<%} %>
					</tr>
				</table>
			</section>
		</article>

		<!-- フッターエリアここから -->
		<div class="wrapper row5">
			<footer id="main_footer">
				<p id="copy">Copyright (C) Shinba Iron Works Corporation. All Rights Reserved.</p>
			</footer>
		</div><!-- /フッターエリアここまで -->
	</body>
</html>