package com.hailian.modules.credit.usercenter.controller;

import com.hailian.jfinal.base.BaseController;
import com.hailian.jfinal.component.annotation.ControllerBind;
import com.hailian.modules.admin.ordermanager.model.CreditOrderInfo;
import com.jfinal.plugin.activerecord.Record;
import java.util.List;
/**
 * 2019/03/29
 * lzg
 * @author lzg
 *
 */
@ControllerBind(controllerKey = "/credit/front/getKpi")
public class KpiController extends BaseController {


        public void run() {
                Integer userId = getSessionUser() == null ? 555 : getSessionUser().getUserid();
                String orderNum = getPara("orderNum");
                CreditOrderInfo model = CreditOrderInfo.dao.findFirst("select * from credit_order_info where num = ?", orderNum);
                new OrderProcessController().getKpi(model, userId);
                renderJson(new Record().set("kpi", "计算完成"));
        }


        public void runs() {
                try{
                        Integer userId = getSessionUser() == null ? 555 : getSessionUser().getUserid();
                        List<CreditOrderInfo> models = CreditOrderInfo.dao.find(" SELECT * FROM credit_order_info WHERE del_flag = 0  AND status =311  AND end_date > '2019-02-28' AND custom_id NOT IN ( 555, 2 ) AND report_type=8  ORDER BY end_date DESC");
                        OrderProcessController op = new OrderProcessController();
                        for (int i = 0; i < models.size(); i++) {
                                op.getKpi(models.get(i), userId,true);
                        }
                }catch (Exception e){
                        e.printStackTrace();
                        renderJson(new Record().set("kpi", "计算失败!"));
                        return;
                }

                renderJson(new Record().set("kpi", "计算完成"));
        }
}
