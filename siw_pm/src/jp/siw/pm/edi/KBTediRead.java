package jp.siw.pm.edi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pm.edi.util.PropertyLoader;



/**
 * Servlet implementation class RegisterFile
 */
public class KBTediRead extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTediRead() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		String resultPage = PropertyLoader.getProperty("url.jsp.error");
		String[] jvan = null;

		try	{
            // ファイルのパスを指定する
            File file = new File("\\\\192.168.101.236\\riseプロジェクト\\16.クボタEDI取込フォルダ\\RCV_JVAN.DAT");
            File file2 = new File("\\\\192.168.101.236\\riseプロジェクト\\16.クボタEDI取込フォルダ\\RCV_JVAN (2).DAT");
            File file3 = new File("\\\\192.168.101.236\\riseプロジェクト\\16.クボタEDI取込フォルダ\\RCV_JVAN (3).DAT");

            // ファイルが存在しない場合に例外が発生するので確認する
           /* if (!file.exists()) {
                System.out.print("ファイルが存在しません");
                return;
                }*/

            // BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
            BufferedReader bufferedReader = null;
            BufferedReader bufferedReader2 = null;
            BufferedReader bufferedReader3 = null;

            String line;
            List<String> lineList = new ArrayList<String>();

            if(!file.exists() && !file2.exists() && !file3.exists()){
            	System.out.print("ファイルが存在しません");
                return;
            }else if(!file2.exists() && !file3.exists()){
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));

                while((line = bufferedReader.readLine()) != null){
                	lineList.add(line);
                	}

                // 最後にファイルを閉じてリソースを開放する
                bufferedReader.close();

            }else if(!file3.exists()){
            	bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"Shift-JIS"));
            	bufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2),"Shift-JIS"));

                while((line = bufferedReader.readLine()) != null){
                	lineList.add(line);
                	}
                while((line = bufferedReader2.readLine()) != null){
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
                	lineList.add(line);
                	}
                while((line = bufferedReader2.readLine()) != null){
                	lineList.add(line);
                	}
                while((line = bufferedReader3.readLine()) != null){
                	lineList.add(line);
                	}

                // 最後にファイルを閉じてリソースを開放する
                bufferedReader.close();
                bufferedReader2.close();
                bufferedReader3.close();

            }

            jvan = lineList.toArray(new String[lineList.size()]);
            int n = jvan.length;
            String[][] kb35 = new String[n][0];

            for(int i=0; i<jvan.length; i++){
            	kb35[i] = jvan;
    	    	}

    		request.setAttribute("kb35", kb35);
			request.setAttribute("message", PropertyLoader.getProperty("message.completeInsertion"));
			resultPage = PropertyLoader.getProperty("url.servlet.KBTediRegister");

		}catch (FileNotFoundException e) {
			request.setAttribute("errorMessage", PropertyLoader.getProperty("message.FileNotFoundException"));
			}
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
		}
	}
