package jp.siw.pm.edi.dao;


	import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;



	public class CsvImportDAO {
		private static final String DELETE_tanpin_zaiko = "DELETE FROM tanpin_zaiko";
		private static final String INSERT  = "insert into tanpin_zaiko "
				+ "values ("
				+ "?, ?, ?, ?, ?, "
				+ "?, ?, ?, ?, ?, "
				+ "?, ? )";
		private DataSource source;
		public CsvImportDAO() throws NamingException {
			InitialContext context = new InitialContext();
			source = (DataSource) context.lookup("java:comp/env/jdbc/datasource");
		}

		public void insertCsv(FileReader fr, BufferedReader br, String lastModifiedStr)	throws SQLException {

			Connection connection = source.getConnection();

	        String line;
	        StringTokenizer token;

			try{
	            PreparedStatement statementDelete = connection.prepareStatement(DELETE_tanpin_zaiko);
	            statementDelete.executeUpdate();
	            System.out.println("tanpin_zaiko insertCsv 1====  削除しました");

	            int i=0;
				//読み込んだファイルを１行ずつ処理する
		            while ((line = br.readLine()) != null) {
		                //区切り文字","で分割する
		                token = new StringTokenizer(line, ",");

		                //分割した文字を画面出力する
		                while (token.hasMoreTokens()) {
							PreparedStatement statement = connection.prepareStatement(INSERT);
							String nony = token.nextToken().replace("\"", "").trim();
							statement.setString(1, nony);
							System.out.println("tanpin_zaiko insertCsv 2==== " + nony);

							String hinb = token.nextToken().replace("\"", "").trim();
							statement.setString(2, hinb);
							System.out.println("tanpin_zaiko insertCsv 2==== " + hinb);

							String hinm = token.nextToken().replace("\"", "").trim();
							statement.setString(3, hinm);
							System.out.println("tanpin_zaiko insertCsv 2==== " + hinm);

							double mysry = Double.parseDouble(token.nextToken().replace("+", ""));
							statement.setDouble(4, mysry);
							System.out.println("tanpin_zaiko insertCsv 2==== " + mysry);

							double josry = Double.parseDouble(token.nextToken().replace("+", ""));
							statement.setDouble(5, josry);
							System.out.println("tanpin_zaiko insertCsv 2==== " + josry);

							double tnsry = Double.parseDouble(token.nextToken().replace("+", ""));
							statement.setDouble(6, tnsry);
							System.out.println("tanpin_zaiko insertCsv 2==== " + tnsry);

							String hgyc = token.nextToken().replace("\"", "").trim();
							statement.setString(7, hgyc);
							System.out.println("tanpin_zaiko insertCsv 2==== " + hgyc);

							String tokk =token.nextToken().replace("\"", "").trim();
							statement.setString(8, tokk);
							System.out.println("tanpin_zaiko insertCsv 2==== " + tokk);

							String kata =token.nextToken().replace("\"", "").trim();
							statement.setString(9, kata);
							System.out.println("tanpin_zaiko insertCsv 2==== " + kata);

							String yobi1 = token.nextToken().replace("\"", "").trim();
							statement.setString(10,yobi1);
							System.out.println("tanpin_zaiko insertCsv 2==== " + yobi1);

							double ysry = Double.parseDouble(token.nextToken().replace("+", ""));
							statement.setDouble(11,ysry);
							System.out.println("tanpin_zaiko insertCsv 2==== " + ysry);

							statement.setString(12,lastModifiedStr);
							System.out.println("tanpin_zaiko insertCsv 2==== " + lastModifiedStr);

							statement.executeUpdate();
							statement.close();

		                }
		                i++;
						System.out.println(i);
		            }

	            //終了処理
	            br.close();
	            System.out.println("tanpin_zaiko insertCsv 2====  登録しました");

	        } catch (IOException ex) {
	            //例外発生時処理
	            ex.printStackTrace();
	        } catch(Exception e){

	        }
		}

	}


