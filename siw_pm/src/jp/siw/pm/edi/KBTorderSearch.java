package jp.siw.pm.edi;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pm.edi.bean.KBTItemBean;
import jp.siw.pm.edi.dao.KBTediDAO;
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KBTorderSearch
 */
public class KBTorderSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTorderSearch() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        String resultPage = PropertyLoader.getProperty("url.jsp.error");

        String hinban = request.getParameter("hinban");
        String insymd1 = request.getParameter("day1");
        String insymd2 = request.getParameter("day2");
        String insymd3 = request.getParameter("day3");
        String dispDate = request.getParameter("disp_date");

        try {
            KBTediDAO dao = new KBTediDAO();
            List<KBTItemBean> itemListHinban = dao.getItemListHinban();
            request.setAttribute("itemListHinban", itemListHinban);

            List<KBTItemBean> itemListOrder = dao.getItemListOrder(hinban, insymd1, insymd2, insymd3, dispDate);
            request.setAttribute("itemListOrder", itemListOrder);

            resultPage = PropertyLoader.getProperty("url.jsp.inquireKBTorder");

        } catch (NamingException e) {
            request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);
    }

}