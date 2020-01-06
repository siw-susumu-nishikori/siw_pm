<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.Iterator"
	import="java.util.List"
    import="jp.siw.pm.edi.bean.KBTItemBean"
    import="jp.siw.pm.edi.bean.KakuteikikanBean"
    import="jp.siw.pm.edi.util.PropertyLoader"
    import="jp.siw.pm.edi.util.Cast"
    import="jp.siw.pm.edi.util.Cast_Kakuteikikan"
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="./css/common.css" rel="stylesheet" type="text/css">
	<link href="./css/juchu.css" rel="stylesheet" type="text/css">
<title>確定期間変更完了</title>
</head>
<body>
<h2>確定期間変更完了</h2>
<button type="button" onclick="location.href='<%=PropertyLoader.getProperty("url.servlet.TopMenuManager") %>'">トップメニューへ</button>
<% List<KakuteikikanBean> kikanList = Cast_Kakuteikikan.castList(request.getAttribute("kikanList"));
Iterator<KakuteikikanBean> iteratorKikan = kikanList.iterator();
%>
<article>
	<section>
		<table>
			<tr>
				<% while (iteratorKikan.hasNext()) {KakuteikikanBean item = iteratorKikan.next();%>
				<th>確定期間</th>
			</tr>
			<tr>
				<td> <%=item.getJun_nm() %> </td>
				<td>＜<%=item.getS_date() %></td>
				<td>～</td>
				<td><%=item.getE_date() %>＞</td>
				<%} %>
				<td>に設定しました</td>
			</tr>
		</table>
	</section>
</article>
</body>
</html>