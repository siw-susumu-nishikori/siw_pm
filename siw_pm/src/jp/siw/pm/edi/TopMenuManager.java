package jp.siw.pm.edi;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class TopMenuManager
 */
public class TopMenuManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopMenuManager() {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String resultPage = PropertyLoader.getProperty("url.jsp.topMenu");

		Timestamp nowTime= new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
		String today  = timeStampNowDay.format(nowTime);
		request.setAttribute("today", today);

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);

	}

}
