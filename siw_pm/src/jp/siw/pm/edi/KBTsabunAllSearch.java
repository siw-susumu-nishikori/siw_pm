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
 * Servlet implementation class KBTsabunAllSearch
 */
public class KBTsabunAllSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTsabunAllSearch() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("=====KBTsabunAllSearch=====");
		String resultPage = PropertyLoader.getProperty("url.pm.edi_jsp.error");

		String insymd1 = request.getParameter("day1");
		String insymd2 = request.getParameter("day2");

		try {

			KBTediDAO dao = new KBTediDAO();
			List<KBTItemBean> SabunAllList = dao.getSabunAllList(insymd1, insymd2);

			request.setAttribute("SabunAllList", SabunAllList);
			resultPage = PropertyLoader.getProperty("url.servlet.KBTsabunAllSearch2");

		} catch (NamingException e) {
			request.setAttribute("errorMessage", e.getMessage());

		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());

		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);

	}

}
