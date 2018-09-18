package com.hailian.util.excel;

import java.util.List;

import com.hailian.component.base.BaseProjectController;
import com.hailian.modules.credit.orderpoimanager.service.OrderPoiService;

/**
* @author dyc:
* @time 2018年9月18日 上午10:09:32
* @todo
*/
public class MainExcel {
	private static BaseProjectController c;

	public static void main(String[] args) {
		String path = "C:\\Users\\Happy\\Desktop\\模板.xlsx";
        try {
            List<List<String>> result = new ReadExcel().readXlsx(path);
            System.out.println(result.size());
            for (int i = 0; i < result.size(); i++) {
                List<String> model = result.get(i);
                String orderId = model.get(0);
                OrderPoiService service=new OrderPoiService();
				
                System.out.println("第一列:" + model.get(0));
                System.out.println("第二列:" +  model.get(1));
                System.out.println("第三列:" +  model.get(2));
                System.out.println("第四列:" +  model.get(3));
                System.out.println("第六列:" +  model.get(5));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
