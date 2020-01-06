package jp.siw.pm.edi;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.siw.pm.edi.bean.KBTItemBean;
import jp.siw.pm.edi.dao.KBTediDAO;
import jp.siw.pm.edi.util.Cast;
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KBTnaiji_juchuRegister
 */
public class KBTnaiji_juchuRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTnaiji_juchuRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.jsp.error");
		List<KBTItemBean> kb35juchuList = Cast.castList(request.getAttribute("itemList"));
		Iterator<KBTItemBean> iterator = kb35juchuList.iterator();
		List<KBTItemBean> juchuList = Cast.castList(request.getAttribute("juchuList"));
		Iterator<KBTItemBean> iterator_juchu = juchuList.iterator();

        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeStampNowTime = new SimpleDateFormat("HH:mm:ss");
        String insymd  = timeStampNowDay.format(nowTime);
        String instime = timeStampNowTime.format(nowTime);

	    int juchu_no = 0;
	    String nounyushiji_time = "0";
	    String chumon_no = "0";
	    String naikaku_kb = "3";
	    String tok_cd = "1511";
	    int add_kb = 0;

	    String[] S_time = {null};
	    while (iterator_juchu.hasNext()) {KBTItemBean item2 = iterator_juchu.next();
	    String[] insS_Time ={item2.getInsStartTime()};
	    S_time = insS_Time;
	    }

	    while (iterator.hasNext()) {KBTItemBean item = iterator.next();
	    System.out.println("===========================================");
	    String[] kensa_kb = {item.getKb_kensa_kb()};
	    String[] juchu_ymd = {item.getInsertDay()};
	    String[] insStartTime = {item.getInsertTime()};
	    String[] hinban = {item.getKb_hin_ban()};
	    String[] nounyushiji_ymd = {item.getKb_shiji_dt_01(), item.getKb_shiji_dt_02(), item.getKb_shiji_dt_03(), item.getKb_shiji_dt_04(), item.getKb_shiji_dt_05(),
	    		 item.getKb_shiji_dt_06(), item.getKb_shiji_dt_07(), item.getKb_shiji_dt_08(), item.getKb_shiji_dt_09(), item.getKb_shiji_dt_10(), item.getKb_shiji_dt_11(),
	    		 item.getKb_shiji_dt_12(), item.getKb_shiji_dt_13(), item.getKb_shiji_dt_14(), item.getKb_shiji_dt_15(), item.getKb_shiji_dt_16(), item.getKb_shiji_dt_17(),
	    		 item.getKb_shiji_dt_18(), item.getKb_shiji_dt_19(), item.getKb_shiji_dt_20(), item.getKb_shiji_dt_21(), item.getKb_shiji_dt_22(), item.getKb_shiji_dt_23(),
	    		 item.getKb_shiji_dt_24(), item.getKb_shiji_dt_25(), item.getKb_shiji_dt_26(), item.getKb_shiji_dt_27(), item.getKb_shiji_dt_28(), item.getKb_shiji_dt_29(),
	    		 item.getKb_shiji_dt_30(), item.getKb_shiji_dt_31()};
	    String[] noba_cd = {item.getKb_noba()};
	    String[] kojo_cd = {item.getKb_kojo()};
	    String[] hin_nm = {item.getKb_hin_name()};
	    int[] juchu_su = {Integer.parseInt(item.getKb_shiji_su_01()), Integer.parseInt(item.getKb_shiji_su_02()), Integer.parseInt(item.getKb_shiji_su_03()), Integer.parseInt(item.getKb_shiji_su_04()), Integer.parseInt(item.getKb_shiji_su_05()),
	    		Integer.parseInt(item.getKb_shiji_su_06()), Integer.parseInt(item.getKb_shiji_su_07()), Integer.parseInt(item.getKb_shiji_su_08()), Integer.parseInt(item.getKb_shiji_su_09()), Integer.parseInt(item.getKb_shiji_su_10()), Integer.parseInt(item.getKb_shiji_su_11()),
	    		Integer.parseInt(item.getKb_shiji_su_12()), Integer.parseInt(item.getKb_shiji_su_13()), Integer.parseInt(item.getKb_shiji_su_14()), Integer.parseInt(item.getKb_shiji_su_15()), Integer.parseInt(item.getKb_shiji_su_16()), Integer.parseInt(item.getKb_shiji_su_17()),
	    		Integer.parseInt(item.getKb_shiji_su_18()), Integer.parseInt(item.getKb_shiji_su_19()), Integer.parseInt(item.getKb_shiji_su_20()), Integer.parseInt(item.getKb_shiji_su_21()), Integer.parseInt(item.getKb_shiji_su_22()), Integer.parseInt(item.getKb_shiji_su_23()),
	    		Integer.parseInt(item.getKb_shiji_su_24()), Integer.parseInt(item.getKb_shiji_su_25()), Integer.parseInt(item.getKb_shiji_su_26()), Integer.parseInt(item.getKb_shiji_su_27()), Integer.parseInt(item.getKb_shiji_su_28()), Integer.parseInt(item.getKb_shiji_su_29()),
	    		Integer.parseInt(item.getKb_shiji_su_30()), Integer.parseInt(item.getKb_shiji_su_31())};

	    String[] getsudo_kb = {item.getKb_getsudo_shiki()};
	    String[] kumitate_getsudo = {item.getKb_kumi_getsudo()};
System.out.println(Arrays.toString(hinban));

    try {
            KBTediDAO dao = new KBTediDAO();

            dao.insertkb35juchu(juchu_no, naikaku_kb, kensa_kb, juchu_ymd, insStartTime, nounyushiji_ymd, nounyushiji_time, tok_cd, chumon_no, noba_cd,
            					kojo_cd, hinban, hin_nm, juchu_su, getsudo_kb, kumitate_getsudo, add_kb, insymd, instime/*, S_time*/);
            request.setAttribute("kb35juchuList", kb35juchuList);

	    } catch (NamingException e) {
            request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());

        }

	    }

	    resultPage = PropertyLoader.getProperty("url.jsp.importKBTediComplete");
        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);

	}

}

