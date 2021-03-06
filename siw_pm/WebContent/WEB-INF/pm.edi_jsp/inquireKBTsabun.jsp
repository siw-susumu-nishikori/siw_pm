<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"
import="java.util.Iterator"
import="java.util.List"
import="jp.siw.pm.edi.bean.KBTItemBean"
import="jp.siw.pm.edi.bean.CsvImportBean"
import="jp.siw.pm.edi.util.PropertyLoader"
import="jp.siw.pm.edi.util.Cast"
import="jp.siw.pm.edi.util.Cast_String"
import="jp.siw.pm.edi.util.Cast_CsvImportBean"
import="java.util.Calendar"%>

<%
String today = (String)request.getAttribute("today");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>クボタ差分検索</title>
				<link href="./css/common.css" rel="stylesheet" type="text/css">
				<link href="./css/table.css" rel="stylesheet" type="text/css">
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
	</head>
	<body>
		<div class="wrapper row1">
			<header id="main_header" >
			<h1>内示差分検索</h1>
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

			<% List<KBTItemBean> HinmokuMasterList = Cast.castList(request.getAttribute("HinmokuMasterList"));
			Iterator<KBTItemBean> iteratorHinmoku = HinmokuMasterList.iterator();
			%>

			<% List<KBTItemBean> SabunList = Cast.castList(request.getAttribute("SabunList"));
			Iterator<KBTItemBean> iterator = SabunList.iterator();
			Iterator<KBTItemBean> iterator2 = SabunList.iterator();
			Iterator<KBTItemBean> iterator3 = SabunList.iterator();
			Iterator<KBTItemBean> iterator4 = SabunList.iterator();
			Iterator<KBTItemBean> iterator5 = SabunList.iterator();
			Iterator<KBTItemBean> iterator14 = SabunList.iterator();
			%>

			<% List<String> sasuList = Cast_String.castList(request.getAttribute("sasuList"));
			Iterator<String> iterator6 = sasuList.iterator();
			%>

			<% List<CsvImportBean> ZaikoList = Cast_CsvImportBean.castList(request.getAttribute("ZaikoList"));
			Iterator<CsvImportBean> iterator7 = ZaikoList.iterator();
			Iterator<CsvImportBean> iterator8 = ZaikoList.iterator();
			%>

			<% List<KBTItemBean> KikanZaikoList = Cast.castList(request.getAttribute("KikanZaikoList"));
			Iterator<KBTItemBean> iterator9 = KikanZaikoList.iterator();
			%>

			<% List<KBTItemBean> Tsuika_juchuList = Cast.castList(request.getAttribute("Tsuika_juchuList"));
			Iterator<KBTItemBean> iterator10 = Tsuika_juchuList.iterator();
			Iterator<KBTItemBean> iterator11 = Tsuika_juchuList.iterator();
			Iterator<KBTItemBean> iterator12 = Tsuika_juchuList.iterator();
			Iterator<KBTItemBean> iterator13 = Tsuika_juchuList.iterator();
			%>

			<% List<KBTItemBean> naijiListDay = Cast.castList(request.getAttribute("naijiListDay"));
			Iterator<KBTItemBean> iteratorNaijiDay = naijiListDay.iterator();
			%>

			<%String hinban = request.getParameter("hinban");
			 String day1 = request.getParameter("day1");
			 String day2 = request.getParameter("day2");
			 %>

			 <%request.setCharacterEncoding("UTF-8");
			 String e_date = request.getParameter("e_date");
			 double mysry = 0;
			 int total_qty = 0;
			 %>


		<article>
			<section style="width:1350px;">
				<form method="Post" class="order_search" target="_blank" action="<%=PropertyLoader.getProperty("url.servlet.KBTorderSearch") %>" >
					<table>
						<tr>
							<td class="hinban">品番：<%out.println(hinban); %></td>
							<% while (iteratorHinmoku.hasNext()) {KBTItemBean item = iteratorHinmoku.next();%>
							<td class="hinm">品名：<%=item.getHinm() %></td>
							<td class="hgyc">加工先：<%=item.getHgyc() %></td>
							<%} %>
						</tr>
					</table>
					<table class="order_search" style="clear:both;">
						<tr>
							<td class="border-none">[受注照会] 内示取込日(中間)：<input type="text" style="width:100px;" name="day2" list="insymd"></td>
								<datalist id="insymd">
									<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
									<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
									<%} %>
								</datalist>
							<td class="border-none">表示開始日：<input type="text" style="width:100px;" name="disp_date" class="selectDate" value="<%=today%>"></td>
							<td class="border-none"><input type="submit" class="btn" value="受注照会へ"></td>
						</tr>
					</table>
						<input type="hidden" name="hinban" value="<%out.print(hinban); %>">
						<input type="hidden" name="day1" value="<%out.print(day1);%>">
						<input type="hidden" name="day3" value="<%out.print(day2);%>">

				</form>
				<table class="juchu_waku" style="float:none;">
				<caption>《内示》</caption>
					<tr>
						<td>納入指示日</td>
						<% while (iterator.hasNext()) {KBTItemBean item = iterator.next();%>
						<td class="shiji_ymd"><%=item.getNounyushiji_ymd()%></td>
						<%} %>
					</tr>
					<tr>
						<td>納入場所</td>
						<% while (iterator2.hasNext()) {KBTItemBean item2 = iterator2.next();%>
						<td><%=item2.getNoba_cd()%></td>
						<%} %>
					</tr>
					<!-- 2019/12/26 -->
					<!-- 上段Qty1、下段Qty2から上段Qty2、下段Qty1へ変更 -->
					<tr>
						<td><%out.println(day1); %>数量</td>
						<% while (iterator4.hasNext()) {KBTItemBean item4 = iterator4.next();%>
						<td><%=item4.getQty2()%></td>
						<%} %>
					</tr>
					<tr>
						<td><%out.println(day2); %>数量</td>
						<% while (iterator3.hasNext()) {KBTItemBean item3 = iterator3.next();%>
						<td><%=item3.getQty1()%></td>
						<%} %>
					</tr>
					<tr>
						<td>数量差</td>
						<% while (iterator5.hasNext()) {KBTItemBean item5 = iterator5.next();%>
						<td <%if(item5.getSu_sa()>0){out.println("style=\"background: #FA5882;\"");}else if(item5.getSu_sa()<0){out.println("style=\"background: #CEECF5;\"");} %>><%=item5.getSu_sa()%></td>
						<%} %>
					</tr>
					<tr>
						<td>差分</td>
						<% for(int i=0; i<sasuList.size(); i++){%>
						<!-- 2019/12/27 -->
						<!-- if文追加　差分数マイナスのセルに色を付ける -->
						<td <%if(Integer.parseInt(sasuList.get(i))<0){out.println("style=\"background: #FFC0CB	;\"");} %>><%=sasuList.get(i)%></td>
						<%} %>
					</tr>
				</table>
			</section>
		</article>

		<article>
			<section>
				<table>
				<caption style="width:250px;">《追加受注》本日以降の納入指示日を表示</caption>
					<tr>
						<td>納入指示日</td>
						<% while (iterator10.hasNext()) {KBTItemBean item = iterator10.next();%>
						<td><%=item.getNounyushiji_ymd()%></td>
						<%} %>
					</tr>
					<tr>
						<td>納入場所</td>
						<% while (iterator11.hasNext()) {KBTItemBean item = iterator11.next();%>
						<td><%=item.getNoba_cd()%></td>
							<%} %>
					</tr>
					<tr>
						<td>数量</td>
						<% while (iterator12.hasNext()) {KBTItemBean item = iterator12.next();%>
						<td ><%=item.getJuchu_su()%></td>
						<%} %>
					</tr>
				</table>
			</section>
		</article>

		<article>
			<section>
				<table>
				<caption>《在庫》</caption>
					<tr>
						<th>自在庫数(単品在庫データ)</th>
					</tr>
					<tr>
						<% while (iterator7.hasNext()) {CsvImportBean item7 = iterator7.next();%>
						<td><%=item7.getKoshin_ymd()%>時点</td>
					</tr>
					<tr>
						<td><%=item7.getMysry()%></td>
						<%System.out.println("mysry="+item7.getMysry()); %>
						<%mysry = (int)item7.getMysry(); %>
						<%} %>
					</tr>
				</table>
				<table>
				<caption>　</caption>
					<tr>
						<th>内示･追加受注数合計</th>
					</tr>
					<tr>
						<% while (iterator9.hasNext()) {KBTItemBean item9 = iterator9.next();%>
						<td><%=today%>～<%out.println(e_date);%></td>
					</tr>
					<tr>
						<td><%=item9.getTotal_qty()%></td>
						<%total_qty = item9.getTotal_qty(); %>
						<%} %>
					</tr>
				</table>
				<table>
				<caption>　</caption>
					<tr>
						<th>過不足在庫数</th>
					</tr>
					<tr>
						<td>自在庫数－受注数合計</td>
					</tr>
					<tr>
						<td <% if((mysry - total_qty) < 0){out.println("style=\"background: #FFC0CB;\"");} %>><%out.println(mysry - total_qty); %></td>
					</tr>
				</table>
			</section>
		</article>
		<article>
			<section class="under">
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