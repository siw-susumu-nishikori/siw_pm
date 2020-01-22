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
import="java.util.Calendar"


import="java.sql.Connection"
import="java.sql.PreparedStatement"
import="java.sql.ResultSet"
import="java.sql.SQLException"
import="java.sql.Timestamp"
import="java.text.SimpleDateFormat"
import="java.util.ArrayList"
import="java.util.Arrays"
import="java.util.Iterator"
import="java.util.List"

import="javax.naming.InitialContext"
import="javax.naming.NamingException"
import="javax.servlet.http.HttpServletRequest"
import="javax.servlet.http.HttpServletResponse"
import="javax.sql.DataSource"

import="jp.siw.pm.edi.bean.CsvImportBean"
import="jp.siw.pm.edi.bean.KBTItemBean"
import="jp.siw.pm.edi.bean.KBTNvanBean"
%>

<%
String today = (String)request.getAttribute("today");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>クボタ差分マイナス品番一覧</title>
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

		<%
		request.setCharacterEncoding("UTF-8");
		/*String day1,day2,day3 受注照会遷移用*/
		String day1 = request.getParameter("day1");
		String day2 = request.getParameter("day2");
		String day3 = request.getParameter("day3");

		String kikan_s = (String)request.getAttribute("kikan_s");
		String kikan_e = (String)request.getAttribute("kikan_e");

//		String kikan_s = null;
//		String kikan_e = null;
/*		if(request.getParameter("kikan_s").equals("") && request.getParameter("kikan_e").equals("")){
			kikan_s = "";
			kikan_e = "2099-12-31";
		}else if(request.getParameter("kikan_s").equals("") && request.getParameter("kikan_e") != ""){
			kikan_s = "";
			kikan_e = request.getParameter("kikan_e");
			}else if(request.getParameter("kikan_s") != "" && request.getParameter("kikan_e").equals("")){
				kikan_s = request.getParameter("kikan_s");
				kikan_e = "2099-12-31";
				}else if(request.getParameter("kikan_s") != "" && request.getParameter("kikan_e") != ""){
					kikan_s = request.getParameter("kikan_s");
					kikan_e = request.getParameter("kikan_e");
					}
*/
		String insymd1 = request.getParameter("day1");
		String insymd2 = request.getParameter("day2");
		String hyoujiymd = request.getParameter("hyoujiymd");
			System.out.println("kikan_s=" + kikan_s);
			System.out.println("kikan_e=" + kikan_e);
			System.out.println(insymd1 + insymd2);
			System.out.println(insymd2);
			System.out.println("hyoujiymd="+hyoujiymd);
		Timestamp nowTime= new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
		String toDay  = timeStampNowDay.format(nowTime);
		String e_date = request.getParameter("e_date");
			System.out.println("e_date="+e_date);
		double mysry = 0;
		int total_qty = 0;
		%>

		<% final DataSource source;
		InitialContext context = new InitialContext();
		source = (DataSource) context.lookup("java:comp/env/jdbc/datasource");

		List<KBTItemBean>sabunMinusAllList = new ArrayList<KBTItemBean> ();

		List<String>sasuList = new ArrayList<String>();
		List<CsvImportBean>ZaikoList = new ArrayList<CsvImportBean> ();
		List<KBTItemBean>Tsuika_juchuMinusList = new ArrayList<KBTItemBean> ();
		List<KBTItemBean>KikanZaikoMinusList = new ArrayList<KBTItemBean> ();
		Connection connection = source.getConnection();

		try {

			//PreparedStatement statement = connection.prepareStatement("SELECT * FROM kb_sabun_aggr WHERE nounyushiji_ymd BETWEEN ? AND ? AND sasu<0 GROUP BY hinban");
			//statement.setString(1, kikan_s);
			//statement.setString(2, kikan_e);
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM kb_sabun_aggr WHERE nounyushiji_ymd AND sasu<0 GROUP BY hinban");
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				KBTItemBean item = new KBTItemBean();
				item.setHinban(result.getString("hinban"));
				String hinban = result.getString("hinban");
				System.out.println("品番="+hinban);%>

				<article>
					<section style="width:1350px;">
						<form method="Post" target="_blank" action="<%=PropertyLoader.getProperty("url.servlet.KBTorderSearch") %>" >
							<table>
								<tr>
									<td class="border-none">品番：<%=item.getHinban()%></td>
								</tr>
							</table>
							<table class="order_search" style="clear:both;">
								<tr>
									<td class="border-none">[受注照会] 内示取込日(中間)：<input type="text" style="width:100px;" name="day2" list="insymd"></td>
										<datalist id="insymd">
										<% List<KBTItemBean> naijiListDay = Cast.castList(request.getAttribute("naijiListDay"));
										Iterator<KBTItemBean> iteratorNaijiDay = naijiListDay.iterator();%>
										<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item0 = iteratorNaijiDay.next();%>
										<option value="<%=item0.getInsNaijiDay()%>"><%=item0.getInsNaijiDay()%></option>
										<%} %>
									</datalist>
									<td class="border-none">[受注照会]表示開始日：<input type="text" style="width:100px;" name="disp_date" class="selectDate" value="<%= today %>"></td>
									<td class="border-none"><input type="submit" value="受注照会へ"></td>
								</tr>
							</table>
								<input type="hidden" name="hinban" value="<%=item.getHinban()%>">
								<input type="hidden" name="day1" value="<%out.print(day1);%>">
								<input type="hidden" name="day3" value="<%out.print(day2);%>"><!-- 受注照会検索のためnameをday3へ -->
						</form>

						<% PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM(SELECT hinban, nounyushiji_ymd, noba_cd, "
																						+ "SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) AS qty1, "
																						+ "SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) AS qty2, "
																						+ "(SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) - SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) ) AS su_sa  "
																						+ "FROM t_juchu_test WHERE hinban=? AND nounyushiji_ymd BETWEEN ? AND ? AND naikaku_kb='3' GROUP BY nounyushiji_ymd, noba_cd) AS X "
																						+ "WHERE qty1 !=0 OR qty2 !=0 ");
						statement2.setString(1, insymd2);
						statement2.setString(2, insymd1);
						statement2.setString(3, insymd2);
						statement2.setString(4, insymd1);
						statement2.setString(5, hinban);
						statement2.setString(6, kikan_s);
						statement2.setString(7, kikan_e);
							System.out.println("NEW hinban="+ result.getString("hinban"));
							System.out.println("NEW insymd1="+insymd1);
							System.out.println("NEW insymd2="+insymd2);
							System.out.println("NEW hyoujiymd="+hyoujiymd);
						ResultSet result2 = statement2.executeQuery();
							System.out.println("++++++++++++++++++");
							System.out.println(hinban);

						while (result2.next()) {

								System.out.println("@@@@@@@@@@@@@@@@@@@");
							KBTItemBean item2 = new KBTItemBean();
							item2.setHinban(result2.getString("hinban"));
							item2.setNounyushiji_ymd(result2.getString("nounyushiji_ymd"));
							item2.setNoba_cd(result2.getString("noba_cd"));
							item2.setQty1(result2.getInt("qty1"));
							item2.setQty2(result2.getInt("qty2"));
							item2.setSu_sa(result2.getInt("su_sa"));
								System.out.println(result2.getString("nounyushiji_ymd"));
								System.out.println(result2.getInt("qty1"));
								System.out.println(result2.getInt("qty2"));
							sabunMinusAllList.add(item2);

						}%>

						<table class="juchu_waku" style="float:none">
						<caption>《内示》</caption>
							<tr>
								<td>納入指示日</td>
								<%Iterator<KBTItemBean> iterator1 = sabunMinusAllList.iterator();%>
								<% while (iterator1.hasNext()) {KBTItemBean item1 = iterator1.next();%>
								<td class="shiji_ymd"><%=item1.getNounyushiji_ymd()%></td>
								<%} %>
							</tr>
							<tr>
								<td>納入場所</td>
								<%Iterator<KBTItemBean> iterator3 = sabunMinusAllList.iterator();%>
								<% while (iterator3.hasNext()) {KBTItemBean item3 = iterator3.next();%>
								<td><%=item3.getNoba_cd()%></td>
								<%} %>
							</tr>
							<tr>
								<td><%out.println(day1); %>数量</td>
								<%Iterator<KBTItemBean> iterator4 = sabunMinusAllList.iterator();%>
								<% while (iterator4.hasNext()) {KBTItemBean item4 = iterator4.next();%>
								<td><%=item4.getQty2()%></td>
								<%} %>
							</tr>
							<tr>
								<td><%out.println(day2); %>数量</td>
								<%Iterator<KBTItemBean> iterator5 = sabunMinusAllList.iterator();%>
								<% while (iterator5.hasNext()) {KBTItemBean item5 = iterator5.next();%>
								<td><%=item5.getQty1()%></td>
								<%} %>
							</tr>
							<tr>
								<td>数量差</td>
								<%Iterator<KBTItemBean> iterator6 = sabunMinusAllList.iterator();%>
								<% while (iterator6.hasNext()) {KBTItemBean item6 = iterator6.next();%>
								<td <%if(item6.getSu_sa()>0){out.println("style=\"background: #FA5882;\"");}else if(item6.getSu_sa()<0){out.println("style=\"background: #CEECF5;\"");} %>><%=item6.getSu_sa()%></td>
								<%} %>
							</tr>

							<%System.out.println("sabunMinusAllList="+sabunMinusAllList);
							System.out.println("******************");

							List<String>qtyList1 = new ArrayList<String>();
							List<String>qtyList2 = new ArrayList<String>();
							Iterator<KBTItemBean> iterator = sabunMinusAllList.iterator();
							while (iterator.hasNext()) {KBTItemBean item2 = iterator.next();
							int Qty1 = item2.getQty1();
							int Qty2 = item2.getQty2();
								//System.out.println("Qty1="+Qty1);
								//System.out.println("Qty2="+Qty2);
							qtyList1.add(Integer.toString(Qty1));
							qtyList2.add(Integer.toString(Qty2));
								System.out.println("qtyList1="+qtyList1);
								System.out.println("qtyList2="+qtyList2); %>

							<%}

							int sasu = 0;
							for(int j=0; j<qtyList2.size(); j++){
								if(j==0){
									sasu = Integer.parseInt(qtyList2.get(j)) - Integer.parseInt(qtyList1.get(j));
									}else if(j==1){
										sasu = ((Integer.parseInt(qtyList2.get(j-1)) - Integer.parseInt(qtyList1.get(j-1)))+Integer.parseInt(qtyList2.get(j))) - Integer.parseInt(qtyList1.get(j));
										}else{
											sasu = Integer.parseInt(sasuList.get(j-1))+Integer.parseInt(qtyList2.get(j)) - Integer.parseInt(qtyList1.get(j));
											}
								sasuList.add(Integer.toString(sasu));%>
								 <%} %>
							<tr>
								<td>差分</td>
								<% for(int i=0; i<sasuList.size(); i++){%>
								<!-- 2019/12/27 -->
								<!-- if文追加　差分数マイナスのセルに色を付ける -->
								<td <%if(Integer.parseInt(sasuList.get(i))<0){out.println("style=\"background: #FFC0CB	;\"");} %>><%=sasuList.get(i)%></td>
								<%} %>
							</tr>

							<%System.out.println("sasuList="+sasuList );
							sasuList.clear();
							qtyList1.clear();
							qtyList2.clear();
							sabunMinusAllList.clear();%>
						</table>
					</section>
				</article>

				<article>
					<section>
						<table>
						<% PreparedStatement statement3 = connection.prepareStatement("SELECT * FROM t_juchu_test WHERE hinban=? AND nounyushiji_ymd>=? AND naikaku_kb='5' AND add_kb='1' ");
						statement3.setString(1, hinban);
						statement3.setString(2, toDay);
						ResultSet result3 = statement3.executeQuery();

						while (result3.next()) {
							KBTItemBean item3 = new KBTItemBean();
							item3.setHinban(result3.getString("hinban"));
							item3.setNoba_cd(result3.getString("noba_cd"));
							item3.setNounyushiji_ymd(result3.getString("nounyushiji_ymd"));
							item3.setJuchu_su(result3.getString("juchu_su"));
							Tsuika_juchuMinusList.add(item3);%>

							<%} %>
						<caption style="width:250px;">《追加受注》本日以降の納入指示日を表示</caption>
							<tr>
								<td>納入指示日</td>
								<%Iterator<KBTItemBean> iterator10 = Tsuika_juchuMinusList.iterator();%>
								<% while (iterator10.hasNext()) {KBTItemBean item10 = iterator10.next();%>
								<td><%=item10.getNounyushiji_ymd()%></td>
								<%} %>
							</tr>
							<tr>
								<td>納入場所</td>
								<%Iterator<KBTItemBean> iterator11 = Tsuika_juchuMinusList.iterator();%>
								<% while (iterator11.hasNext()) {KBTItemBean item11 = iterator11.next();%>
								<td><%=item11.getNoba_cd()%></td>
								<%} %>
							</tr>
							<tr>
								<td>数量</td>
								<%Iterator<KBTItemBean> iterator12 = Tsuika_juchuMinusList.iterator();%>
								<% while (iterator12.hasNext()) {KBTItemBean item12 = iterator12.next();%>
								<td ><%=item12.getJuchu_su()%></td>
								<%} %>
							</tr>
							<% Tsuika_juchuMinusList.clear();%>
						</table>
					</section>
				</article>

				<article>
					<section>
						<table>
						<% PreparedStatement statement4 = connection.prepareStatement("SELECT * FROM tanpin_zaiko WHERE hinb=? AND nony='0700'");
						statement4.setString(1, hinban);
						ResultSet result4 = statement4.executeQuery();
							System.out.println("result!!="+result3);

						while (result4.next()) {
							CsvImportBean item4 = new CsvImportBean();
							item4.setMysry(result4.getDouble("mysry"));
							item4.setKoshin_ymd(result4.getString("koshin_ymd"));
								System.out.println("ABC="+item4);
							ZaikoList.add(item4);
								System.out.println("ZaikoList="+ZaikoList);%>
						<caption>《在庫》</caption>
							<tr>
								<th>自在庫数(単品在庫データ)</th>
							</tr>
							<tr>
								<%Iterator<CsvImportBean> iterator7 = ZaikoList.iterator();%>
								<% while (iterator7.hasNext()) {CsvImportBean item7 = iterator7.next();%>
								<td><%=item7.getKoshin_ymd()%>時点</td>
							</tr>
							<tr>
								<td><%=item7.getMysry()%></td>
								<%System.out.println("mysry="+item7.getMysry()); %>
								<%mysry = (int)item7.getMysry(); %>
							</tr>
						</table>
						<%} %>
						<% ZaikoList.clear();%>

						<% PreparedStatement statement5 = connection.prepareStatement("SELECT SUM(total_qty) AS Total_Qty FROM("
																						+ "SELECT sum(juchu_su) AS total_qty FROM `t_juchu_test` "
																						+ "WHERE hinban=? AND insymd=? AND naikaku_kb='3' AND nounyushiji_ymd BETWEEN ? AND ? "
																						+ "UNION ALL "
																						+ "SELECT sum(juchu_su) AS total_qty FROM `t_juchu_test` "
																						+ "WHERE hinban=? AND nounyushiji_ymd BETWEEN ? AND ? AND add_kb='1') AS X");
						statement5.setString(1, hinban);
						statement5.setString(2, insymd2);
						statement5.setString(3, toDay);
						statement5.setString(4, e_date);
						statement5.setString(5, hinban);
						statement5.setString(6, toDay);
							System.out.println("toDay="+toDay);
						statement5.setString(7, e_date);
						ResultSet result5 = statement5.executeQuery();

						while (result5.next()) {
							KBTItemBean item5 = new KBTItemBean();
							item5.setTotal_qty(result5.getInt("Total_Qty"));
							KikanZaikoMinusList.add(item5);%>

							<% }%>
						<table>
						<caption>　</caption>
							<tr>
								<th>内示･追加受注数合計</th>
							</tr>
							<tr>
								<%Iterator<KBTItemBean> iterator8 = KikanZaikoMinusList.iterator();%>
								<% while (iterator8.hasNext()) {KBTItemBean item8 = iterator8.next();%>
								<td><%= today %>～<%out.println(e_date);%></td>
							</tr>
							<tr>
								<td><%=item8.getTotal_qty()%></td>
								<%total_qty = item8.getTotal_qty(); %>
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

						<%KikanZaikoMinusList.clear();
							System.out.println("???????????????????");%>
					</section>
				</article>
				<article>
					<section class="under">
					</section>
				</article>
				<%} %>

			<%} %>

		<%}catch (SQLException e) {
			e.printStackTrace();
				} %>

		<!-- フッターエリアここから -->
		<div class="wrapper row5">
			<footer id="main_footer">
				<p id="copy">Copyright (C) Shinba Iron Works Corporation. All Rights Reserved.</p>
			</footer>
		</div><!-- /フッターエリアここまで -->
	</body>
</html>