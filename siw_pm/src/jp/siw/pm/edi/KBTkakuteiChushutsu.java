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
import jp.siw.pm.edi.bean.KBTNvanBean;
import jp.siw.pm.edi.dao.KBTediDAO;
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KBTkakuteiChushutsu
 */
public class KBTkakuteiChushutsu extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTkakuteiChushutsu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.jsp.error");

        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
        String insymd  = timeStampNowDay.format(nowTime);
        String insertDay = insymd;
        System.out.println("2/2/2/2/2/2");
        try {
            KBTediDAO dao = new KBTediDAO();
            List<KBTNvanBean> itemList45 = dao.getItemList45(insertDay);
            request.setAttribute("itemList45", itemList45);
            List<KBTNvanBean> itemList46 = dao.getItemList46(insertDay);
            request.setAttribute("itemList46", itemList46);
            List<KBTNvanBean> itemList47 = dao.getItemList47(insertDay);
            request.setAttribute("itemList47", itemList47);
            List<KBTItemBean> juchuList = dao.getJuchuList(insymd);
            request.setAttribute("juchuList", juchuList);

            resultPage = PropertyLoader.getProperty("url.servlet.KBTkakutei_juchuRegister");
            System.out.println("3/3/3/3/3/3");
        } catch (NamingException e) {
            request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);
        }

}