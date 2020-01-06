package jp.siw.pm.edi.util;

import java.util.List;

import jp.siw.pm.edi.bean.KakuteikikanBean;

public class Cast_Kakuteikikan {
    @SuppressWarnings("unchecked")
    public static List <KakuteikikanBean>castList(Object object) {
        return (List<KakuteikikanBean>)object;
    }
}
