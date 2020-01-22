package jp.siw.pm.edi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KBTnvanRead
 */
public class KBTnvanRead extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTnvanRead() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		String resultPage = PropertyLoader.getProperty("url.jsp.error");
		String[] nvan = null;

        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy-MM-dd");
        String today  = timeStampNowDay.format(nowTime);
        request.setAttribute("today", today);

		try	{

			// ファイルのパスを指定する
			File file = new File("\\\\192.168.101.236\\riseプロジェクト\\16.クボタEDI取込フォルダ\\RCV_NVAN.DAT");
			File file2 = new File("\\\\192.168.101.236\\riseプロジェクト\\16.クボタEDI取込フォルダ\\RCV_NVAN (2).DAT");
			File file3 = new File("\\\\192.168.101.236\\riseプロジェクト\\16.クボタEDI取込フォルダ\\RCV_NVAN (3).DAT");

			// ファイルが存在しない場合に例外が発生するので確認する
			/* if (!file.exists()) {
			 * System.out.print("ファイルが存在しません");
			 * return;
			 * }*/

			// BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
			BufferedReader bufferedReader = null;
			BufferedReader bufferedReader2 = null;
			BufferedReader bufferedReader3 = null;

			String line;
			List<String> lineList = new ArrayList<String>();

			if(!file2.exists() && !file3.exists()){
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));

				while((line = bufferedReader.readLine()) != null){
					int line_kbno =Integer.parseInt(line.substring(0,2));
					if(line_kbno == 45 || line_kbno == 46 || line_kbno == 47)
						lineList.add(line);

				}

				// 最後にファイルを閉じてリソースを開放する
				bufferedReader.close();

			}else if(!file3.exists()){
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
				bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2),"Shift-JIS"));

				while((line = bufferedReader.readLine()) != null){
					int line_kbno =Integer.parseInt(line.substring(0,2));
					if(line_kbno == 45 || line_kbno == 46 || line_kbno == 47)
						lineList.add(line);

				}

				while((line = bufferedReader2.readLine()) != null){
					int line_kbno =Integer.parseInt(line.substring(0,2));
					if(line_kbno == 45 || line_kbno == 46 || line_kbno == 47)
						lineList.add(line);

				}

				// 最後にファイルを閉じてリソースを開放する
				bufferedReader.close();
				bufferedReader2.close();

			}else{
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
				bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2),"Shift-JIS"));
				bufferedReader3 = new BufferedReader(new InputStreamReader(new FileInputStream(file3),"Shift-JIS"));

				while((line = bufferedReader.readLine()) != null){
					int line_kbno =Integer.parseInt(line.substring(0,2));
					if(line_kbno == 45 || line_kbno == 46 || line_kbno == 47)
						lineList.add(line);

				}

				while((line = bufferedReader2.readLine()) != null){
					int line_kbno =Integer.parseInt(line.substring(0,2));
					if(line_kbno == 45 || line_kbno == 46 || line_kbno == 47)
						lineList.add(line);

				}

                while((line = bufferedReader3.readLine()) != null){
                	int line_kbno =Integer.parseInt(line.substring(0,2));
                	if(line_kbno == 45 || line_kbno == 46 || line_kbno == 47)
                		lineList.add(line);

                }

                // 最後にファイルを閉じてリソースを開放する
				bufferedReader.close();
				bufferedReader2.close();
				bufferedReader3.close();

			}

			nvan = lineList.toArray(new String[lineList.size()]);
			int n = nvan.length;
			String[][] NVAN = new String[n][0];

			for(int i=0; i<nvan.length; i++){
				NVAN[i] = nvan;

			}

			System.out.println("NVAN="+(Arrays.deepToString(NVAN)));

			request.setAttribute("NVAN", NVAN);
			resultPage = PropertyLoader.getProperty("url.servlet.KBTnvanRegister");

		}catch (FileNotFoundException e) {
			request.setAttribute("errorMessage", PropertyLoader.getProperty("message.FileNotFoundException_nvan"));

		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);

	}

}