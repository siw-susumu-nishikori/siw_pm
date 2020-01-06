<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="jp.siw.pm.edi.util.PropertyLoader"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="./css/common.css" rel="stylesheet" type="text/css">
	<link href="./css/juchu.css" rel="stylesheet" type="text/css">
<title>クボタ受注TopMenu</title>
</head>
<body>
<h2>トップメニュー</h2>
<article>
	<section>
		<form method="Get" action="<%=PropertyLoader.getProperty("url.servlet.KBTediManager") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
			<table>
				<tr>
					<td><input type="submit" class="top_button" value="クボタEDIデータ取込"  ></td>
				</tr>
			</table>
		</form>
	</section>
</article>
<article>
	<section>
		<form method="Get" action="<%=PropertyLoader.getProperty("url.servlet.KBTorderManager") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
			<table>
				<tr>
					<td><input class="top_button" type="submit" value="受注検索" ></td>
				</tr>
			</table>
		</form>
	</section>
</article>
<article>
	<section>
		<form method="Get" action="<%=PropertyLoader.getProperty("url.servlet.TanpinZaikoManager") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
			<table>
				<tr>
					<td><input class="top_button"type="submit" value="単品在庫データ取込" ></td>
				</tr>
			</table>
		</form>
	</section>
</article>
<article>
	<section>
		<form method="Get" action="<%=PropertyLoader.getProperty("url.servlet.KakuteiKikanSetManager") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
			<table>
				<tr>
					<td><input class="top_button" type="submit" value="定期確定期間設定" ></td>
				</tr>
			</table>
		</form>
	</section>
</article>
</body>
</html>