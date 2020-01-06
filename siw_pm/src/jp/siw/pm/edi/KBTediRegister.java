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
 * Servlet implementation class Validator
 */
public class KBTediRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.jsp.error");
	    String[][] kb35 =(String[][])request.getAttribute("kb35");

        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeStampNowTime = new SimpleDateFormat("HH:mm:ss");
        String insertDay  = timeStampNowDay.format(nowTime);
        String insertTime = timeStampNowTime.format(nowTime);

	    int kanri_no = 0;
	    String kb_no = null;
	    String kb_kojo = null;
	    String kb_torihiki = null;
	    String kb_nitei_line_at = null;
	    String kb_keisan_dt = null;
	    String kb_sei_kan_dt35 = null;
	    String kb_kojo_henko = null;
	    String kb_hin_ku = null;
	    String kb_hin_ban = null;
	    String kb_ac = null;
	    String kb_kakonai = null;
	    String kb_hin_name = null;
	    String kb_noba = null;
	    String kb_hakoshu = null;
	    String kb_shuyo_su = null;
	    String kb_matome_kb = null;
	    String kb_keitai_kb = null;
	    String kb_kensa_kb = null;
	    String kb_tehai_kb = null;
	    String kb_teiki_hoju_kb = null;
	    String kb_lead_time35 = null;
	    String kb_teiten_dt = null;
	    String kb_shukka_kojo = null;
	    String kb_tori_saki_hin = null;
	    String kb_getsudo_shiki = null;
	    String kb_kumi_getsudo = null;
	    String kb_hitsuyosu_gk = null;
	    String kb_hitsuyosu_01 = null;
	    String kb_hitsuyosu_02 = null;
	    String kb_hitsuyosu_03 = null;
	    String kb_hitsuyosu_04 = null;
	    String kb_hitsuyosu_05 = null;
	    String kb_hitsuyosu_06 = null;
	    String kb_hitsuyosu_07 = null;
	    String kb_hitsuyosu_08 = null;
	    String kb_hitsuyosu_09 = null;
	    String kb_hitsuyosu_10 = null;
	    String kb_hitsuyosu_11 = null;
	    String kb_hitsuyosu_12 = null;
	    String kb_hitsuyosu_13 = null;
	    String kb_hitsuyosu_14 = null;
	    String kb_hitsuyosu_15 = null;
	    String kb_hitsuyosu_16 = null;
	    String kb_hitsuyosu_17 = null;
	    String kb_hitsuyosu_18 = null;
	    String kb_hitsuyosu_19 = null;
	    String kb_hitsuyosu_20 = null;
	    String kb_hitsuyosu_21 = null;
	    String kb_hitsuyosu_22 = null;
	    String kb_hitsuyosu_23 = null;
	    String kb_hitsuyosu_24 = null;
	    String kb_hitsuyosu_25 = null;
	    String kb_hitsuyosu_26 = null;
	    String kb_hitsuyosu_27 = null;
	    String kb_hitsuyosu_28 = null;
	    String kb_hitsuyosu_29 = null;
	    String kb_hitsuyosu_30 = null;
	    String kb_hitsuyosu_31 = null;
	    String kb_shiji_yohaku = null;
	    String kb_shiji_dt_01 = null;
	    String kb_shiji_dt_02 = null;
	    String kb_shiji_dt_03 = null;
	    String kb_shiji_dt_04 = null;
	    String kb_shiji_dt_05 = null;
	    String kb_shiji_dt_06 = null;
	    String kb_shiji_dt_07 = null;
	    String kb_shiji_dt_08 = null;
	    String kb_shiji_dt_09 = null;
	    String kb_shiji_dt_10 = null;
	    String kb_shiji_dt_11 = null;
	    String kb_shiji_dt_12 = null;
	    String kb_shiji_dt_13 = null;
	    String kb_shiji_dt_14 = null;
	    String kb_shiji_dt_15 = null;
	    String kb_shiji_dt_16 = null;
	    String kb_shiji_dt_17 = null;
	    String kb_shiji_dt_18 = null;
	    String kb_shiji_dt_19 = null;
	    String kb_shiji_dt_20 = null;
	    String kb_shiji_dt_21 = null;
	    String kb_shiji_dt_22 = null;
	    String kb_shiji_dt_23 = null;
	    String kb_shiji_dt_24 = null;
	    String kb_shiji_dt_25 = null;
	    String kb_shiji_dt_26 = null;
	    String kb_shiji_dt_27 = null;
	    String kb_shiji_dt_28 = null;
	    String kb_shiji_dt_29 = null;
	    String kb_shiji_dt_30 = null;
	    String kb_shiji_dt_31 = null;
	    String kb_shiji_su_gk = null;
	    String kb_shiji_su_01 = null;
	    String kb_shiji_su_02 = null;
	    String kb_shiji_su_03 = null;
	    String kb_shiji_su_04 = null;
	    String kb_shiji_su_05 = null;
	    String kb_shiji_su_06 = null;
	    String kb_shiji_su_07 = null;
	    String kb_shiji_su_08 = null;
	    String kb_shiji_su_09 = null;
	    String kb_shiji_su_10 = null;
	    String kb_shiji_su_11 = null;
	    String kb_shiji_su_12 = null;
	    String kb_shiji_su_13 = null;
	    String kb_shiji_su_14 = null;
	    String kb_shiji_su_15 = null;
	    String kb_shiji_su_16 = null;
	    String kb_shiji_su_17 = null;
	    String kb_shiji_su_18 = null;
	    String kb_shiji_su_19 = null;
	    String kb_shiji_su_20 = null;
	    String kb_shiji_su_21 = null;
	    String kb_shiji_su_22 = null;
	    String kb_shiji_su_23 = null;
	    String kb_shiji_su_24 = null;
	    String kb_shiji_su_25 = null;
	    String kb_shiji_su_26 = null;
	    String kb_shiji_su_27 = null;
	    String kb_shiji_su_28 = null;
	    String kb_shiji_su_29 = null;
	    String kb_shiji_su_30 = null;
	    String kb_shiji_su_31 = null;


    try{
    	for(int i=0; i<kb35.length; i++){
	    	String list = kb35[0][i];
	    	kb_no = list.substring(0,2);
	    	kb_kojo = list.substring(2,4);
	    	kb_torihiki = list.substring(4,9);
	    	kb_nitei_line_at = list.substring(9,12);
	    	kb_keisan_dt = list.substring(12,18);
	    	kb_sei_kan_dt35 = list.substring(18,24);
	    	kb_kojo_henko = list.substring(24,26);
	    	kb_hin_ku = list.substring(26,27);
    		kb_hin_ban = list.substring(27,37);
    		kb_ac = list.substring(37,40);
    		kb_kakonai = list.substring(40,43);
    		kb_hin_name = list.substring(43,63);
    		kb_noba = list.substring(63,69);
    		kb_hakoshu = list.substring(69,74);
    		kb_shuyo_su = list.substring(74,79);
    		kb_matome_kb = list.substring(79,80);
    		kb_keitai_kb = list.substring(80,81);
    		kb_kensa_kb = list.substring(81,83);
    		kb_tehai_kb = list.substring(83,84);
    		kb_teiki_hoju_kb = list.substring(84,86);
    		kb_lead_time35 = list.substring(86,89);
    		kb_teiten_dt = list.substring(89,91);
    		kb_shukka_kojo = list.substring(91,94);
    		kb_tori_saki_hin = list.substring(94,107);
    		kb_getsudo_shiki = list.substring(107,109);
    		kb_kumi_getsudo = list.substring(109,113);
    		kb_hitsuyosu_gk = list.substring(113,120);
    		kb_hitsuyosu_01 = list.substring(120,125);
    		kb_hitsuyosu_02 = list.substring(125,130);
    		kb_hitsuyosu_03 = list.substring(130,135);
    		kb_hitsuyosu_04 = list.substring(135,140);
    		kb_hitsuyosu_05 = list.substring(140,145);
    		kb_hitsuyosu_06 = list.substring(145,150);
    		kb_hitsuyosu_07 = list.substring(150,155);
    		kb_hitsuyosu_08 = list.substring(155,160);
    		kb_hitsuyosu_09 = list.substring(160,165);
    		kb_hitsuyosu_10 = list.substring(165,170);
    		kb_hitsuyosu_11 = list.substring(170,175);
    		kb_hitsuyosu_12 = list.substring(175,180);
    		kb_hitsuyosu_13 = list.substring(180,185);
    		kb_hitsuyosu_14 = list.substring(185,190);
    		kb_hitsuyosu_15 = list.substring(190,195);
    		kb_hitsuyosu_16 = list.substring(195,200);
    		kb_hitsuyosu_17 = list.substring(200,205);
    		kb_hitsuyosu_18 = list.substring(205,210);
    		kb_hitsuyosu_19 = list.substring(210,215);
    		kb_hitsuyosu_20 = list.substring(215,220);
    		kb_hitsuyosu_21 = list.substring(220,225);
    		kb_hitsuyosu_22 = list.substring(225,230);
    		kb_hitsuyosu_23 = list.substring(230,235);
    		kb_hitsuyosu_24 = list.substring(235,240);
    		kb_hitsuyosu_25 = list.substring(240,245);
    		kb_hitsuyosu_26 = list.substring(245,250);
    		kb_hitsuyosu_27 = list.substring(250,255);
    		kb_hitsuyosu_28 = list.substring(255,260);
    		kb_hitsuyosu_29 = list.substring(260,265);
    		kb_hitsuyosu_30 = list.substring(265,270);
    		kb_hitsuyosu_31 = list.substring(270,275);
    		kb_shiji_yohaku = list.substring(275,282);
    		kb_shiji_dt_01 = list.substring(282,287);
    		kb_shiji_dt_02 = list.substring(287,292);
    		kb_shiji_dt_03 = list.substring(292,297);
    		kb_shiji_dt_04 = list.substring(297,302);
    		kb_shiji_dt_05 = list.substring(302,307);
    		kb_shiji_dt_06 = list.substring(307,312);
    		kb_shiji_dt_07 = list.substring(312,317);
    		kb_shiji_dt_08 = list.substring(317,322);
    		kb_shiji_dt_09 = list.substring(322,327);
    		kb_shiji_dt_10 = list.substring(327,332);
    		kb_shiji_dt_11 = list.substring(332,337);
    		kb_shiji_dt_12 = list.substring(337,342);
    		kb_shiji_dt_13 = list.substring(342,347);
    		kb_shiji_dt_14 = list.substring(347,352);
    		kb_shiji_dt_15 = list.substring(352,357);
    		kb_shiji_dt_16 = list.substring(357,362);
    		kb_shiji_dt_17 = list.substring(362,367);
    		kb_shiji_dt_18 = list.substring(367,372);
    		kb_shiji_dt_19 = list.substring(372,377);
    		kb_shiji_dt_20 = list.substring(377,382);
    		kb_shiji_dt_21 = list.substring(382,387);
    		kb_shiji_dt_22 = list.substring(387,392);
    		kb_shiji_dt_23 = list.substring(392,397);
    		kb_shiji_dt_24 = list.substring(397,402);
    		kb_shiji_dt_25 = list.substring(402,407);
    		kb_shiji_dt_26 = list.substring(407,412);
    		kb_shiji_dt_27 = list.substring(412,417);
    		kb_shiji_dt_28 = list.substring(417,422);
    		kb_shiji_dt_29 = list.substring(422,427);
    		kb_shiji_dt_30 = list.substring(427,432);
    		kb_shiji_dt_31 = list.substring(432,437);
    		kb_shiji_su_gk = list.substring(437,444);
    		kb_shiji_su_01 = list.substring(444,449);
    		kb_shiji_su_02 = list.substring(449,454);
    		kb_shiji_su_03 = list.substring(454,459);
    		kb_shiji_su_04 = list.substring(459,464);
    		kb_shiji_su_05 = list.substring(464,469);
    		kb_shiji_su_06 = list.substring(469,474);
    		kb_shiji_su_07 = list.substring(474,479);
    		kb_shiji_su_08 = list.substring(479,484);
    		kb_shiji_su_09 = list.substring(484,489);
    		kb_shiji_su_10 = list.substring(489,494);
    		kb_shiji_su_11 = list.substring(494,499);
    		kb_shiji_su_12 = list.substring(499,504);
    		kb_shiji_su_13 = list.substring(504,509);
    		kb_shiji_su_14 = list.substring(509,514);
    		kb_shiji_su_15 = list.substring(514,519);
    		kb_shiji_su_16 = list.substring(519,524);
    		kb_shiji_su_17 = list.substring(524,529);
    		kb_shiji_su_18 = list.substring(529,534);
    		kb_shiji_su_19 = list.substring(534,539);
    		kb_shiji_su_20 = list.substring(539,544);
    		kb_shiji_su_21 = list.substring(544,549);
    		kb_shiji_su_22 = list.substring(549,554);
    		kb_shiji_su_23 = list.substring(554,559);
    		kb_shiji_su_24 = list.substring(559,564);
    		kb_shiji_su_25 = list.substring(564,569);
    		kb_shiji_su_26 = list.substring(569,574);
    		kb_shiji_su_27 = list.substring(574,579);
    		kb_shiji_su_28 = list.substring(579,584);
    		kb_shiji_su_29 = list.substring(584,589);
    		kb_shiji_su_30 = list.substring(589,594);
    		kb_shiji_su_31 = list.substring(594,599);

    		//System.out.println(list);

    		KBTediDAO dao = new KBTediDAO();
    		dao.insertItem(kanri_no, kb_no,	kb_kojo, kb_torihiki, kb_nitei_line_at, kb_keisan_dt, kb_sei_kan_dt35, kb_kojo_henko, kb_hin_ku, kb_hin_ban, kb_ac, kb_kakonai, kb_hin_name, kb_noba,
    				kb_hakoshu, kb_shuyo_su, kb_matome_kb, kb_keitai_kb, kb_kensa_kb, kb_tehai_kb, kb_teiki_hoju_kb, kb_lead_time35, kb_teiten_dt, kb_shukka_kojo, kb_tori_saki_hin, kb_getsudo_shiki,
    				kb_kumi_getsudo, kb_hitsuyosu_gk, kb_hitsuyosu_01, kb_hitsuyosu_02, kb_hitsuyosu_03, kb_hitsuyosu_04, kb_hitsuyosu_05, kb_hitsuyosu_06, kb_hitsuyosu_07, kb_hitsuyosu_08, kb_hitsuyosu_09,
    				kb_hitsuyosu_10, kb_hitsuyosu_11, kb_hitsuyosu_12, kb_hitsuyosu_13, kb_hitsuyosu_14, kb_hitsuyosu_15, kb_hitsuyosu_16, kb_hitsuyosu_17, kb_hitsuyosu_18, kb_hitsuyosu_19, kb_hitsuyosu_20,
    				kb_hitsuyosu_21, kb_hitsuyosu_22, kb_hitsuyosu_23, kb_hitsuyosu_24, kb_hitsuyosu_25, kb_hitsuyosu_26, kb_hitsuyosu_27, kb_hitsuyosu_28, kb_hitsuyosu_29, kb_hitsuyosu_30, kb_hitsuyosu_31,
    				kb_shiji_yohaku, kb_shiji_dt_01, kb_shiji_dt_02, kb_shiji_dt_03, kb_shiji_dt_04, kb_shiji_dt_05, kb_shiji_dt_06, kb_shiji_dt_07, kb_shiji_dt_08, kb_shiji_dt_09, kb_shiji_dt_10, kb_shiji_dt_11,
    				kb_shiji_dt_12, kb_shiji_dt_13, kb_shiji_dt_14, kb_shiji_dt_15, kb_shiji_dt_16, kb_shiji_dt_17, kb_shiji_dt_18, kb_shiji_dt_19, kb_shiji_dt_20, kb_shiji_dt_21, kb_shiji_dt_22, kb_shiji_dt_23,
    				kb_shiji_dt_24, kb_shiji_dt_25, kb_shiji_dt_26, kb_shiji_dt_27, kb_shiji_dt_28, kb_shiji_dt_29, kb_shiji_dt_30, kb_shiji_dt_31, kb_shiji_su_gk, kb_shiji_su_01, kb_shiji_su_02, kb_shiji_su_03,
    				kb_shiji_su_04, kb_shiji_su_05, kb_shiji_su_06, kb_shiji_su_07, kb_shiji_su_08, kb_shiji_su_09, kb_shiji_su_10, kb_shiji_su_11, kb_shiji_su_12, kb_shiji_su_13, kb_shiji_su_14, kb_shiji_su_15,
    				kb_shiji_su_16, kb_shiji_su_17, kb_shiji_su_18, kb_shiji_su_19, kb_shiji_su_20, kb_shiji_su_21, kb_shiji_su_22, kb_shiji_su_23, kb_shiji_su_24, kb_shiji_su_25, kb_shiji_su_26, kb_shiji_su_27,
    				kb_shiji_su_28, kb_shiji_su_29, kb_shiji_su_30, kb_shiji_su_31, insertDay, insertTime);

    	}

    	request.setAttribute("message", PropertyLoader.getProperty("message.completeInsertion"));
    	resultPage = PropertyLoader.getProperty("url.servlet.KBTnaijiChushutsu");

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
