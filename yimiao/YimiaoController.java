package com.yimiao;

import com.web.ReturnKit;
import com.common.Constants;
import com.common.IdentificationInterceptor;
import com.common.LogInterceptor;
import com.common.PermissionInterceptor;
import com.common.cache.MyCacheInterceptor;
import com.common.cache.MyEvictInterceptor;
import com.common.interceptor.YimiaoTypeInterceptor;
import com.common.model.Admin;
import com.common.model.Yimiao;
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
 * 疫苗信息对应表控制器操作类-相关的数据库操作方法都在里面
 * @ClassNmae：YimiaoController
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
@Before({IdentificationInterceptor.class, PermissionInterceptor.class, YimiaoValidator.class, Tx.class})
public class YimiaoController extends Controller {
    
    @SuppressWarnings("serial")
    @Clear({PermissionInterceptor.class})
    @Before({YimiaoTypeInterceptor.class})
    public void _add() {
        keepPara();
        ReturnKit.getRender(this);
    }
    @SuppressWarnings("serial")
    public void gets() {
        String sql = "from yimiao a left join admin ad on ad.admin_id=a.y_adminid where 1=1";
        Admin admin = getSessionAttr(Constants.SESSION_ADMIN);
        if(Validate.isNotNull(admin.getBaseId())){//不是超级管理员
            sql += " and a.y_adminid='"+admin.getAdminId()+"'";
        }
        if (!Validate.isNull(getPara("yimiao.admin_name"))) {
            sql += " and ad.admin_name like '%" + StringUtils.TransactSQLInjection(getPara("yimiao.admin_name")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiao.y_name"))) {
            sql += " and a.y_name like '%" + StringUtils.TransactSQLInjection(getPara("yimiao.y_name")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiao.y_changjia"))) {
            sql += " and a.y_changjia like '%" + StringUtils.TransactSQLInjection(getPara("yimiao.y_changjia")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiao.y_status"))) {
            sql += " and a.y_status=" + StringUtils.TransactSQLInjection(getPara("yimiao.y_status"));
        }
        if (Validate.isNotNull(getPara("yimiao.from_time")) && Validate.isNotNull(getPara("yimiao.to_time"))) {
            sql += " and a.y_addtime between " + getParaToDate("yimiao.from_time").getTime() + " and " + getParaToDate("yimiao.to_time").getTime();
        }
        sql += " order by a." + getPara("sort", "y_addtime") + " " + getPara("order", "asc");
        setAttr("yimiao", Yimiao.dao.paginate(getParaToInt("page", 1), getParaToInt("rows", 10), "select a.*,ad.admin_name", sql));
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    public void add() {
        setAttr("success", false);
        Admin admin = getSessionAttr(Constants.SESSION_ADMIN);
        if(Validate.isNotNull(Yimiao.dao.find("select y_id from yimiao where y_adminid=? and y_name=? and y_guige=? and y_xinghao=? and y_danwei=? and y_changjia=?",admin.getAdminId(),getPara("yimiao.y_name"),getPara("yimiao.y_guige"),getPara("yimiao.y_xinghao"),getPara("yimiao.y_danwei"),getPara("yimiao.y_changjia")))){
            setAttr("message", "已经添加过相同的疫苗");
            setAttr("success", false);
        }else{
            Yimiao yimiao = getModel(Yimiao.class,true);
            yimiao.set("y_adminid", admin.getAdminId()).set("y_addtime", new Date().getTime());
            if (yimiao.save()) {
                setAttr("message", "添加成功");
                setAttr("success", true);
            } else {
                setAttr("message", "添加失败");
                setAttr("success", false);
            }
        }
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    public void update() {
        Yimiao yimiao = getModel(Yimiao.class,true);
        if (yimiao.update()) {
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
        Yimiao yimiao = getModel(Yimiao.class,true);
        if (yimiao.update()) {
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
        if(PublicMethod.delete(idString, "yimiao", "y_id")){
            setAttr("message", "删除成功");
            setAttr("success", true);
        }else{
            setAttr("message", "删除失败");
            setAttr("success", false);
        }
        ReturnKit.getRender(this);
    }
}
