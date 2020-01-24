package jp.siw.pm.edi;

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

import jp.siw.pm.edi.dao.KBTediDAO;
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KBTnvanRegister
 */
public class KBTnvanRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTnvanRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.pm.edi_jsp.error");
	    String[][] NVAN =(String[][])request.getAttribute("NVAN");
    	System.out.println("KBTnvanRegister");
        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeStampNowTime = new SimpleDateFormat("HH:mm:ss");
        String insertDay  = timeStampNowDay.format(nowTime);
        String insertTime = timeStampNowTime.format(nowTime);

	    String kb_no = null;
	    String kb_kojo = null;
	    String kb_torihiki = null;
	    String kb_chumon_no = null;
	    String kb_hin_ku = null;
	    String kb_hin_ban = null;
	    String kb_ac = null;
	    String kb_kakonai = null;
	    String kb_hin_name = null;
	    String kb_shi_tan = null;
	    String kb_kensa_kb = null;
	    String kb_keiyaku_s = null;
	    String kb_hakoshu = null;
	    String kb_noba = null;
	    String kb_shuyo_su45 = null;
	    String kb_lead_time = null;
	    String kb_seiban = null;
	    String kb_yohaku45_1 = null;
	    String kb_no_shiji_dt = null;
	    String kb_no_shiji_su = null;
	    String kb_sei_kan_dt = null;
	    String kb_hakko_dt = null;
	    String kb_me_hin_no = null;
	    String kb_shukka_kojo = null;
	    String kb_kako = null;
	    String kb_hachu_tnk46 = null;
	    String kb_hachu_su46 = null;
	    String kb_zaishit_nm46 = null;
	    String kb_noba_name = null;
	    String kb_shin_hatsu_sa = null;
	    String kb_hatsu_tan = null;
	    String kb_noki = null;
	    String kb_shijisu = null;
	    String kb_tokki = null;
	    String kb_joken = null;
	    String kb_a_kb = null;
	    String kb_shuyo_su = null;
	    String kb_hachu_tnk47 = null;
	    String kb_hachu_su47 = null;
	    String kb_zai_nm47 = null;
	    String kb_zaishit_nm47 = null;
	    String kb_seihin_no = null;
	    String kb_chumon_tnk = null;
	    String kb_chumon_kin = null;
	    String kb_arazai_tan = null;
	    String kb_kako_tnk = null;

	    try{
	    	System.out.println("KBTnvanRegister2");
	    	for(int i=0; i<NVAN.length; i++){
		    	String list = NVAN[0][i];
		    	System.out.println(list);
		    	kb_no = list.substring(0,2);
		    	System.out.println(kb_no);
		    	if(kb_no.equals("45")){
		    		kb_kojo = list.substring(2,4);
		    		kb_torihiki = list.substring(4,9);
		    		kb_chumon_no = list.substring(9,17);
		    		kb_hin_ku = list.substring(17,18);
		    		kb_hin_ban = list.substring(18,28);
		    		kb_ac = list.substring(28,31);
		    		kb_kakonai = list.substring(31,34);
		    		kb_hin_name = list.substring(34,54);
		    		kb_shi_tan = list.substring(54,57);
		    		kb_kensa_kb = list.substring(57,59);
		    		kb_keiyaku_s = list.substring(59,60);
		    		kb_hakoshu = list.substring(60,65);
		    		kb_noba = list.substring(65,71);
		    		kb_shuyo_su45 = list.substring(71,75);
		    		kb_lead_time = list.substring(75,78);
		    		kb_seiban = list.substring(78,88);
		    		kb_yohaku45_1 = list.substring(88,98);
		    		kb_no_shiji_dt = list.substring(98,104);
		    		kb_no_shiji_su = list.substring(104,109);
		    		kb_sei_kan_dt = list.substring(109,115);
		    		kb_hakko_dt = list.substring(115,121);
		    		kb_me_hin_no = list.substring(121,134);
		    		kb_shukka_kojo = list.substring(134,137);

		    		KBTediDAO dao = new KBTediDAO();
		    		dao.insertNvan45(kb_no, kb_kojo, kb_torihiki, kb_chumon_no, kb_hin_ku, kb_hin_ban, kb_ac, kb_kakonai, kb_hin_name, kb_shi_tan,
		    						 kb_kensa_kb, kb_keiyaku_s, kb_hakoshu, kb_noba, kb_shuyo_su45, kb_lead_time, kb_seiban, kb_yohaku45_1, kb_no_shiji_dt,
		    						 kb_no_shiji_su, kb_sei_kan_dt, kb_hakko_dt, kb_me_hin_no, kb_shukka_kojo, insertDay, insertTime );

		    	}else if(kb_no.equals("46")){
		    		kb_kojo = list.substring(2,4);
		    		kb_torihiki = list.substring(4,9);
		    		kb_chumon_no = list.substring(9,17);
		    		kb_hin_ku = list.substring(17,18);
		    		kb_hin_ban = list.substring(18,28);
		    		kb_ac = list.substring(28,31);
		    		kb_kako = list.substring(31,34);
		    		kb_hachu_tnk46 = list.substring(34,37);
		    		kb_hachu_su46 = list.substring(37,43);
		    		kb_hin_name = list.substring(43,63);
		    		kb_zaishit_nm46 = list.substring(63,75);
		    		kb_noba = list.substring(75,81);
		    		kb_noba_name = list.substring(81,101);
		    		kb_kensa_kb = list.substring(101,103);
		    		kb_shin_hatsu_sa = list.substring(103,104);
		    		kb_seiban = list.substring(104,114);
		    		kb_hatsu_tan = list.substring(114,117);
		    		kb_shi_tan = list.substring(117,120);
		    		kb_noki = list.substring(120,124);
		    		kb_shijisu = list.substring(124,130);
		    		kb_tokki = list.substring(130,150);
		    		kb_hakko_dt = list.substring(150,156);
		    		kb_joken = list.substring(156,157);
		    		kb_a_kb = list.substring(157,159);
		    		kb_shukka_kojo = list.substring(159,162);
		    		kb_me_hin_no = list.substring(162,175);
		    		kb_hakoshu = list.substring(175,180);
		    		kb_shuyo_su = list.substring(180,185);

		    		KBTediDAO dao = new KBTediDAO();
		    		dao.insertNvan46(kb_no, kb_kojo, kb_torihiki, kb_chumon_no, kb_hin_ku, kb_hin_ban, kb_ac, kb_kako, kb_hachu_tnk46, kb_hachu_su46,
		    						 kb_hin_name, kb_zaishit_nm46, kb_noba, kb_noba_name, kb_kensa_kb, kb_shin_hatsu_sa, kb_seiban, kb_hatsu_tan, kb_shi_tan,
		    						 kb_noki, kb_shijisu, kb_tokki, kb_hakko_dt, kb_joken, kb_a_kb, kb_shukka_kojo, kb_me_hin_no, kb_hakoshu, kb_shuyo_su,
		    						 insertDay, insertTime);

		    	}else if(kb_no.equals("47")){
		    		kb_kojo = list.substring(2,4);
		    		kb_torihiki = list.substring(4,9);
		    		kb_chumon_no = list.substring(9,17);
		    		kb_hin_ku = list.substring(17,18);
		    		kb_hin_ban = list.substring(18,28);
		    		kb_ac = list.substring(28,31);
		    		kb_kako = list.substring(31,34);
		    		kb_hachu_tnk47 = list.substring(34,37);
		    		kb_hachu_su47 = list.substring(37,43);
		    		kb_hin_name = list.substring(43,63);
		    		kb_zai_nm47 = list.substring(63,75);
		    	    kb_zaishit_nm47 = String.format("%-12s", kb_zai_nm47);
		    		kb_noba = list.substring(75,81);
		    		kb_noba_name = list.substring(81,101);
		    		kb_kensa_kb = list.substring(101,103);
		    		kb_shin_hatsu_sa = list.substring(103,104);
		    		kb_seihin_no = list.substring(104,114);
		    		kb_hatsu_tan = list.substring(114,117);
		    		kb_shi_tan = list.substring(117,120);
		    		kb_chumon_tnk = list.substring(120,129);
		    		kb_chumon_kin = list.substring(129,138);
		    		kb_arazai_tan = list.substring(138,147);
		    		kb_kako_tnk = list.substring(147,156);
		    		kb_noki = list.substring(156,160);
		    		kb_shijisu = list.substring(160,166);
		    		kb_tokki = list.substring(166,186);
		    		kb_hakko_dt = list.substring(186,192);
		    		kb_joken = list.substring(192,193);
		    		kb_a_kb = list.substring(193,195);
		    		kb_shukka_kojo = list.substring(195,198);
		    		kb_me_hin_no = list.substring(198,211);
		    		kb_hakoshu = list.substring(211,216);
		    		kb_shuyo_su = list.substring(216,221);
		    		KBTediDAO dao = new KBTediDAO();
		    		dao.insertNvan47(kb_no, kb_kojo, kb_torihiki, kb_chumon_no, kb_hin_ku, kb_hin_ban, kb_ac, kb_kako, kb_hachu_tnk47, kb_hachu_su47,
		    						 kb_hin_name, kb_zaishit_nm47, kb_noba, kb_noba_name, kb_kensa_kb, kb_shin_hatsu_sa, kb_seihin_no, kb_hatsu_tan, kb_shi_tan, kb_chumon_tnk,
		    						 kb_chumon_kin, kb_arazai_tan, kb_kako_tnk, kb_noki, kb_shijisu, kb_tokki, kb_hakko_dt, kb_joken, kb_a_kb, kb_shukka_kojo, kb_me_hin_no,
		    						 kb_hakoshu, kb_shuyo_su, insertDay, insertTime);
		    		}
	            System.out.println("1/1/1/1/1/1");
		    	}

	    	request.setAttribute("message", PropertyLoader.getProperty("message.completeInsertion"));
	    	resultPage = PropertyLoader.getProperty("url.servlet.KBTkakuteiChushutsu");

	    }catch (NamingException e) {
	    	request.setAttribute("errorMessage", e.getMessage());

	    } catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());

		} catch (NumberFormatException e) {
			request.setAttribute("errorMessage", PropertyLoader.getProperty("message.NumberFormatException"));
			}
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
		}

	}


