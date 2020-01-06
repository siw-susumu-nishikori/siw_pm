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
	<link href="./css/juchu.css" rel="stylesheet" type="text/css">
</head>
<body>
<h2>取込完了</h2>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.KBTediManager") %>'">戻る</button>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.TopMenuManager") %>'">トップメニューへ</button>


</body>
</html>
