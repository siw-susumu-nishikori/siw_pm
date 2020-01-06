package jp.siw.pm.edi.util;

import java.util.List;

import jp.siw.pm.edi.bean.KBTItemBean;

public class Cast {

    @SuppressWarnings("unchecked")
    public static List <KBTItemBean>castList(Object object) {
        return (List<KBTItemBean>)object;
    }
}
