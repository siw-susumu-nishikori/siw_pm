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

import jp.siw.pm.edi.bean.KakuteikikanBean;
import jp.siw.pm.edi.dao.KakuteikikanDAO;
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KakuteiKikanSetManager
 */
public class KakuteiKikanSetManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KakuteiKikanSetManager() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String resultPage = PropertyLoader.getProperty("url.jsp.error");

        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
        String today  = timeStampNowDay.format(nowTime);
        request.setAttribute("today", today);

        try {

        	KakuteikikanDAO dao = new KakuteikikanDAO();
        	List<KakuteikikanBean> kikanList = dao.getKikanList();
        	request.setAttribute("kikanList", kikanList);

            resultPage = PropertyLoader.getProperty("url.jsp.setKikan");

        } catch (NamingException e) {

        	request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {

        	request.setAttribute("errorMessage", e.getMessage());

        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);

	}

}
