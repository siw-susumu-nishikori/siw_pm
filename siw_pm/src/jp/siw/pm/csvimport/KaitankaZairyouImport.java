package jp.siw.pm.csvimport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pm.csvimport.dao.CsvImportDao;

/**
 * Servlet implementation class KaitankaZairyouImport
 */
public class KaitankaZairyouImport extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KaitankaZairyouImport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

        //タイトル横に日付表示用
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
        String today  = timeStampNowDay.format(nowTime);
        request.setAttribute("today", today);
       //日付表示ここまで


		//ファイルを読み込む
		FileReader fr = new FileReader("\\\\192.168.101.236\\riseプロジェクト\\15.生産負荷計算\\インポートデータ\\kaitankaZairyou.csv");
		BufferedReader br = new BufferedReader(fr);
		//**/

		try{

            CsvImportDao kaitankaZairyouImportDao = new CsvImportDao();
            kaitankaZairyouImportDao.importkaitankaZairyouCsv(fr, br, today );

        } catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			//br.close();
		} catch (NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			//br.close();
		//} catch (IOException e) {
		//	e.printStackTrace();
		//	//br.close();
		//} catch (NullPointerException e) {
		//	e.printStackTrace();
		//	//br.close();
		}
		//resultPage = PropertyLoader.getProperty("url.yamazumi_jsp.importCsv");
		//RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		//dispatcher.forward(request, response);
		System.out.println("取込み終了");
	}
}
