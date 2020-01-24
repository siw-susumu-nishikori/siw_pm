<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="java.util.Iterator"
import="java.util.List"
import="jp.siw.pm.edi.bean.KakuteikikanBean"
import="jp.siw.pm.edi.util.PropertyLoader"
import="jp.siw.pm.edi.util.Cast_Kakuteikikan"
import="java.util.Calendar"%>

<%
String today = (String)request.getAttribute("today");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<link href="./css/common.css" rel="stylesheet" type="text/css">
			<link href="./css/table.css" rel="stylesheet" type="text/css">
			<link href="./css/juchu.css" rel="stylesheet" type="text/css">

			<script>
				function submitKikan(b){
					var flag = confirm("確定期間を変更します。\n本当によろしいですか？");
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

		<title>確定期間設定</title>
	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
		<h1>確定期間設定</h1>
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
				<% List<KakuteikikanBean>kikanList = Cast_Kakuteikikan.castList(request.getAttribute("kikanList"));
				Iterator<KakuteikikanBean> iterator = kikanList.iterator();%>
				<% while (iterator.hasNext()) {KakuteikikanBean item = iterator.next();%>
				<form method="Post" action="<%=PropertyLoader.getProperty("url.servlet.KakuteiKikanRegister") %>" >
					<table class="center" style="float:none; margin-bottom:10px;">
						<tr>
							<th style="padding-left:5px; padding-right:5px;">今回の確定期間を選択</th>
						</tr>
						<tr>
							<td style="padding:10px;"><select name="kikan">
								<option value="1A" <% if(item.getJun_kb().equals("1A")){out.println("selected");}else{} %>>1月上旬</option>
								<option value="1B" <% if(item.getJun_kb().equals("1B")){out.println("selected");}else{} %>>1月下旬</option>
								<option value="2A" <% if(item.getJun_kb().equals("2A")){out.println("selected");}else{} %>>2月上旬</option>
								<option value="2B" <% if(item.getJun_kb().equals("2B")){out.println("selected");}else{} %>>2月下旬</option>
								<option value="3A" <% if(item.getJun_kb().equals("3A")){out.println("selected");}else{} %>>3月上旬</option>
								<option value="3B" <% if(item.getJun_kb().equals("3B")){out.println("selected");}else{} %>>3月下旬</option>
								<option value="4A" <% if(item.getJun_kb().equals("4A")){out.println("selected");}else{} %>>4月上旬</option>
								<option value="4B" <% if(item.getJun_kb().equals("4B")){out.println("selected");}else{} %>>4月下旬</option>
								<option value="5A" <% if(item.getJun_kb().equals("5A")){out.println("selected");}else{} %>>5月上旬</option>
								<option value="5B" <% if(item.getJun_kb().equals("5B")){out.println("selected");}else{} %>>5月下旬</option>
								<option value="6A" <% if(item.getJun_kb().equals("6A")){out.println("selected");}else{} %>>6月上旬</option>
								<option value="6B" <% if(item.getJun_kb().equals("6B")){out.println("selected");}else{} %>>6月下旬</option>
								<option value="7A" <% if(item.getJun_kb().equals("7A")){out.println("selected");}else{} %>>7月上旬</option>
								<option value="7B" <% if(item.getJun_kb().equals("7B")){out.println("selected");}else{} %>>7月下旬</option>
								<option value="8A" <% if(item.getJun_kb().equals("8A")){out.println("selected");}else{} %>>8月上旬</option>
								<option value="8B" <% if(item.getJun_kb().equals("8B")){out.println("selected");}else{} %>>8月下旬</option>
								<option value="9A" <% if(item.getJun_kb().equals("9A")){out.println("selected");}else{} %>>9月上旬</option>
								<option value="9B" <% if(item.getJun_kb().equals("9B")){out.println("selected");}else{} %>>9月下旬</option>
								<option value="10A" <% if(item.getJun_kb().equals("10A")){out.println("selected");}else{} %>>10月上旬</option>
								<option value="10B" <% if(item.getJun_kb().equals("10B")){out.println("selected");}else{} %>>10月下旬</option>
								<option value="11A" <% if(item.getJun_kb().equals("11A")){out.println("selected");}else{} %>>11月上旬</option>
								<option value="11B" <% if(item.getJun_kb().equals("11B")){out.println("selected");}else{} %>>11月下旬</option>
								<option value="12A" <% if(item.getJun_kb().equals("12A")){out.println("selected");}else{} %>>12月上旬</option>
								<option value="12B" <% if(item.getJun_kb().equals("12B")){out.println("selected");}else{} %>>12月下旬</option>
							</select></td>
						</tr>
						<tr>
							<td style="border: none; padding-top: 10px;"><input type="submit" class="submit_btn" value="セット" onclick="return submitKikan(this)"></td>
						</tr>
					</table>
				</form>
				<%} %>
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