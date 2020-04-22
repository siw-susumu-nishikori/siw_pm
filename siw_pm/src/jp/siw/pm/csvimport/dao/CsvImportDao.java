package jp.siw.pm.csvimport.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.siw.pm.csvimport.bean.HinmokuBean;

public class CsvImportDao {

	private static final String DELETE_M_HINMOKU = "delete from m_hinmoku";
	private static final String INSERT_M_HINMOKU = "insert into m_hinmoku "
			+ "(hinb, hinm, hinr, thin, zaikb, size1, size2, size3, size4, syor, "
			+ "forsiwt, seno, tkcd, sybe1, sybe2, bunr, sktk, tehk, senk, zaik, "
			+ "gjik, tefk, kaek, sgyk, kenk, tani, hinc, kata, zuno, ords, "
			+ "soub, tana, hack, hosk, sosk, bumn, hgyc, hgyc2, nony, tyok, "
			+ "yumu, lotk, lots, zobs, mart, bdom, seil, anzl, kenl, sklt, "
			+ "cyc1, cyc2, cyc3, bumns, hako, syuy, haks, hyot, seis, ksyk, "
			+ "ksyj, genz, hkiz, mhkz, hraz, linz, skyz, anzz, nykbi, nykbo, "
			+ "conti, conto, minz, azct, tnjb, tnjs, tnsa, tnks, tnzs, suryz, "
			+ "mhkzz, hkizz, linzz, skyzz, suryt, mhkzt, hkizt, linzt, skyzt, nyuk, "
			+ "syukn, syutn, ukein, uketn, dnps, dnpn, lowc, hinba, surya, kouda, "
			+ "updateDay)"
			+ "values( "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
			+ "? )";
	private static final String DELETE_M_HINMOKU_ZAIRYOU = "delete from m_hinmoku_zairyou";
	private static final String INSERT_M_HINMOKU_ZAIRYOU = "insert into m_hinmoku_zairyou "
			+ "values("
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ? "
			+ ")";
	private static final String DELETE_M_KOUSEI = "delete from m_kousei";
	private static final String INSERT_M_KOUSEI = "insert into m_kousei "
			+ "values("
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ? "
			+ ")";
	private static final String DELETE_M_KAITANKA = "delete from m_kaitanka";
	private static final String INSERT_M_KAITANKA = "insert into m_kaitanka "
			+ "values("
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ? "
			+ ")";
	private static final String DELETE_M_KAITANKA_ZAIRYOU = "delete from m_kaitanka_zairyou";
	private static final String INSERT_M_KAITANKA_ZAIRYOU = "insert into m_kaitanka_zairyou "
			+ "values("
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ? "
			+ ")";
	private static final String DELETE_M_URITANKA = "delete from m_uritanka";
	private static final String INSERT_M_URITANKA = "insert into m_uritanka "
			+ "values("
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "?, ?, ?, ?, ?, "
			+ "? "
			+ ")";
	private DataSource source;
	public CsvImportDao() throws NamingException {
		InitialContext context = new InitialContext();
		source = (DataSource) context.lookup("java:comp/env/jdbc/datasource");
	}

	//品目取込み
	public void importHinmokuCsv(FileReader fr, BufferedReader br, String today) throws SQLException {

		Connection connection = source.getConnection();

        String line;
        StringTokenizer token;


		try{

			//**m_hinmokuテーブル削除
	        PreparedStatement statementDelete = connection.prepareStatement(DELETE_M_HINMOKU);
	        statementDelete.executeUpdate();
	        System.out.println("tanpin_zaiko insertCsv 1====  削除しました");
	        //**/

            int i=0;
			//読み込んだファイルを１行ずつ処理する
	            while ((line = br.readLine()) != null) {
	                //区切り文字","で分割する
	                token = new StringTokenizer(line, ",");

	                String size1="",size2="",size3="",size4="",syor="",forsiwt="", zaikb="";

	                //分割した文字を画面出力する
	                while (token.hasMoreTokens()) {
						PreparedStatement statement = connection.prepareStatement(INSERT_M_HINMOKU);

						String hinb =token.nextToken().replace("\"", "").trim();

						System.out.println("品番= " + hinb);

						//品番頭文字がB,C,G,H,Jの時
						if(hinb.substring(0, 1).equals("B") || hinb.substring(0, 1).equals("C") || hinb.substring(0, 1).equals("G") || hinb.substring(0, 1).equals("H") || hinb.substring(0, 1).equals("J")){


							zaikb = "1" ;
							int leng = hinb.length();
							StringBuilder AA = new StringBuilder(hinb);
							StringBuilder BB = AA.delete(0,2);
							String hinbZai = BB.toString();

							System.out.println("材料コード削除 " + hinbZai );

							//右から数えて2つ目がTH(タイ用材料)表記があるとき
							if(hinbZai.substring(leng-2).equals("TH")){
								//タイコードTHを追加
								forsiwt = "TH";
								//TH除去、空白除去
								String hinbTH1 =hinbZai.replace("TH", "").trim();

								int lengTH1 = hinbTH1.length();
								//右から二つ目の文字が英数(処理区分)の時
								String shori = hinbTH1.substring(lengTH1-2);

								//検証結果を格納する変数
								boolean result = true;



								 //一文字ずつ先頭から確認する。for文は文字数分繰り返す
								for(int j = 0; j < shori.length(); j++) {

								//i文字めの文字についてCharacter.isDigitメソッドで判定する
								if(Character.isDigit(shori.charAt(j))) {

								//数字の場合は次の文字の判定へ
								continue;

								}else {

								      //数字でない場合は検証結果をfalseに上書きする
								      result =false;
								      syor = shori ;
								    }
								  }
								//結果出力
								System.out.println(shori + "は文字である=>" + result);
								String hinbTH2 = hinb.replace("syor", "").trim();
								String hinbTH3 = hinbTH2.replace("X", ",");

						        String[] strArray = hinbTH3.split(",");
						        if(strArray.length ==1){
						        	size1 = strArray[0];
						    		System.out.println(size1);
						        }else if(strArray.length ==2){
						        	size1 = strArray[0];
						        	size2 = strArray[1];
						    		System.out.println(size1);
						    		System.out.println(size2);
						        }else if(strArray.length ==3){
						        	size1 = strArray[0];
						        	size2 = strArray[1];
						        	size3 = strArray[2];
						    		System.out.println(size1);
						    		System.out.println(size2);
						    		System.out.println(size3);
						        }else if(strArray.length ==4){
						        	size1 = strArray[0];
						        	size2 = strArray[1];
						        	size3 = strArray[2];
						        	size4 = strArray[3];
						    		System.out.println(size1);
						    		System.out.println(size2);
						    		System.out.println(size3);
						    		System.out.println(size4);
						        }else{
						        	size1 = strArray[0];
						        	size2 = strArray[1];
						        	size3 = strArray[2];
						        	size4 = strArray[3];
						    		System.out.println(size1);
						    		System.out.println(size2);
						    		System.out.println(size3);
						    		System.out.println(size4);
						        }
							} else{

								//タイコードない場合
								System.out.println("タイコードない場合 "  + hinbZai);
								String hinbTH1 =hinbZai.trim();
								int lengTH1 = hinbTH1.length();

								//右から二つ目の文字が英数(処理区分)の時
								String shori = hinbTH1.substring(lengTH1-2);

								 //一文字ずつ先頭から確認する。for文は文字数分繰り返す
								for(int j = 0; j < shori.length(); j++) {

								//i文字めの文字についてCharacter.isDigitメソッドで判定する
								if(Character.isDigit(shori.charAt(j))) {

								//数字の場合は次の文字の判定へ
								continue;

								}else {
									//結果の代入
									syor = shori ;
								    }
								  }
								//結果出力
								System.out.println(shori + "は文字である");

								String hinbTH2 = hinbTH1.replace(syor, "").trim();
								System.out.println("処理コード削除 " + hinbTH2);


								String hinbTH3 = hinbTH2.replace("X", ",");

								System.out.println("カンマ " + hinbTH3);

						        String[] strArray = hinbTH3.split(",");
						        if(strArray.length ==1){
						        	size1 = strArray[0];
						    		System.out.println(size1);
						        }else if(strArray.length ==2){
						        	size1 = strArray[0];
						        	size2 = strArray[1];
						    		System.out.println(size1);
						    		System.out.println(size2);
						        }else if(strArray.length ==3){
						        	size1 = strArray[0];
						        	size2 = strArray[1];
						        	size3 = strArray[2];
						    		System.out.println(size1);
						    		System.out.println(size2);
						    		System.out.println(size3);
						        }else if(strArray.length ==4){
						        	size1 = strArray[0];
						        	size2 = strArray[1];
						        	size3 = strArray[2];
						        	size4 = strArray[3];
						    		System.out.println(size1);
						    		System.out.println(size2);
						    		System.out.println(size3);
						    		System.out.println(size4);
						        }else{
						        	size1 = strArray[0];
						        	size2 = strArray[1];
						        	size3 = strArray[2];
						        	size4 = strArray[3];
						    		System.out.println(size1);
						    		System.out.println(size2);
						    		System.out.println(size3);
						    		System.out.println(size4);
						        }

							}
						}else{

							//材料以外はsize1,2,3,4,syor,forsiwtは記述無し
							System.out.println("材料ではない品番");
						}

						String  hinm = token.nextToken().replace("\"", "").trim();
						String  hinr = token.nextToken().replace("\"", "").trim();
						String  thin = token.nextToken().replace("\"", "").trim();
						String  seno = token.nextToken().replace("\"", "").trim();
						String  tkcd = token.nextToken().replace("\"", "").trim();
						String  sybe1 = token.nextToken().replace("\"", "").trim();
						String  sybe2 = token.nextToken().replace("\"", "").trim();
						String  bunr = token.nextToken().replace("\"", "").trim();
						String  sktk = token.nextToken().replace("\"", "").trim();
						String  tehk = token.nextToken().replace("\"", "").trim();
						String  senk = token.nextToken().replace("\"", "").trim();
						String  zaik = token.nextToken().replace("\"", "").trim();
						String  gjik = token.nextToken().replace("\"", "").trim();
						String  tefk = token.nextToken().replace("\"", "").trim();
						String  kaek = token.nextToken().replace("\"", "").trim();
						String  sgyk = token.nextToken().replace("\"", "").trim();
						String  kenk = token.nextToken().replace("\"", "").trim();
						String  tani = token.nextToken().replace("\"", "").trim();
						String  hinc = token.nextToken().replace("\"", "").trim();
						String  kata = token.nextToken().replace("\"", "").trim();
						String  zuno = token.nextToken().replace("\"", "").trim();
						String  ords = token.nextToken().replace("\"", "").trim();
						String  soub = token.nextToken().replace("\"", "").trim();
						String  tana = token.nextToken().replace("\"", "").trim();
						String  hack = token.nextToken().replace("\"", "").trim();
						String  hosk = token.nextToken().replace("\"", "").trim();
						String  sosk = token.nextToken().replace("\"", "").trim();
						String  bumn = token.nextToken().replace("\"", "").trim();
						String  hgyc = token.nextToken().replace("\"", "").trim();
						String  hgyc2 = token.nextToken().replace("\"", "").trim();
						String  nony = token.nextToken().replace("\"", "").trim();
						String  tyok = token.nextToken().replace("\"", "").trim();
						String  yumu = token.nextToken().replace("\"", "").trim();
						String  lotk = token.nextToken().replace("\"", "").trim();
						String  lots = token.nextToken().replace("\"", "").trim();
						String  zobs = token.nextToken().replace("\"", "").trim();
						String  mart = token.nextToken().replace("\"", "").trim();
						String  bdom = token.nextToken().replace("\"", "").trim();
						String  seil = token.nextToken().replace("\"", "").trim();
						String  anzl = token.nextToken().replace("\"", "").trim();
						String  kenl = token.nextToken().replace("\"", "").trim();
						String  sklt = token.nextToken().replace("\"", "").trim();
						String  cyc1 = token.nextToken().replace("\"", "").trim();
						String  cyc2 = token.nextToken().replace("\"", "").trim();
						String  cyc3 = token.nextToken().replace("\"", "").trim();
						String  bumns = token.nextToken().replace("\"", "").trim();
						String  hako = token.nextToken().replace("\"", "").trim();
						String  syuy = token.nextToken().replace("\"", "").trim();
						String  haks = token.nextToken().replace("\"", "").trim();
						String  hyot = token.nextToken().replace("\"", "").trim();
						String  seis = token.nextToken().replace("\"", "").trim();
						String  ksyk = token.nextToken().replace("\"", "").trim();
						String  ksyj = token.nextToken().replace("\"", "").trim();
						String  genz = token.nextToken().replace("\"", "").trim();
						String  hkiz = token.nextToken().replace("\"", "").trim();
						String  mhkz = token.nextToken().replace("\"", "").trim();
						String  hraz = token.nextToken().replace("\"", "").trim();
						String  linz = token.nextToken().replace("\"", "").trim();
						String  skyz = token.nextToken().replace("\"", "").trim();
						String  anzz = token.nextToken().replace("\"", "").trim();
						String  nykbi = token.nextToken().replace("\"", "").trim();
						String  nykbo = token.nextToken().replace("\"", "").trim();
						String  conti = token.nextToken().replace("\"", "").trim();
						String  conto = token.nextToken().replace("\"", "").trim();
						String  minz = token.nextToken().replace("\"", "").trim();
						String  azct = token.nextToken().replace("\"", "").trim();
						String  tnjb = token.nextToken().replace("\"", "").trim();
						String  tnjs = token.nextToken().replace("\"", "").trim();
						String  tnsa = token.nextToken().replace("\"", "").trim();
						String  tnks = token.nextToken().replace("\"", "").trim();
						String  tnzs = token.nextToken().replace("\"", "").trim();
						String  suryz = token.nextToken().replace("\"", "").trim();
						String  mhkzz = token.nextToken().replace("\"", "").trim();
						String  hkizz = token.nextToken().replace("\"", "").trim();
						String  linzz = token.nextToken().replace("\"", "").trim();
						String  skyzz = token.nextToken().replace("\"", "").trim();
						String  suryt = token.nextToken().replace("\"", "").trim();
						String  mhkzt = token.nextToken().replace("\"", "").trim();
						String  hkizt = token.nextToken().replace("\"", "").trim();
						String  linzt = token.nextToken().replace("\"", "").trim();
						String  skyzt = token.nextToken().replace("\"", "").trim();
						String  nyuk = token.nextToken().replace("\"", "").trim();
						String  syukn = token.nextToken().replace("\"", "").trim();
						String  syutn = token.nextToken().replace("\"", "").trim();
						String  ukein = token.nextToken().replace("\"", "").trim();
						String  uketn = token.nextToken().replace("\"", "").trim();
						String  dnps = token.nextToken().replace("\"", "").trim();
						String  dnpn = token.nextToken().replace("\"", "").trim();
						String  lowc = token.nextToken().replace("\"", "").trim();
						String  hinba = token.nextToken().replace("\"", "").trim();
						String  surya = token.nextToken().replace("\"", "").trim();
						String  kouda = token.nextToken().replace("\"", "").trim();

						System.out.println("=====1");
						statement.setString (1,hinb);
						statement.setString (2, hinm);
						statement.setString (3, hinr);
						statement.setString (4, thin);
						statement.setString (5, zaikb);
						statement.setString (6, size1);
						statement.setString (7, size2);
						statement.setString (8, size3);
						statement.setString (9, size4);
						statement.setString (10, syor);
						statement.setString (11, forsiwt);
						statement.setString (12, seno);
						statement.setString (13, tkcd);
						statement.setString (14, sybe1);
						statement.setString (15, sybe2);
						statement.setString (16, bunr);
						statement.setString (17, sktk);
						statement.setString (18, tehk);
						statement.setString (19, senk);
						statement.setString (20, zaik);
						statement.setString (21, gjik);
						statement.setString (22, tefk);
						statement.setString (23, kaek);
						statement.setString (24, sgyk);
						statement.setString (25, kenk);
						statement.setString (26, tani);
						statement.setString (27, hinc);
						statement.setString (28, kata);
						statement.setString (29, zuno);
						statement.setString (30, ords);
						statement.setString (31, soub);
						statement.setString (32, tana);
						statement.setString (33, hack);
						statement.setString (34, hosk);
						statement.setString (35, sosk);
						statement.setString (36, bumn);
						statement.setString (37, hgyc);
						statement.setString (38, hgyc2);
						statement.setString (39, nony);
						statement.setString (40, tyok);
						statement.setString (41, yumu);
						statement.setString (42, lotk);
						statement.setString (43, lots);
						statement.setString (44, zobs);
						statement.setString (45, mart);
						statement.setString (46, bdom);
						statement.setString (47, seil);
						statement.setString (48, anzl);
						statement.setString (49, kenl);
						statement.setString (50, sklt);
						statement.setString (51, cyc1);
						statement.setString (52, cyc2);
						statement.setString (53, cyc3);
						statement.setString (54, bumns);
						statement.setString (55, hako);
						statement.setString (56, syuy);
						statement.setString (57, haks);
						statement.setString (58, hyot);
						statement.setString (59, seis);
						statement.setString (60, ksyk);
						statement.setString (61, ksyj);
						statement.setString (62, genz);
						statement.setString (63, hkiz);
						statement.setString (64, mhkz);
						statement.setString (65, hraz);
						statement.setString (66, linz);
						statement.setString (67, skyz);
						statement.setString (68, anzz);
						statement.setString (69, nykbi);
						statement.setString (70, nykbo);
						statement.setString (71, conti);
						statement.setString (72, conto);
						statement.setString (73, minz);
						statement.setString (74, azct);
						statement.setString (75, tnjb);
						statement.setString (76, tnjs);
						statement.setString (77, tnsa);
						statement.setString (78, tnks);
						statement.setString (79, tnzs);
						statement.setString (80, suryz);
						statement.setString (81, mhkzz);
						statement.setString (82, hkizz);
						statement.setString (83, linzz);
						statement.setString (84, skyzz);
						statement.setString (85, suryt);
						statement.setString (86, mhkzt);
						statement.setString (87, hkizt);
						statement.setString (88, linzt);
						statement.setString (89, skyzt);
						statement.setString (90, nyuk);
						statement.setString (91, syukn);
						statement.setString (92, syutn);
						statement.setString (93, ukein);
						statement.setString (94, uketn);
						statement.setString (95, dnps);
						statement.setString (96, dnpn);
						statement.setString (97, lowc);
						statement.setString (98, hinba);
						statement.setString (99, surya);
						statement.setString (100, kouda);
						statement.setString (101,today);
						System.out.println("=====2");
						statement.executeUpdate();
						System.out.println("=====3");
						statement.close();
	                }
	                i++;
					System.out.println(i+ "件登録");
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

	//品目材料取込み
	public void importHinmokuZairyouCsv(FileReader fr, BufferedReader br, String today) throws SQLException {

		Connection connection = source.getConnection();

        String line;
        StringTokenizer token;


		try{

			//**m_hinmokuテーブル削除
	        PreparedStatement statementDelete = connection.prepareStatement(DELETE_M_HINMOKU_ZAIRYOU);
	        statementDelete.executeUpdate();
	        System.out.println("tanpin_zaiko insertCsv 1====  削除しました");
	        //**/

            int i=0;
			//読み込んだファイルを１行ずつ処理する
	            while ((line = br.readLine()) != null) {
	                //区切り文字","で分割する
	                token = new StringTokenizer(line, ",");

	                String size1="",size2="",size3="",size4="",forsiwt="", thin="", zaikb="", sunpTH="" ;

	                //分割した文字を画面出力する
	                while (token.hasMoreTokens()) {
						PreparedStatement statement = connection.prepareStatement(INSERT_M_HINMOKU_ZAIRYOU);

						String hinb2 =token.nextToken().replace("\"", "").trim();
						String tkcd =token.nextToken().replace("\"", "").trim();
						String sunp =token.nextToken().replace("\"", "").trim();
						System.out.println("品番= " + sunp);
						zaikb = "1" ;


						//右から数えて2つ目がTH(タイ用材料)表記があるとき
						int leng = sunp.length();
						if(sunp.substring(leng-2).equals("TH")){
							//タイコードTHを追加
							forsiwt = "TH";
							//TH除去、空白除去
							sunpTH =sunp.replace("TH", "").trim();
							sunpTH = sunpTH.replace("X", ",");

						}else if(sunp.substring(leng-2).equals("DM")){
							//タイコードTHを追加
							forsiwt = "DM";
							//TH除去、空白除去
							sunpTH =sunp.replace("DM", "").trim();
							sunpTH = sunpTH.replace("X", ",");

						}else {
							sunpTH = sunp.replace("X", ",");
						}

					        String[] strArray = sunpTH.split(",");
					        if(strArray.length ==1){
					        	size1 = strArray[0];
					    		System.out.println(size1);
					        }else if(strArray.length ==2){
					        	size1 = strArray[0];
					        	size2 = strArray[1];
					    		System.out.println(size1);
					    		System.out.println(size2);
					        }else if(strArray.length ==3){
					        	size1 = strArray[0];
					        	size2 = strArray[1];
					        	size3 = strArray[2];
					    		System.out.println(size1);
					    		System.out.println(size2);
					    		System.out.println(size3);
					        }else if(strArray.length ==4){
					        	size1 = strArray[0];
					        	size2 = strArray[1];
					        	size3 = strArray[2];
					        	size4 = strArray[3];
					    		System.out.println(size1);
					    		System.out.println(size2);
					    		System.out.println(size3);
					    		System.out.println(size4);
					        }else{
					        	size1 = strArray[0];
					        	size2 = strArray[1];
					        	size3 = strArray[2];
					        	size4 = strArray[3];
					    		System.out.println(size1);
					    		System.out.println(size2);
					    		System.out.println(size3);
					    		System.out.println(size4);
					        }

						String  zais =token.nextToken().replace("\"", "").trim();
						String  syor =token.nextToken().replace("\"", "").trim();
						hinb2 = tkcd + sunp + zais + syor ;
						hinb2 = hinb2.replace(" ", "");
						String  jyur =token.nextToken().replace("\"", "").trim();
						String  tkan =token.nextToken().replace("\"", "").trim();
						String  zaik =token.nextToken().replace("\"", "").trim();
						String  hgyc =token.nextToken().replace("\"", "").trim();
						String  msry =token.nextToken().replace("\"", "").trim();
						String  mart =token.nextToken().replace("\"", "").trim();
						String  syuy =token.nextToken().replace("\"", "").trim();
						String  kata =token.nextToken().replace("\"", "").trim();
						String  biko =token.nextToken().replace("\"", "").trim();
						String  rcno =token.nextToken().replace("\"", "").trim();
						String  anzz =token.nextToken().replace("\"", "").trim();
						String  ykk1 =token.nextToken().replace("\"", "").trim();
						String  ykk2 =token.nextToken().replace("\"", "").trim();
						String  wsno =token.nextToken().replace("\"", "").trim();
						String  reki =token.nextToken().replace("\"", "").trim();
						String  time =token.nextToken().replace("\"", "").trim();


						System.out.println("=====1");
						statement.setString (1,tkcd);
						statement.setString(2, sunp);
						statement.setString(3,zaikb);
						statement.setString(4, size1);
						statement.setString(5, size2);
						statement.setString (6, size3);
						statement.setString(7, size4);
						statement.setString(8, forsiwt);
						statement.setString(9, zais);
						statement.setString(10, hinb2);
						statement.setString(11, syor);
						statement.setString(12, thin);
						statement.setString(13, jyur);
						statement.setString(14, tkan);
						statement.setString(15, zaik);
						statement.setString(16, hgyc);
						statement.setString(17, msry);
						statement.setString(18, mart);
						statement.setString(19, syuy);
						statement.setString(20, kata);
						statement.setString(21, biko);
						statement.setString(22, rcno);
						statement.setString(23, anzz);
						statement.setString(24, ykk1);
						statement.setString(25, ykk2);
						statement.setString(26, wsno);
						statement.setString(27, reki);
						statement.setString(28, time);
						statement.setString(29, today);

						System.out.println("=====2");
						statement.executeUpdate();
						System.out.println("=====3");
						statement.close();
	                }
	                i++;
					System.out.println(i+ "件登録");
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

	//品目構成マスターインポート
	public void importHinmokuKouseiCsv(FileReader fr, BufferedReader br, String today) throws SQLException {

		Connection connection = source.getConnection();

        String line;
        StringTokenizer token;

		try{

			//**m_kouseiテーブル削除
	        PreparedStatement statementDelete = connection.prepareStatement(DELETE_M_KOUSEI);
	        statementDelete.executeUpdate();
	        System.out.println("tanpin_zaiko insertCsv 1====  削除しました");
	        //**/

            int i=0;
			//読み込んだファイルを１行ずつ処理する
	            while ((line = br.readLine()) != null) {
	                //区切り文字","で分割する
	                token = new StringTokenizer(line, ",");

	                //分割した文字を画面出力する
	                while (token.hasMoreTokens()) {
						PreparedStatement statement = connection.prepareStatement(INSERT_M_KOUSEI);

						String hinb1 =token.nextToken().replace("\"", "").trim();
						String hinb2 =token.nextToken().replace("\"", "").trim();
						String gijk =token.nextToken().replace("\"", "").trim();
						String genti =token.nextToken().replace("\"", "").trim();
						String gentz =token.nextToken().replace("\"", "").trim();
						String seil =token.nextToken().replace("\"", "").trim();
						String furr =token.nextToken().replace("\"", "").trim();
						String opcd =token.nextToken().replace("\"", "").trim();
						String sikk =token.nextToken().replace("\"", "").trim();
						String kous =token.nextToken().replace("\"", "").trim();
						String syuk =token.nextToken().replace("\"", "").trim();
						String sepk =token.nextToken().replace("\"", "").trim();
						String sitb1 =token.nextToken().replace("\"", "").trim();
						String sitb2 =token.nextToken().replace("\"", "").trim();
						String sepb1 =token.nextToken().replace("\"", "").trim();
						String sepb2 =token.nextToken().replace("\"", "").trim();
						String yumu =token.nextToken().replace("\"", "").trim();
						String ykbn1 =token.nextToken().replace("\"", "").trim();
						String ykbn2 =token.nextToken().replace("\"", "").trim();
						String ysp1 =token.nextToken().replace("\"", "").trim();
						String ysp2 =token.nextToken().replace("\"", "").trim();
						String reki =token.nextToken().replace("\"", "").trim();

						System.out.println("=====1");
						statement.setString(1,hinb1);
						statement.setString(2, hinb2);
						statement.setString(3, gijk);
						statement.setString(4, genti);
						statement.setString(5, gentz);
						statement.setString(6, seil);
						statement.setString(7, furr);
						statement.setString(8, opcd);
						statement.setString(9, sikk);
						statement.setString(10, kous);
						statement.setString(11, syuk);
						statement.setString(12, sepk);
						statement.setString(13, sitb1);
						statement.setString(14, sitb2);
						statement.setString(15, sepb1);
						statement.setString(16, sepb2);
						statement.setString(17, yumu);
						statement.setString(18, ykbn1);
						statement.setString(19, ykbn2);
						statement.setString(20, ysp1);
						statement.setString(21, ysp2);
						statement.setString(22, reki);
						statement.setString(23, today);
						System.out.println("=====2");
						statement.executeUpdate();
						System.out.println("=====3");
						statement.close();
	                }
	                System.out.println(i+ "件登録");
	                i++;
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


	//材料構成インポート
	public void importZairyouKouseiCsv(FileReader fr2, BufferedReader br2, String today) throws SQLException {

		Connection connection = source.getConnection();

        String line;
        StringTokenizer token;

		try{

            int i=0;
			//読み込んだファイルを１行ずつ処理する
	            while ((line = br2.readLine()) != null) {
	                //区切り文字","で分割する
	                token = new StringTokenizer(line, ",");

	                //分割した文字を画面出力する
	                while (token.hasMoreTokens()) {
						PreparedStatement statement = connection.prepareStatement(INSERT_M_KOUSEI);

						String hinb1 =token.nextToken().replace("\"", "").trim();
						String hinb2 =token.nextToken().replace("\"", "").trim();

						hinb2 = hinb2.replace(" ", "");
						HinmokuBean rcno = new HinmokuBean();

						PreparedStatement statement2 = connection.prepareStatement("select * from m_hinmoku_zairyou where hinb2='" + hinb2 + "'");
						ResultSet result = statement2.executeQuery();

						while (result.next()){
							rcno.setRcno(result.getString("rcno"));
							System.out.println(rcno.getRcno());
							hinb2 =rcno.getRcno();
						}

						//不要データ
						String dle1 = token.nextToken().replace("\"", "").trim();
						String dle2 = token.nextToken().replace("\"", "").trim();
						String dle3 = token.nextToken().replace("\"", "").trim();
						String dle4 = token.nextToken().replace("\"", "").trim();
						String dle5 = token.nextToken().replace("\"", "").trim();

						String genti =token.nextToken().replace("\"", "").trim();
						String gentz =token.nextToken().replace("\"", "").trim();

						//不要データ
						String dle6 = token.nextToken().replace("\"", "").trim();
						String dle7 = token.nextToken().replace("\"", "").trim();
						String dle8 = token.nextToken().replace("\"", "").trim();
						String dle9 = token.nextToken().replace("\"", "").trim();

						String seil =token.nextToken().replace("\"", "").trim();
						String sepb1 =token.nextToken().replace("\"", "").trim();
						String sepb2 =token.nextToken().replace("\"", "").trim();
						String gijk =token.nextToken().replace("\"", "").trim();
						if(gijk.equals("")){
							gijk = "0";
						}else{}

						//不要データ
						String dle10 = token.nextToken().replace("\"", "").trim();
						String dle11 = token.nextToken().replace("\"", "").trim();

						String reki =token.nextToken().replace("\"", "").trim();

						//不要データ
						String dle12 = token.nextToken().replace("\"", "").trim();

						String furr = "0";
						String opcd = "";
						String sikk = "";
						String kous = "0";
						String syuk = "";
						String sepk = "";
						String sitb1 = "";
						String sitb2 = "";
						String yumu = "";
						String ykbn1 = "";
						String ykbn2 = "";
						String ysp1 = "";
						String ysp2 = "";

						System.out.println("=====1");
						statement.setString(1,hinb1);
						statement.setString(2, hinb2);
						statement.setString(3, gijk);
						statement.setString(4, genti);
						statement.setString(5, gentz);
						statement.setString(6, seil);
						statement.setString(7, furr);
						statement.setString(8, opcd);
						statement.setString(9, sikk);
						statement.setString(10, kous);
						statement.setString(11, syuk);
						statement.setString(12, sepk);
						statement.setString(13, sitb1);
						statement.setString(14, sitb2);
						statement.setString(15, sepb1);
						statement.setString(16, sepb2);
						statement.setString(17, yumu);
						statement.setString(18, ykbn1);
						statement.setString(19, ykbn2);
						statement.setString(20, ysp1);
						statement.setString(21, ysp2);
						statement.setString(22, reki);
						statement.setString(23, today);
						System.out.println("=====2");
						statement.executeUpdate();
						System.out.println("=====3");
						statement.close();
	                }
	                System.out.println(i+ "件登録");
	                i++;
	            }

            //終了処理
            br2.close();
            System.out.println("tanpin_zaiko insertCsv 2====  登録しました");

        } catch (IOException ex) {
            //例外発生時処理
            ex.printStackTrace();
        } catch(Exception e){

        }
	}

	//買単価インポート
	public void importkaitankaCsv(FileReader fr, BufferedReader br, String today) throws SQLException {

		Connection connection = source.getConnection();

        String line;
        StringTokenizer token;

		try{

			//**m_kouseiテーブル削除
	        PreparedStatement statementDelete = connection.prepareStatement(DELETE_M_KAITANKA);
	        statementDelete.executeUpdate();
	        System.out.println("tanpin_zaiko insertCsv 1====  削除しました");
	        //**/

            int i=0;
			//読み込んだファイルを１行ずつ処理する
	            while ((line = br.readLine()) != null) {
	                //区切り文字","で分割する
	                token = new StringTokenizer(line, ",");

	                //分割した文字を画面出力する
	                while (token.hasMoreTokens()) {
						PreparedStatement statement = connection.prepareStatement(INSERT_M_KAITANKA);

						String hgyc = token.nextToken().replace("\"", "").trim();
						String  hinb = token.nextToken().replace("\"", "").trim();
						String  tank = token.nextToken().replace("\"", "").trim();
						String  ktan1 = token.nextToken().replace("\"", "").trim();
						String  shiz = token.nextToken().replace("\"", "").trim();
						String  ehiz = token.nextToken().replace("\"", "").trim();
						String  tkcd = token.nextToken().replace("\"", "").trim();
						String  hinm = token.nextToken().replace("\"", "").trim();
						String  kata = token.nextToken().replace("\"", "").trim();
						String  thiz = token.nextToken().replace("\"", "").trim();
						String  ktan2 = token.nextToken().replace("\"", "").trim();
						String  ysp1 = token.nextToken().replace("\"", "").trim();
						String  ykk1 = token.nextToken().replace("\"", "").trim();
						String  ykk2 = token.nextToken().replace("\"", "").trim();


						System.out.println("=====1");
						statement.setString (1,hgyc);
						statement.setString(2, hinb);
						statement.setString(3, tank);
						statement.setString(4, ktan1);
						statement.setString(5, shiz);
						statement.setString (6, ehiz);
						statement.setString(7, tkcd);
						statement.setString(8, hinm);
						statement.setString(9, kata);
						statement.setString(10, thiz);
						statement.setString(11, ktan2);
						statement.setString(12, ysp1);
						statement.setString(13, ykk1);
						statement.setString(14, ykk2);
						statement.setString(15, today);

						System.out.println("=====2");
						statement.executeUpdate();
						System.out.println("=====3");
						statement.close();
	                }
	                System.out.println(i+ "件登録");
	                i++;
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

	//材料回単価インポート
	public void importkaitankaZairyouCsv(FileReader fr, BufferedReader br, String today) throws SQLException {

		Connection connection = source.getConnection();

        String line;
        StringTokenizer token;

		try{

			//**m_kouseiテーブル削除
	        PreparedStatement statementDelete = connection.prepareStatement(DELETE_M_KAITANKA_ZAIRYOU);
	        statementDelete.executeUpdate();
	        System.out.println("tanpin_zaiko insertCsv 1====  削除しました");
	        //**/

            int i=0;
			//読み込んだファイルを１行ずつ処理する
	            while ((line = br.readLine()) != null) {
	                //区切り文字","で分割する
	                token = new StringTokenizer(line, ",");

	                //分割した文字を画面出力する
	                while (token.hasMoreTokens()) {
						PreparedStatement statement = connection.prepareStatement(INSERT_M_KAITANKA_ZAIRYOU);

						String hgyc = token.nextToken().replace("\"", "").trim();
						String  tkcd = token.nextToken().replace("\"", "").trim();
						String  sunp = token.nextToken().replace("\"", "").trim();
						String  zais = token.nextToken().replace("\"", "").trim();
						String  syor = token.nextToken().replace("\"", "").trim();
						String  ztan = token.nextToken().replace("\"", "").trim();
						String  tank = token.nextToken().replace("\"", "").trim();
						String  shiz = token.nextToken().replace("\"", "").trim();
						String  ehiz = token.nextToken().replace("\"", "").trim();
						String  rcno = token.nextToken().replace("\"", "").trim();


						String hinb2 = tkcd + sunp + zais + syor;
						hinb2= hinb2.replace(" ","");
						System.out.println(hinb2);

						HinmokuBean rcnoZai = new HinmokuBean();

						System.out.println("====1");
						PreparedStatement statement2 = connection.prepareStatement("select * from m_hinmoku_zairyou where hinb2='" + hinb2 + "'");
						ResultSet result = statement2.executeQuery();

						while (result.next()){
							rcnoZai.setRcnoZai(result.getString("rcno"));
							System.out.println(rcnoZai.getRcnoZai());
							rcno =rcnoZai.getRcnoZai();
						}

						String  ykk1 = token.nextToken().replace("\"", "").trim();
						String  ykk2 = token.nextToken().replace("\"", "").trim();
						String  kata = token.nextToken().replace("\"", "").trim();
						String  reki = token.nextToken().replace("\"", "").trim();
						String  time = token.nextToken().replace("\"", "").trim();
						String  zmjyur = token.nextToken().replace("\"", "").trim();
						System.out.println(zmjyur);
						String  zmhgyc = token.nextToken().replace("\"", "").trim();
						System.out.println(zmhgyc);

						System.out.println("=====1");
						statement.setString (1,hgyc);
						statement.setString(2, tkcd);
						statement.setString(3, sunp);
						statement.setString(4, zais);
						statement.setString (5, syor);
						statement.setString(6, ztan);
						statement.setString(7, tank);
						statement.setString(8, shiz);
						statement.setString(9, ehiz);
						statement.setString(10, rcno);
						statement.setString(11, ykk1);
						statement.setString(12, ykk2);
						statement.setString(13, kata);
						statement.setString(14, reki);
						statement.setString(15, time);
						statement.setString(16, zmjyur);
						statement.setString(17, zmhgyc);
						statement.setString(18, today);
						System.out.println("=====2");
						statement.executeUpdate();
						System.out.println("=====3");
						statement.close();
	                }
	                System.out.println(i+ "件登録");
	                i++;
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

	//売単価インポート
	public void importUritankaCsv(FileReader fr, BufferedReader br, String today) throws SQLException {

		Connection connection = source.getConnection();

        String line;
        StringTokenizer token;

		try{

			//**m_kouseiテーブル削除
	        PreparedStatement statementDelete = connection.prepareStatement(DELETE_M_URITANKA);
	        statementDelete.executeUpdate();
	        System.out.println("tanpin_zaiko insertCsv 1====  削除しました");
	        //**/

            int i=0;
			//読み込んだファイルを１行ずつ処理する
	            while ((line = br.readLine()) != null) {
	                //区切り文字","で分割する
	                token = new StringTokenizer(line, ",");

	                //分割した文字を画面出力する
	                while (token.hasMoreTokens()) {
						PreparedStatement statement = connection.prepareStatement(INSERT_M_URITANKA);

						String sybe = token.nextToken().replace("\"", "").trim();
						String  hinb = token.nextToken().replace("\"", "").trim();
						String  tkcd = token.nextToken().replace("\"", "").trim();
						String  ysp1 = token.nextToken().replace("\"", "").trim();
						String  utan1 = token.nextToken().replace("\"", "").trim();
						String  utan2 = token.nextToken().replace("\"", "").trim();
						String  utan3 = token.nextToken().replace("\"", "").trim();
						String  utan4 = token.nextToken().replace("\"", "").trim();
						String  btan = token.nextToken().replace("\"", "").trim();
						String  ptan = token.nextToken().replace("\"", "").trim();
						String  ktan = token.nextToken().replace("\"", "").trim();
						String  ytan1 = token.nextToken().replace("\"", "").trim();
						String  ytan2 = token.nextToken().replace("\"", "").trim();
						String  ytan3 = token.nextToken().replace("\"", "").trim();
						String  ytan4 = token.nextToken().replace("\"", "").trim();
						String  ysp2 = token.nextToken().replace("\"", "").trim();
						String  shiz = token.nextToken().replace("\"", "").trim();
						String  ehiz = token.nextToken().replace("\"", "").trim();
						String  ykk1 = token.nextToken().replace("\"", "").trim();
						String  ykk2 = token.nextToken().replace("\"", "").trim();
						String  ykk3 = token.nextToken().replace("\"", "").trim();
						String  tank = token.nextToken().replace("\"", "").trim();
						String  ysry = token.nextToken().replace("\"", "").trim();
						String  jsry = token.nextToken().replace("\"", "").trim();
						String  ysp3 = token.nextToken().replace("\"", "").trim();
						String  hhiz = token.nextToken().replace("\"", "").trim();
						String  heno = token.nextToken().replace("\"", "").trim();
						String  keno = token.nextToken().replace("\"", "").trim();
						String  thiz = token.nextToken().replace("\"", "").trim();
						String  ysp4 = token.nextToken().replace("\"", "").trim();

						System.out.println(sybe);
						System.out.println( hinb);
						System.out.println( tkcd);
						System.out.println( ysp1);
						System.out.println( utan1);
						System.out.println( utan2);
						System.out.println( utan3);
						System.out.println( utan4);
						System.out.println( btan);
						System.out.println( ptan);
						System.out.println( ktan);
						System.out.println( ytan1);
						System.out.println( ytan2);
						System.out.println( ytan3);
						System.out.println( ytan4);
						System.out.println( ysp2);
						System.out.println( shiz);
						System.out.println( ehiz);
						System.out.println( ykk1);
						System.out.println( ykk2);
						System.out.println( ykk3);
						System.out.println( tank);
						System.out.println( ysry);
						System.out.println( jsry);
						System.out.println( ysp3);
						System.out.println( hhiz);
						System.out.println( heno);
						System.out.println( keno);
						System.out.println( thiz);
						System.out.println( ysp4);

						System.out.println("=====1");
						statement.setString (1,sybe);
						statement.setString(2, hinb);
						statement.setString(3, tkcd);
						statement.setString(4, ysp1);
						statement.setString (5, utan1);
						statement.setString(6, utan2);
						statement.setString(7, utan3);
						statement.setString(8, utan4);
						statement.setString(9, btan);
						statement.setString(10, ptan);
						statement.setString(11, ktan);
						statement.setString(12, ytan1);
						statement.setString(13, ytan2);
						statement.setString(14, ytan3);
						statement.setString(15, ytan4);
						statement.setString(16, ysp2);
						statement.setString(17, shiz);
						statement.setString(18, ehiz);
						statement.setString(19, ykk1);
						statement.setString(20, ykk2);
						statement.setString(21, ykk3);
						statement.setString(22, tank);
						statement.setString(23, ysry);
						statement.setString(24, jsry);
						statement.setString(25, ysp3);
						statement.setString(26, hhiz);
						statement.setString(27, heno);
						statement.setString(28, keno);
						statement.setString(29, thiz);
						statement.setString(30, ysp4);
						statement.setString(31,today);
						System.out.println("=====2");
						statement.executeUpdate();
						System.out.println("=====3");
						statement.close();
	                }
	                System.out.println(i+ "件登録");
	                i++;
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


