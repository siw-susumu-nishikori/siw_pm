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
			<title>EDIデータ取込</title>
				<link href="./css/common.css" rel="stylesheet" type="text/css">
				<link href="./css/juchu.css" rel="stylesheet" type="text/css">

			<script>
				function submitJvan(b){
					var flag = confirm("内示データ(RCV_JVAN.DAT)の取込を開始します。\n本当によろしいですか？");
					if(flag == true){
						b.disabled = true;
						b.value = '取込中';
						return true;
					}else{
						window.alert('キャンセルされました');
						return false;
					}
				}

				function submitNvan(b){
					var flag = confirm("確定データ(RCV_NVAN.DAT)の取込を開始します。\n本当によろしいですか？");
					if(flag == true){
						b.disabled = true;
						b.value = '取込中';
						return true;
					}else{
						window.alert('キャンセルされました');
						return false;
					}
				}
			</script>

	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
		<h1>クボタEDIデータ取込</h1>
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
					<li><a href="/siw_pm/KBTorderManager">受注検索メニュー<br/><span></span></a></li>
				</ul>
			</nav>
		</div>
		<!-- /グローバルナビゲーションエリアここまで -->

		<article>
			<section class="cntr">
				<a href="file:\\192.168.101.236\riseプロジェクト\16.クボタEDI取込フォルダ">クボタEDIデータ取込フォルダを開く</a>
				<form method="post" action="<%=PropertyLoader.getProperty("url.servlet.KBTediRead") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
					<table class="center">
						<tr>
							<td>■内示データ取込</td>
						</tr>
						<tr>
							<td><input type="submit" class="submit_btn" value="JVAN取込開始" onclick="return submitJvan(this)"  ></td>
						</tr>
					</table>
				</form>
			</section>
		</article>

		<article>
			<section class="cntr" style="margin-bottom:10px;">
				<form method="post" action="<%=PropertyLoader.getProperty("url.servlet.KBTnvanRead") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
					<table class="center">
						<tr>
							<td>■確定データ取込</td>
						</tr>
						<tr>
							<td><input type="submit" class="submit_btn" value="NVAN取込開始" onclick="return submitNvan(this)" ></td>
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
