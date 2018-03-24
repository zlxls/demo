package com.yimiao;

import com.web.ReturnKit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 *
 * Yimiao对应模型验证器
 * @ClassNmae：YimiaoValidator   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class YimiaoValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        String actionKey = getActionKey();
        switch (actionKey) {
            case "/yimiao/add":
                validateRequiredString("yimiao.y_name", "message", "名称不能为空");
                validateRequiredString("yimiao.y_guige", "message", "规格不能为空");
                validateRequiredString("yimiao.y_xinghao", "message", "型号不能为空");
                validateRequiredString("yimiao.y_danwei", "message", "单位不能为空");
                validateRequiredString("yimiao.y_changjia", "message", "厂家不能为空");
                validateRequired("yimiao.y_type", "message", "疫苗类型不能为空");
                break;
            case "/yimiao/update":
                validateRequired("yimiao.y_id", "message", "ID获取失败");
                validateRequiredString("yimiao.y_name", "message", "名称不能为空");
                validateRequiredString("yimiao.y_guige", "message", "规格不能为空");
                validateRequiredString("yimiao.y_xinghao", "message", "型号不能为空");
                validateRequiredString("yimiao.y_danwei", "message", "单位不能为空");
                validateRequiredString("yimiao.y_changjia", "message", "厂家不能为空");
                validateRequired("yimiao.y_type", "message", "疫苗类型不能为空");
                break;
            case "/yimiao/updateStatus":
                validateRequiredString("yimiao.y_id", "message", "ID获取失败");
                validateInteger("yimiao.y_status", "message", "状态不能为空");
                break;
            case "/yimiao/delete":
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
