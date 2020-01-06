package jp.siw.pm.edi;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class SabunAllCsvController2
 */
public class SabunAllCsvController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SabunAllCsvController2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String kikan_s = request.getParameter("kikan_s");
        String kikan_e = request.getParameter("kikan_e");
        String hinban_sum =request.getParameter("hinban_summary");
        String csv = request.getParameter("csv");
        String disp = request.getParameter("disp");

        if(csv != null){
    	//出力するCSVファイル名を設定
        response.setHeader("Content-Disposition", "attachment; filename=\"KBT_sabunAll_kikan.csv\"");
        PrintWriter pw = response.getWriter();
        try{
            KBTediDAO dao = new KBTediDAO();

            if(hinban_sum.equals("yes")){
            List<KBTItemBean>sabunAllCsvList2 = dao.getSabunAllCsvList2(kikan_s, kikan_e);
            request.setAttribute("sabunAllCsvList2", sabunAllCsvList2);
            String header ="\"" + "hinban" + "\"" + "," + "\"" + "nounyushiji_ymd" + "\"" + "," + "\"" + "noba" + "\"" + "," + "\"" + "sabun sry" + "\""  + "\r\n";
            pw.print(header);

            for (int i = 0; i < sabunAllCsvList2.size(); i++) {
                String hinban = sabunAllCsvList2.get(i).getHinban();
                String nounyushiji_ymd = sabunAllCsvList2.get(i).getNounyushiji_ymd();
                String noba = sabunAllCsvList2.get(i).getNoba_cd();
                int sasu = sabunAllCsvList2.get(i).getSasu();

                //CSVファイル内部に記載する形式で文字列を設定
                String outputString = "\"" + hinban + "\"" + "," + "\"" + nounyushiji_ymd + "\"" + "," + "\"" + noba + "\"" + "," + "\"" + sasu + "\"" + "\r\n";

                //CSVファイルに書き込み
                pw.print(outputString);

            }

            }else if(hinban_sum.equals("none")){
                List<KBTItemBean>sabunAllCsvList3 = dao.getSabunAllCsvList3(kikan_s, kikan_e);
                request.setAttribute("sabunAllCsvList3", sabunAllCsvList3);
                String header ="\"" + "hinban" + "\"" + "," + "\"" + "nounyushiji_ymd" + "\"" + "," + "\"" + "noba" + "\"" + "," + "\"" + "sabun sry" + "\""  + "\r\n";
                pw.print(header);

                for (int i = 0; i < sabunAllCsvList3.size(); i++) {
                    String hinban = sabunAllCsvList3.get(i).getHinban();
                    String nounyushiji_ymd = sabunAllCsvList3.get(i).getNounyushiji_ymd();
                    String noba = sabunAllCsvList3.get(i).getNoba_cd();
                    int sasu = sabunAllCsvList3.get(i).getSasu();

                    //CSVファイル内部に記載する形式で文字列を設定
                    String outputString = "\"" + hinban + "\"" + "," + "\"" + nounyushiji_ymd + "\"" + "," + "\"" + noba + "\"" + "," + "\"" + sasu + "\"" + "\r\n";

                    //CSVファイルに書き込み
                    pw.print(outputString);

            }

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

        }else if(disp != null){
        	System.out.println("SabunAllCsvController2 **1");
        	String resultPage = PropertyLoader.getProperty("url.jsp.error");

        	try {
            	System.out.println("SabunAllCsvController2 **2");
        		KBTediDAO dao = new KBTediDAO();
        		List<KBTItemBean>sabunAllCsvList2 = dao.getSabunAllCsvList2(kikan_s, kikan_e);

        		request.setAttribute("sabunAllCsvList2", sabunAllCsvList2);
            	System.out.println("SabunAllCsvController2 **3");
                resultPage = PropertyLoader.getProperty("url.servlet.KBTsabunSearch2");

        	} catch (NamingException e) {
        		request.setAttribute("errorMessage", e.getMessage());

        	} catch (SQLException e) {
        		request.setAttribute("errorMessage", e.getMessage());

        	}

        	RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        	dispatcher.forward(request, response);

        }

	}

}
