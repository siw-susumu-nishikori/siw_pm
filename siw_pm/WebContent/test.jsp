<%@ page language="java" contentType="text/html; charset=Shift_JIS"
 pageEncoding="Shift_JIS"%>


<%@ page import="java.util.Calendar" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>�J�����_�[�R</title>

</head>
<body>



	<%
int intNen; //�\���N
int intTuki; //�\����
String stWk1; //�O��Form�ւ̑���@�N
String stWk2; //�O��Form�ւ̑���@��
String stWk3; //����Form�ւ̑���@�N
String stWk4; //����Form�ւ̑���@��
int intWk1;
	int intWk2;
	int intWk3;
	int intWk4;

	Calendar cal = Calendar.getInstance();
	int intYear = cal.get(Calendar.YEAR); //�V�X�e���̔N
int intMonth = cal.get(Calendar.MONTH); //�V�X�e���̌�
int intDate = cal.get(Calendar.DATE); //�V�X�e���̓�


//FORM����N�A�����擾�Bnull�Ȃ�V�X�e���N�����g���B
String nen = request.getParameter("nen1");
	if (nen==null) {
	intNen = intYear;
	}else{
	intNen =Integer.parseInt (nen);
	}

	String tuki = request.getParameter("tuki1");
	if (tuki==null) {
	intTuki = intMonth;
	}else{
	intTuki = Integer.parseInt (tuki);
	}





	cal.set(intNen, intTuki, 1); //�\�������Z�b�g
int intLastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //�\�����̓���
int intFirstDay = cal.get(Calendar.DAY_OF_WEEK); //�\�����̂P���̗j��


//FORM�f�[�^���쐬�@�@���͓��������ł͎��ۂ̌��}�C�i�X�P
intWk1 = intNen;
	intWk2 = intTuki-1;
	intWk3 = intNen;
	intWk4 = intTuki+1;

	if (intWk2 < 0 ){
	intWk1--;
	intWk2=11;
	}

	if (intWk4 > 11){
	intWk3++;
	intWk4 = 0;
	}

	//�����𕶎��ɕϊ�
stWk1 = Integer.toString (intWk1);
	stWk2= Integer.toString (intWk2);
	stWk3 = Integer.toString (intWk3);
	stWk4= Integer.toString (intWk4);
	%>

	<table cellpadding="10">
	<tr>
	<td valign = "top">
	<form method="post" action="Calendar3.jsp">
	<input type="hidden" name="nen1" value="<%=stWk1%>">
	<input type="hidden" name="tuki1" value="<%=stWk2%>">
	<input type="submit" value="<<">
	</form>
	</td>


	<td bgcolor="d2ffff">
	<%
out.println("<h2>   <u>"+intNen+"�N"+(intTuki+1)+"��</u></h2>");
out.println("<font color=#FF0000>��</font>�@���@�΁@���@�؁@���@<font color=#0000FF>�y</font><br>");


int intIchi = 0; //�o�͐��@�J�E���g�p

//�P�s�ڂ̋󔒏o��
for (int i=1; i < intFirstDay; i++){
	intIchi++;
	out.println("   ");
	}

	// �V�Ŋ������]�肪1�Ȃ�ԁA0�Ȃ�Ɖ��s
for (int i=1; i <=intLastDate ;i++){
	intIchi++;

	//�j���̐F�Â�
switch ( intIchi % 7) {
	case 0:
	out.println("<font color=#0000FF>");
break;
	case 1:
	out.println("<font color=#FF0000>");
break;
	default:
	out.println("<font color=#000000>");
	}

	//�{���𑾎�
if (intNen ==intYear && intTuki==intMonth && i==intDate){
	out.println("<b>");
	}

	//�P���̂Ƃ��󔒂�O�ɑ}�� �@�@�o��
if (i < 10){
	out.println(" "+i+" ");
	}
	else {
	out.println( i+" ");
	}


	if (intNen ==intYear && intTuki==intMonth && i==intDate){
	out.println("</b>");
	}

out.println("</font>");

	//�y�j���Ȃ���s
if (intIchi % 7 == 0) {
	out.println("<br>");
	}

	}

	%>
	</td>
	<td valign = "top">
	<form method="post" action="Calendar3.jsp">
	<input type="hidden" name="nen1" value="<%=stWk3 %>">
	<input type="hidden" name="tuki1" value="<%=stWk4 %>">
	<input type="submit" value=">>">
	</form>
	</td>
	</tr>
	</table>
</body>
</html>