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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>在庫と受注の過不足</title>
	<link href="./css/common.css" rel="stylesheet" type="text/css">
	<link href="./css/table.css" rel="stylesheet" type="text/css">
	<link href="./css/juchu.css" rel="stylesheet" type="text/css">
</head>
<body>
<h2>処理終了</h2>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.KBTorderManager") %>'">戻る</button>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.TopMenuManager") %>'">トップメニューへ</button>
<% List<KBTItemBean> kabusokuList = Cast.castList(request.getAttribute("kabusokuList"));
Iterator<KBTItemBean> iterator = kabusokuList.iterator();
%>

<%request.setCharacterEncoding("UTF-8");
String e_date = request.getParameter("e_date");
%>

<% Calendar cal = Calendar.getInstance(); //カレンダーオブジェクトを取得
 int year = cal.get(Calendar.YEAR); //現在の年を取得
 int month = cal.get(Calendar.MONTH) + 1; //現在の月を取得
 int day = cal.get(Calendar.DATE); //現在の日を取得
%>
<article style="clear:both;">
	<section>
		<p>■過不足数データダウンロード</p>
		<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.KabusokuCsvController") %>'">CSVダウンロード</button>

		<p>■単品在庫データに品番の無い内示データ</p>
		<table>
			<tr>
				<th>内示数合計期間</th>
				<td><%=year%>-<%=month%>-<%=day%>～<%out.println(e_date); %></td>
			</tr>
		</table>
	</section>
</article>
<article>
	<section>
		<table>
			<tr>
				<th>品番</th>
				<th class="naiji_total">内示数計</th>
			</tr>
			<tr>
			<% while (iterator.hasNext()) {KBTItemBean item = iterator.next();%>
				<td><%=item.getHinban()%></td>
				<td><%=item.getGokei_su()%></td>
			<%} %>
			</tr>
		</table>
	</section>
</article>
</body>
</html>