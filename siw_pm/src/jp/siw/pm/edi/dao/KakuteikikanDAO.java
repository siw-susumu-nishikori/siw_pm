package jp.siw.pm.edi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.siw.pm.edi.bean.KakuteikikanBean;

public class KakuteikikanDAO {
	private static final String DELETE = "delete from kakutei_kikan";
	private static final String INSERT  = "insert into kakutei_kikan values(?, ?, ?, ?)";
	private static final String SELECT  = "select * from kakutei_kikan";
	private DataSource source;
	    public KakuteikikanDAO() throws NamingException {
	        InitialContext context = new InitialContext();
	        source = (DataSource) context.lookup("java:comp/env/jdbc/datasource");
	    }

	    //旬区を指定して、確定開始日および終了日をinsert
	    public void insertKikan(String kikanValue)throws SQLException {
	    	Connection connection = source.getConnection();

	        try {
	        	 PreparedStatement statement = connection.prepareStatement(DELETE);
	        	 statement.executeUpdate();
	        	 PreparedStatement statement2 = connection.prepareStatement(INSERT);
					Calendar cal = Calendar.getInstance(); //カレンダーオブジェクトを取得
					int year = cal.get(Calendar.YEAR); //現在の年を取得
					int month = cal.get(Calendar.MONTH) + 1; //現在の月を取得
					int day = cal.get(Calendar.DATE); //現在の日を取得
					String jun_nm = null;
					String s_date = null;
					String e_date = null;
	        			statement2.setString(1, kikanValue);
	        			if(kikanValue.equals("1A")){
	        				jun_nm = "1月上旬";
	        				s_date = Integer.toString(year+1)+"01"+"01";
	        				e_date = Integer.toString(year+1)+"01"+"15";
	        			}else if(kikanValue.equals("1B")){
	        				jun_nm = "1月下旬";
	        				s_date = Integer.toString(year)+"01"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"01"+lastday;
	        			}else if(kikanValue.equals("2A")){
	        				jun_nm = "2月上旬";
	        				s_date = Integer.toString(year)+"02"+"01";
	        				e_date = Integer.toString(year)+"02"+"15";
	        			}else if(kikanValue.equals("2B")){
	        				jun_nm = "2月下旬";
	        				s_date = Integer.toString(year)+"02"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"02"+lastday;
	        			}else if(kikanValue.equals("3A")){
	        				jun_nm = "3月上旬";
	        				s_date = Integer.toString(year)+"03"+"01";
	        				e_date = Integer.toString(year)+"03"+"15";
	        			}else if(kikanValue.equals("3B")){
	        				jun_nm = "3月下旬";
	        				s_date = Integer.toString(year)+"03"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"03"+lastday;
	        			}else if(kikanValue.equals("4A")){
	        				jun_nm = "4月上旬";
	        				s_date = Integer.toString(year)+"04"+"01";
	        				e_date = Integer.toString(year)+"04"+"15";
	        			}else if(kikanValue.equals("4B")){
	        				jun_nm = "4月下旬";
	        				s_date = Integer.toString(year)+"04"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"04"+lastday;
	        			}else if(kikanValue.equals("5A")){
	        				jun_nm = "5月上旬";
	        				s_date = Integer.toString(year)+"05"+"01";
	        				e_date = Integer.toString(year)+"05"+"15";
	        			}else if(kikanValue.equals("5B")){
	        				jun_nm = "5月下旬";
	        				s_date = Integer.toString(year)+"05"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"05"+lastday;
	        			}else if(kikanValue.equals("6A")){
	        				jun_nm = "6月上旬";
	        				s_date = Integer.toString(year)+"06"+"01";
	        				e_date = Integer.toString(year)+"06"+"15";
	        			}else if(kikanValue.equals("6B")){
	        				jun_nm = "6月下旬";
	        				s_date = Integer.toString(year)+"06"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"06"+lastday;
	        			}else if(kikanValue.equals("7A")){
	        				jun_nm = "7月上旬";
	        				s_date = Integer.toString(year)+"07"+"01";
	        				e_date = Integer.toString(year)+"07"+"15";
	        			}else if(kikanValue.equals("7B")){
	        				jun_nm = "7月下旬";
	        				s_date = Integer.toString(year)+"07"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"07"+lastday;
	        			}else if(kikanValue.equals("8A")){
	        				jun_nm = "8月上旬";
	        				s_date = Integer.toString(year)+"08"+"01";
	        				e_date = Integer.toString(year)+"08"+"15";
	        			}else if(kikanValue.equals("8B")){
	        				jun_nm = "8月下旬";
	        				s_date = Integer.toString(year)+"08"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"08"+lastday;
	        			}else if(kikanValue.equals("9A")){
	        				jun_nm = "9月上旬";
	        				s_date = Integer.toString(year)+"09"+"01";
	        				e_date = Integer.toString(year)+"09"+"15";
	        			}else if(kikanValue.equals("9B")){
	        				jun_nm = "9月下旬";
	        				s_date = Integer.toString(year)+"09"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"09"+lastday;
	        			}else if(kikanValue.equals("10A")){
	        				jun_nm = "10月上旬";
	        				s_date = Integer.toString(year)+"10"+"01";
	        				e_date = Integer.toString(year)+"10"+"15";
	        			}else if(kikanValue.equals("10B")){
	        				jun_nm = "10月下旬";
	        				s_date = Integer.toString(year)+"10"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"10"+lastday;
	        			}else if(kikanValue.equals("11A")){
	        				jun_nm = "11月上旬";
	        				s_date = Integer.toString(year)+"11"+"01";
	        				e_date = Integer.toString(year)+"11"+"15";
	        			}else if(kikanValue.equals("11B")){
	        				jun_nm = "11月下旬";
	        				s_date = Integer.toString(year)+"11"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"11"+lastday;
	        			}else if(kikanValue.equals("12A")){
	        				jun_nm = "12月上旬";
	        				s_date = Integer.toString(year)+"12"+"01";
	        				e_date = Integer.toString(year)+"12"+"15";
	        			}else if(kikanValue.equals("12B")){
	        				jun_nm = "12月下旬";
	        				s_date = Integer.toString(year)+"12"+"16";
	        				int lastday = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	        				e_date = Integer.toString(year)+"12"+lastday;
	        			}
	        			statement2.setString(2, jun_nm);
	        			statement2.setString(3, s_date);
	        			statement2.setString(4, e_date);
	        			statement2.executeUpdate();

	        } catch (SQLException e) {
	        	e.printStackTrace();
	        	} finally {
	        		if (connection != null) {
	        			connection.close();
	        			}
	        		}
	        }


	    public List<KakuteikikanBean>getKikanList() throws SQLException {

	         List<KakuteikikanBean>kikanList = new ArrayList<KakuteikikanBean> ();
	         Connection connection = source.getConnection();

	         try {
	             PreparedStatement statement = connection.prepareStatement(SELECT);
	             ResultSet result = statement.executeQuery();

	             while (result.next()) {
	                 KakuteikikanBean item = new KakuteikikanBean();
	                 item.setJun_kb(result.getString("jun_kb"));
	                 item.setJun_nm(result.getString("jun_nm"));
	                 item.setS_date(result.getString("s_date"));
	                 item.setE_date(result.getString("e_date"));
	                 kikanList.add(item);
	             }

	         } catch (SQLException e) {
	             e.printStackTrace();
	         } finally {
	             if (connection != null) {
	                 connection.close();
	             }
	         }
	         return kikanList;
	     }


}
