package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.jfinal.kit.PathKit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基本信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class BaseInfoZh {

    public static void main(String args[]) throws Exception{
        //getForm?tableName=credit_company_info&className=CreditCompanyInfo*company_id
        String s = "getForm?tableName=credit_company_info&className=CreditCompanyInfo*company_id";
        //Map<String,String> map = parseUrl(s);
        //System.out.println(map.get("tableName"));
    }

    /**
     * 生成基本报告
     * @param reportType  报告类型
     * @param orderId     订单ID
     * @param companyId   公司ID
     * @param sysLanguage 语言
     * @param userid 当前登录人
     */
    public static void reportTable(String reportType, String orderId, String companyId, String sysLanguage, Integer userid) {
        //报告类型
        //String reportType = "1";
        //语言
        //String sysLanguage = "612";
        //公司id
        //String companyId = "77";
        //项目路劲
        String webRoot = PathKit.getWebRootPath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String _prePath = webRoot + "/upload/tmp/" + reportType + sysLanguage + companyId;
        HashMap<String, Object> map = new HashMap<String, Object>();
        //获取订单信息
        CreditCompanyInfo order = CreditCompanyInfo.dao.findById(companyId);
        //订单公司名称
        map.put("company", order.getStr("name_en"));
        //联信编码
        map.put("code", order.getStr("lianxin_id"));
        map.put("date", sdf.format(new Date()));

        //找到当前报告类型下的父节点
        List<CreditReportModuleConf> crmcs = CreditReportModuleConf.dao.findByReport(reportType);
        for (CreditReportModuleConf crmc : crmcs) {
            //找到当前父节点下的子节点
            List<CreditReportModuleConf> child = CreditReportModuleConf.dao.findSon(crmc.get("id").toString(), reportType);
            String tempName = crmc.getStr("temp_name");
            String source = crmc.getStr("get_source");
            String confId = crmc.getInt("id") + "";
            //无url的跳过取数
            if (source == null || "".equals(source)) {
                continue;
            }
            Map<String, String> params = BaseWord.parseUrl(source);
            String tableName = params.get("tableName");
            String clName = params.get("className");
            if (clName == null || "".equals(clName)) {
                continue;
            }
            String[] requireds = clName.split("\\*");
            String className = requireds.length > 0 ? requireds[0] : "";
            ReportInfoGetDataController report = new ReportInfoGetDataController();
            if ("企业注册信息".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = BaseWord.createTableS(child, rows);
                map.put("regist", table);
            }
            if ("历史变更记录".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = BaseWord.createTableH(child, rows);
                map.put("history", table);
            }
            if ("股东信息".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = BaseWord.createTableH(child, rows);
                map.put("partner", table);
            }
            if("法人股东详情".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = BaseWord.createTableS(child, rows);
                map.put("details", table);
            }
            if("投资情况".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = BaseWord.createTableH(child, rows);
                map.put("invest", table);
            }
            if("管理层".equals(tempName)){
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = BaseWord.createTableS(child, rows);
                map.put("leader", table);
            }
        }

        String str = "该公司目前主要从事管道支吊架、垃圾给料机、钢结构件（除建筑构件）的制造、加工；机械零部件加工；金属材料的批发、零售、代购代销。\n" +
                "周**先生目前在该公司担任董事长。\n" +
                "该公司目前有120名员工。\n" +
                "该公司目前在首页所述之地址办公。该地址位于浙江丽水市***********，面积未能获知。\n";

        //总结
        map.put("result", str);


        //MainWord.buildWord(map, "h://word/_基本信息报告样本.docx", "h://1.docx");
        BaseWord.buildWord(map, webRoot + "/word/" + "_基本信息报告样本.docx", _prePath + ".docx");

        //注册信息-表格
        //RowRenderData header2 = RowRenderData.build(new TextRenderData("000000", "姓名"), new TextRenderData("000000", "学历"));
        /*RowRenderData row0_2 = RowRenderData.build("统一社会信用代码123", "9133110073946505XY");
                RowRenderData row1_2 = RowRenderData.build("法人代表", "周**");
                RowRenderData row2_2 = RowRenderData.build("公司性质", "私人有限公司");
                RowRenderData row3_2 = RowRenderData.build("成立日期", "2002-06-12");
                RowRenderData row4_2 = RowRenderData.build("营业期限至", "2032-06-11");
                RowRenderData row5_2 = RowRenderData.build("注册资本", "21,000,000");
                RowRenderData row6_2 = RowRenderData.build("注册地址", "浙江丽水市********");
                MiniTableRenderData list2 = new MiniTableRenderData(Arrays.asList(row0_2, row1_2, row2_2, row3_2, row4_2, row5_2, row6_2));*/
        //map.put("regist", list2);

        //历史变更信息-表格
        /*RowRenderData header3 = RowRenderData.build(
                new TextRenderData("000000", "日期"),
                new TextRenderData("000000", "变更项"),
                new TextRenderData("000000", "变更前"),
                new TextRenderData("000000", "变更后")
        );
        RowRenderData row0_3 = RowRenderData.build("2009-03-19", "注册地址", "海曙环城西路********", "浙江丽水市*****");
        MiniTableRenderData list3 = new MiniTableRenderData(header3, Arrays.asList(row0_3));
        map.put("history", list3);*/



        //股东-表格
        /*RowRenderData header4 = RowRenderData.build(
                new TextRenderData("000000", "姓名"),
                new TextRenderData("000000", "国家/国籍"),
                new TextRenderData("000000", "出资比例(%)")
        );
        RowRenderData row0_4 = RowRenderData.build("*******有限公司", "中国", "100%");
        RowRenderData row1_4 = RowRenderData.build("total", "", "100%");
        MiniTableRenderData list4 = new MiniTableRenderData(header4, Arrays.asList(row0_4, row1_4));
        map.put("partner", list4);*/


        //股东（详情） - 表格
        /*RowRenderData header5 = RowRenderData.build(new TextRenderData("000000", "杭州公共消防安全有限公司"), new TextRenderData());
        RowRenderData row0_5 = RowRenderData.build("统一社会信用代码123", "9133110073946505XY");
        RowRenderData row1_5 = RowRenderData.build("法人代表", "周**");
        RowRenderData row2_5 = RowRenderData.build("公司性质", "私人有限公司");
        RowRenderData row3_5 = RowRenderData.build("成立日期", "2002-06-12");
        RowRenderData row4_5 = RowRenderData.build("营业期限至", "2032-06-11");
        RowRenderData row5_5 = RowRenderData.build("注册资本", "21,000,000");
        RowRenderData row6_5 = RowRenderData.build("注册地址", "浙江丽水市********");
        MiniTableRenderData list5 = new MiniTableRenderData(header5, Arrays.asList(row0_5, row1_5, row2_5, row3_5, row4_5, row5_5, row6_5));
        map.put("details", list5);*/

        //股东（投资情况） - 表格
       /* RowRenderData header6 = RowRenderData.build(
                new TextRenderData("000000", "投资情况")
        );
        RowRenderData row0_6 = RowRenderData.build(
                new TextRenderData("000000", "公司名称"),
                new TextRenderData("000000", "出资比例(%)"));

        RowRenderData row1_6 = RowRenderData.build("*******有限公司", "100%");
        RowRenderData row2_6 = RowRenderData.build("*******有限公司", "100%");
        MiniTableRenderData list6 = new MiniTableRenderData(header6, Arrays.asList(row0_6, row1_6, row2_6));
        map.put("invest", list6);*/

        //股东（管理层） - 表格
        /*RowRenderData header7 = RowRenderData.build(new TextRenderData("000000", "杭州公共消防安全有限公司"), new TextRenderData());
        RowRenderData row0_7 = RowRenderData.build("统一社会信用代码123", "9133110073946505XY");
        RowRenderData row1_7 = RowRenderData.build("法人代表", "周**");
        RowRenderData row2_7 = RowRenderData.build("公司性质", "私人有限公司");
        RowRenderData row3_7 = RowRenderData.build("成立日期", "2002-06-12");
        RowRenderData row4_7 = RowRenderData.build("营业期限至", "2032-06-11");
        RowRenderData row5_7 = RowRenderData.build("注册资本", "21,000,000");
        RowRenderData row6_7 = RowRenderData.build("注册地址", "浙江丽水市********");
        MiniTableRenderData list7 = new MiniTableRenderData(header7, Arrays.asList(row0_7, row1_7, row2_7, row3_7, row4_7, row5_7, row6_7));
        map.put("leader", list7);*/

        //Configure config = Configure.newBuilder().customPolicy("detail_table", new DetailTablePolicy()).build();

        //MainWord.buildWord(map, webRoot + "/word/" + "_基本信息报告样本.docx", "h://1.docx");

    }





}
