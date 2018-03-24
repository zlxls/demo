package com.yimiaoin;

import com.web.ReturnKit;
import com.common.Constants;
import com.common.IdentificationInterceptor;
import com.common.LogInterceptor;
import com.common.PermissionInterceptor;
import com.common.cache.MyEvictInterceptor;
import com.common.interceptor.EmployInterceptor;
import com.common.interceptor.YimiaoInterceptor;
import com.common.model.Admin;
import com.common.model.Yimiaoin;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.zlxls.sys.method.PublicMethod;
import java.util.Date;
import com.zlxls.util.StringUtils;
import com.zlxls.util.Validate;

/**
 * 
 * 疫苗入库信息对应表控制器操作类-相关的数据库操作方法都在里面
 * @ClassNmae：YimiaoinController
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
@Before({IdentificationInterceptor.class, PermissionInterceptor.class, YimiaoinValidator.class, Tx.class})
public class YimiaoinController extends Controller {
    /**
     * 新增，更新方法数据项使用拦截器方法
     * 1，获取疫苗信息
     * 2，获取员工信息
     */
    @SuppressWarnings("serial")
    @Clear({PermissionInterceptor.class})
    @Before({YimiaoInterceptor.class,EmployInterceptor.class})
    public void _add() {
        keepPara();
        ReturnKit.getRender(this);
    }  
    @SuppressWarnings("serial")
    public void gets() {
        String sql = "from yimiaoin a left join admin ad on ad.admin_id=a.yi_adminid left join employ e on e.e_id=a.yi_doemployid left join yimiao y on y.y_id=a.yi_yimiaoid where 1=1";
        Admin admin = getSessionAttr(Constants.SESSION_ADMIN);
        if(Validate.isNotNull(admin.getBaseId())){//不是超级管理员
            sql += " and a.yi_adminid='"+admin.getAdminId()+"'";
        }
        if (!Validate.isNull(getPara("yimiaoin.admin_name"))) {
            sql += " and ad.admin_name like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoin.admin_name")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiaoin.e_name"))) {
            sql += " and e.e_name like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoin.e_name")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiaoin.y_name"))) {
            sql += " and y.y_name like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoin.y_name")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiaoin.yi_pihao"))) {
            sql += " and a.yi_pihao like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoin.yi_pihao")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiaoin.yi_pici"))) {
            sql += " and a.yi_pici like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoin.yi_pici")) + "%'";
        }
        if (Validate.isNotNull(getPara("yimiaoin.from_time")) && Validate.isNotNull(getPara("yimiaoin.to_time"))) {
            sql += " and a.yi_addtime between " + getParaToDate("yimiaoin.from_time").getTime() + " and " + getParaToDate("yimiaoin.to_time").getTime();
        }
        if (Validate.isNotNull(getPara("yimiaoin.from_time_s")) && Validate.isNotNull(getPara("yimiaoin.to_time_s"))) {
            sql += " and a.yi_shengchantime between " + getParaToDate("yimiaoin.from_time_s").getTime() + " and " + getParaToDate("yimiaoin.to_time_s").getTime();
        }
        if (Validate.isNotNull(getPara("yimiaoin.from_time_y")) && Validate.isNotNull(getPara("yimiaoin.to_time_y"))) {
            sql += " and a.yi_youxiaotime between " + getParaToDate("yimiaoin.from_time_y").getTime() + " and " + getParaToDate("yimiaoin.to_time_y").getTime();
        }
        sql += " order by a.yi_pici desc,a." + getPara("sort", "yi_addtime") + " " + getPara("order", "asc");
        setAttr("yimiaoin", Yimiaoin.dao.paginate(getParaToInt("page", 1), getParaToInt("rows", 10), "select a.*,ad.admin_name,y.y_name,y.y_danwei,e.e_name", sql));
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    public void add() {
        setAttr("success", false);
        Admin admin = getSessionAttr(Constants.SESSION_ADMIN);
        if (Yimiaoin.dao.findFirst("select yi_id from yimiaoin where yi_pici=? and yi_yimiaoid=? and yi_adminid=?",getPara("yimiaoin.yi_pici"),getPara("yimiaoin.yi_yimiaoid"),admin.getAdminId()) != null) {
            setAttr("message", "该记录已经存在(批次和疫苗同时相同)");
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        Yimiaoin yimiaoin = getModel(Yimiaoin.class,true);
        double yi_sumprice = getParaToInt("yimiaoin.yi_number")*(Double.valueOf(getPara("yimiaoin.yi_price")));
        yimiaoin.set("yi_sumprice",yi_sumprice)
                .set("yi_adminid",admin.getAdminId())
                .set("yi_shengchantime",getParaToDate("yimiaoin.yi_shengchantime").getTime())
                .set("yi_youxiaotime",getParaToDate("yimiaoin.yi_youxiaotime").getTime())
                .set("yi_addtime", new Date().getTime());
        if (yimiaoin.save()) {
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
        Admin admin = getSessionAttr(Constants.SESSION_ADMIN);
        if (Yimiaoin.dao.findFirst("select yi_id from yimiaoin where yi_pici=? and yi_yimiaoid=? and yi_adminid=? and yi_id<>?",getPara("yimiaoin.yi_pici"),getPara("yimiaoin.yi_yimiaoid"),admin.getAdminId(),getPara("yimiaoin.yi_id")) != null) {
            setAttr("message", "该记录已经存在(批次和疫苗同时相同)");
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        Yimiaoin yimiaoin = getModel(Yimiaoin.class,true);
        double yi_sumprice = getParaToInt("yimiaoin.yi_number")*(Double.valueOf(getPara("yimiaoin.yi_price")));
        yimiaoin.set("yi_sumprice",yi_sumprice)
                .set("yi_shengchantime",getParaToDate("yimiaoin.yi_shengchantime").getTime())
                .set("yi_youxiaotime",getParaToDate("yimiaoin.yi_youxiaotime").getTime());
        if (yimiaoin.update()) {
            setAttr("message", "更新成功");
            setAttr("success", true);
        } else {
            setAttr("message", "更新失败");
            setAttr("success", false);
        }
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    public void updateStatus() {
        Yimiaoin yimiaoin = getModel(Yimiaoin.class,true);
        if (yimiaoin.update()) {
            setAttr("message", "状态更改成功");
            setAttr("success", true);
        } else {
            setAttr("message", "状态更改失败");
            setAttr("success", false);
        }
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    public void delete() {
        String idString = getPara("idString");
        if(PublicMethod.delete(idString, "yimiaoin", "yi_id")){
            setAttr("message", "删除成功");
            setAttr("success", true);
        }else{
            setAttr("message", "删除失败");
            setAttr("success", false);
        }
        ReturnKit.getRender(this);
    }
}
