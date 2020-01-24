package jp.siw.pm.edi;

import java.io.IOException;
import java.io.PrintWriter;
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
		request.setCharacterEncoding("UTF-8");

		String kikan_s = null;
		String kikan_e = null;
		if(request.getParameter("kikan_s").equals("") && request.getParameter("kikan_e").equals("")){
			kikan_s = "";
			kikan_e = "2099-12-31";
		}else if(request.getParameter("kikan_s").equals("") && request.getParameter("kikan_e") != ""){
			kikan_s = "";
			kikan_e = request.getParameter("kikan_e");

			}else if(request.getParameter("kikan_s") != "" && request.getParameter("kikan_e").equals("")){
				kikan_s = request.getParameter("kikan_s");
				kikan_e = "2099-12-31";

				}else if(request.getParameter("kikan_s") != "" && request.getParameter("kikan_e") != ""){
					kikan_s = request.getParameter("kikan_s");
					kikan_e = request.getParameter("kikan_e");

					}

		request.setAttribute("kikan_s", kikan_s);
        request.setAttribute("kikan_e", kikan_e);

        String insymd1 = request.getParameter("day1");
        String insymd2 = request.getParameter("day2");
        String hyoujiymd = request.getParameter("hyoujiymd");
	    	System.out.println(insymd1 + insymd2);
	    	System.out.println(insymd2);
	    	System.out.println("hyoujiymd="+hyoujiymd);
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
        String today  = timeStampNowDay.format(nowTime);
        request.setAttribute("today", today);

        String e_date = request.getParameter("e_date");
	    	System.out.println("e_date="+e_date);

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
            //CSVファイル1行目の項目名設定
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
                //CSVファイル1行目の項目名設定
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
        	String resultPage = PropertyLoader.getProperty("url.pm.edi_jsp.error");

        	try{

        		KBTediDAO dao = new KBTediDAO();
        		List<KBTItemBean> naijiListDay = dao.getNaijiListDay();
        		request.setAttribute("naijiListDay", naijiListDay);

	            resultPage = PropertyLoader.getProperty("url.pm.edi_jsp.inquireKBTsabunMinusAll");

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
