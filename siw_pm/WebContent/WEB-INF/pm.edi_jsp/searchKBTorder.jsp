<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="java.util.Iterator"
import="java.util.List"
import="jp.siw.pm.edi.bean.KBTItemBean"
import="jp.siw.pm.edi.bean.KakuteikikanBean"
import="jp.siw.pm.edi.util.PropertyLoader"
import="jp.siw.pm.edi.util.Cast"
import="jp.siw.pm.edi.util.Cast_Kakuteikikan"
import="java.util.Calendar"%>

<%
String today = (String)request.getAttribute("today");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>クボタ受注検索</title>
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

				<script>
				function checkForm1(b){
					if(document.sabunAll.day1.value == "" && document.sabunAll.day2.value == ""){
						alert("内示取込日1(前回)\n内示取込日2(今回)\nを入力して下さい。");
						return false;
					}else if(document.sabunAll.day1.value == ""){
						alert("内示取込日1(前回)を入力して下さい。");
						return false;
					}else if(document.sabunAll.day2.value == ""){
						alert("内示取込日2(今回)を入力して下さい。");
						return false;
					}else{
						b.disabled = true;
						b.value = '処理中';
						return true;
					}
				}

				function checkForm2(b){
					if(document.sabun.hinban.value == ""){
						alert("品番を入力して下さい。");
						return false;
					}else{
						b.disabled = true;
						b.value = '処理中';
						return true;
					}
				}

				function checkForm3(b){
					if(document.syokai.hinban.value == ""){
						alert("品番を入力して下さい。");
						return false;
					}else{
						b.disabled = true;
						b.value = '処理中';
						return true;
					}
				}

				function checkForm4(b){
					if(document.kabusoku.day1.value == ""){
						alert("内示取込日を入力して下さい。");
						return false;
					}else{
						b.disabled = true;
						b.value = '処理中';
						return true;
					}
				}
				</script>

	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
		<h1>クボタ受注検索</h1>
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

				<% List<KBTItemBean> itemListHinban = Cast.castList(request.getAttribute("itemListHinban"));
				Iterator<KBTItemBean> iterator = itemListHinban.iterator();
				%>

				<% List<KBTItemBean> itemListDay = Cast.castList(request.getAttribute("itemListDay"));
				Iterator<KBTItemBean> iteratorDay = itemListDay.iterator();
				%>

				<% List<KakuteikikanBean> kikanList = Cast_Kakuteikikan.castList(request.getAttribute("kikanList"));
				Iterator<KakuteikikanBean> iteratorKikan = kikanList.iterator();
				Iterator<KakuteikikanBean> iteratorKikan2 = kikanList.iterator();
				Iterator<KakuteikikanBean> iteratorKikan3 = kikanList.iterator();
				Iterator<KakuteikikanBean> iteratorKikan4 = kikanList.iterator();
				%>

				<% List<KBTItemBean> naijiListDay = Cast.castList(request.getAttribute("naijiListDay"));
				Iterator<KBTItemBean> iteratorNaijiDay = naijiListDay.iterator();
				Iterator<KBTItemBean> iteratorNaijiDay2 = naijiListDay.iterator();
				Iterator<KBTItemBean> iteratorNaijiDay3 = naijiListDay.iterator();
				Iterator<KBTItemBean> iteratorNaijiDay4 = naijiListDay.iterator();
				%>

			<article >
				<section>
					<table class="center">
						<tr>
							<% while (iteratorKikan.hasNext()) {KakuteikikanBean item = iteratorKikan.next();%>
							<th>今回確定期間：</th>
							<td class="kikan"> <%=item.getJun_nm() %> </td>
							<td class="kikan">＜<%=item.getS_date() %></td>
							<td class="kikan">～</td>
							<td class="kikan"><%=item.getE_date() %>＞</td>
							<%} %>
						</tr>
					</table>
				</section>
			</article>
			<!-- 2019/12/26 -->
			<!-- [全品番内示差分出力]→[内示差分検索]→[受注検索]→[全品番過不足数出力]順に並び替え -->
			<article class="search_juchu">
				<section style="border:solid 1px;">
					<form method="Post" name="sabunAll" action="<%=PropertyLoader.getProperty("url.servlet.KBTsabunAllSearch") %>">
						<p class="caption2">[ 全品番内示差分出力 ]</p>
						<table class="center">
							<tr>
								<% while (iteratorKikan4.hasNext()) {KakuteikikanBean item4 = iteratorKikan4.next();%>
								<td><input type="hidden" name="e_date" value="<%=item4.getE_date()%>"></td>
								<%} %>
							</tr>
							<tr>
								<th>内示取込日1(前回)</th>
								<td><input type="text" name="day1" list="insymd_naiji"></td>
								<datalist id="insymd_naiji">
								<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
								<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
								<%} %>
								</datalist>
								<td align="left"></td>
							</tr>
							<tr>
								<th>内示取込日2(今回)</th>
								<td><input type="text" name="day2" list="insymd_naiji"></td>
								<datalist id="insymd_naiji">
								<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
								<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
								<%} %>
								</datalist>
								<td align="left"></td>
							</tr>
						</table>
							<p><input type="submit" class="submit_btn" value="実行" onclick="return checkForm1(this);"></p>
					</form>
				</section>
			</article>

			<article class="search_juchu">
				<section style="border:solid 1px;">
					<form method="Post" name="sabun" action="<%=PropertyLoader.getProperty("url.servlet.KBTsabunSearch") %>">
						<p class="caption2">[ 内示差分検索 ]</p>
						<table class="center">
							<tr>
								<% while (iteratorKikan2.hasNext()) {KakuteikikanBean item2 = iteratorKikan2.next();%>
								<td><input type="hidden" name="e_date" value="<%=item2.getE_date()%>"></td>
								<%} %>
							</tr>
							<tr>
								<th>品番</th>
								<td><input type="text" name="hinban" list="hinban"></td>
								<datalist id="hinban">
								<% while (iterator.hasNext()) {KBTItemBean item = iterator.next();%>
								<option value="<%=item.getHinban()%>"><%=item.getHinban()%></option>
								<%} %>
								</datalist>
							</tr>
							<tr>
								<th>内示取込日1(前回)</th>
								<td><input type="text" name="day1" list="insymd_naiji"></td>
								<datalist id="insymd_naiji">
								<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
								<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
								<%} %>
								</datalist>
							</tr>
							<tr>
								<th>内示取込日2(今回)</th>
								<td><input type="text" name="day2" list="insymd_naiji"></td>
								<datalist id="insymd_naiji">
								<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
								<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
								<%} %>
								</datalist>
							</tr>
							<tr>
								<th>表示開始日</th>
								<td><input type="text" name="hyoujiymd" class="selectDate" value="<%=today%>"></td>
							</tr>
						</table>
						<p><input type="submit" class="submit_btn" value="検索" onclick="return checkForm2(this);"></p>
					</form>
				</section>
			</article>

			<article class="search_juchu">
				<section style="border:solid 1px;">
					<form method="Post" name="syokai" action="<%=PropertyLoader.getProperty("url.servlet.KBTorderSearch") %>" >
						<p class="caption2">[ 受注照会 ]</p>
						<table class="center">
							<tr>
								<th>品番</th>
								<td><input type="text" name="hinban" list="hinban"></td>
								<datalist id="hinban">
								<% while (iterator.hasNext()) {KBTItemBean item = iterator.next();%>
								<option value="<%=item.getHinban()%>"><%=item.getHinban()%></option>
								<%} %>
								</datalist>
							</tr>
							<tr>
								<th>内示取込日1(前回)</th>
								<td><input type="text" name="day1" list="insymd_naiji"></td>
								<datalist id="insymd_naiji">
								<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
								<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
								<%} %>
								</datalist>
							</tr>
							<tr>
								<th>内示取込日2(中間)</th>
								<td><input type="text" name="day2" list="insymd_naiji"></td>
								<datalist id="insymd_naiji">
								<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
								<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
								<%} %>
								</datalist>
							</tr>
							<tr>
								<th>内示取込日3(今回)</th>
								<td><input type="text" name="day3" list="insymd_naiji"></td>
								<datalist id="insymd_naiji">
								<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
								<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
								<%} %>
								</datalist>
							</tr>
							<tr>
								<th>表示開始日</th>
								<td><input type="text" name="disp_date" class="selectDate" value="<%=today%>" ></td>
							</tr>
						</table>
							<p><input type="submit" class="submit_btn" value="検索" onclick="return checkForm3(this);"></p>
					</form>
				</section>
			</article>
			<article class="search_juchu">
				<section class="bottom" style="border:solid 1px;">
					<form method="Post" name="kabusoku" action="<%=PropertyLoader.getProperty("url.servlet.KabusokuSearch") %>">
						<p class="caption2">[ 全品番過不足数出力 ]</p>
						<table class="center">
							<tr>
								<% while (iteratorKikan3.hasNext()) {KakuteikikanBean item3 = iteratorKikan3.next();%>
								<td><input type="hidden" name="e_date" value="<%=item3.getE_date()%>"></td>
								<%} %>
							</tr>
							<tr>
								<th>内示取込日</th>
								<td><input type="text" name="day1" list="insymd_naiji"></td>
								<datalist id="insymd_naiji">
								<% while (iteratorDay.hasNext()) {KBTItemBean item = iteratorDay.next();%>
								<option value="<%=item.getInsymd()%>"><%=item.getInsymd()%></option>
								<%} %>
								</datalist>
							</tr>
						</table>
							<p><input type="submit" class="submit_btn" value="実行" onclick="return checkForm4(this);"></p>
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

