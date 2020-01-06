package jp.siw.pm.edi.util;

import java.util.List;

import jp.siw.pm.edi.bean.CsvImportBean;

public class Cast_CsvImportBean {
    @SuppressWarnings("unchecked")
    public static List <CsvImportBean>castList(Object object) {
        return (List<CsvImportBean>)object;
    }
}
