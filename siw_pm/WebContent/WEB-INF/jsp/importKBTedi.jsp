<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="jp.siw.pm.edi.util.PropertyLoader"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ImportFile</title>
	<link href="./css/common.css" rel="stylesheet" type="text/css">
	<link href="./css/juchu.css" rel="stylesheet" type="text/css">
</head>
<body>

<h2>クボタEDIデータ取込</h2>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.TopMenuManager") %>'">トップメニューへ</button>
<article>
	<section>
		<a href="file:\\192.168.101.236\riseプロジェクト\16.クボタEDI取込フォルダ">クボタEDIデータ取込フォルダ</a>
		<form method="post" action="<%=PropertyLoader.getProperty("url.servlet.KBTediRead") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
			<table>
				<tr>
					<td>■内示データ取込</td>
				</tr>
				<tr>
					<td><input type="submit" value="JVAN取込開始" onclick="this.disabled = true; this.value='取込中';" style="width:120px" ></td>
				</tr>
			</table>
		</form>
	</section>
</article>
<article>
	<section>
		<form method="post" action="<%=PropertyLoader.getProperty("url.servlet.KBTnvanRead") %>" enctype="multipart/form-data" accept="text/plain,text/csv">
			<table>
				<tr>
					<td>■確定データ取込</td>
				</tr>
				<tr>
					<td><input type="submit" value="NVAN取込開始" onclick="this.disabled = true; this.value='取込中';" style="width:120px" ></td>
				</tr>
			</table>
		</form>
	</section>
</article>
</body>
</html>
