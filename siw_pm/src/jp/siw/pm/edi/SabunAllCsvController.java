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
 * Servlet implementation class SabunAllCsvController
 */
public class SabunAllCsvController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SabunAllCsvController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //出力するCSVファイル名を設定
        response.setHeader("Content-Disposition", "attachment; filename=\"KBT_sabunAll.csv\"");

        try(PrintWriter pw = response.getWriter()) {
            KBTediDAO dao = new KBTediDAO();
            List<KBTItemBean>sabunAllCsvList = dao.getSabunAllCsvList();
            request.setAttribute("sabunAllCsvList", sabunAllCsvList);

            String header ="\"" + "hinban" + "\"" + "," + "\"" + "nounyushiji_ymd" + "\"" + "," + "\"" + "noba" + "\"" + "," + "\"" + "sabun sry" + "\""  + "\r\n";
            pw.print(header);

            for (int i = 0; i < sabunAllCsvList.size(); i++) {
                String hinban = sabunAllCsvList.get(i).getHinban();
                String nounyushiji_ymd = sabunAllCsvList.get(i).getNounyushiji_ymd();
                String noba = sabunAllCsvList.get(i).getNoba_cd();
                int sasu = sabunAllCsvList.get(i).getSasu();

                //CSVファイル内部に記載する形式で文字列を設定
                String outputString = "\"" + hinban + "\"" + "," + "\"" + nounyushiji_ymd + "\"" + "," + "\"" + noba + "\"" + "," + "\"" + sasu + "\"" + "\r\n";

                //CSVファイルに書き込み
                pw.print(outputString);
            }

        } catch (NamingException e) {
            request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());
        }

    }

}
