package com.hailian.api.constant;

import java.util.HashMap;

/**
 *
 */
public class ReportTypeCons {
	 
	
    public static final String BASE_ZH = "1";

    public static final String BASE_EN = "7";
    /**
     * 商业信息报告
     */
    public static final String BUSI_ZH = "8";

    public static final String BUSI_EN = "9";

    public static final String CRED_ZH = "10";

    public static final String CRED_EN = "11";

    public static final String ROC_ZH = "12";

    public static final String ROC_EN = "14";

    public static final String ROC_HY = "15";
    
    public static final String[] ZH_ARRAY = new String[] {BASE_ZH,BUSI_ZH,CRED_ZH,ROC_ZH };
    public static final String[] EN_ARRAY = new String[] {BASE_EN,BUSI_EN,CRED_EN,ROC_EN };
    public static final String[] HY_ARRAY = new String[] {ROC_HY};
    
    public static boolean isRoc102(String reportType) {
    	if(ROC_HY.equals(reportType)||ROC_EN.equals(reportType)||ROC_ZH.equals(reportType)) {
    		return true;
    	}
    	
		return false;
    }
    
    /**
     * 
     * @param reportType
     * @return
     * ZH
     * EN
     * HY
     */
    public static String whichLanguage(String reportType) {
    	for (String str : ZH_ARRAY) {
			if(str.equals(reportType)) return "ZH";
		}
    	for (String str : EN_ARRAY) {
			if(str.equals(reportType)) return "EN";
		}
    	for (String str : HY_ARRAY) {
			if(str.equals(reportType)) return "HY";
		}
		return "";
    }
    
}
