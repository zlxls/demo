package com.zlxls.sys;
import com.web.ReturnKit;
import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;
/**
 *
 * 系统控制器对应模型验证器
 * @ClassNmae：SysValidator   
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
public class SysValidator extends Validator {
    @Override
    protected void validate(Controller controller) {
        String actionKey = getActionKey();
        switch (actionKey) {
            case "/method/add":
                validateRequiredString("method.method_name", "message", "操作方法名称不能为空");
                validateRequiredString("method.actionkey", "message", "操作方法值不能为空");
                validateRequiredString("method.module_id", "message", "模块方法不能为空");
                break;
            case "/method/update":
                validateRequired("method.method_id", "message", "ID不能为空");
                validateRequiredString("method.method_name", "message", "操作方法名称不能为空");
                validateRequiredString("method.actionkey", "message", "操作方法值不能为空");
                validateRequiredString("method.module_id", "message", "模块方法不能为空");
                break;
            case "/module/add":
                validateRequiredString("module.module_name", "message", "模块方法名称不能为空");
                validateRequiredString("module.controllerkey", "message", "模块方法值不能为空");
                break;
            case "/module/update":
                validateRequired("module.module_id", "message", "ID不能为空");
                validateRequiredString("module.module_name", "message", "模块方法名称不能为空");
                validateRequiredString("module.controllerkey", "message", "模块方法值不能为空");
                break;
            case "/method/delete":
            case "/module/delete":
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
