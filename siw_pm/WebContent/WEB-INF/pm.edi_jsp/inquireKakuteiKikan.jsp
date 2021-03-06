<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="java.util.Iterator"
import="java.util.List"
import="jp.siw.pm.edi.bean.KBTItemBean"
import="jp.siw.pm.edi.bean.KakuteikikanBean"
import="jp.siw.pm.edi.util.PropertyLoader"
import="jp.siw.pm.edi.util.Cast"
import="jp.siw.pm.edi.util.Cast_Kakuteikikan"
%>

<%
String today = (String)request.getAttribute("today");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<link href="./css/common.css" rel="stylesheet" type="text/css">
			<link href="./css/juchu.css" rel="stylesheet" type="text/css">
		<title>確定期間変更完了</title>
	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
				<h1>確定期間変更完了</h1>
					<p id="logo"><a href="index.html">
				<img src="../../siwportalsite/SIW-Portal-site/images/siw-logo.png" width="150" height="25" alt="シンバテッコウショ" class="auto-style1"/></a><%=today%></p>
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

		<% List<KakuteikikanBean> kikanList = Cast_Kakuteikikan.castList(request.getAttribute("kikanList"));
		Iterator<KakuteikikanBean> iteratorKikan = kikanList.iterator();
		%>

		<article>
			<section>
				<table class="center" style="margin-bottom:20px;">
					<tr>
						<% while (iteratorKikan.hasNext()) {KakuteikikanBean item = iteratorKikan.next();%>
						<th>今回確定期間</th>
					</tr>
					<tr>
						<td class="kikan"><%=item.getJun_nm() %></td>
						<td class="kikan">＜<%=item.getS_date() %></td>
						<td class="kikan">～</td>
						<td class="kikan"><%=item.getE_date() %>＞</td>
						<%} %>
						<td>に設定しました</td>
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