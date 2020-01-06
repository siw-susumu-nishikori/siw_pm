<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="jp.siw.pm.edi.util.PropertyLoader"
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<% request.setCharacterEncoding("UTF-8");
String day1 = request.getParameter("day1");
String day2 = request.getParameter("day2");
String day3 = request.getParameter("day3");
String e_date = request.getParameter("e_date");
%>
</head>
<body>
<h2>処理終了</h2>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.KBTorderManager") %>'">戻る</button>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.TopMenuManager") %>'">トップメニューへ</button>

<p>[ 差分全データダウンロード ]</p>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.SabunAllCsvController") %>'">CSVダウンロード</button>
<article>
	<section>
		<form method="Post" action="<%=PropertyLoader.getProperty("url.servlet.SabunAllCsvController2") %>">
			<table>
			<caption>[ 差分マイナスデータのみダウンロード ]</caption>
				<tr>
					<th colspan="3" style="border: none; background-color: #ffffff;">■出力期間指定《推奨：前回確定期間初日～次回確定期間末日》</th>
				</tr>
				<tr>
					<td style="border-style: none; padding-top: 10px; padding-bottom: 10px;"><input type="text" name="kikan_s" class="selectDate" placeholder="yyyy-mm-dd"></td>
					<td style="border-style: none; padding-top: 10px; padding-bottom: 10px;">～</td>
					<td style="border-style: none; padding-top: 10px; padding-bottom: 10px;"><input type="text" name="kikan_e" class="selectDate" placeholder="yyyy-mm-dd"></td>
				</tr>
			</table>
			<table class="hinban_sum">
				<tr>
					<td style="border-style: none;"><input type="radio" name="hinban_summary" value="yes" checked="checked">品番まとめ有</td>
					<td style="border-style: none;"><input type="radio" name="hinban_summary" value="none">品番まとめ無</td>
					<td style="border-style: none;">※CSVデータダウンロードにのみ有効</td>
				</tr>
				<tr>
					<td style="border:none;"><input type="hidden" name="hyoujiymd"></td>
					<td style="border:none;"><input type="hidden" name="e_date" value="<%out.println(e_date); %>"></td>
				</tr>
			</table>
			<p style="clear:both"><button type="submit" name="csv">CSVダウンロード</button></p>
			<p style="clear:both"><button type="submit" name="disp" onClick="win_open()">マイナス品番一覧表示</button></p>
		</form>
	</section>
</article>

</body>
</html>