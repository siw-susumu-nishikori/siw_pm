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
</head>
<body>
<h2>クボタ受注検索</h2>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.TopMenuManager") %>'">トップメニューへ</button>
<% Calendar cal = Calendar.getInstance(); //カレンダーオブジェクトを取得
 int year = cal.get(Calendar.YEAR); //現在の年を取得
 int month = cal.get(Calendar.MONTH) + 1; //現在の月を取得
 int day = cal.get(Calendar.DATE); //現在の日を取得
%>
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

<article>
	<section>
		<table>
			<tr>
				<td>DATE:<%=year%>/<%=month%>/<%=day%></td>
			</tr>
		</table>
		<table>
			<tr>
				<% while (iteratorKikan.hasNext()) {KakuteikikanBean item = iteratorKikan.next();%>
				<th>今回確定期間：</th>
				<td> <%=item.getJun_nm() %> </td>
				<td>＜<%=item.getS_date() %></td>
				<td>～</td>
				<td><%=item.getE_date() %>＞</td>
				<%} %>
			</tr>
		</table>
	</section>
</article>
<!-- 2019/12/26 -->
<!-- [全品番内示差分出力]→[内示差分検索]→[受注検索]→[全品番過不足数出力]順に並び替え -->
<article class="search_juchu">
	<section>
		<form method="Post" action="<%=PropertyLoader.getProperty("url.servlet.KBTsabunAllSearch") %>">
			<table>
				<caption>[ 全品番内示差分出力 ]</caption>
				<tr>
					<% while (iteratorKikan4.hasNext()) {KakuteikikanBean item4 = iteratorKikan4.next();%>
					<td><input type="hidden" name="e_date" value="<%=item4.getE_date()%>"></td>
					<%} %>
				</tr>
				<tr>
					<th>取込日1(前回)</th>
					<td><input type="text" name="day1" list="insymd_naiji"></td>
						<datalist id="insymd_naiji">
						<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
						<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
						<%} %>
						</datalist>
					<td align="left">：計算用</td>
				</tr>
				<tr>
					<th>取込日2(今回)</th>
					<td><input type="text" name="day2" list="insymd_naiji"></td>
						<datalist id="insymd_naiji">
						<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
						<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
						<%} %>
						</datalist>
					<td align="left">：計算用</td>
				</tr>
				<!-- 2019/12/27 -->
				<!-- 取込日3(中間)を表示検索用としてついか　マイナス一覧画面から受注照会画面への検索用 -->
				<tr>
					<th>取込日3(中間)</th>
					<td><input type="text" name="day3" list="insymd_naiji"></td>
						<datalist id="insymd_naiji">
						<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
						<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
						<%} %>
						</datalist>
					<td>：表示検索用</td>
				</tr>
			</table>
				<p><input type="submit" value="実行" onclick="this.disabled = true; this.value='処理中';"></p>
		</form>
	</section>
</article>

<article class="search_juchu">
	<section>
		<form method="Post" action="<%=PropertyLoader.getProperty("url.servlet.KBTsabunSearch") %>">
			<table>
			<caption>[ 内示差分検索 ]</caption>
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
					<th>取込日1(前回)</th>
					<td><input type="text" name="day1" list="insymd_naiji"></td>
						<datalist id="insymd_naiji">
						<% while (iteratorNaijiDay3.hasNext()) {KBTItemBean item3 = iteratorNaijiDay3.next();%>
						<option value="<%=item3.getInsNaijiDay()%>"><%=item3.getInsNaijiDay()%></option>
						<%} %>
						</datalist>
				</tr>
				<tr>
					<th>取込日2(今回)</th>
					<td><input type="text" name="day2" list="insymd_naiji"></td>
						<datalist id="insymd_naiji">
						<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
						<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
						<%} %>
						</datalist>
				</tr>
				<tr>
					<th>表示開始日</th>
						<td><input type="text" name="hyoujiymd" class="selectDate" value="<%=year%>-<%=month%>-<%=day%>"></td>
				</tr>
			</table>
				<p><input type="submit" value="検索" onclick="this.disabled = true; this.value='処理中';"></p>
		</form>
	</section>
</article>

<article class="search_juchu">
	<section>
		<form method="Post" action="<%=PropertyLoader.getProperty("url.servlet.KBTorderSearch") %>" >
			<table>
				<caption>[ 受注照会 ]</caption>
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
					<td><input type="text" name="day1" list="insymd"></td>
						<datalist id="insymd">
						<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
						<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
						<%} %>
						</datalist>
				</tr>
				<tr>
					<th>内示取込日2(中間)</th>
					<td><input type="text" name="day2" list="insymd"></td>
						<datalist id="insymd">
						<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
						<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
						<%} %>
						</datalist>
				</tr>
				<tr>
					<th>内示取込日3(今回)</th>
						<td><input type="text" name="day3" list="insymd"></td>
						<datalist id="insymd">
						<% while (iteratorNaijiDay.hasNext()) {KBTItemBean item = iteratorNaijiDay.next();%>
						<option value="<%=item.getInsNaijiDay()%>"><%=item.getInsNaijiDay()%></option>
						<%} %>
						</datalist>
				</tr>
				<tr>
					<th>表示開始日</th>
					<td><input type="text" name="disp_date" class="selectDate" value="<%=year%>-<%=month%>-<%=day%>" ></td>
				</tr>
			</table>
				<p><input type="submit" value="検索" onclick="this.disabled = true; this.value='処理中';"></p>
		</form>
	</section>
</article>
<article class="search_juchu">
	<section>
		<form method="Post" action="<%=PropertyLoader.getProperty("url.servlet.KabusokuSearch") %>">
			<table>
			<caption>[ 全品番過不足数出力 ]</caption>
				<tr>
					<% while (iteratorKikan3.hasNext()) {KakuteikikanBean item3 = iteratorKikan3.next();%>
					<td><input type="hidden" name="e_date" value="<%=item3.getE_date()%>"></td>
					<%} %>
				</tr>
				<tr>
					<th>内示取込日</th>
					<td><input type="text" name="day1" list="insymd"></td>
						<datalist id="insymd">
						<% while (iteratorDay.hasNext()) {KBTItemBean item = iteratorDay.next();%>
						<option value="<%=item.getInsymd()%>"><%=item.getInsymd()%></option>
						<%} %>
						</datalist>
				</tr>
			</table>
				<p><input type="submit" value="実行" onclick="this.disabled = true; this.value='処理中';"></p>
		</form>
	</section>
</article>

</body>
</html>

