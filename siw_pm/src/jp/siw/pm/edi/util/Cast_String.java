package jp.siw.pm.edi.util;

import java.util.List;

public class Cast_String {
    @SuppressWarnings("unchecked")
    public static List <String>castList(Object object) {
        return (List<String>)object;
    }
}
