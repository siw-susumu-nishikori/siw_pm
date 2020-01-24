<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="jp.siw.pm.edi.util.PropertyLoader"
%>

<%
String today = (String)request.getAttribute("today");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>内示差分出力</title>
				<link href="./css/common.css" rel="stylesheet" type="text/css">

				<link href="./css/juchu.css" rel="stylesheet" type="text/css">
				<link rel="stylesheet" href="./js_css/default.css">
				<link rel="stylesheet" href="./js_css/default.date.css">
				<script src="js/jquery-1.10.2.min.js"></script>
				<script src="js/picker.js"></script>
				<script src="js/picker.date.js"></script>
				<script src="js/ja_JP.js"></script>

				<script type="text/javascript">
				$(function(){
					$('.selectDate').pickadate({
					format:"yyyy-mm-dd"
					});
				});
				</script>

				<% request.setCharacterEncoding("UTF-8");
				String day1 = request.getParameter("day1");
				String day2 = request.getParameter("day2");
				String day3 = request.getParameter("day3");
				String e_date = request.getParameter("e_date");
				%>
	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
				<h1>内示差分出力</h1>
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

		<article>
			<section class="cntr">
				<form>
					<table class="center">
					<caption class="caption">[ 全データダウンロード ]</caption>
						<tr>
							<th style="border: none; background-color: #ffffff;">■全品番の差分データ出力</th>
						</tr>
					</table>
					<p><button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.SabunAllCsvController") %>'">CSVダウンロード</button></p>
				</form>
				<form method="Post" action="<%=PropertyLoader.getProperty("url.servlet.SabunAllCsvController2") %>">
					<input type="hidden" name="hyoujiymd">
					<input type="hidden" name="day1" value="<%out.print(day1);%>">
					<input type="hidden" name="day2" value="<%out.print(day2);%>">
					<input type="hidden" name="day3" value="<%out.print(day3);%>">
					<input type="hidden" name="e_date" value="<%out.print(e_date);%>">
					<table class="center">
					<caption class="caption" style="text-align:center;">[ マイナスデータのみダウンロード/マイナス品番一覧表示 ]</caption>
						<tr>
							<th colspan="3" style="border: none; background-color: #ffffff;">■出力期間指定《推奨：前回確定期間初日～次回確定期間末日》</th>
						</tr>
						<tr>
							<td class="border-style"><input type="text" name="kikan_s" class="selectDate" placeholder="yyyy-mm-dd"></td>
							<td class="border-style">～</td>
							<td class="border-style"><input type="text" name="kikan_e" class="selectDate" placeholder="yyyy-mm-dd"></td>
						</tr>
					</table>
					<table class="hinban_sum, center">
						<tr>
							<td class="border-none"><input type="radio" name="hinban_summary" value="yes" checked="checked">品番まとめ有</td>
							<td class="border-none"><input type="radio" name="hinban_summary" value="none">品番まとめ無</td>
							<td class="border-none">※CSVデータダウンロードにのみ有効</td>
						</tr>
					</table>
					<p style="display: inline-block;"><button type="submit" name="csv">CSVダウンロード</button></p>
					<p style="display: inline-block; padding-left:10px;"><button type="submit" name="disp">マイナス品番一覧表示</button></p>
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