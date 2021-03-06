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

import jp.siw.pm.edi.bean.KBTItemBean;
import jp.siw.pm.edi.dao.KBTediDAO;
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KBTsabunAllSearch2
 */
public class KBTsabunAllSearch2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTsabunAllSearch2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
System.out.println("=====KBTsabunAllSearch2=====");
		String resultPage = PropertyLoader.getProperty("url.pm.edi_jsp.error");

		Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
        String today  = timeStampNowDay.format(nowTime);
        request.setAttribute("today", today);

        try {

        	KBTediDAO dao = new KBTediDAO();
        	List<KBTItemBean> SabunAllList2 = dao.getSabunAllList2();

        	request.setAttribute("SabunAlluList2", SabunAllList2);

        	resultPage = PropertyLoader.getProperty("url.pm.edi_jsp.inquireKBTsabunAll");

        } catch (NamingException e) {
        	request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
        	request.setAttribute("errorMessage", e.getMessage());

        }
System.out.println("=====KBTsabunAllSearch2 END=====");
        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);

	}

}

