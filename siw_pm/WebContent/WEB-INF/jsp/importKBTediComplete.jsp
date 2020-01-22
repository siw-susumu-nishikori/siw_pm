<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="jp.siw.pm.edi.util.PropertyLoader"
import="java.sql.Timestamp"
import="java.text.SimpleDateFormat"
%>

<%
Timestamp nowTime= new Timestamp(System.currentTimeMillis());
SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
String today  = timeStampNowDay.format(nowTime);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>取込完了</title>
				<link href="./css/common.css" rel="stylesheet" type="text/css">
				<link href="./css/juchu.css" rel="stylesheet" type="text/css">
	</head>
	<body>

		<div class="wrapper row1">
			<header id="main_header" >
				<h1>取込完了</h1>
					<p id="logo"><a href="index.html">
				<img src="../../siwportalsite/SIW-Portal-site/images/siw-logo.png" width="150" height="25" alt="シンバテッコウショ" class="auto-style1"/></a><%= today %></p>
			</header>
		</div>
		<!-- /ヘッダーエリアここまで -->

		<!-- グローバルナビゲーションエリアここから -->
		<div class="wrapper row2">
			<nav id="g_navi">
				<ul>
					<li><a href="/siw_pm/TopMenuManager" >トップメニュー<br><span></span></a></li>
					<li><a href="/siw_pm/KBTediManager" >クボタEDI取込<br><span></span></a></li>
					<li><a href="/siw_pm/KBTorderManager">受注検索<br/><span></span></a></li>
					<li><a href="/siw_pm/TanpinZaikoManager" >単品在庫データ取込<br><span></span></a></li>
					<li><a href="/siw_pm/KakuteiKikanSetManager" >定期確定間設定<br><span></span></a></li>
				</ul>
			</nav>
		</div>
		<!-- /グローバルナビゲーションエリアここまで -->

		<article>
			<section class="cntr">
				<p class="complete">取込完了しました</p>
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
