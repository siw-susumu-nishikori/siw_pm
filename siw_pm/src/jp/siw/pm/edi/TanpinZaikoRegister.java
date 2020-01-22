package jp.siw.pm.edi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pm.edi.dao.CsvImportDAO;
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class TanpinZaikoRegister
 */
public class TanpinZaikoRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TanpinZaikoRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		String resultPage = PropertyLoader.getProperty("url.jsp.error");

		Timestamp nowTime= new Timestamp(System.currentTimeMillis());
		SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
		String today  = timeStampNowDay.format(nowTime);
		request.setAttribute("today", today);


		try{

			FileReader fr = new FileReader("\\\\192.168.101.236\\riseプロジェクト\\16.クボタEDI取込フォルダ\\FSKN280A.csv");
			BufferedReader br = new BufferedReader(fr);


			//(1) 最終更新日時取得対象ファイル
			String targetPath = new File("\\\\192.168.101.236\\riseプロジェクト\\16.クボタEDI取込フォルダ\\FSKN280A.csv").getAbsolutePath();

			//(2) 最終更新日時表示書式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			//(3) 対象ファイルのファイルオブジェクト生成
			File targetFile = new File(targetPath);

			//(4) 対象ファイルの最終更新日時取得（1970年1月1日からの経過時間）
			Long lastModified = targetFile.lastModified();

			//(5) 最終更新日時書式整形
			String lastModifiedStr = sdf.format(lastModified);

			//(6) 表示
			System.out.println(lastModifiedStr);

			CsvImportDAO csvImportDAO = new CsvImportDAO();
			csvImportDAO.insertCsv(fr, br, lastModifiedStr);

			resultPage = PropertyLoader.getProperty("url.jsp.importKBTediComplete");
			br.close(); //2019-12-26追記

		}catch (FileNotFoundException e){
			request.setAttribute("errorMessage", PropertyLoader.getProperty("message.FileNotFoundException_tanpin"));


		}catch (SQLException | NamingException e) {
			request.setAttribute("errorMessage", PropertyLoader.getProperty("message.NumberFormatException"));


		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);

	}

}
