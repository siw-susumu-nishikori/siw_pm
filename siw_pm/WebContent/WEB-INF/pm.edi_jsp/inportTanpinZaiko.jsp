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

			<script>
				function submitChk(b){
					var flag = confirm("単品在庫データの取込を開始します。\n本当によろしいですか？");
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

		<title>単品在庫データ取込</title>
	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
		<h1>単品在庫データ取込</h1>
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
				<a href="file:\\192.168.101.236\riseプロジェクト\16.クボタEDI取込フォルダ">単品在庫データ取込フォルダを開く</a>
				<form method="post" action="<%=PropertyLoader.getProperty("url.servlet.TanpinZaikoRegister") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
					<table class="center">
						<tr>
							<td>■単品在庫データ取込</td>
						</tr>
						<tr>
							<td><input type="submit" class="submit_btn" value="取込開始" onclick="return submitChk(this)" ></td>
						</tr>
					</table>
					<p>注1.取込ファイル名:FSKN280A.csv</p>
					<p>注2.発注基準で作成した単品在庫データ(CSV形式)を取込んで下さい</p>
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