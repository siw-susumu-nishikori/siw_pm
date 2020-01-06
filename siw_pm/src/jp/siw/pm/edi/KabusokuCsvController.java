package jp.siw.pm.edi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pm.edi.bean.KBTItemBean;
import jp.siw.pm.edi.dao.KBTediDAO;

/**
 * Servlet implementation class KabusokuCsvController
 */
public class KabusokuCsvController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KabusokuCsvController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setHeader("Content-Disposition", "attachment; filename=\"KBT_kabusoku.csv\"");
        PrintWriter pw = response.getWriter();
        try {
            KBTediDAO dao = new KBTediDAO();
            List<KBTItemBean> kabusokuCsvList = dao.getKabusokuCsvList();
            request.setAttribute("kabusokuCsvList", kabusokuCsvList);

            String header ="\"" + "hinban" + "\"" + "," + "\"" + "jizaiko sry" + "\"" + "," + "\"" + "naiji sry" + "\"" + "," + "\"" + "kabusoku sry" + "\""  + "\r\n";

            pw.print(header);

            for (int i = 0; i < kabusokuCsvList.size(); i++) {
                String hinban = kabusokuCsvList.get(i).getHinban();
                int mysry = kabusokuCsvList.get(i).getMysry();
                int kikansry = kabusokuCsvList.get(i).getKikansry();
                int kabusoku = kabusokuCsvList.get(i).getKabusoku();


                //CSVファイル内部に記載する形式で文字列を設定
                String outputString = "\"" + hinban + "\"" + "," + "\"" + mysry + "\"" + "," + "\"" + kikansry + "\"" + "," + "\"" + kabusoku + "\"" + "\r\n";

                //CSVファイルに書き込み
                pw.print(outputString);
            }


        } catch (NamingException e) {
            request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }finally{
        	if(pw != null){
        		pw.close();
        	}
        }

    }

}
