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
		<title>クボタ受注TopMenu</title>
	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
				<h1>トップメニュー</h1>
				<p id="logo"><a href="index.html">
				<img src="../../siwportalsite/SIW-Portal-site/images/siw-logo.png" width="150" height="25" alt="シンバテッコウショ" class="auto-style1"/></a><%= today %></p>
			</header>
		</div>
		<!-- /ヘッダーエリアここまで -->

		<!-- グローバルナビゲーションエリアここから -->
		<div class="wrapper row2">
			<nav id="g_navi">
				<ul>
					<li><a href="/siw_pm/KBTorderManager">受注検索メニュー<br/><span></span></a></li>
				</ul>
			</nav>
		</div>
		<!-- /グローバルナビゲーションエリアここまで -->

		<article>
			<section>
				<form method="Get" action="<%=PropertyLoader.getProperty("url.servlet.KBTediManager") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
					<table class="center">
						<tr>
							<td><input type="submit" class="top_button" value="クボタEDIデータ取込"  ></td>
						</tr>
					</table>
				</form>
			</section>
		</article>
		<article>
			<section>
				<form method="Get" action="<%=PropertyLoader.getProperty("url.servlet.KBTorderManager") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
					<table class="center">
						<tr>
							<td><input class="top_button" type="submit" value="受注検索" ></td>
						</tr>
					</table>
				</form>
			</section>
		</article>
		<article>
			<section>
				<form method="Get" action="<%=PropertyLoader.getProperty("url.servlet.TanpinZaikoManager") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
					<table class="center">
						<tr>
							<td><input class="top_button"type="submit" value="単品在庫データ取込" ></td>
						</tr>
					</table>
				</form>
			</section>
		</article>
		<article>
			<section class="bottom">
				<form method="Get" action="<%=PropertyLoader.getProperty("url.servlet.KakuteiKikanSetManager") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
					<table class="center">
						<tr>
							<td><input class="top_button" type="submit" value="定期確定期間設定" ></td>
						</tr>
					</table>
				</form>
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