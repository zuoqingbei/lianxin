package com.hailian.util.word;

import com.deepoove.poi.data.MiniTableRenderData;
import com.hailian.modules.admin.ordermanager.model.CreditCompanyInfo;
import com.hailian.modules.admin.ordermanager.model.CreditCustomInfo;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.hailian.modules.credit.reportmanager.model.CreditReportModuleConf;
import com.hailian.modules.credit.usercenter.controller.ReportInfoGetDataController;
import com.hailian.modules.credit.utils.SendMailUtil;
import com.hailian.util.Config;
import com.jfinal.kit.PathKit;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 基本信息报告样本
 * Created by Thinkpad on 2018/11/17.
 */
public class BaseInfoZh {
    //ftp文件服务器 ip
    public static final String ip = Config.getStr("ftp_ip");
    //ftp端口 9980
    public static final int serverPort = Config.getToInt("searver_port");

    /**
     * 生成基本报告
     * @param order  订单
     * @param userid 当前登录人
     */
    public static void reportTable(CreditOrderInfo order, Integer userid) {
        //项目路劲
        String webRoot = PathKit.getWebRootPath();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, Object> map = new HashMap<String, Object>();

        //获取订单信息
        String companyId = order.getStr("company_id");
        String customId = order.getStr("custom_id");
        String reportType = order.getStr("report_type");
        String orderId = order.getInt("id") + "";

        //获取公司信息
        CreditCompanyInfo companyInfo = CreditCompanyInfo.dao.findById(companyId);
        String sysLanguage = companyInfo.getInt("sys_language") + "";
        String _prePath = webRoot + "/upload/tmp/" + reportType + sysLanguage + companyId;
        //订单公司名称
        map.put("company", companyInfo.getStr("name_en"));
        //联信编码
        map.put("code", companyInfo.getStr("lianxin_id"));
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
            if ("法人股东详情".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = BaseWord.createTableS(child, rows);
                map.put("details", table);
            }
            if ("投资情况".equals(tempName)) {
                String selectInfo = "";
                List rows = report.getTableData(sysLanguage, companyId, tableName, className, confId, selectInfo);
                MiniTableRenderData table = BaseWord.createTableH(child, rows);
                map.put("invest", table);
            }
            if ("管理层".equals(tempName)) {
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

        BaseWord.buildWord(map, webRoot + "/word/" + "_基本信息报告样本.docx", _prePath + ".docx");

        //上传文件
        String filePath = BaseWord.uploadReport(_prePath + ".docx", orderId, userid);
        //发送邮件
        List<CreditCustomInfo> customList = CreditCustomInfo.dao.find("select * from credit_custom_info where id=" + customId + " and del_flag=0 ");
        if (customList != null && customList.size() > 0) {
            CreditCustomInfo customInfo = customList.get(0);
            List<Map<String, String>> fileList = new ArrayList();
            Map<String, String> fileMap = new HashMap();
            fileMap.put("商业信息报告.doc", "http://" + ip + ":" + serverPort + "/" + filePath);
            fileList.add(fileMap);
            try {
                String email = customInfo.getStr("email");
                new SendMailUtil(email, "", "商业信息报告", "", fileList).sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
