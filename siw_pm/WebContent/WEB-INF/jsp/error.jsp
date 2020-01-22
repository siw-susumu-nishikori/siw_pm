<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="jp.siw.pm.edi.util.PropertyLoader"%>

<%
String today = (String)request.getAttribute("today");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<link href="./css/common.css" rel="stylesheet" type="text/css">
			<link href="./css/juchu.css" rel="stylesheet" type="text/css">

		<title>error</title>
	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
		<h1>error</h1>
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
					<li><a href="/siw_pm/KBTediManager" >クボタEDI取込<br><span></span></a></li>
					<li><a href="/siw_pm/KBTorderManager">受注検索<br/><span></span></a></li>
					<li><a href="/siw_pm/TanpinZaikoManager" >単品在庫データ取込<br><span></span></a></li>
					<li><a href="/siw_pm/KakuteiKikanSetManager" >定期確定間設定<br><span></span></a></li>
				</ul>
			</nav>
		</div>
		<!-- /グローバルナビゲーションエリアここまで -->

		<article>
			<section class="cntr" style="margin-top:50px; margin-bottom:50px;">
				<p class="error">エラー</p>
				<p class="error1">${errorMessage}</p>
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
