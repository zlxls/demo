package com.yimiaoout;

import com.web.ReturnKit;
import com.common.Constants;
import com.common.interceptor.EmployLoginInterceptor;
import com.common.model.Employ;
import com.common.model.Yimiaoin;
import com.common.model.Yimiaoout;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.message.MessageValidator;
import com.zlxls.util.StringUtils;
import com.zlxls.util.Validate;

/**
 * 
 * API接口<br>
 * 我的疫苗，疫苗出库信息接口，对应表控制器操作类-相关的数据库操作方法都在里面
 * @ClassNmae：YimiaooutAPIController
 * @author zlx-雄雄
 * @date    2017-8-16 11:42:41
 * 
 */
@Before({MessageValidator.class,EmployLoginInterceptor.class,EmployLoginInterceptor.class,  Tx.class})
public class YimiaooutAPIController extends Controller {
    /**
     * API疫苗信息
     */
     public void get() {
         String sql = "select a.*,y.*,yi.yi_youxiaotime,yi.yi_shengchantime from yimiaoout a left join yimiaoin yi on a.yo_yimiaoinid=yi.yi_id left join yimiao y on y.y_id=yi.yi_yimiaoid where a.yo_id=?";
         setAttr("data", Yimiaoout.dao.findFirst(sql,getPara("yimiaoout.yo_id")));
         setAttr("success", true);
         setAttr("code", 200);
         ReturnKit.getRender(this);
     }
    /**
     * API疫苗列表
     */
    @SuppressWarnings("serial")
    public void gets() {
        Employ employ = Constants.employmap.get(getRequest().getHeader("accesstoken"));
        String sql = "from yimiaoout a left join yimiaoin yi on a.yo_yimiaoinid=yi.yi_id left join yimiao y on y.y_id=yi.yi_yimiaoid where a.yo_employid="+employ.getEId();
        sql += " and a.yo_pici ='" + StringUtils.TransactSQLInjection(getPara("yimiaoout.yo_pici")) + "'";
        if (!Validate.isNull(getPara("yimiaoout.y_name"))) {
            sql += " and y.y_name like '%" + StringUtils.TransactSQLInjection(getPara("yimiaoout.y_name")) + "%'";
        }
        if (Validate.isNotNull(getPara("yimiaoout.from_time")) && Validate.isNotNull(getPara("yimiaoout.to_time"))) {
            sql += " and a.yo_addtime between " + getParaToDate("yimiaoout.from_time").getTime() + " and " + getParaToDate("yimiaoout.to_time").getTime();
        }
        sql += " order by a.yo_pici desc,a." + getPara("sort", "yo_addtime") + " " + getPara("order", "asc");
        setAttr("data", Yimiaoout.dao.find("select a.*,y.*,yi.yi_youxiaotime,yi.yi_shengchantime "+sql));
        setAttr("success", true);
        setAttr("code", 200);
        ReturnKit.getRender(this);
    }
    @SuppressWarnings("serial")
    public void getsPici() {
        Employ employ = Constants.employmap.get(getRequest().getHeader("accesstoken"));
        String sql = "from yimiaoout a  where a.yo_employid="+employ.getEId();
        sql += " group by a.yo_pici order by a.yo_pici desc";
        sql +=" limit "+(getParaToInt("page", 1)-1)*getParaToInt("size", 10)+","+getParaToInt("size", 10);
        setAttr("data", Yimiaoout.dao.find("select a.yo_pici,count(*) as yo_number "+sql));
        setAttr("success", true);
        setAttr("code", 200);
        ReturnKit.getRender(this);
    }
}
