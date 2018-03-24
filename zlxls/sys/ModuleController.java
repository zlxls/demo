package com.zlxls.sys;

import com.web.ReturnKit;
import com.common.IdentificationInterceptor;
import com.common.LogInterceptor;
import com.common.PermissionInterceptor;
import com.common.cache.MyCacheInterceptor;
import com.common.cache.MyEvictInterceptor;
import com.common.model.Method;
import com.common.model.Module;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zlxls.util.StringUtils;
import com.zlxls.util.Validate;

/**
 * 
 * 模块方法对应表控制器操作类-相关的数据库操作方法都在里面
 * @ClassNmae：ModuleController
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
@Before({IdentificationInterceptor.class, PermissionInterceptor.class, SysValidator.class, Tx.class})
public class ModuleController extends Controller {
    @SuppressWarnings("serial")
    @Before(MyCacheInterceptor.class)
    public void gets() {
        String sql = "from module a where 1=1";
        if (!Validate.isNull(getPara("module.controllerkey"))) {
            sql += " and a.controllerkey like '%" + StringUtils.TransactSQLInjection(getPara("module.controllerkey")) + "%'";
        }
        if (!Validate.isNull(getPara("module.module_name"))) {
            sql += "  and (a.module_name like '%" + StringUtils.TransactSQLInjection(getPara("module.module_name")) + "%' "; 
            sql += " or a.module_content like '%" + StringUtils.TransactSQLInjection(getPara("module.module_name")) + "%')";
        }
        sql += " order by a." + getPara("sort", "module_id") + " " + getPara("order", "desc");
        setAttr("module", Module.dao.paginate(getParaToInt("page", 1), getParaToInt("rows", 10), "select a.*", sql));
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    @Clear
    public void add() {
        setAttr("success", false);
        if (Module.dao.findFirst("select module_id from module where controllerkey=?",getPara("module.controllerkey")) != null) {
            setAttr("message", "该记录已经存在");
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        Module module = getModel(Module.class,true);
        if (module.save()) {
            setAttr("message", "添加成功");
            setAttr("success", true);
        } else {
            setAttr("message", "添加失败");
            setAttr("success", false);
        }
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    public void update() {
        setAttr("success", false);
        if (Module.dao.findFirst("select module_id from module where controllerkey=? and module_id<>?",getPara("module.controllerkey"),getPara("module.module_id")) != null) {
            setAttr("message", "该记录已经存在");
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        Module module = getModel(Module.class,true);
        if (module.update()) {
            setAttr("message", "更新成功");
            setAttr("success", true);
        } else {
            setAttr("message", "更新失败");
            setAttr("success", false);
        }
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    public void delete() {
        try {
            String message="";
            String idString = getPara("idString");
            String idArr[] = idString.split(",");
            for (String idArr1 : idArr) {
                if (Validate.isNotNull(idArr1)) {
                    if((Method.dao.findFirst("select method_id from method where module_id=?",idArr1) != null)){
                        message+="["+idArr1+"]";
                    }else{
                        Db.update("delete from module where module_id = ?", idArr1);
                    }
                }
            }
            if(Validate.isNotNull(message)){
                message = ",ID为："+message+"的数据列在操作方法管理中存在关联数据，暂未删除，请删除操作方法管理中的关联数据后，再行删除。";
            }
            setAttr("message", "删除成功"+message);
            setAttr("success", true);
        } catch (Exception e) {
            System.out.println("++++++++++++++++++++" + e.getMessage());
            setAttr("message", "删除失败");
            setAttr("success", false);
        }
        ReturnKit.getRender(this);
    }
}
