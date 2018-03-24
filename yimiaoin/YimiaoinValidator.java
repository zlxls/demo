package com.yimiaoin;

import com.web.ReturnKit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 *
 * Yimiaoin对应模型验证器
 * @ClassNmae：YimiaoinValidator   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class YimiaoinValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        String actionKey = getActionKey();
        switch (actionKey) {
            case "/yimiaoin/add":
                validateRequiredString("yimiaoin.yi_pici", "message", "批次不能为空");
                validateRequiredString("yimiaoin.yi_yimiaoid", "message", "疫苗不能为空");
                validateRequired("yimiaoin.yi_number", "message", "数量不能为空");
                validateRequiredString("yimiaoin.yi_youxiaotime", "message", "有效期至不能为空");
                validateRequiredString("yimiaoin.yi_shengchantime", "message", "生产日期不能为空");
                break;
            case "/yimiaoin/update":
                validateRequired("yimiaoin.yi_id", "message", "ID获取失败");
                validateRequiredString("yimiaoin.yi_pici", "message", "批次不能为空");
                validateRequiredString("yimiaoin.yi_yimiaoid", "message", "疫苗不能为空");
                validateRequired("yimiaoin.yi_number", "message", "数量不能为空");
                validateRequiredString("yimiaoin.yi_youxiaotime", "message", "有效期至不能为空");
                validateRequiredString("yimiaoin.yi_shengchantime", "message", "生产日期不能为空");
                break;
            case "/yimiaoin/updateStatus":
                validateRequired("yimiaoin.yi_id", "message", "ID获取失败");
                validateRequiredString("yimiaoin.yi_status", "message", "状态不能为空");
                break;
            case "/yimiaoin/delete":
                validateRequiredString("idString", "message", "ID不能为空");
                break;
        }
    }
    @Override
    protected void handleError(Controller controller) {
        controller.getRequest().setAttribute("success", false);
        ReturnKit.getRender(controller);
    }
}
