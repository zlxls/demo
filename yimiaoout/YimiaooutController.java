package com.yimiaoout;

import com.web.ReturnKit;
import com.common.Constants;
import com.common.IdentificationInterceptor;
import com.common.LogInterceptor;
import com.common.PermissionInterceptor;
import com.common.cache.MyEvictInterceptor;
import com.common.interceptor.EmployInterceptor;
import com.common.interceptor.YimiaoinInterceptor;
import com.common.model.Admin;
import com.common.model.Yimiaoout;
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
 * 疫苗出库信息对应表控制器操作类-相关的数据库操作方法都在里面
 * @ClassNmae：YimiaooutController
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
@Before({IdentificationInterceptor.class, PermissionInterceptor.class, YimiaooutValidator.class, Tx.class})
public class YimiaooutController extends Controller {
    /**
     * 新增，更新方法数据项使用拦截器方法
     * 1，获取疫苗入库信息
     * 2，获取员工信息
     */
    @SuppressWarnings("serial")
    @Clear({PermissionInterceptor.class})
    @Before({YimiaoinInterceptor.class,EmployInterceptor.class})
    public void _add() {
        keepPara();
        ReturnKit.getRender(this);
    }  
    @SuppressWarnings("serial")
    public void gets() {
       String sql = "from yimiaoout a left join yimiaoin yi on a.yo_yimiaoinid=yi.yi_id left join admin ad on ad.admin_id=yi.yi_adminid left join employ e on e.e_id=a.yo_employid left join yimiao y on y.y_id=yi.yi_yimiaoid where 1=1";
        Admin admin = getSessionAttr(Constants.SESSION_ADMIN);
        if(Validate.isNotNull(admin.getBaseId())){//不是超级管理员
            sql += " and yi.yi_adminid='"+admin.getAdminId()+"'";
        }
        if (!Validate.isNull(getPara("yimiaoout.admin_name"))) {
            sql += " and ad.admin_name like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoout.admin_name")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiaoout.e_name"))) {
            sql += " and e.e_name like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoout.e_name")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiaoout.y_name"))) {
            sql += " and y.y_name like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoout.y_name")) + "%'";
        }
        if (!Validate.isNull(getPara("yimiaoout.yi_pici"))) {
            sql += " and a.yo_pici like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoout.yi_pici")) + "%'";
        }
        if (Validate.isNotNull(getPara("yimiaoout.from_time")) && Validate.isNotNull(getPara("yimiaoout.to_time"))) {
            sql += " and a.yo_addtime between " + getParaToDate("yimiaoout.from_time").getTime() + " and " + getParaToDate("yimiaoout.to_time").getTime();
        }
        sql += " order by a.yo_pici desc,a." + getPara("sort", "yo_addtime") + " " + getPara("order", "asc");
        setAttr("yimiaoout", Yimiaoin.dao.paginate(getParaToInt("page", 1), getParaToInt("rows", 10), "select a.*,ad.admin_name,y.y_name,e.e_name", sql));
        ReturnKit.getRender(this);
    }
    @Before({MyEvictInterceptor.class, LogInterceptor.class})
    public void add() {
        Admin admin = getSessionAttr(Constants.SESSION_ADMIN);
        if (Yimiaoin.dao.findFirst("select yo.yo_id from yimiaoout yo left join yimiaoin yi on yi.yi_id=yo.yo_yimiaoinid where yo.yo_yimiaoinid=? and yo.yo_employid=? and yi.yi_adminid=?",getPara("yimiaoout.yo_yimiaoinid"),getPara("yimiaoout.yo_employid"),admin.getAdminId()) != null) {
            setAttr("message", "该记录已经存在(同批次疫苗和所得员工同时相同)");
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        Yimiaoout yimiaoout = getModel(Yimiaoout.class,true);
        Yimiaoin yimiaoin= Yimiaoin.dao.findFirst("select yi_price,yi_pici,yi_number from yimiaoin where yi_id=?",yimiaoout.getYoYimiaoinid());
        /**
         * 计算可出库疫苗数量
         * useNumber已经分配的当前批次疫苗数量
         */
        int useNumber= Db.queryBigDecimal("select ifnull(sum(yo_number),0) from yimiaoout where yo_pici=? and yo_yimiaoinid=?",yimiaoin.getYiPici(),yimiaoout.getYoYimiaoinid()).intValue();
        if((useNumber+yimiaoout.getYoNumber())>yimiaoin.getYiNumber()){
            setAttr("message", "疫苗数量不足：当前批次疫苗数"+yimiaoin.getYiNumber()+",目前已出库"+useNumber+",剩余可出库"+(yimiaoin.getYiNumber()-useNumber));
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        double sumPrice =getParaToInt("yimiaoout.yo_number")*Double.valueOf(yimiaoin.get("yi_price").toString());
        yimiaoout.set("yo_pici",yimiaoin.getYiPici())
                .set("yo_sumprice", sumPrice)
                .set("yo_addtime", new Date().getTime())
                .set("yo_price", yimiaoin.getYiPrice());
        if (yimiaoout.save()) {
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
        Admin admin = getSessionAttr(Constants.SESSION_ADMIN);
        if (Yimiaoin.dao.findFirst("select yo.yo_id from yimiaoout yo left join yimiaoin yi on yi.yi_id=yo.yo_yimiaoinid where yo.yo_yimiaoinid=? and yo.yo_employid=? and yi.yi_adminid=? and yo.yo_id<>?",getPara("yimiaoout.yo_yimiaoinid"),getPara("yimiaoout.yo_employid"),admin.getAdminId(),getPara("yimiaoout.yo_id")) != null) {
            setAttr("message", "该记录已经存在(同批次疫苗和所得员工同时相同)");
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        Yimiaoout yimiaoout = getModel(Yimiaoout.class,true);
        Yimiaoin yimiaoin= Yimiaoin.dao.findFirst("select yi_price,yi_pici,yi_number from yimiaoin where yi_id=?",yimiaoout.getYoYimiaoinid());
        /**
         * 计算可出库疫苗数量
         * useNumber已经分配的当前批次疫苗数量，包括更改前的当前记录
         * number更改前的当前记录分配的疫苗数量
         * 更新时需要减去number
         */
        int useNumber= Db.queryBigDecimal("select ifnull(sum(yo_number),0) from yimiaoout where yo_pici=? and yo_yimiaoinid=?",yimiaoin.getYiPici(),yimiaoout.getYoYimiaoinid()).intValue();
        int number= Db.queryBigDecimal("select ifnull(sum(yo_number),0) from yimiaoout where yo_id=?",yimiaoout.getYoId()).intValue();
        if((useNumber+yimiaoout.getYoNumber()-number)>yimiaoin.getYiNumber()){
            setAttr("message", "疫苗数量不足：当前批次疫苗数"+yimiaoin.getYiNumber()+",目前已出库"+(useNumber-number)+",剩余可出库"+(yimiaoin.getYiNumber()-useNumber+number));
            setAttr("success", false);
            ReturnKit.getRender(this);
            return;
        }
        double sumPrice =getParaToInt("yimiaoout.yo_number")*Double.valueOf(yimiaoin.get("yi_price").toString());
        yimiaoout.set("yo_pici",yimiaoin.getYiPici())
                .set("yo_sumprice", sumPrice)
                .set("yo_price", yimiaoin.getYiPrice());
        if (yimiaoout.update()) {
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
        Yimiaoout yimiaoout = getModel(Yimiaoout.class,true);
        if (yimiaoout.update()) {
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
        if(PublicMethod.delete(idString, "yimiaoout", "yo_id")){
            setAttr("message", "删除成功");
            setAttr("success", true);
        }else{
            setAttr("message", "删除失败");
            setAttr("success", false);
        }
        ReturnKit.getRender(this);
    }
}
