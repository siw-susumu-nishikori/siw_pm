package jp.siw.pm.edi;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pm.edi.bean.CsvImportBean;
import jp.siw.pm.edi.bean.KBTItemBean;
import jp.siw.pm.edi.dao.KBTediDAO;
import jp.siw.pm.edi.util.Cast;
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KBTsabunSearch2
 */
public class KBTsabunSearch2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTsabunSearch2() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	       String resultPage = PropertyLoader.getProperty("url.jsp.error");
	       List<KBTItemBean> sabunAllCsvList2 = Cast.castList(request.getAttribute("sabunAllCsvList2"));

           for (int i = 0; i < sabunAllCsvList2.size(); i++) {
               String[] hinban = {sabunAllCsvList2.get(i).getHinban()};
           	System.out.println("hinban="+Arrays.toString(hinban));

	        String insymd1 = request.getParameter("kikan_s");
	        String insymd2 = request.getParameter("kikan_s");
	        String hyoujiymd = request.getParameter("hyoujiymd");
        	System.out.println(insymd1);
        	System.out.println(insymd2);
        	System.out.println("hyoujiymd="+hyoujiymd);
	        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
	        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
	        String toDay  = timeStampNowDay.format(nowTime);

	        String e_date = request.getParameter("e_date");
        	System.out.println("e_date="+e_date);

	        try {
	            KBTediDAO dao = new KBTediDAO();
	            List<KBTItemBean> SabunMinusList = dao.getSabunMinusList(hinban, insymd1, insymd2, hyoujiymd, request, response);
	            List<KBTItemBean> Tsuika_juchuMinusList = dao.getTsuika_juchuMinusList(hinban, toDay);
	            List<KBTItemBean> KikanZaikoMinusList = dao.getKikanZaikoMinusList(hinban, insymd2, toDay, e_date);

	            @SuppressWarnings("unchecked")
				List<String> sasuList = (List<String>) request.getAttribute("sasuList");
	            @SuppressWarnings("unchecked")
				List<CsvImportBean> ZaikoList = (List<CsvImportBean>) request.getAttribute("ZaikoList");

	            request.setAttribute("SabunMinusList", SabunMinusList);
	            request.setAttribute("Tsuika_juchuMinusList", Tsuika_juchuMinusList);
	            request.setAttribute("KikanZaikoMinusList", KikanZaikoMinusList);

	            System.out.println("List="+sasuList);
	            System.out.println("zaiko_su="+ZaikoList);
	            System.out.println("Tsuika_juchuList="+Tsuika_juchuMinusList);

	            resultPage = PropertyLoader.getProperty("url.jsp.inquireKBTsabun");

	        } catch (NamingException e) {
	            request.setAttribute("errorMessage", e.getMessage());

	        } catch (SQLException e) {
	            request.setAttribute("errorMessage", e.getMessage());
	        }
           }
	        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
	        dispatcher.forward(request, response);
	    }

	}