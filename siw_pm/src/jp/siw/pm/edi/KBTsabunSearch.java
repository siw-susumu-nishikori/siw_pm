package jp.siw.pm.edi;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KBTsabunSearch
 */
public class KBTsabunSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTsabunSearch() {
        super();

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String resultPage = PropertyLoader.getProperty("url.jsp.error");
        String hinban = request.getParameter("hinban");
        String insymd1 = request.getParameter("day1");
        String insymd2 = request.getParameter("day2");
        String hyoujiymd = request.getParameter("hyoujiymd");
    	System.out.println("hyoujiymd="+hyoujiymd);
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
        String toDay  = timeStampNowDay.format(nowTime);
        request.setAttribute("today", toDay);

        String e_date = request.getParameter("e_date");

        try {

        	KBTediDAO dao = new KBTediDAO();
            List<KBTItemBean> SabunList = dao.getSabunList(hinban, insymd1, insymd2, hyoujiymd, request, response);
            request.setAttribute("SabunList", SabunList);

            List<KBTItemBean> Tsuika_juchuList = dao.getTsuika_juchuList(hinban, toDay);
            request.setAttribute("Tsuika_juchuList", Tsuika_juchuList);

            List<KBTItemBean> KikanZaikoList = dao.getKikanZaikoList(hinban, insymd2, toDay, e_date);
            request.setAttribute("KikanZaikoList", KikanZaikoList);

            @SuppressWarnings("unchecked")
			List<String> sasuList = (List<String>) request.getAttribute("sasuList");

            @SuppressWarnings("unchecked")
			List<CsvImportBean> ZaikoList = (List<CsvImportBean>) request.getAttribute("ZaikoList");

            List<KBTItemBean> naijiListDay = dao.getNaijiListDay();
            request.setAttribute("naijiListDay", naijiListDay);

            System.out.println("List="+sasuList);
            System.out.println("zaiko_su="+ZaikoList);
            System.out.println("Tsuika_juchuList="+Tsuika_juchuList);

            resultPage = PropertyLoader.getProperty("url.jsp.inquireKBTsabun");

        } catch (NamingException e) {
            request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);
    }

}
