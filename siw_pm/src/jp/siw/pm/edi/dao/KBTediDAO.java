package jp.siw.pm.edi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jp.siw.pm.edi.bean.CsvImportBean;
import jp.siw.pm.edi.bean.KBTItemBean;
import jp.siw.pm.edi.bean.KBTNvanBean;

public class KBTediDAO{
	 private static final String INSERT  = "insert into kb_35_hibetsunaiji values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
	 											+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
	 											+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, "
	 											+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 private static final String SELECT_insertDay = "select * from kb_35_hibetsunaiji where insertDay=?";
	 private static final String SELECT_insymd = "select * from t_juchu_test where insymd=?";
	 private static final String SELECT_hinban = "select hinban from t_juchu_test group by hinban";
	 private static final String SELECT_day = "select insymd from t_juchu_test group by insymd";
	 private static final String SELECT_naiji_ins_day = "select insymd from t_juchu_test where naikaku_kb='3' group by insymd order by insymd desc limit 0,6"; //insymdを降順に6行分取得
	 private static final String SELECT_order = "SELECT * FROM("
	 											+ "SELECT hinban, nounyushiji_ymd, noba_cd, "
	 											+ "SUM(CASE WHEN insymd= ? THEN CASE WHEN naikaku_kb='3' THEN juchu_su ELSE 0 END ELSE 0 END) AS qty1, "
	 											+ "SUM(CASE WHEN insymd= ? THEN CASE WHEN naikaku_kb='3' THEN juchu_su ELSE 0 END ELSE 0 END) AS qty2, "
	 											+ "SUM(CASE WHEN insymd= ? THEN CASE WHEN naikaku_kb='3' THEN juchu_su ELSE 0 END ELSE 0 END) AS qty3, "
	 											+ "SUM(CASE WHEN naikaku_kb='5' THEN juchu_su ELSE 0 END) AS final_qty "
	 											+ "FROM t_juchu_test WHERE hinban= ? AND nounyushiji_ymd >= ? "
	 											+ "GROUP BY nounyushiji_ymd, noba_cd) AS X WHERE qty1 !=0 OR qty2 !=0 OR qty3 !=0 OR final_qty !=0 ";
	 private static final String INSERT_juchu = "insert into t_juchu_test values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 private static final String INSERT_45 = "insert into kb_45_nounyushiji values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 private static final String INSERT_46 = "insert into kb_46_hachuyoteirenraku values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 private static final String INSERT_47 = "insert into kb_47_hojuchumon values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 private static final String SELECT_45 = "select * from kb_45_nounyushiji where insertDay=?";
	 private static final String SELECT_46 = "select * from kb_46_hachuyoteirenraku where insertDay=?";
	 private static final String SELECT_47 = "select * from kb_47_hojuchumon where insertDay=?";
	 private static final String INSERT_juchu2  = "insert into t_juchu_test values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 private static final String SELECT_sabun = "SELECT * FROM(SELECT hinban, nounyushiji_ymd, noba_cd, "
	 											+ "SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) AS qty1, "
	 											+ "SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) AS qty2, "
	 											+ "(SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) - SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) ) AS su_sa  "
	 											+ "FROM t_juchu_test WHERE hinban=? AND nounyushiji_ymd >=? AND naikaku_kb='3' GROUP BY nounyushiji_ymd, noba_cd) AS X WHERE qty1 !=0 OR qty2 !=0 ";
	 private static final String SELECT_tsuika_juchu ="SELECT * FROM t_juchu_test WHERE hinban=? AND nounyushiji_ymd>=? AND naikaku_kb='5' AND add_kb='1' ";
	 private static final String SELECT_tanpin_zaiko ="SELECT * FROM tanpin_zaiko WHERE hinb=? AND nony='0700'";
	 private static final String SELECT_Kikan_Zaiko ="SELECT SUM(total_qty) AS Total_Qty FROM("
	 											+ "SELECT sum(juchu_su) AS total_qty FROM `t_juchu_test` "
	 											+ "WHERE hinban=? AND insymd=? AND naikaku_kb='3' AND nounyushiji_ymd BETWEEN ? AND ? "
	 											+ "UNION ALL "
	 											+ "SELECT sum(juchu_su) AS total_qty FROM `t_juchu_test` "
	 											+ "WHERE hinban=? AND nounyushiji_ymd BETWEEN ? AND ? AND add_kb='1') AS X";
	 private static final String SELECT_sabunAll = "SELECT hinban, nounyushiji_ymd, noba_cd, "
												+ "SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) AS qty1, "
												+ "SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) AS qty2, "
												+ "(SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) - SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) ) AS su_sa  "
												+ "FROM t_juchu_test WHERE naikaku_kb='3' GROUP BY hinban,  nounyushiji_ymd, noba_cd";
	 private static final String INSERT_sabun_all = "insert into kb_sabun_all(hinban, nounyushiji_ymd, noba_cd, qty1, qty2, su_sa, insertDay, insertTime) values(?, ?, ?, ?, ?, ?, ?, ?)";
	 private static final String DELETE_sabun_all = "DELETE FROM kb_sabun_all";
	 private static final String SELECT_sabun_hinban = "select hinban from kb_sabun_all group by hinban";
	 private static final String SELECT_sabun_all = "select * from kb_sabun_all group by hinban";
	 private static final String INSERT_sabun_aggr = "insert into kb_sabun_aggr values(?, ?, ?, ?)";
	 private static final String DELETE_sabun_aggr = "DELETE FROM kb_sabun_aggr";
	 private static final String SELECT_sabun_aggr = "SELECT * FROM kb_sabun_aggr";
	 private static final String SELECT_sabun_aggr2 = "SELECT * FROM kb_sabun_aggr WHERE nounyushiji_ymd BETWEEN ? AND ? AND sasu<0 GROUP BY hinban";
	 private static final String SELECT_sabun_aggr3 = "SELECT * FROM kb_sabun_aggr WHERE nounyushiji_ymd BETWEEN ? AND ? AND sasu<0";
	 private static final String INSERT_kabusoku = "INSERT INTO kabusoku(hinban, mysry, kikansry, kabusoku)"
	 											+ "SELECT hinban, mysry, SUM(total_qty), (mysry-SUM(total_qty)) FROM("
	 											+ "SELECT hinban, juchu_su AS total_qty FROM `t_juchu_test` "
	 											+ "WHERE insymd=? AND naikaku_kb='3' AND nounyushiji_ymd BETWEEN ? AND ? "
	 											+ "UNION ALL "
	 											+ "SELECT hinban, juchu_su AS total_qty FROM `t_juchu_test` "
	 											+ "WHERE nounyushiji_ymd BETWEEN ? AND ? AND add_kb='1') AS X "
	 											+ "INNER JOIN tanpin_zaiko ON hinban = hinb "
	 											+ "WHERE nony='0700' GROUP BY hinban";
	 private static final String DELETE_kabusoku = "DELETE FROM kabusoku";
	 private static final String SELECT_No_target = "SELECT hinban, SUM(juchu_su) AS gokei_su FROM t_juchu_test "
	 											+ "WHERE NOT EXISTS(SELECT 1 FROM tanpin_zaiko "
	 											+ "WHERE t_juchu_test.hinban=tanpin_zaiko.hinb) AND "
	 											+ "insymd=? AND nounyushiji_ymd BETWEEN ? AND ? ";
	 private static final String SELECT_kabusoku = "SELECT * FROM kabusoku";
	 private DataSource source;

	 public KBTediDAO() throws NamingException {
		 InitialContext context = new InitialContext();
		 source = (DataSource) context.lookup("java:comp/env/jdbc/datasource");

	 }

	 public void insertItem(int kanri_no, String kb_no, String kb_kojo, String kb_torihiki, String kb_nitei_line_at, String kb_keisan_dt, String kb_sei_kan_dt35, String kb_kojo_henko, String kb_hin_ku,
							 String kb_hin_ban, String kb_ac, String kb_kakonai, String kb_hin_name, String kb_noba, String kb_hakoshu, String kb_shuyo_su, String kb_matome_kb, String kb_keitai_kb, String kb_kensa_kb,
							 String kb_tehai_kb, String kb_teiki_hoju_kb, String kb_lead_time35, String kb_teiten_dt, String kb_shukka_kojo, String kb_tori_saki_hin, String kb_getsudo_shiki, String kb_kumi_getsudo,
							 String kb_hitsuyosu_gk, String kb_hitsuyosu_01, String kb_hitsuyosu_02, String kb_hitsuyosu_03, String kb_hitsuyosu_04, String kb_hitsuyosu_05, String kb_hitsuyosu_06, String kb_hitsuyosu_07,
							 String kb_hitsuyosu_08, String kb_hitsuyosu_09, String kb_hitsuyosu_10, String kb_hitsuyosu_11, String kb_hitsuyosu_12, String kb_hitsuyosu_13, String kb_hitsuyosu_14, String kb_hitsuyosu_15,
							 String kb_hitsuyosu_16, String kb_hitsuyosu_17, String kb_hitsuyosu_18, String kb_hitsuyosu_19, String kb_hitsuyosu_20, String kb_hitsuyosu_21, String kb_hitsuyosu_22, String kb_hitsuyosu_23,
							 String kb_hitsuyosu_24, String kb_hitsuyosu_25, String kb_hitsuyosu_26, String kb_hitsuyosu_27, String kb_hitsuyosu_28, String kb_hitsuyosu_29, String kb_hitsuyosu_30, String kb_hitsuyosu_31,
							 String kb_shiji_yohaku, String kb_shiji_dt_01, String kb_shiji_dt_02, String kb_shiji_dt_03, String kb_shiji_dt_04, String kb_shiji_dt_05, String kb_shiji_dt_06, String kb_shiji_dt_07,
							 String kb_shiji_dt_08, String kb_shiji_dt_09, String kb_shiji_dt_10, String kb_shiji_dt_11, String kb_shiji_dt_12, String kb_shiji_dt_13, String kb_shiji_dt_14, String kb_shiji_dt_15,
							 String kb_shiji_dt_16, String kb_shiji_dt_17, String kb_shiji_dt_18, String kb_shiji_dt_19, String kb_shiji_dt_20, String kb_shiji_dt_21, String kb_shiji_dt_22, String kb_shiji_dt_23,
							 String kb_shiji_dt_24, String kb_shiji_dt_25, String kb_shiji_dt_26, String kb_shiji_dt_27, String kb_shiji_dt_28, String kb_shiji_dt_29, String kb_shiji_dt_30, String kb_shiji_dt_31,
							 String kb_shiji_su_gk, String kb_shiji_su_01, String kb_shiji_su_02, String kb_shiji_su_03, String kb_shiji_su_04, String kb_shiji_su_05, String kb_shiji_su_06, String kb_shiji_su_07,
							 String kb_shiji_su_08, String kb_shiji_su_09, String kb_shiji_su_10, String kb_shiji_su_11, String kb_shiji_su_12, String kb_shiji_su_13, String kb_shiji_su_14, String kb_shiji_su_15,
							 String kb_shiji_su_16, String kb_shiji_su_17, String kb_shiji_su_18, String kb_shiji_su_19, String kb_shiji_su_20, String kb_shiji_su_21, String kb_shiji_su_22, String kb_shiji_su_23,
							 String kb_shiji_su_24, String kb_shiji_su_25, String kb_shiji_su_26, String kb_shiji_su_27, String kb_shiji_su_28, String kb_shiji_su_29, String kb_shiji_su_30, String kb_shiji_su_31,
							 String insertDay, String insertTime)

					 throws SQLException {
		 Connection connection = source.getConnection();

		 try {

			 //品番に"-"ハイフンを挿入"XXXXX-XXXX-X"の形へ
			 StringBuilder hin_ban = new StringBuilder();
			 hin_ban.append(kb_hin_ban);
			 hin_ban.insert(5, "-");
			 hin_ban.insert(10, "-");
			 String hinban = new String(hin_ban);

			 //指示日フォーマット変更 ymmdd -> yyyymmdd
			 int year = 0;
			 String hiduke = null;
			 String[] shiji_dt = new String[]{kb_shiji_dt_01, kb_shiji_dt_02, kb_shiji_dt_03, kb_shiji_dt_04 ,kb_shiji_dt_05, kb_shiji_dt_06, kb_shiji_dt_07, kb_shiji_dt_08, kb_shiji_dt_09, kb_shiji_dt_10,
								 kb_shiji_dt_11, kb_shiji_dt_12, kb_shiji_dt_13, kb_shiji_dt_14, kb_shiji_dt_15,	kb_shiji_dt_16, kb_shiji_dt_17, kb_shiji_dt_18, kb_shiji_dt_19, kb_shiji_dt_20,	kb_shiji_dt_21,
								 kb_shiji_dt_22, kb_shiji_dt_23, kb_shiji_dt_24, kb_shiji_dt_25, kb_shiji_dt_26, kb_shiji_dt_27, kb_shiji_dt_28, kb_shiji_dt_29,	kb_shiji_dt_30, kb_shiji_dt_31};

			 for(int i=0; i<shiji_dt.length; i++){
				 if(Integer.parseInt((shiji_dt[i].substring(0)))==0){
					 shiji_dt[i] = ("00000");

				 }else if(Integer.parseInt(kb_keisan_dt.substring(1,2)) == Integer.parseInt((shiji_dt[i].substring(0,1)))){
					 year = Integer.parseInt(kb_keisan_dt.substring(0,2));
					 hiduke = shiji_dt[i].substring(1);
					 shiji_dt[i] = ("20"+year + hiduke);

				 }else if(Integer.parseInt(kb_keisan_dt.substring(2,4)) > Integer.parseInt((shiji_dt[i].substring(1,3)))){
					 year= Integer.parseInt(kb_keisan_dt.substring(0,2))+1;
					 hiduke = shiji_dt[i].substring(1);
					 shiji_dt[i] = ("20"+year + hiduke);

				 }else if(Integer.parseInt(kb_keisan_dt.substring(2,4)) < Integer.parseInt((shiji_dt[i].substring(1,3)))){
					 year= Integer.parseInt(kb_keisan_dt.substring(0,2))-1;
					 hiduke = shiji_dt[i].substring(1);
					 shiji_dt[i] = ("20"+year + hiduke);

				 }

			 }

			 PreparedStatement statement = connection.prepareStatement(INSERT);
			 statement.setInt(1, kanri_no);
			 statement.setString(2, kb_no);
			statement.setString(3, kb_kojo);
			statement.setString(4, kb_torihiki);
			statement.setString(5, kb_nitei_line_at);
			statement.setString(6, kb_keisan_dt);
			statement.setString(7, kb_sei_kan_dt35);
			statement.setString(8, kb_kojo_henko);
			statement.setString(9, kb_hin_ku);
			statement.setString(10, hinban);
			statement.setString(11, kb_ac);
			statement.setString(12, kb_kakonai);
			statement.setString(13, kb_hin_name);
			statement.setString(14, kb_noba);
			statement.setString(15, kb_hakoshu);
			statement.setString(16, kb_shuyo_su);
			statement.setString(17, kb_matome_kb);
			statement.setString(18, kb_keitai_kb);
			statement.setString(19, kb_kensa_kb);
			statement.setString(20, kb_tehai_kb);
			statement.setString(21, kb_teiki_hoju_kb);
			statement.setString(22, kb_lead_time35);
			statement.setString(23, kb_teiten_dt);
			statement.setString(24, kb_shukka_kojo);
			statement.setString(25, kb_tori_saki_hin);
			statement.setString(26, kb_getsudo_shiki);
			statement.setString(27, kb_kumi_getsudo);
			statement.setString(28, kb_hitsuyosu_gk);
			statement.setString(29, kb_hitsuyosu_01);
			statement.setString(30, kb_hitsuyosu_02);
			statement.setString(31, kb_hitsuyosu_03);
			statement.setString(32, kb_hitsuyosu_04);
			statement.setString(33, kb_hitsuyosu_05);
			statement.setString(34, kb_hitsuyosu_06);
			statement.setString(35, kb_hitsuyosu_07);
			statement.setString(36, kb_hitsuyosu_08);
			statement.setString(37, kb_hitsuyosu_09);
			statement.setString(38, kb_hitsuyosu_10);
			statement.setString(39, kb_hitsuyosu_11);
			statement.setString(40, kb_hitsuyosu_12);
			statement.setString(41, kb_hitsuyosu_13);
			statement.setString(42, kb_hitsuyosu_14);
			statement.setString(43, kb_hitsuyosu_15);
			statement.setString(44, kb_hitsuyosu_16);
			statement.setString(45, kb_hitsuyosu_17);
			statement.setString(46, kb_hitsuyosu_18);
			statement.setString(47, kb_hitsuyosu_19);
			statement.setString(48, kb_hitsuyosu_20);
			statement.setString(49, kb_hitsuyosu_21);
			statement.setString(50, kb_hitsuyosu_22);
			statement.setString(51, kb_hitsuyosu_23);
			statement.setString(52, kb_hitsuyosu_24);
			statement.setString(53, kb_hitsuyosu_25);
			statement.setString(54, kb_hitsuyosu_26);
			statement.setString(55, kb_hitsuyosu_27);
			statement.setString(56, kb_hitsuyosu_28);
			statement.setString(57, kb_hitsuyosu_29);
			statement.setString(58, kb_hitsuyosu_30);
			statement.setString(59, kb_hitsuyosu_31);
			statement.setString(60, kb_shiji_yohaku);
			statement.setString(61, shiji_dt[0]);
			statement.setString(62, shiji_dt[1]);
			statement.setString(63, shiji_dt[2]);
			statement.setString(64, shiji_dt[3]);
			statement.setString(65, shiji_dt[4]);
			statement.setString(66, shiji_dt[5]);
			statement.setString(67, shiji_dt[6]);
			statement.setString(68, shiji_dt[7]);
			statement.setString(69, shiji_dt[8]);
			statement.setString(70, shiji_dt[9]);
			statement.setString(71, shiji_dt[10]);
			statement.setString(72, shiji_dt[11]);
			statement.setString(73, shiji_dt[12]);
			statement.setString(74, shiji_dt[13]);
			statement.setString(75, shiji_dt[14]);
			statement.setString(76, shiji_dt[15]);
			statement.setString(77, shiji_dt[16]);
			statement.setString(78, shiji_dt[17]);
			statement.setString(79, shiji_dt[18]);
			statement.setString(80, shiji_dt[19]);
			statement.setString(81, shiji_dt[20]);
			statement.setString(82, shiji_dt[21]);
			statement.setString(83, shiji_dt[22]);
			statement.setString(84, shiji_dt[23]);
			statement.setString(85, shiji_dt[24]);
			statement.setString(86, shiji_dt[25]);
			statement.setString(87, shiji_dt[26]);
			statement.setString(88, shiji_dt[27]);
			statement.setString(89, shiji_dt[28]);
			statement.setString(90, shiji_dt[29]);
			statement.setString(91, shiji_dt[30]);
			statement.setString(92, kb_shiji_su_gk);
			statement.setString(93, kb_shiji_su_01);
			statement.setString(94, kb_shiji_su_02);
			statement.setString(95, kb_shiji_su_03);
			statement.setString(96, kb_shiji_su_04);
			statement.setString(97, kb_shiji_su_05);
			statement.setString(98, kb_shiji_su_06);
			statement.setString(99, kb_shiji_su_07);
			statement.setString(100, kb_shiji_su_08);
			statement.setString(101, kb_shiji_su_09);
			statement.setString(102, kb_shiji_su_10);
			statement.setString(103, kb_shiji_su_11);
			statement.setString(104, kb_shiji_su_12);
			statement.setString(105, kb_shiji_su_13);
			statement.setString(106, kb_shiji_su_14);
			statement.setString(107, kb_shiji_su_15);
			statement.setString(108, kb_shiji_su_16);
			statement.setString(109, kb_shiji_su_17);
			statement.setString(110, kb_shiji_su_18);
			statement.setString(111, kb_shiji_su_19);
			statement.setString(112, kb_shiji_su_20);
			statement.setString(113, kb_shiji_su_21);
			statement.setString(114, kb_shiji_su_22);
			statement.setString(115, kb_shiji_su_23);
			statement.setString(116, kb_shiji_su_24);
			statement.setString(117, kb_shiji_su_25);
			statement.setString(118, kb_shiji_su_26);
			statement.setString(119, kb_shiji_su_27);
			statement.setString(120, kb_shiji_su_28);
			statement.setString(121, kb_shiji_su_29);
			statement.setString(122, kb_shiji_su_30);
			statement.setString(123, kb_shiji_su_31);
			statement.setString(124, insertDay);
			statement.setString(125, insertTime);
			statement.executeUpdate();

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

	 }


	 //kb_35_hibetsunaijiからinsertDay=insymdにより一覧取得
	 public List<KBTItemBean>getItemListAll(String insertDay) throws SQLException {

		 List<KBTItemBean>itemList = new ArrayList<KBTItemBean> ();
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(SELECT_insertDay);
			 statement.setString(1, insertDay);
			 ResultSet result = statement.executeQuery();

			 while (result.next()) {
				 KBTItemBean item = new KBTItemBean();
				 item.setKanri_no(result.getInt("kanri_no"));
				 item.setKb_no(result.getString("kb_no"));
				 item.setKb_kojo(result.getString("kb_kojo"));
				 item.setKb_torihiki(result.getString("kb_torihiki"));
				 item.setKb_nitei_line_at(result.getString("kb_nitei_line_at"));
				 item.setKb_keisan_dt(result.getString("kb_keisan_dt"));
				 item.setKb_sei_kan_dt35(result.getString("kb_sei_kan_dt35"));
				 item.setKb_kojo_henko(result.getString("kb_kojo_henko"));
				 item.setKb_hin_ku(result.getString("kb_hin_ku"));
				 item.setKb_hin_ban(result.getString("kb_hin_ban"));
				 item.setKb_ac(result.getString("kb_ac"));
				 item.setKb_kakonai(result.getString("kb_kakonai"));
				 item.setKb_hin_name(result.getString("kb_hin_name"));
				 item.setKb_noba(result.getString("kb_noba"));
				 item.setKb_hakoshu(result.getString("kb_hakoshu"));
				 item.setKb_shuyo_su(result.getString("kb_shuyo_su"));
				 item.setKb_matome_kb(result.getString("kb_matome_kb"));
				 item.setKb_keitai_kb(result.getString("kb_keitai_kb"));
				 item.setKb_kensa_kb(result.getString("kb_kensa_kb"));
				 item.setKb_tehai_kb(result.getString("kb_tehai_kb"));
				 item.setKb_teiki_hoju_kb(result.getString("kb_teiki_hoju_kb"));
				 item.setKb_lead_time35(result.getString("kb_lead_time35"));
				 item.setKb_teiten_dt(result.getString("kb_teiten_dt"));
				 item.setKb_shukka_kojo(result.getString("kb_shukka_kojo"));
				 item.setKb_tori_saki_hin(result.getString("kb_tori_saki_hin"));
				 item.setKb_getsudo_shiki(result.getString("kb_getsudo_shiki"));
				 item.setKb_kumi_getsudo(result.getString("kb_kumi_getsudo"));
				 item.setKb_hitsuyosu_gk(result.getString("kb_hitsuyosu_gk"));
				 item.setKb_hitsuyosu_01(result.getString("kb_hitsuyosu_01"));
				 item.setKb_hitsuyosu_02(result.getString("kb_hitsuyosu_02"));
				 item.setKb_hitsuyosu_03(result.getString("kb_hitsuyosu_03"));
				 item.setKb_hitsuyosu_04(result.getString("kb_hitsuyosu_04"));
				 item.setKb_hitsuyosu_05(result.getString("kb_hitsuyosu_05"));
				 item.setKb_hitsuyosu_06(result.getString("kb_hitsuyosu_06"));
				 item.setKb_hitsuyosu_07(result.getString("kb_hitsuyosu_07"));
				 item.setKb_hitsuyosu_08(result.getString("kb_hitsuyosu_08"));
				 item.setKb_hitsuyosu_09(result.getString("kb_hitsuyosu_09"));
				 item.setKb_hitsuyosu_10(result.getString("kb_hitsuyosu_10"));
				 item.setKb_hitsuyosu_11(result.getString("kb_hitsuyosu_11"));
				 item.setKb_hitsuyosu_12(result.getString("kb_hitsuyosu_12"));
				 item.setKb_hitsuyosu_13(result.getString("kb_hitsuyosu_13"));
				 item.setKb_hitsuyosu_14(result.getString("kb_hitsuyosu_14"));
				 item.setKb_hitsuyosu_15(result.getString("kb_hitsuyosu_15"));
				 item.setKb_hitsuyosu_16(result.getString("kb_hitsuyosu_16"));
				 item.setKb_hitsuyosu_17(result.getString("kb_hitsuyosu_17"));
				 item.setKb_hitsuyosu_18(result.getString("kb_hitsuyosu_18"));
				 item.setKb_hitsuyosu_19(result.getString("kb_hitsuyosu_19"));
				 item.setKb_hitsuyosu_20(result.getString("kb_hitsuyosu_20"));
				 item.setKb_hitsuyosu_21(result.getString("kb_hitsuyosu_21"));
				 item.setKb_hitsuyosu_22(result.getString("kb_hitsuyosu_22"));
				 item.setKb_hitsuyosu_23(result.getString("kb_hitsuyosu_23"));
				 item.setKb_hitsuyosu_24(result.getString("kb_hitsuyosu_24"));
				 item.setKb_hitsuyosu_25(result.getString("kb_hitsuyosu_25"));
				 item.setKb_hitsuyosu_26(result.getString("kb_hitsuyosu_26"));
				 item.setKb_hitsuyosu_27(result.getString("kb_hitsuyosu_27"));
				 item.setKb_hitsuyosu_28(result.getString("kb_hitsuyosu_28"));
				 item.setKb_hitsuyosu_29(result.getString("kb_hitsuyosu_29"));
				 item.setKb_hitsuyosu_30(result.getString("kb_hitsuyosu_30"));
				 item.setKb_hitsuyosu_31(result.getString("kb_hitsuyosu_31"));
				 item.setKb_shiji_yohaku(result.getString("kb_shiji_yohaku"));
				 item.setKb_shiji_dt_01(result.getString("kb_shiji_dt_01"));
				 item.setKb_shiji_dt_02(result.getString("kb_shiji_dt_02"));
				 item.setKb_shiji_dt_03(result.getString("kb_shiji_dt_03"));
				 item.setKb_shiji_dt_04(result.getString("kb_shiji_dt_04"));
				 item.setKb_shiji_dt_05(result.getString("kb_shiji_dt_05"));
				 item.setKb_shiji_dt_06(result.getString("kb_shiji_dt_06"));
				 item.setKb_shiji_dt_07(result.getString("kb_shiji_dt_07"));
				 item.setKb_shiji_dt_08(result.getString("kb_shiji_dt_08"));
				 item.setKb_shiji_dt_09(result.getString("kb_shiji_dt_09"));
				 item.setKb_shiji_dt_10(result.getString("kb_shiji_dt_10"));
				 item.setKb_shiji_dt_11(result.getString("kb_shiji_dt_11"));
				 item.setKb_shiji_dt_12(result.getString("kb_shiji_dt_12"));
				 item.setKb_shiji_dt_13(result.getString("kb_shiji_dt_13"));
				 item.setKb_shiji_dt_14(result.getString("kb_shiji_dt_14"));
				 item.setKb_shiji_dt_15(result.getString("kb_shiji_dt_15"));
				 item.setKb_shiji_dt_16(result.getString("kb_shiji_dt_16"));
				 item.setKb_shiji_dt_17(result.getString("kb_shiji_dt_17"));
				 item.setKb_shiji_dt_18(result.getString("kb_shiji_dt_18"));
				 item.setKb_shiji_dt_19(result.getString("kb_shiji_dt_19"));
				 item.setKb_shiji_dt_20(result.getString("kb_shiji_dt_20"));
				 item.setKb_shiji_dt_21(result.getString("kb_shiji_dt_21"));
				 item.setKb_shiji_dt_22(result.getString("kb_shiji_dt_22"));
				 item.setKb_shiji_dt_23(result.getString("kb_shiji_dt_23"));
				 item.setKb_shiji_dt_24(result.getString("kb_shiji_dt_24"));
				 item.setKb_shiji_dt_25(result.getString("kb_shiji_dt_25"));
				 item.setKb_shiji_dt_26(result.getString("kb_shiji_dt_26"));
				 item.setKb_shiji_dt_27(result.getString("kb_shiji_dt_27"));
				 item.setKb_shiji_dt_28(result.getString("kb_shiji_dt_28"));
				 item.setKb_shiji_dt_29(result.getString("kb_shiji_dt_29"));
				 item.setKb_shiji_dt_30(result.getString("kb_shiji_dt_30"));
				 item.setKb_shiji_dt_31(result.getString("kb_shiji_dt_31"));
				 item.setKb_shiji_su_gk(result.getString("kb_shiji_su_gk"));
				 item.setKb_shiji_su_01(result.getString("kb_shiji_su_01"));
				 item.setKb_shiji_su_02(result.getString("kb_shiji_su_02"));
				 item.setKb_shiji_su_03(result.getString("kb_shiji_su_03"));
				 item.setKb_shiji_su_04(result.getString("kb_shiji_su_04"));
				 item.setKb_shiji_su_05(result.getString("kb_shiji_su_05"));
				 item.setKb_shiji_su_06(result.getString("kb_shiji_su_06"));
				 item.setKb_shiji_su_07(result.getString("kb_shiji_su_07"));
				 item.setKb_shiji_su_08(result.getString("kb_shiji_su_08"));
				 item.setKb_shiji_su_09(result.getString("kb_shiji_su_09"));
				 item.setKb_shiji_su_10(result.getString("kb_shiji_su_10"));
				 item.setKb_shiji_su_11(result.getString("kb_shiji_su_11"));
				 item.setKb_shiji_su_12(result.getString("kb_shiji_su_12"));
				 item.setKb_shiji_su_13(result.getString("kb_shiji_su_13"));
				 item.setKb_shiji_su_14(result.getString("kb_shiji_su_14"));
				 item.setKb_shiji_su_15(result.getString("kb_shiji_su_15"));
				 item.setKb_shiji_su_16(result.getString("kb_shiji_su_16"));
				 item.setKb_shiji_su_17(result.getString("kb_shiji_su_17"));
				 item.setKb_shiji_su_18(result.getString("kb_shiji_su_18"));
				 item.setKb_shiji_su_19(result.getString("kb_shiji_su_19"));
				 item.setKb_shiji_su_20(result.getString("kb_shiji_su_20"));
				 item.setKb_shiji_su_21(result.getString("kb_shiji_su_21"));
				 item.setKb_shiji_su_22(result.getString("kb_shiji_su_22"));
				 item.setKb_shiji_su_23(result.getString("kb_shiji_su_23"));
				 item.setKb_shiji_su_24(result.getString("kb_shiji_su_24"));
				 item.setKb_shiji_su_25(result.getString("kb_shiji_su_25"));
				 item.setKb_shiji_su_26(result.getString("kb_shiji_su_26"));
				 item.setKb_shiji_su_27(result.getString("kb_shiji_su_27"));
				 item.setKb_shiji_su_28(result.getString("kb_shiji_su_28"));
				 item.setKb_shiji_su_29(result.getString("kb_shiji_su_29"));
				 item.setKb_shiji_su_30(result.getString("kb_shiji_su_30"));
				 item.setKb_shiji_su_31(result.getString("kb_shiji_su_31"));
				 item.setInsertDay(result.getString("insertDay"));
				 item.setInsertTime(result.getString("insertTime"));
				 itemList.add(item);

			 }

			 System.out.println(insertDay);
			 System.out.println(itemList);

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }
		 return itemList;

	 }


	 //t_juchuからinsymdより一覧取得
	 public List<KBTItemBean>getJuchuList(String insymd) throws SQLException {

		 List<KBTItemBean>juchuList = new ArrayList<KBTItemBean> ();
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(SELECT_insymd);
			 statement.setString(1, insymd);
			 ResultSet result = statement.executeQuery();

			 while (result.next()) {
				 KBTItemBean item = new KBTItemBean();
				 item.setJuchu_no(result.getInt("juchu_no"));
				 item.setNaikaku_kb(result.getString("naikaku_kb"));
				 item.setKensa_kb(result.getString("kensa_kb"));
				 item.setJuchu_ymd(result.getString("juchu_ymd"));
				 item.setInsStartTime(result.getString("insStartTime"));
				 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
				 item.setNounyushiji_time(result.getString("nounyushiji_time"));
				 item.setTok_cd(result.getString("tok_cd"));
				 item.setChumon_no(result.getString("chumon_no"));
				 item.setNoba_cd(result.getString("noba_cd"));
				 item.setKojo_cd(result.getString("kojo_cd"));
				 item.setHinban(result.getString("hinban"));
				 item.setHin_nm(result.getString("hin_nm"));
				 item.setJuchu_su(result.getString("juchu_su"));
				 item.setInsymd(result.getString("insymd"));
				 item.setInstime(result.getString("instime"));
				 juchuList.add(item);

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

		 return juchuList;

	 }


	 //品番取得
	 public List<KBTItemBean>getItemListHinban() throws SQLException {

		 List<KBTItemBean>itemListHinban = new ArrayList<KBTItemBean> ();
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(SELECT_hinban);
			 ResultSet result = statement.executeQuery();

			 while (result.next()) {
				 KBTItemBean item = new KBTItemBean();
				 item.setHinban(result.getString("hinban"));
				 itemListHinban.add(item);

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

		 return itemListHinban;

	 }


	 //取込日取得
	 public List<KBTItemBean>getItemListDay() throws SQLException {

		 List<KBTItemBean>itemListDay = new ArrayList<KBTItemBean> ();
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(SELECT_day);
			 ResultSet result = statement.executeQuery();

			 while (result.next()) {
				 KBTItemBean item = new KBTItemBean();
				 item.setInsymd(result.getString("insymd"));
				 itemListDay.add(item);

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

		 return itemListDay;

	 }


	 //内示データ取込日取得
	 public List<KBTItemBean>getNaijiListDay() throws SQLException {

		 List<KBTItemBean>naijiListDay = new ArrayList<KBTItemBean> ();
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(SELECT_naiji_ins_day);
			 ResultSet result = statement.executeQuery();

			 while (result.next()) {
				 KBTItemBean item = new KBTItemBean();
				 item.setInsNaijiDay(result.getString("insymd"));
				 naijiListDay.add(item);

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

		 return naijiListDay;

	 }


	 //指示日、指示数を指定し取得
	 public List<KBTItemBean>getItemListOrder(String hinban, String insymd1, String insymd2, String insymd3, String dispDate) throws SQLException {

		 List<KBTItemBean>itemListOrder = new ArrayList<KBTItemBean> ();
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(SELECT_order);
			 statement.setString(1, insymd1);
			 statement.setString(2, insymd2);
			 statement.setString(3, insymd3);
			 statement.setString(4, hinban);
			 statement.setString(5, dispDate);
			 ResultSet result = statement.executeQuery();

			 while (result.next()) {
				 KBTItemBean item = new KBTItemBean();
				 item.setHinban(result.getString("hinban"));
				 item.setNoba_cd(result.getString("noba_cd"));
				 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
				 item.setQty1(result.getInt("qty1"));
				 item.setQty2(result.getInt("qty2"));
				 item.setQty3(result.getInt("qty3"));
				 item.setFinal_qty(result.getInt("final_qty"));
				 itemListOrder.add(item);

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

		 return itemListOrder;

	 }


	 //kb_35_hibetsunaijiより抽出した項目をt_juchuへinsert
	 public void insertkb35juchu(int juchu_no, String naikaku_kb, String[] kensa_kb, String[] juchu_ymd, String[] insStartTime, String[] nounyushiji_ymd, String nounyushiji_time, String tok_cd, String chumon_no,
			 String[] noba_cd, String[] kojo_cd, String[] hinban, String[] hin_nm, int[] juchu_su, String[] getsudo_kb, String[] kumitate_getsudo, int add_kb, String insymd, String instime/*, String[] S_time*/) throws SQLException {
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(INSERT_juchu);

			 int i=0;
			 int j=0;
			 for(j=0; j < 31; j++){
				 statement.setString(6, nounyushiji_ymd[j]);
				 statement.setInt(14, juchu_su[j]);

				 if(nounyushiji_ymd[j] != "00000" && juchu_su[j] != 0){
					 for(i=0; i<1; i++){
						 //if((S_time[i]) == null || insStartTime[i].compareTo(S_time[i]) > 0){
						 statement.setInt(1, juchu_no);
						 statement.setString(2, naikaku_kb);
						 statement.setString(3, kensa_kb[i]);
						 statement.setString(4, juchu_ymd[i]);
						 statement.setString(5, insStartTime[i]);
						 statement.setString(7, nounyushiji_time);
						 statement.setString(8, tok_cd);
						 statement.setString(9, chumon_no);
						 statement.setString(10, noba_cd[i]);
						 statement.setString(11, kojo_cd[i]);
						 statement.setString(12, hinban[i]);
						 statement.setString(13, hin_nm[i]);
						 statement.setString(15, getsudo_kb[i]);
						 statement.setString(16, kumitate_getsudo[i]);
						 statement.setInt(17, add_kb);
						 statement.setString(18, insymd);
						 statement.setString(19, instime);

						 //}else{}

					 }

					 statement.executeUpdate();

				 }

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

	 }


	 //NVAN.DATから識別番号45のデータをkb_45_nounyushijiへINSERT
	 public void insertNvan45(String kb_no, String kb_kojo, String kb_torihiki, String kb_chumon_no, String kb_hin_ku, String kb_hin_ban, String kb_ac, String kb_kakonai, String kb_hin_name,
			 String kb_shi_tan, String kb_kensa_kb, String kb_keiyaku_s, String kb_hakoshu, String kb_noba, String kb_shuyo_su45, String kb_lead_time, String kb_seiban, String kb_yohaku45_1,
			 String kb_no_shiji_dt,	String kb_no_shiji_su, String kb_sei_kan_dt, String kb_hakko_dt, String kb_me_hin_no, String kb_shukka_kojo, String insertDay, String insertTime) throws SQLException {
		 Connection connection = source.getConnection();

		 try {

			 //品番に"-"ハイフンを挿入"XXXXX-XXXX-X"の形へ
			 StringBuilder hin_ban = new StringBuilder();
			 hin_ban.append(kb_hin_ban);
			 hin_ban.insert(5, "-");
			 hin_ban.insert(10, "-");
			 String hinban = new String(hin_ban);

			 //kb_chumon_noに"-"ハイフンを挿入し"XXXX-XXXX"の形へ
			 StringBuilder chumon_no = new StringBuilder();
			 chumon_no.append(kb_chumon_no);
			 chumon_no.insert(4,  "-");
			 String kb_chuban = new String(chumon_no);

			 //指示日フォーマット変更 yymmdd -> 20yymmdd
			 String hiduke = "20"+kb_no_shiji_dt;

			 PreparedStatement statement = connection.prepareStatement(INSERT_45);
			 statement.setString(1, kb_no);
			 statement.setString(2, kb_kojo);
			 statement.setString(3, kb_torihiki);
			 statement.setString(4, kb_chuban);
			 statement.setString(5, kb_hin_ku);
			 statement.setString(6, hinban);
			 statement.setString(7, kb_ac);
			 statement.setString(8, kb_kakonai);
			 statement.setString(9, kb_hin_name);
			 statement.setString(10, kb_shi_tan);
			 statement.setString(11, kb_kensa_kb);
			 statement.setString(12, kb_keiyaku_s);
			 statement.setString(13, kb_hakoshu);
			 statement.setString(14, kb_noba);
			 statement.setString(15, kb_shuyo_su45);
			 statement.setString(16, kb_lead_time);
			 statement.setString(17, kb_seiban);
			 statement.setString(18, kb_yohaku45_1);
			 statement.setString(19, hiduke);
			 statement.setString(20, kb_no_shiji_su);
			 statement.setString(21, kb_sei_kan_dt);
			 statement.setString(22, kb_hakko_dt);
			 statement.setString(23, kb_me_hin_no);
			 statement.setString(24, kb_shukka_kojo);
			 statement.setString(25, insertDay);
			 statement.setString(26, insertTime);
			 statement.executeUpdate();

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

	 }


	 //NVAN.DATから識別番号46のデータをkb_46_hachuyoteirenrakuへINSERT
	 public void insertNvan46(String kb_no, String kb_kojo, String kb_torihiki, String kb_chumon_no, String kb_hin_ku, String kb_hin_ban, String kb_ac, String kb_kako, String kb_hachu_tnk46, String kb_hachu_su46,
			 String kb_hin_name, String kb_zaishit_nm46, String kb_noba, String kb_noba_name, String kb_kensa_kb, String kb_shin_hatsu_sa, String kb_seiban, String kb_hatsu_tan, String kb_shi_tan,
			 String kb_noki, String kb_shijisu, String kb_tokki, String kb_hakko_dt, String kb_joken, String kb_a_kb, String kb_shukka_kojo, String kb_me_hin_no, String kb_hakoshu, String kb_shuyo_su,
			 String insertDay, String insertTime) throws SQLException {
		 Connection connection = source.getConnection();

		 try {

			 //品番に"-"ハイフンを挿入"XXXXX-XXXX-X"の形へ
			 StringBuilder hin_ban = new StringBuilder();
			 hin_ban.append(kb_hin_ban);
			 hin_ban.insert(5, "-");
			 hin_ban.insert(10, "-");
			 String hinban = new String(hin_ban);

			 //kb_chumon_noに"-"ハイフンを挿入し"XXXX-XXXX"の形へ
			 StringBuilder chumon_no = new StringBuilder();
			 chumon_no.append(kb_chumon_no);
			 chumon_no.insert(4,  "-");
			 String kb_chuban = new String(chumon_no);

			 //指示日フォーマット変更 mmdd -> 20yymmdd
			 System.out.println("AAAAA");
			 String hiduke = null;

			 if(kb_noki.equals("    ")){
				 hiduke ="0";
				 System.out.println("BBBBB");

			 }else if(Integer.parseInt(kb_hakko_dt.substring(2,4)) > Integer.parseInt(kb_noki.substring(0,2))){
				 System.out.println("CCCCC");
				 hiduke = "20"+(Integer.parseInt(kb_hakko_dt.substring(0,2))+1)+kb_noki;

			 }else{
				 System.out.println("DDDDD");
				 hiduke = "20"+kb_hakko_dt.substring(0,2)+kb_noki;

			 }

			 PreparedStatement statement = connection.prepareStatement(INSERT_46);
			 statement.setString(1, kb_no);
			 statement.setString(2, kb_kojo);
			 statement.setString(3, kb_torihiki);
			 statement.setString(4, kb_chuban);
			 statement.setString(5, kb_hin_ku);
			 statement.setString(6, hinban);
			 statement.setString(7, kb_ac);
			 statement.setString(8, kb_kako);
			 statement.setString(9, kb_hachu_tnk46);
			 statement.setString(10, kb_hachu_su46);
			 statement.setString(11, kb_hin_name);
			 statement.setString(12, kb_zaishit_nm46);
			 statement.setString(13, kb_noba);
			 statement.setString(14, kb_noba_name);
			 statement.setString(15, kb_kensa_kb);
			 statement.setString(16, kb_shin_hatsu_sa);
			 statement.setString(17, kb_seiban);
			 statement.setString(18, kb_hatsu_tan);
			 statement.setString(19, kb_shi_tan);
			 statement.setString(20, hiduke);
			 statement.setString(21, kb_shijisu);
			 statement.setString(22, kb_tokki);
			 statement.setString(23, kb_hakko_dt);
			 statement.setString(24, kb_joken);
			 statement.setString(25, kb_a_kb);
			 statement.setString(26, kb_shukka_kojo);
			 statement.setString(27, kb_me_hin_no);
			 statement.setString(28, kb_hakoshu);
			 statement.setString(29, kb_shuyo_su);
			 statement.setString(30, insertDay);
			 statement.setString(31, insertTime);
			 statement.executeUpdate();

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

	 }


	 //NVAN.DATから識別番号47のデータをkb_47_hojuchumonへINSERT
	 public void insertNvan47(String kb_no, String kb_kojo, String kb_torihiki, String kb_chumon_no, String kb_hin_ku, String kb_hin_ban, String kb_ac, String kb_kako, String kb_hachu_tnk47, String kb_hachu_su47,
			 String kb_hin_name, String kb_zaishit_nm47, String kb_noba, String kb_noba_name, String kb_kensa_kb, String kb_shin_hatsu_sa, String kb_seihin_no, String kb_hatsu_tan, String kb_shi_tan,
			 String kb_chumon_tnk, String kb_chumon_kin, String kb_arazai_tan, String kb_kako_tnk, String kb_noki, String kb_shijisu, String kb_tokki, String kb_hakko_dt, String kb_joken, String kb_a_kb,
			 String kb_shukka_kojo, String kb_me_hin_no, String kb_hakoshu, String kb_shuyo_su, String insertDay, String insertTime) throws SQLException {
		 Connection connection = source.getConnection();

		 try {

			 System.out.println("/////");
			 //kb_hin_banに"-"ハイフンを挿入"XXXXX-XXXX-X"の形へ
			 StringBuilder hin_ban = new StringBuilder();
			 hin_ban.append(kb_hin_ban);
			 hin_ban.insert(5, "-");
			 hin_ban.insert(10, "-");
			 String hinban = new String(hin_ban);

			 //kb_chumon_noに"-"ハイフンを挿入し"XXXX-XXXX"の形へ
			 StringBuilder chumon_no = new StringBuilder();
			 chumon_no.append(kb_chumon_no);
			 chumon_no.insert(4,  "-");
			 String kb_chuban = new String(chumon_no);

			 //指示日フォーマット変更 mmdd -> 20yymmdd
			 System.out.println("AAAAA");
			 String hiduke = null;

			 if(kb_noki.equals("    ")){
				 hiduke ="0";
				 System.out.println("BBBBB");

			 }else if(Integer.parseInt(kb_hakko_dt.substring(2,4)) > Integer.parseInt(kb_noki.substring(0,2))){
				 System.out.println("CCCCC");
				 hiduke = "20"+(Integer.parseInt(kb_hakko_dt.substring(0,2))+1)+kb_noki;

			 }else{
				 System.out.println("DDDDD");
				 hiduke = "20"+kb_hakko_dt.substring(0,2)+kb_noki;

			 }

			 PreparedStatement statement = connection.prepareStatement(INSERT_47);
			 statement.setString(1, kb_no);
			 statement.setString(2, kb_kojo);
			 statement.setString(3, kb_torihiki);
			 statement.setString(4, kb_chuban);
			 statement.setString(5, kb_hin_ku);
			 statement.setString(6, hinban);
			 statement.setString(7, kb_ac);
			 statement.setString(8, kb_kako);
			 statement.setString(9, kb_hachu_tnk47);
			 statement.setString(10, kb_hachu_su47);
			 statement.setString(11, kb_hin_name);
			 statement.setString(12, kb_zaishit_nm47);
			 statement.setString(13, kb_noba);
			 statement.setString(14, kb_noba_name);
			 statement.setString(15, kb_kensa_kb);
			 statement.setString(16, kb_shin_hatsu_sa);
			 statement.setString(17, kb_seihin_no);
			 statement.setString(18, kb_hatsu_tan);
			 statement.setString(19, kb_shi_tan);
			 statement.setString(20, kb_chumon_tnk);
			 statement.setString(21, kb_chumon_kin);
			 statement.setString(22, kb_arazai_tan);
			 statement.setString(23, kb_kako_tnk);
			 statement.setString(24, hiduke);
			 statement.setString(25, kb_shijisu);
			 statement.setString(26, kb_tokki);
			 statement.setString(27, kb_hakko_dt);
			 statement.setString(28, kb_joken);
			 statement.setString(29, kb_a_kb);
			 statement.setString(30, kb_shukka_kojo);
			 statement.setString(31, kb_me_hin_no);
			 statement.setString(32, kb_hakoshu);
			 statement.setString(33, kb_shuyo_su);
			 statement.setString(34, insertDay);
			 statement.setString(35, insertTime);
			 statement.executeUpdate();

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

	 }


	 //kb_45_nounyushijiからinsertDay=insymdにより一覧取得
	 public List<KBTNvanBean> getItemList45(String insertDay) throws SQLException {

		 List<KBTNvanBean>itemList45 = new ArrayList<KBTNvanBean> ();
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(SELECT_45);
			 statement.setString(1, insertDay);
			 ResultSet result = statement.executeQuery();

			 while (result.next()) {
				 KBTNvanBean item = new KBTNvanBean();
				 item.setKb_no(result.getString("kb_no"));
				 item.setKb_kojo(result.getString("kb_kojo"));
				 item.setKb_torihiki(result.getString("kb_torihiki"));
				 item.setKb_chumon_no(result.getString("kb_chumon_no"));
				 item.setKb_hin_ku(result.getString("kb_hin_ku"));
				 item.setKb_hin_ban(result.getString("kb_hin_ban"));
				 item.setKb_ac(result.getString("kb_ac"));
				 item.setKb_kakonai(result.getString("kb_kakonai"));
				 item.setKb_hin_name(result.getString("kb_hin_name"));
				 item.setKb_shi_tan(result.getString("kb_shi_tan"));
				 item.setKb_kensa_kb(result.getString("kb_kensa_kb"));
				 item.setKb_keiyaku_s(result.getString("kb_keiyaku_s"));
				 item.setKb_hakoshu(result.getString("kb_hakoshu"));
				 item.setKb_noba(result.getString("kb_noba"));
				 item.setKb_shuyo_su45(result.getString("kb_shuyo_su45"));
				 item.setKb_lead_time(result.getString("kb_lead_time"));
				 item.setKb_seiban(result.getString("kb_seiban"));
				 item.setKb_yohaku45_1(result.getString("kb_yohaku45_1"));
				 item.setKb_no_shiji_dt(result.getString("kb_no_shiji_dt"));
				 item.setKb_no_shiji_su(result.getString("kb_no_shiji_su"));
				 item.setKb_sei_kan_dt(result.getString("kb_sei_kan_dt"));
				 item.setKb_hakko_dt(result.getString("kb_hakko_dt"));
				 item.setKb_me_hin_no(result.getString("kb_me_hin_no"));
				 item.setKb_shukka_kojo(result.getString("kb_shukka_kojo"));
				 item.setInsertDay(result.getString("insertDay"));
				 item.setInsertTime(result.getString("insertTime"));
				 itemList45.add(item);

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

		 return itemList45;

	 }


	 //kb_46_hachuyoteirenrakuからinsertDay=insymdにより一覧取得
	 public List<KBTNvanBean> getItemList46(String insertDay) throws SQLException {

		 List<KBTNvanBean>itemList46 = new ArrayList<KBTNvanBean> ();
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(SELECT_46);
			 statement.setString(1, insertDay);
			 ResultSet result = statement.executeQuery();

			 while (result.next()) {
				 KBTNvanBean item = new KBTNvanBean();
				 item.setKb_no(result.getString("kb_no"));
				 item.setKb_kojo(result.getString("kb_kojo"));
				 item.setKb_torihiki(result.getString("kb_torihiki"));
				 item.setKb_chumon_no(result.getString("kb_chumon_no"));
				 item.setKb_hin_ku(result.getString("kb_hin_ku"));
				 item.setKb_hin_ban(result.getString("kb_hin_ban"));
				 item.setKb_ac(result.getString("kb_ac"));
				 item.setKb_kako(result.getString("kb_kako"));
				 item.setKb_hachu_tnk46(result.getString("kb_hachu_tnk46"));
				 item.setKb_hachu_su46(result.getString("kb_hachu_su46"));
				 item.setKb_hin_name(result.getString("kb_hin_name"));
				 item.setKb_zaishit_nm46(result.getString("kb_zaishit_nm46"));
				 item.setKb_noba(result.getString("kb_noba"));
				 item.setKb_noba_name(result.getString("kb_noba_name"));
				 item.setKb_kensa_kb(result.getString("kb_kensa_kb"));
				 item.setKb_shin_hatsu_sa(result.getString("kb_shin_hatsu_sa"));
				 item.setKb_seiban(result.getString("kb_seiban"));
				 item.setKb_hatsu_tan(result.getString("kb_hatsu_tan"));
				 item.setKb_shi_tan(result.getString("kb_shi_tan"));
				 item.setKb_noki(result.getString("kb_noki"));
				 item.setKb_shijisu(result.getString("kb_shijisu"));
				 item.setKb_tokki(result.getString("kb_tokki"));
				 item.setKb_hakko_dt(result.getString("kb_hakko_dt"));
				 item.setKb_joken(result.getString("kb_joken"));
				 item.setKb_a_kb(result.getString("kb_a_kb"));
				 item.setKb_shukka_kojo(result.getString("kb_shukka_kojo"));
				 item.setKb_me_hin_no(result.getString("kb_me_hin_no"));
				 item.setKb_hakoshu(result.getString("kb_hakoshu"));
				 item.setKb_shuyo_su(result.getString("kb_shuyo_su"));
				 item.setInsertDay(result.getString("insertDay"));
				 item.setInsertTime(result.getString("insertTime"));
				 itemList46.add(item);

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

		 return itemList46;

	 }


	 //kb_47_hojuchumonからinsertDay=insymdにより一覧取得
	 public List<KBTNvanBean> getItemList47(String insertDay) throws SQLException {

		 List<KBTNvanBean>itemList47 = new ArrayList<KBTNvanBean> ();
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(SELECT_47);
			 statement.setString(1, insertDay);
			 ResultSet result = statement.executeQuery();

			 while (result.next()) {
				 KBTNvanBean item = new KBTNvanBean();
				 item.setKb_no(result.getString("kb_no"));
				 item.setKb_kojo(result.getString("kb_kojo"));
				 item.setKb_torihiki(result.getString("kb_torihiki"));
				 item.setKb_chumon_no(result.getString("kb_chumon_no"));
				 item.setKb_hin_ku(result.getString("kb_hin_ku"));
				 item.setKb_hin_ban(result.getString("kb_hin_ban"));
				 item.setKb_ac(result.getString("kb_ac"));
				 item.setKb_kako(result.getString("kb_kako"));
				 item.setKb_hachu_tnk47(result.getString("kb_hachu_tnk47"));
				 item.setKb_hachu_su47(result.getString("kb_hachu_su47"));
				 item.setKb_hin_name(result.getString("kb_hin_name"));
				 item.setKb_zaishit_nm47(result.getString("kb_zaishit_nm47"));
				 item.setKb_noba(result.getString("kb_noba"));
				 item.setKb_noba_name(result.getString("kb_noba_name"));
				 item.setKb_kensa_kb(result.getString("kb_kensa_kb"));
				 item.setKb_shin_hatsu_sa(result.getString("kb_shin_hatsu_sa"));
				 item.setKb_seihin_no(result.getString("kb_seihin_no"));
				 item.setKb_hatsu_tan(result.getString("kb_hatsu_tan"));
				 item.setKb_shi_tan(result.getString("kb_shi_tan"));
				 item.setKb_chumon_tnk(result.getString("kb_chumon_tnk"));
				 item.setKb_chumon_kin(result.getString("kb_chumon_kin"));
				 item.setKb_arazai_tan(result.getString("kb_arazai_tan"));
				 item.setKb_kako_tnk(result.getString("kb_kako_tnk"));
				 item.setKb_noki(result.getString("kb_noki"));
				 item.setKb_shijisu(result.getString("kb_shijisu"));
				 item.setKb_tokki(result.getString("kb_tokki"));
				 item.setKb_hakko_dt(result.getString("kb_hakko_dt"));
				 item.setKb_joken(result.getString("kb_joken"));
				 item.setKb_a_kb(result.getString("kb_a_kb"));
				 item.setKb_shukka_kojo(result.getString("kb_shukka_kojo"));
				 item.setKb_me_hin_no(result.getString("kb_me_hin_no"));
				 item.setKb_hakoshu(result.getString("kb_hakoshu"));
				 item.setKb_shuyo_su(result.getString("kb_shuyo_su"));
				 item.setInsertDay(result.getString("insertDay"));
				 item.setInsertTime(result.getString("insertTime"));
				 itemList47.add(item);

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

		 return itemList47;

	 }


	 //kb_45_nounyushiji, kb_46_hachuyoteirenraku, kb_47_hojuchumonより抽出した項目をt_juchuへinsert
	 public void insertKakutei(int juchu_no, String naikaku_kb, String[] kensa_kb, String[] juchu_ymd, String[] insStartTime, String[] nounyushiji_ymd, String nounyushiji_time, String tok_cd, String[] chumon_no,
			 String[] noba_cd, String[] kojo_cd, String[] hinban, String[] hin_nm, int[] juchu_su, String[] getsudo_kb, String[] kumitate_getsudo, String[] seiban, String insymd, String instime,String[]rec_no) throws SQLException {
		 Connection connection = source.getConnection();

		 try {

			 PreparedStatement statement = connection.prepareStatement(INSERT_juchu2);
			 System.out.println(Arrays.toString(rec_no));
			 int add_kb = 0;
			 if(chumon_no != null){
				 System.out.println("6/6/6/6/6/6");

				 int i=0;
				 for(i=0; i<chumon_no.length; i++){
					 if(rec_no[i].equals("45")){
						 System.out.println("7/7/7/7/7/7");
						 System.out.println("insert/insert/insert");
						 add_kb = 0;

					 }else if(rec_no[i].equals("46")){
						 add_kb = 1;

					 }else if(rec_no[i].equals("47")){
						 for(int j=0; j<seiban.length; j++){
							 System.out.println("seiban="+seiban[j].substring(9));
							 if(seiban[j].substring(9).equals("9")){
								 add_kb = 0;

							 }else{
								 add_kb = 1;

							 }

						 }

					 }

					 statement.setInt(1, juchu_no);
					 statement.setString(2, naikaku_kb);
					 statement.setString(3, kensa_kb[i]);
					 statement.setString(4, juchu_ymd[i]);
					 statement.setString(5, insStartTime[i]);
					 statement.setString(6, nounyushiji_ymd[i]);
					 statement.setString(7, nounyushiji_time);
					 statement.setString(8, tok_cd);
					 statement.setString(9, chumon_no[i]);
					 statement.setString(10, noba_cd[i]);
					 statement.setString(11, kojo_cd[i]);
					 statement.setString(12, hinban[i]);
					 statement.setString(13, hin_nm[i]);
					 statement.setInt(14, juchu_su[i]);
					 statement.setString(15, getsudo_kb[i]);
					 statement.setString(16, kumitate_getsudo[i]);
					 statement.setInt(17, add_kb);
					 statement.setString(18, insymd);
					 statement.setString(19, instime);

				 }

				 statement.executeUpdate();

			 }

		 } catch (SQLException e) {
			 e.printStackTrace();

		 } finally {
			 if (connection != null) {
				 connection.close();

			 }

		 }

	 }


	     //品番指定で数量差と差分検索
	    public List<KBTItemBean>getSabunList(String hinban, String insymd1, String insymd2, String hyoujiymd, HttpServletRequest request, HttpServletResponse response ) throws SQLException {

	         List<KBTItemBean>SabunList = new ArrayList<KBTItemBean> ();
             List<String>sasuList = new ArrayList<String>();
	         Connection connection = source.getConnection();
	         List<CsvImportBean>ZaikoList = new ArrayList<CsvImportBean> ();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT_sabun);
	             statement.setString(1, insymd2);
	             statement.setString(2, insymd1);
	             statement.setString(3, insymd2);
	             statement.setString(4, insymd1);
	             statement.setString(5, hinban);
	             statement.setString(6, hyoujiymd);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
	                 item.setNoba_cd(result.getString("noba_cd"));
	                 item.setQty1(result.getInt("qty1"));
	                 item.setQty2(result.getInt("qty2"));
	                 item.setSu_sa(result.getInt("su_sa"));
	                 SabunList.add(item);
	                 System.out.println(item);
	             }

	             List<String>qtyList1 = new ArrayList<String>();
	             List<String>qtyList2 = new ArrayList<String>();
	             Iterator<KBTItemBean> iterator = SabunList.iterator();
	             while (iterator.hasNext()) {KBTItemBean item2 = iterator.next();
	             int Qty1 = item2.getQty1();
	             int Qty2 = item2.getQty2();
	             qtyList1.add(Integer.toString(Qty1));
	             qtyList2.add(Integer.toString(Qty2));
	             }

	             System.out.println("CCC="+qtyList1);
	             System.out.println("DDD="+qtyList2);

	             int sasu = 0;
	             for(int i=0; i<qtyList2.size(); i++){
	            	 if(i==0){
	            		 sasu = Integer.parseInt(qtyList2.get(i)) - Integer.parseInt(qtyList1.get(i));
	            	 }else if(i==1){
	            		 sasu = ((Integer.parseInt(qtyList2.get(i-1)) - Integer.parseInt(qtyList1.get(i-1)))+Integer.parseInt(qtyList2.get(i))) - Integer.parseInt(qtyList1.get(i));
	            	 }else{
	            		 sasu = Integer.parseInt(sasuList.get(i-1))+Integer.parseInt(qtyList2.get(i)) - Integer.parseInt(qtyList1.get(i));
	            	 }
            		 sasuList.add(Integer.toString(sasu));
		             System.out.println("EEE="+sasu );
	             }

	             System.out.println("FFF="+sasuList );
	             request.setAttribute("sasuList", sasuList);

	             PreparedStatement statement2 = connection.prepareStatement(SELECT_tanpin_zaiko);
	             statement2.setString(1, hinban);
	             ResultSet result2 = statement2.executeQuery();
                 System.out.println("result2="+result2);
	             while (result2.next()) {
	             CsvImportBean item3 = new CsvImportBean();
                 item3.setMysry(result2.getDouble("mysry"));
                 item3.setKoshin_ymd(result2.getString("koshin_ymd"));
	             System.out.println("ABC="+item3);
                 ZaikoList.add(item3);
	             }

	             request.setAttribute("ZaikoList", ZaikoList);

	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }

	         }
	         return SabunList;
	     }


	     //差分数マイナス品番指定で数量差と差分検索
/**	    public List<KBTItemBean>getSabunMinusList(String[] hinban, String insymd1, String insymd2, String hyoujiymd, HttpServletRequest request, HttpServletResponse response ) throws SQLException {
	         //List<KBTItemBean>MinusAllList = new ArrayList<KBTItemBean> ();
	         List<KBTItemBean>SabunMinusList = new ArrayList<KBTItemBean> ();
	         List<String>sasuList = new ArrayList<String>();
	         Connection connection = source.getConnection();
	         List<CsvImportBean>ZaikoList = new ArrayList<CsvImportBean> ();

	         try {
	             System.out.println("=====DAO START=====");
	             PreparedStatement statement = connection.prepareStatement(SELECT_sabun);
	             for(int i=0; i < hinban.length; i++){
		             statement.setString(1, insymd2);
		             statement.setString(2, insymd1);
		             statement.setString(3, insymd2);
		             statement.setString(4, insymd1);
		             statement.setString(5, hinban[i]);
		             statement.setString(6, hyoujiymd);
		             System.out.println("hinban"+Arrays.toString(hinban));
		             System.out.println("insymd1="+insymd1);
		             System.out.println("insymd2="+insymd2);
		             System.out.println("hyoujiymd="+hyoujiymd);
		             ResultSet result = statement.executeQuery();

		             System.out.println("++++++++++++++++++");

		             while (result.next()) {
			             System.out.println("@@@@@@@@@@@@@@@@@@@");
		            	 KBTItemBean item = new KBTItemBean();
		                 item.setHinban(result.getString("hinban"));
		                 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
		                 item.setNoba_cd(result.getString("noba_cd"));
		                 item.setQty1(result.getInt("qty1"));
		                 item.setQty2(result.getInt("qty2"));
		                 item.setSu_sa(result.getInt("su_sa"));
		                 SabunMinusList.add(item);
		             }

		         System.out.println("SabunMinusList="+SabunMinusList);
	             System.out.println("******************");

	             List<String>qtyList1 = new ArrayList<String>();
	             List<String>qtyList2 = new ArrayList<String>();
	             Iterator<KBTItemBean> iterator = SabunMinusList.iterator();
	             while (iterator.hasNext()) {KBTItemBean item2 = iterator.next();
	             int Qty1 = item2.getQty1();
	             int Qty2 = item2.getQty2();
	             //System.out.println("Qty1="+Qty1);
	             //System.out.println("Qty2="+Qty2);
	             qtyList1.add(Integer.toString(Qty1));
	             qtyList2.add(Integer.toString(Qty2));
	             }

	             System.out.println("qtyList1="+qtyList1);
	             System.out.println("qtyList2="+qtyList2);

	             int sasu = 0;
	             for(int j=0; j<qtyList2.size(); j++){
	            	 if(j==0){
	            		 sasu = Integer.parseInt(qtyList2.get(j)) - Integer.parseInt(qtyList1.get(j));
	            	 }else if(j==1){
	            		 sasu = ((Integer.parseInt(qtyList2.get(j-1)) - Integer.parseInt(qtyList1.get(j-1)))+Integer.parseInt(qtyList2.get(j))) - Integer.parseInt(qtyList1.get(j));
	            	 }else{
	            		 sasu = Integer.parseInt(sasuList.get(j-1))+Integer.parseInt(qtyList2.get(j)) - Integer.parseInt(qtyList1.get(j));
	            	 }
           		 sasuList.add(Integer.toString(sasu));
		             //System.out.println("EEE="+sasu );
	             }

	             System.out.println("sasuList="+sasuList );
	             request.setAttribute("sasuList", sasuList);



	             PreparedStatement statement2 = connection.prepareStatement(SELECT_tanpin_zaiko);
	             for(int j=0; j<hinban.length; j++){
	             statement2.setString(1, hinban[j]);
	             ResultSet result2 = statement2.executeQuery();
                System.out.println("result!!="+result2);
	             while (result2.next()) {
	             CsvImportBean item3 = new CsvImportBean();
                item3.setMysry(result2.getDouble("mysry"));
                item3.setKoshin_ymd(result2.getString("koshin_ymd"));
	             System.out.println("ABC="+item3);
                ZaikoList.add(item3);
	             }
	             System.out.println("ZaikoList="+ZaikoList);
	             request.setAttribute("ZaikoList", ZaikoList);
	             System.out.println("???????????????????");
	             }

	             }


	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }

	         }
             //2020/01/07
             MinusAllList.addAll(SabunMinusList);
	         System.out.println("MinusAllList="+MinusAllList);
             request.setAttribute("MinusAllList", MinusAllList);
	         return SabunMinusList;

	     }
**/

	     //
	    public List<KBTItemBean> getTsuika_juchuList(String hinban, String toDay) throws SQLException {

	         List<KBTItemBean>Tsuika_juchuList = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT_tsuika_juchu);
	             statement.setString(1, hinban);
	             statement.setString(2, toDay);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setHinban(result.getString("hinban"));
	                 item.setNoba_cd(result.getString("noba_cd"));
	                 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
	                 item.setJuchu_su(result.getString("juchu_su"));
	                 Tsuika_juchuList.add(item);
	             }

	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }
	         }
	         return Tsuika_juchuList;
	     }


	     //差分数マイナス品番
/**	    public List<KBTItemBean> getTsuika_juchuMinusList(String[] hinban, String toDay) throws SQLException {

	         List<KBTItemBean>Tsuika_juchuMinusList = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT_tsuika_juchu);
	             for(int i=0; i<hinban.length; i++){
	             statement.setString(1, hinban[i]);
	             statement.setString(2, toDay);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setHinban(result.getString("hinban"));
	                 item.setNoba_cd(result.getString("noba_cd"));
	                 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
	                 item.setJuchu_su(result.getString("juchu_su"));
	                 Tsuika_juchuMinusList.add(item);
	             }

	             }
	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }
	         }
	         return Tsuika_juchuMinusList;
	     }
**/

	     //t_juchuから品番、取込日、期間(当日～確定期間末日)を指定して受注合計数を取得
	    public List<KBTItemBean> getKikanZaikoList(String hinban, String insymd2, String toDay, String e_date) throws SQLException {

	         List<KBTItemBean>KikanZaikoList = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT_Kikan_Zaiko);
	             statement.setString(1, hinban);
	             statement.setString(2, insymd2);
	             statement.setString(3, toDay);
	             statement.setString(4, e_date);
	             statement.setString(5, hinban);
	             statement.setString(6, toDay);
	             statement.setString(7, e_date);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setTotal_qty(result.getInt("Total_Qty"));
	                 KikanZaikoList.add(item);
	             }

	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }
	         }
	         return KikanZaikoList;
	     }


	     //差分数マイナス品番　t_juchuから品番、取込日、期間(当日～確定期間末日)を指定して受注合計数を取得
/**	    public List<KBTItemBean> getKikanZaikoMinusList(String[] hinban, String insymd2, String toDay, String e_date) throws SQLException {

	         List<KBTItemBean>KikanZaikoMinusList = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT_Kikan_Zaiko);
	             for(int i=0; i<hinban.length; i++){
	             statement.setString(1, hinban[i]);
	             statement.setString(2, insymd2);
	             statement.setString(3, toDay);
	             statement.setString(4, e_date);
	             statement.setString(5, hinban[i]);
	             statement.setString(6, toDay);
	             statement.setString(7, e_date);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setTotal_qty(result.getInt("Total_Qty"));
	                 KikanZaikoMinusList.add(item);
	             }

	             }

	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }
	         }
	         return KikanZaikoMinusList;
	     }
**/

	     //全品番ソートして差分検索
	    public List<KBTItemBean>getSabunAllList(String insymd1, String insymd2) throws SQLException {

	        List<KBTItemBean>SabunAllList = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	        	 PreparedStatement statement = connection.prepareStatement(DELETE_sabun_all);
	        	 statement.executeUpdate();
	             PreparedStatement statement2 = connection.prepareStatement(SELECT_sabunAll);
	             statement2.setString(1, insymd2);
	             statement2.setString(2, insymd1);
	             statement2.setString(3, insymd2);
	             statement2.setString(4, insymd1);
	             ResultSet result = statement2.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	            	 item.setHinban(result.getString("hinban"));
	                 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
	                 item.setNoba_cd(result.getString("noba_cd"));
	                 item.setQty1(result.getInt("qty1"));
	                 item.setQty2(result.getInt("qty2"));
	                 item.setSu_sa(result.getInt("su_sa"));

	                 SabunAllList.add(item);
	                 //System.out.println(item);

	             }

		       Iterator<KBTItemBean> iterator = SabunAllList.iterator();
		         while (iterator.hasNext()) {KBTItemBean item2 = iterator.next();

		         String hinban = item2.getHinban();
		         String nounyushiji_ymd = item2.getNounyushiji_ymd();
		         String noba_cd = item2.getNoba_cd();
		         int qty1 = item2.getQty1();
		         int qty2 = item2.getQty2();
		         int su_sa = item2.getSu_sa();
		         Timestamp nowTime= new Timestamp(System.currentTimeMillis());
		         SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
		         SimpleDateFormat timeStampNowTime = new SimpleDateFormat("HH:mm:ss");
		         String insertDay = timeStampNowDay.format(nowTime);
		         String insertTime = timeStampNowTime.format(nowTime);

			     	PreparedStatement statement3 = connection.prepareStatement(INSERT_sabun_all);
			    	statement3.setString(1, hinban);
			    	statement3.setString(2, nounyushiji_ymd);
			    	statement3.setString(3, noba_cd);
			    	statement3.setInt(4, qty1);
			    	statement3.setInt(5, qty2);
			    	statement3.setInt(6, su_sa);
			    	statement3.setString(7, insertDay);
			    	statement3.setString(8, insertTime);
			    	statement3.executeUpdate();
		         }

			    } catch (SQLException e) {
			    	e.printStackTrace();
			    	} finally {
			    		if (connection != null) {
			    			connection.close();
			    			}
			    	}
					return SabunAllList;

	    }


	     //kb_sabun_allから品番一覧の取得
	    public List<KBTItemBean> getSabunAllList2() throws SQLException {

	         List<KBTItemBean>sb_hinbanList2 = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	        	 PreparedStatement statement = connection.prepareStatement(DELETE_sabun_aggr);
	        	 statement.executeUpdate();
	             PreparedStatement statement2 = connection.prepareStatement(SELECT_sabun_hinban);
	             ResultSet result2 = statement2.executeQuery();

	             while (result2.next()) {
	                 String hinban = result2.getString("hinban");

	             PreparedStatement statement3 = connection.prepareStatement(SELECT_sabun_all);
	             ResultSet result3 = statement3.executeQuery();

	             List<String>sasuList2 = null;
	             List<String>shiji_ymdList = new ArrayList<String>();
	             List<String>nobaList = new ArrayList<String>();
	             while (result3.next()) {

                	 String hinban2 = result3.getString("hinban");

	                	 if(hinban.equals(hinban2)){
	    		             PreparedStatement statement4 = connection.prepareStatement("select * from kb_sabun_all where hinban='" + hinban +  "\'" );
	    		             ResultSet result4 = statement4.executeQuery();

	    		             List<String>qtyList1 = new ArrayList<String>();
	    		             List<String>qtyList2 = new ArrayList<String>();

	    		             while (result4.next()) {
	    	                 String Nounyushiji_ymd = (result4.getString("nounyushiji_ymd"));
	    	                 String Noba_cd = (result4.getString("noba_cd"));
	    		             int Qty1 = (result4.getInt("qty1"));
	    		             int Qty2 = (result4.getInt("qty2"));
	    		             qtyList1.add(Integer.toString(Qty1));
	    		             qtyList2.add(Integer.toString(Qty2));
	    		             shiji_ymdList.add(Nounyushiji_ymd);
	    		             nobaList.add(Noba_cd);

	    		             //System.out.println("CCC="+qtyList1);
	    		             //System.out.println("DDD="+qtyList2);

	    		             }
	    		             //System.out.println("size="+qtyList2.size());

	    		             sasuList2 = new ArrayList<String>();
	    		             int sasu = 0;
	    		             for(int i=0; i<qtyList2.size(); i++){
	    		            	 if(i==0){
	    		            		 sasu = Integer.parseInt(qtyList2.get(i)) - Integer.parseInt(qtyList1.get(i));
	    		            	 }else if(i==1){
	    		            		 sasu = ((Integer.parseInt(qtyList2.get(i-1)) - Integer.parseInt(qtyList1.get(i-1)))+Integer.parseInt(qtyList2.get(i))) - Integer.parseInt(qtyList1.get(i));
	    		            	 }else{
	    		            		 sasu = Integer.parseInt(sasuList2.get(i-1))+Integer.parseInt(qtyList2.get(i)) - Integer.parseInt(qtyList1.get(i));
	    		            	 }
	    	            		 sasuList2.add(Integer.toString(sasu));
	    			             System.out.println("EEE="+sasu );

	    		             }

	                	 }else{

	                	 }

	             }

	             PreparedStatement statement5 = connection.prepareStatement(INSERT_sabun_aggr);
	             for(int i=0; i<sasuList2.size(); i++){
	            	 String nounyushiji_ymd = shiji_ymdList.get(i);
					 String noba_cd =nobaList.get(i);
					 int sasu = Integer.parseInt(sasuList2.get(i));

					 statement5.setString(1, hinban);
					 statement5.setString(2, nounyushiji_ymd);
					 statement5.setString(3, noba_cd);
					 statement5.setInt(4, sasu);
					 statement5.executeUpdate();

	             }

	             }

	             //System.out.println("55555");

	         } catch (SQLException e) {
	        	 e.printStackTrace();

	         } finally {
	        	 if (connection != null) {
	        		 connection.close();

	        	 }

	         }
	         return sb_hinbanList2;

	    }



	     //CSVデータ出力用 kb_sabun_aggrテーブル全件抽出
	    public List<KBTItemBean> getSabunAllCsvList() throws SQLException {

	         List<KBTItemBean>sabunAllCsvList = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT_sabun_aggr);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setHinban(result.getString("hinban"));
	                 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
	                 item.setNoba_cd(result.getString("noba_cd"));
	                 item.setSasu(result.getInt("sasu"));
	                 sabunAllCsvList.add(item);
	             }

	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }
	         }
	         return sabunAllCsvList;
	     }


	     //CSVデータ出力用 kb_sabun_aggrテーブル期間指定（品番まとめ有）で抽出
	    public List<KBTItemBean> getSabunAllCsvList2(String kikan_s, String kikan_e) throws SQLException {

	         List<KBTItemBean>sabunAllCsvList2 = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT_sabun_aggr2);
	             statement.setString(1, kikan_s);
	             statement.setString(2, kikan_e);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setHinban(result.getString("hinban"));
	                 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
	                 item.setNoba_cd(result.getString("noba_cd"));
	                 item.setSasu(result.getInt("sasu"));
	                 sabunAllCsvList2.add(item);
	             }


	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }
	         }
	         return sabunAllCsvList2;
	     }


	     //CSVデータ出力用 kb_sabun_aggrテーブル期間指定（品番まとめ無）で抽出
	    public List<KBTItemBean> getSabunAllCsvList3(String kikan_s, String kikan_e) throws SQLException {

	         List<KBTItemBean>sabunAllCsvList3 = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {

	             PreparedStatement statement = connection.prepareStatement(SELECT_sabun_aggr3);
	             statement.setString(1, kikan_s);
	             statement.setString(2, kikan_e);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setHinban(result.getString("hinban"));
	                 item.setNounyushiji_ymd(result.getString("nounyushiji_ymd"));
	                 item.setNoba_cd(result.getString("noba_cd"));
	                 item.setSasu(result.getInt("sasu"));
	                 sabunAllCsvList3.add(item);
	             }

	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }
	         }
	         return sabunAllCsvList3;
	     }



	     //差分マイナス一覧画面表示用
/**	    public List<KBTItemBean> getSabunAllCsvList4(String kikan_s, String kikan_e, String insymd1, String insymd2, String hyoujiymd, String toDay, String e_date, HttpServletRequest request, HttpServletResponse response) throws SQLException {

	         List<KBTItemBean>sabunAllCsvList4 = new ArrayList<KBTItemBean> ();

	         List<String>sasuList = new ArrayList<String>();
	         List<CsvImportBean>ZaikoList = new ArrayList<CsvImportBean> ();
	         List<KBTItemBean>Tsuika_juchuMinusList = new ArrayList<KBTItemBean> ();
	         List<KBTItemBean>KikanZaikoMinusList = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT_sabun_aggr2);
	             statement.setString(1, kikan_s);
	             statement.setString(2, kikan_e);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setHinban(result.getString("hinban"));

	                 String hinban = result.getString("hinban");

	                 PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM(SELECT hinban, nounyushiji_ymd, noba_cd, "
																				+ "SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) AS qty1, "
																				+ "SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) AS qty2, "
																				+ "(SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) - SUM(CASE WHEN insymd=? THEN juchu_su ELSE 0 END) ) AS su_sa  "
																				+ "FROM t_juchu_test WHERE hinban=? AND nounyushiji_ymd >=? AND naikaku_kb='3' GROUP BY nounyushiji_ymd, noba_cd) AS X "
																				+ "WHERE qty1 !=0 OR qty2 !=0 ");
			             statement2.setString(1, insymd2);
			             statement2.setString(2, insymd1);
			             statement2.setString(3, insymd2);
			             statement2.setString(4, insymd1);
			             statement2.setString(5, hinban);
			             statement2.setString(6, hyoujiymd);
			             System.out.println("NEW hinban"+ result.getString("hinban"));
			             System.out.println("NEW insymd1="+insymd1);
			             System.out.println("NEW insymd2="+insymd2);
			             System.out.println("NEW hyoujiymd="+hyoujiymd);
			             ResultSet result2 = statement2.executeQuery();

			             System.out.println("++++++++++++++++++");
			             System.out.println(hinban);
			             while (result2.next()) {
				             System.out.println("@@@@@@@@@@@@@@@@@@@");
			            	 KBTItemBean item2 = new KBTItemBean();
			                 item2.setHinban(result2.getString("hinban"));
			                 item2.setNounyushiji_ymd(result2.getString("nounyushiji_ymd"));
			                 item2.setNoba_cd(result2.getString("noba_cd"));
			                 item2.setQty1(result2.getInt("qty1"));
			                 item2.setQty2(result2.getInt("qty2"));
			                 item2.setSu_sa(result2.getInt("su_sa"));
			                 sabunAllCsvList4.add(item2);
			             }

			         System.out.println("sabunAllCsvList4="+sabunAllCsvList4);
		             System.out.println("******************");

		             List<String>qtyList1 = new ArrayList<String>();
		             List<String>qtyList2 = new ArrayList<String>();
		             Iterator<KBTItemBean> iterator = sabunAllCsvList4.iterator();
		             while (iterator.hasNext()) {KBTItemBean item2 = iterator.next();
		             int Qty1 = item2.getQty1();
		             int Qty2 = item2.getQty2();
		             //System.out.println("Qty1="+Qty1);
		             //System.out.println("Qty2="+Qty2);
		             qtyList1.add(Integer.toString(Qty1));
		             qtyList2.add(Integer.toString(Qty2));
		             }

		             System.out.println("qtyList1="+qtyList1);
		             System.out.println("qtyList2="+qtyList2);

		             int sasu = 0;
		             for(int j=0; j<qtyList2.size(); j++){
		            	 if(j==0){
		            		 sasu = Integer.parseInt(qtyList2.get(j)) - Integer.parseInt(qtyList1.get(j));
		            	 }else if(j==1){
		            		 sasu = ((Integer.parseInt(qtyList2.get(j-1)) - Integer.parseInt(qtyList1.get(j-1)))+Integer.parseInt(qtyList2.get(j))) - Integer.parseInt(qtyList1.get(j));
		            	 }else{
		            		 sasu = Integer.parseInt(sasuList.get(j-1))+Integer.parseInt(qtyList2.get(j)) - Integer.parseInt(qtyList1.get(j));
		            	 }
	           		 sasuList.add(Integer.toString(sasu));
			             //System.out.println("EEE="+sasu );
		             }

		             System.out.println("sasuList="+sasuList );


		             PreparedStatement statement3 = connection.prepareStatement("SELECT * FROM tanpin_zaiko WHERE hinb=? AND nony='0700'");
		             statement3.setString(1, hinban);
		             ResultSet result3 = statement3.executeQuery();
	                System.out.println("result!!="+result3);
		             while (result3.next()) {
		             CsvImportBean item3 = new CsvImportBean();
	                item3.setMysry(result3.getDouble("mysry"));
	                item3.setKoshin_ymd(result3.getString("koshin_ymd"));
		             System.out.println("ABC="+item3);
	                ZaikoList.add(item3);
		             }
		             System.out.println("ZaikoList="+ZaikoList);

		             System.out.println("???????????????????");



	             PreparedStatement statement4 = connection.prepareStatement("SELECT * FROM t_juchu_test WHERE hinban=? AND nounyushiji_ymd>=? AND naikaku_kb='5' AND add_kb='1' ");
	             statement4.setString(1, hinban);
	             statement4.setString(2, toDay);
	             ResultSet result4 = statement4.executeQuery();

	             while (result4.next()) {
	            	 KBTItemBean item4 = new KBTItemBean();
	                 item4.setHinban(result4.getString("hinban"));
	                 item4.setNoba_cd(result4.getString("noba_cd"));
	                 item4.setNounyushiji_ymd(result4.getString("nounyushiji_ymd"));
	                 item4.setJuchu_su(result4.getString("juchu_su"));
	                 Tsuika_juchuMinusList.add(item4);
	             }


	             PreparedStatement statement5 = connection.prepareStatement("SELECT SUM(total_qty) AS Total_Qty FROM("
																			+ "SELECT sum(juchu_su) AS total_qty FROM `t_juchu_test` "
																			+ "WHERE hinban=? AND insymd=? AND naikaku_kb='3' AND nounyushiji_ymd BETWEEN ? AND ? "
																			+ "UNION ALL "
																			+ "SELECT sum(juchu_su) AS total_qty FROM `t_juchu_test` "
																			+ "WHERE hinban=? AND nounyushiji_ymd BETWEEN ? AND ? AND add_kb='1') AS X");
	             statement5.setString(1, hinban);
	             statement5.setString(2, insymd2);
	             statement5.setString(3, toDay);
	             statement5.setString(4, e_date);
	             statement5.setString(5, hinban);
	             statement5.setString(6, toDay);
	             statement5.setString(7, e_date);
	             ResultSet result5 = statement5.executeQuery();

	             while (result5.next()) {
	            	 KBTItemBean item5 = new KBTItemBean();
	                 item5.setTotal_qty(result5.getInt("Total_Qty"));
	                 KikanZaikoMinusList.add(item5);
	             }

	             }

	             request.setAttribute("sasuList", sasuList);
	             request.setAttribute("ZaikoList", ZaikoList);
	             request.setAttribute("Tsuika_juchuMinusList", Tsuika_juchuMinusList);
	             request.setAttribute("KikanZaikoMinusList", KikanZaikoMinusList);
	             statement.close();
	         } catch (SQLException e) {
	             e.printStackTrace();
	         }finally {
	        	 if (connection != null) {
	        		 connection.close();
	        		 }
	         }

	         return sabunAllCsvList4;
	         }
**/


	    //t_juchuテーブルとtanpin_zaikoテーブルを比較して過不足数を抽出しinsert　又、t_juchuにあってtanpin_zaikoにない品番をselect
	    public List<KBTItemBean> getKabusokuList(String insymd, String toDay, String e_date) throws SQLException {
	        List<KBTItemBean>KabusokuList = new ArrayList<KBTItemBean> ();
	    	Connection connection = source.getConnection();

	        try {
	        	//kabusokuテーブルをクリア
	        	PreparedStatement statement = connection.prepareStatement(DELETE_kabusoku);
	        	statement.executeUpdate();
	        	System.out.println(insymd);
	        	System.out.println(toDay);
	        	System.out.println(e_date);
	        	//kabusokuテーブルへ在庫、指定期間の内示数合計、在庫－内示数合計をinsert
	        	PreparedStatement statement2 = connection.prepareStatement(INSERT_kabusoku);
	        	statement2.setString(1, insymd);
				statement2.setString(2, toDay);
				statement2.setString(3, e_date);
				statement2.setString(4, toDay);
				statement2.setString(5, e_date);
				statement2.executeUpdate();
				//単品在庫データに無い内示データの品番を検索
	        	PreparedStatement statement3 = connection.prepareStatement(SELECT_No_target);
	        	statement3.setString(1, insymd);
				statement3.setString(2, toDay);
				statement3.setString(3, e_date);
				ResultSet result = statement3.executeQuery();
	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	            	 item.setHinban(result.getString("hinban"));
	                 item.setGokei_su(result.getInt("gokei_su"));
	                 KabusokuList.add(item);

	             }

	        } catch (SQLException e) {
	        	e.printStackTrace();
	        	} finally {
	        		if (connection != null) {
	        			connection.close();
	        			}
	        		}
			return KabusokuList;

	        }


	     //CSVデータ出力用 kabusokuテーブル全件抽出
	    public List<KBTItemBean> getKabusokuCsvList() throws SQLException {

	         List<KBTItemBean>kabusokuCsvList = new ArrayList<KBTItemBean> ();
	         Connection connection = source.getConnection();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT_kabusoku);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	            	 KBTItemBean item = new KBTItemBean();
	                 item.setHinban(result.getString("hinban"));
	                 item.setMysry(result.getInt("mysry"));
	                 item.setKikansry(result.getInt("kikansry"));
	                 item.setKabusoku(result.getInt("kabusoku"));
	                 kabusokuCsvList.add(item);
	             }

	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }
	         }
	         return kabusokuCsvList;
	     }

}

