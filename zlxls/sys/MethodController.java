package com.zlxls.sys;

import com.web.ReturnKit;
import com.common.IdentificationInterceptor;
import com.common.LogInterceptor;
import com.common.PermissionInterceptor;
import com.common.cache.MyCacheInterceptor;
import com.common.cache.MyEvictInterceptor;
import com.common.interceptor.ModuleInterceptor;
import com.common.model.Method;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zlxls.sys.method.PublicMethod;
import com.zlxls.util.StringUtils;
import com.zlxls.util.Validate;

/**
 * 
 * 操作方法对应表控制器操作类-相关的数据库操作方法都在里面
 * @ClassNmae：MethodController
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
@Before({IdentificationInterceptor.class, PermissionInterceptor.class, SysValidator.class, Tx.class})
public class MethodController extends Controller {
    /**
     * 新增，更新方法数据项使用拦截器方法
     * 1，获取功能模块信息
     */
    @SuppressWarnings("serial")
    @Clear({PermissionInterceptor.class})
    @Before({ModuleInterceptor.class})
    public void _add() {
        keepPara();
        ReturnKit.getRender(this);
    }  
    @SuppressWarnings("serial")
    @Before(MyCacheInterceptor.class)
    public void gets() {
        String sql = "from method a left join module m on m.module_id=a.module_id where 1=1";
        if (!Validate.isNull(getPara("method.method_name"))) {
            sql += "  and (a.method_name like '%" + StringUtils.TransactSQLInjection(getPara("method.method_name")) + "%' ";
            sql += " or a.method_content like '%" + StringUtils.TransactSQLInjection(getPara("method.method_name")) + "%')";
        }
        if (!Validate.isNull(getPara("method.actionkey"))) {
            sql += " and a.actionkey like '%" + StringUtils.TransactSQLInjection(getPara("method.actionkey")) + "%'";
        }
        if (!Validate.isNull(getPara("method.module_name"))) {
            sql += " and m.module_name like '%" + StringUtils.TransactSQLInjection(getPara("method.module_name")) + "%'";
        }
        sql += " order by m.module_id,a." + getPara("sort", "method_id") + " " + getPara("order", "desc");
        setAttr("method", Method.dao.paginate(getParaToInt("page", 1), getParaToInt("rows", 10), "select a.*,m.module_name", sql));
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    @Clear
    public void add() {
        setAttr("success", false);
        if (Method.dao.findFirst("select method_id from method where module_id=? and actionkey=?",getPara("method.module_id"),getPara("method.actionkey"))!= null) {
            setAttr("message", "该记录已经存在(模块方法名称和操作方法名称同时相同)");
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        Method method = getModel(Method.class,true);
        if (method.save()) {
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
        if (Method.dao.findFirst("select method_id from method where module_id=? and actionkey=? and method_id<>?",getPara("method.module_id"),getPara("method.actionkey"),getPara("method.method_id"))!= null) {
            setAttr("message", "该记录已经存在(模块方法名称和操作方法名称同时相同)");
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        Method method = getModel(Method.class,true);
        if (method.update()) {
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
        String idString = getPara("idString");
        if(PublicMethod.delete(idString, "method", "method_id")){
            setAttr("message", "删除成功");
            setAttr("success", true);
        }else{
            setAttr("message", "删除失败");
            setAttr("success", false);
        }
        ReturnKit.getRender(this);
    }
}
