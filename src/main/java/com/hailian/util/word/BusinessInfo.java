package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.deepoove.poi.data.RowRenderData;
import com.deepoove.poi.data.TextRenderData;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.modules.credit.usercenter.model.ModuleJsonData;
import com.hailian.system.dict.DictCache;
import com.jfinal.kit.PathKit;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Thinkpad on 2018/11/17.
 */
public class BusinessInfo {

    public static void test() {
        String webRoot = PathKit.getWebRootPath();
        //System.out.println("webRoot--->:"+webRoot);

        String reportType = "1";
        //找到当前报告类型下的父节点
        List<CreditReportModuleConf> crmcs = CreditReportModuleConf.dao.findByReport(reportType);
        List<ModuleJsonData> list = new ArrayList<ModuleJsonData>();
        //获取默认模板
        //List<CreditReportModuleConf> defaultModule = CreditReportModuleConf.dao.getDefaultModule(reportType);
        //获取带锚点模板
        //List<CreditReportModuleConf> tabFixed = CreditReportModuleConf.dao.getTabFixed(reportType);
        //double start = new Date().getTime();
        for (CreditReportModuleConf crmc : crmcs) {
            //找到当前父节点下的子节点
            List<CreditReportModuleConf> child = CreditReportModuleConf.dao.findSon(crmc.get("id").toString(), reportType);
            list.add(new ModuleJsonData(crmc, child, crmc.getStr("small_module_type")));

            String type = crmc.getStr("small_module_type");
            if("1".equals(type)) {
                String sysLanguage = "612";
                String companyId = "77";
                String tableName = "credit_company_info";
                String className = "CreditCompanyInfo";
                String confId = crmc.getInt("id") + "";
                String selectInfo = "";
                ReportInfoGetDataController report = new ReportInfoGetDataController();
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                System.out.println(rows.size());
            }
        }
        //System.out.println("运行时间====================================" + (double) (new Date().getTime() - start));


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String str = "该公司目前主要从事管道支吊架、垃圾给料机、钢结构件（除建筑构件）的制造、加工；机械零部件加工；金属材料的批发、零售、代购代销。\n" +
                "周**先生目前在该公司担任董事长。\n" +
                "该公司目前有120名员工。\n" +
                "该公司目前在首页所述之地址办公。该地址位于浙江丽水市***********，面积未能获知。\n";

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("company", "海尔集团");
        map.put("code", "123");
        map.put("date", sdf.format(new Date()));


        //注册信息-表格
        //RowRenderData header2 = RowRenderData.build(new TextRenderData("000000", "姓名"), new TextRenderData("000000", "学历"));
        RowRenderData row0_2 = RowRenderData.build("统一社会信用代码123", "9133110073946505XY");
        RowRenderData row1_2 = RowRenderData.build("法人代表", "周**");
        RowRenderData row2_2 = RowRenderData.build("公司性质", "私人有限公司");
        RowRenderData row3_2 = RowRenderData.build("成立日期", "2002-06-12");
        RowRenderData row4_2 = RowRenderData.build("营业期限至", "2032-06-11");
        RowRenderData row5_2 = RowRenderData.build("注册资本", "21,000,000");
        RowRenderData row6_2 = RowRenderData.build("注册地址", "浙江丽水市********");
        MiniTableRenderData list2 = new MiniTableRenderData(Arrays.asList(row0_2, row1_2, row2_2, row3_2, row4_2, row5_2, row6_2));
        map.put("regist", list2);

        //历史变更信息-表格
        RowRenderData header3 = RowRenderData.build(
                new TextRenderData("000000", "日期"),
                new TextRenderData("000000", "变更项"),
                new TextRenderData("000000", "变更前"),
                new TextRenderData("000000", "变更后")
        );
        RowRenderData row0_3 = RowRenderData.build("2009-03-19", "注册地址", "海曙环城西路********", "浙江丽水市*****");
        MiniTableRenderData list3 = new MiniTableRenderData(header3, Arrays.asList(row0_3));
        map.put("history", list3);

        //总结
        map.put("result", str);

        //股东-表格
        RowRenderData header4 = RowRenderData.build(
                new TextRenderData("000000", "姓名"),
                new TextRenderData("000000", "国家/国籍"),
                new TextRenderData("000000", "出资比例(%)")
        );
        RowRenderData row0_4 = RowRenderData.build("*******有限公司", "中国", "100%");
        RowRenderData row1_4 = RowRenderData.build("total", "", "100%");
        MiniTableRenderData list4 = new MiniTableRenderData(header4, Arrays.asList(row0_4, row1_4));
        map.put("partner", list4);


        //股东（详情） - 表格
        RowRenderData header5 = RowRenderData.build(new TextRenderData("000000", "杭州公共消防安全有限公司"), new TextRenderData());
        RowRenderData row0_5 = RowRenderData.build("统一社会信用代码123", "9133110073946505XY");
        RowRenderData row1_5 = RowRenderData.build("法人代表", "周**");
        RowRenderData row2_5 = RowRenderData.build("公司性质", "私人有限公司");
        RowRenderData row3_5 = RowRenderData.build("成立日期", "2002-06-12");
        RowRenderData row4_5 = RowRenderData.build("营业期限至", "2032-06-11");
        RowRenderData row5_5 = RowRenderData.build("注册资本", "21,000,000");
        RowRenderData row6_5 = RowRenderData.build("注册地址", "浙江丽水市********");
        MiniTableRenderData list5 = new MiniTableRenderData(header5, Arrays.asList(row0_5, row1_5, row2_5, row3_5, row4_5, row5_5, row6_5));
        map.put("details", list5);

        //股东（投资情况） - 表格
        RowRenderData header6 = RowRenderData.build(
                new TextRenderData("000000", "投资情况")
        );
        RowRenderData row0_6 = RowRenderData.build(
                new TextRenderData("000000", "公司名称"),
                new TextRenderData("000000", "出资比例(%)"));

        RowRenderData row1_6 = RowRenderData.build("*******有限公司", "100%");
        RowRenderData row2_6 = RowRenderData.build("*******有限公司", "100%");
        MiniTableRenderData list6 = new MiniTableRenderData(header6, Arrays.asList(row0_6, row1_6, row2_6));
        map.put("invest", list6);

        //股东（管理层） - 表格
        RowRenderData header7 = RowRenderData.build(new TextRenderData("000000", "杭州公共消防安全有限公司"), new TextRenderData());
        RowRenderData row0_7 = RowRenderData.build("统一社会信用代码123", "9133110073946505XY");
        RowRenderData row1_7 = RowRenderData.build("法人代表", "周**");
        RowRenderData row2_7 = RowRenderData.build("公司性质", "私人有限公司");
        RowRenderData row3_7 = RowRenderData.build("成立日期", "2002-06-12");
        RowRenderData row4_7 = RowRenderData.build("营业期限至", "2032-06-11");
        RowRenderData row5_7 = RowRenderData.build("注册资本", "21,000,000");
        RowRenderData row6_7 = RowRenderData.build("注册地址", "浙江丽水市********");
        MiniTableRenderData list7 = new MiniTableRenderData(header7, Arrays.asList(row0_7, row1_7, row2_7, row3_7, row4_7, row5_7, row6_7));
        map.put("leader", list7);


        MainWord.buildWord(map, webRoot + "/word/" + "_基本信息报告样本.docx", "h://1.docx");
    }


}
