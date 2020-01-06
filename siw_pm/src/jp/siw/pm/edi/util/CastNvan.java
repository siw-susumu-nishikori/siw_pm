package jp.siw.pm.edi.util;

import java.util.List;

import jp.siw.pm.edi.bean.KBTNvanBean;

public class CastNvan {

    @SuppressWarnings("unchecked")
    public static List <KBTNvanBean>castList(Object object) {
        return (List<KBTNvanBean>)object;
    }
}
