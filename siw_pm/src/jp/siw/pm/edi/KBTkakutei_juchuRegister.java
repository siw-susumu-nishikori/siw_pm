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

import jp.siw.pm.edi.bean.KBTNvanBean;
import jp.siw.pm.edi.dao.KBTediDAO;
import jp.siw.pm.edi.util.CastNvan;
import jp.siw.pm.edi.util.PropertyLoader;

/**
 * Servlet implementation class KBTkakutei_juchuRegister
 */
public class KBTkakutei_juchuRegister extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public KBTkakutei_juchuRegister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String resultPage = PropertyLoader.getProperty("url.pm.edi_jsp.error");
		List<KBTNvanBean> juchuList45 = CastNvan.castList(request.getAttribute("itemList45"));
		Iterator<KBTNvanBean> iterator45 = juchuList45.iterator();
		List<KBTNvanBean> juchuList46 = CastNvan.castList(request.getAttribute("itemList46"));
		Iterator<KBTNvanBean> iterator46 = juchuList46.iterator();
		List<KBTNvanBean> juchuList47 = CastNvan.castList(request.getAttribute("itemList47"));
		Iterator<KBTNvanBean> iterator47 = juchuList47.iterator();
		//List<KBTItemBean> juchuList = Cast.castList(request.getAttribute("juchuList"));
		//Iterator<KBTItemBean> iterator_juchu = juchuList.iterator();

        Timestamp nowTime= new Timestamp(System.currentTimeMillis());
        SimpleDateFormat timeStampNowDay = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat timeStampNowTime = new SimpleDateFormat("HH:mm:ss");
        String insymd  = timeStampNowDay.format(nowTime);
        String instime = timeStampNowTime.format(nowTime);

	    int juchu_no = 0;
	    String nounyushiji_time = "0";
	    String naikaku_kb = "5";
	    String tok_cd = "1511";
        System.out.println("4/4/4/4/4/4");
	    while (iterator45.hasNext()) {KBTNvanBean item = iterator45.next();
	    String[] rec_no = {item.getKb_no()};
	    String[] kensa_kb = {item.getKb_kensa_kb()};
	    String[] juchu_ymd = {item.getInsertDay()};
	    String[] insStartTime = {item.getInsertTime()};
	    String[] hinban = {item.getKb_hin_ban()};
	    String[] nounyushiji_ymd = {item.getKb_no_shiji_dt()};
	    String[] chumon_no = {item.getKb_chumon_no()};
	    String[] noba_cd = {item.getKb_noba()};
	    String[] kojo_cd = {item.getKb_kojo()};
	    String[] hin_nm = {item.getKb_hin_name()};
	    int[] juchu_su = {Integer.parseInt(item.getKb_no_shiji_su())};
	    String[] getsudo_kb ={"0"};
	    String[] kumitate_getsudo = {"0"};
	    String[] seiban = {item.getKb_seiban()};
        System.out.println(Arrays.toString(rec_no));
	    try {
            KBTediDAO dao = new KBTediDAO();
            dao.insertKakutei(juchu_no, naikaku_kb, kensa_kb, juchu_ymd, insStartTime, nounyushiji_ymd, nounyushiji_time, tok_cd, chumon_no, noba_cd,
            					kojo_cd, hinban, hin_nm, juchu_su, getsudo_kb, kumitate_getsudo, seiban, insymd, instime, rec_no);
            System.out.println("5/5/5/5/5/5");
            request.setAttribute("juchuList47", juchuList47);

	    } catch (NamingException e) {
            request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());

        	}
	    }


	    while (iterator46.hasNext()) {KBTNvanBean item = iterator46.next();
	    String[] rec_no = {item.getKb_no()};
	    String[] kensa_kb = {item.getKb_kensa_kb()};
	    String[] juchu_ymd = {item.getInsertDay()};
	    String[] insStartTime = {item.getInsertTime()};
	    String[] hinban = {item.getKb_hin_ban()};
	    String[] nounyushiji_ymd = {item.getKb_noki()};
	    String[] chumon_no = {item.getKb_chumon_no()};
	    String[] noba_cd = {item.getKb_noba()};
	    String[] kojo_cd = {item.getKb_kojo()};
	    String[] hin_nm = {item.getKb_hin_name()};
	    int[] juchu_su = {Integer.parseInt(item.getKb_shijisu())};
	    String[] getsudo_kb ={"0"};
	    String[] kumitate_getsudo = {"0"};
	    String[] seiban = {item.getKb_seiban()};

	    try {
            KBTediDAO dao = new KBTediDAO();
            dao.insertKakutei(juchu_no, naikaku_kb, kensa_kb, juchu_ymd, insStartTime, nounyushiji_ymd, nounyushiji_time, tok_cd, chumon_no, noba_cd,
            				  kojo_cd, hinban, hin_nm, juchu_su, getsudo_kb, kumitate_getsudo, seiban, insymd, instime, rec_no);
            request.setAttribute("juchuList47", juchuList47);

	    } catch (NamingException e) {
            request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());

        	}
	    }

	    while (iterator47.hasNext()) {KBTNvanBean item = iterator47.next();
	    System.out.println("====================");
	    String zaishitsu = item.getKb_zaishit_nm47();
	    int zai_su = zaishitsu.length();
	    System.out.println("zai_su="+zai_su);
	    if(zai_su == 12){
	    	String zai12 = zaishitsu.substring(11);
	    	if(zai12 != "B" && !(item.getKb_noki().equals("0"))){
				String[] rec_no = {item.getKb_no()};
				String[] kensa_kb = {item.getKb_kensa_kb()};
				String[] juchu_ymd = {item.getInsertDay()};
				String[] insStartTime = {item.getInsertTime()};
				String[] hinban = {item.getKb_hin_ban()};
				String[] nounyushiji_ymd = {item.getKb_noki()};
				String[] chumon_no = {item.getKb_chumon_no()};
				String[] noba_cd = {item.getKb_noba()};
				String[] kojo_cd = {item.getKb_kojo()};
				String[] hin_nm = {item.getKb_hin_name()};
				int[] juchu_su = {Integer.parseInt(item.getKb_shijisu())};
				String[] getsudo_kb ={"0"};
				String[] kumitate_getsudo = {"0"};
				String[] seiban = {item.getKb_seihin_no()};

	    try {
            KBTediDAO dao = new KBTediDAO();
            dao.insertKakutei(juchu_no, naikaku_kb, kensa_kb, juchu_ymd, insStartTime, nounyushiji_ymd, nounyushiji_time, tok_cd, chumon_no, noba_cd,
            				  kojo_cd, hinban, hin_nm, juchu_su, getsudo_kb, kumitate_getsudo, seiban, insymd, instime, rec_no);
            request.setAttribute("juchuList47", juchuList47);

	    } catch (NamingException e) {
            request.setAttribute("errorMessage", e.getMessage());

        } catch (SQLException e) {
            request.setAttribute("errorMessage", e.getMessage());

        	}
	    }

	    }else if(zai_su != 12){
	    	if(!(item.getKb_noki().equals("0"))){
				String[] rec_no = {item.getKb_no()};
				String[] kensa_kb = {item.getKb_kensa_kb()};
				String[] juchu_ymd = {item.getInsertDay()};
				String[] insStartTime = {item.getInsertTime()};
				String[] hinban = {item.getKb_hin_ban()};
				String[] nounyushiji_ymd = {item.getKb_noki()};
				String[] chumon_no = {item.getKb_chumon_no()};
				String[] noba_cd = {item.getKb_noba()};
				String[] kojo_cd = {item.getKb_kojo()};
				String[] hin_nm = {item.getKb_hin_name()};
				int[] juchu_su = {Integer.parseInt(item.getKb_shijisu())};
				String[] getsudo_kb ={"0"};
				String[] kumitate_getsudo = {"0"};
				String[] seiban = {item.getKb_seihin_no()};

		    try {
	            KBTediDAO dao = new KBTediDAO();

	            dao.insertKakutei(juchu_no, naikaku_kb, kensa_kb, juchu_ymd, insStartTime, nounyushiji_ymd, nounyushiji_time, tok_cd, chumon_no, noba_cd,
	            				  kojo_cd, hinban, hin_nm, juchu_su, getsudo_kb, kumitate_getsudo, seiban, insymd, instime, rec_no);
	            request.setAttribute("juchuList47", juchuList47);

		    } catch (NamingException e) {
	            request.setAttribute("errorMessage", e.getMessage());

	        } catch (SQLException e) {
	            request.setAttribute("errorMessage", e.getMessage());

	        	}
		    }
	    }

	    }

	    resultPage = PropertyLoader.getProperty("url.pm.edi_jsp.importKBTediComplete");
        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);

	}

}

