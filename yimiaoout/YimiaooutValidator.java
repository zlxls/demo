package com.yimiaoout;

import com.web.ReturnKit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

/**
 *
 * Yimiaoout对应模型验证器
 * @ClassNmae：YimiaooutValidator   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class YimiaooutValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        String actionKey = getActionKey();
        switch (actionKey) {
            case "/yimiaoout/add":
                validateRequired("yimiaoout.yo_yimiaoinid", "message", "疫苗名称不能为空");
                validateRequired("yimiaoout.yo_employid", "message", "所得员工不能为空");
                validateRequired("yimiaoout.yo_number", "message", "数量不能为空");
                break;
            case "/yimiaoout/update":
                validateRequired("yimiaoout.yo_id", "message", "ID获取失败");
                validateRequired("yimiaoout.yo_yimiaoinid", "message", "疫苗名称不能为空");
                validateRequired("yimiaoout.yo_employid", "message", "所得员工不能为空");
                validateRequired("yimiaoout.yo_number", "message", "数量不能为空");
                break;
            case "/yimiaoout/updateStatus":
                validateRequiredString("yimiaoout.yo_id", "message", "ID获取失败");
                validateInteger("yimiaoout.yo_status", "message", "状态不能为空");
                break;
            case "/yimiaoout/delete":
                validateRequiredString("idString", "message", "ID不能为空");
                break;
            case "/yimiaooutAPI/gets":
                validateRequiredString("yimiaoout.yo_pici", "message", "批次不能为空");
                break;
        }
    }
    @Override
    protected void handleError(Controller controller) {
        controller.getRequest().setAttribute("success", false);
        ReturnKit.getRender(controller);
    }
}
