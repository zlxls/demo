package com.zlxls.sys.method;

import com.jfinal.plugin.activerecord.Db;
import com.zlxls.util.StringUtils;
import com.zlxls.util.Validate;

/**
 * 公共操作方法
 * @ClassNmae：PublicMethod   
 * @author zlx-雄雄
 * @date    2017-9-28 9:26:02
 * 
 */
public class PublicMethod {
    /**
     * 数据库（in方法）多数据列删除方法<br>
     * @param idString id集合，逗号链接的id集合
     * @param tableName 表名
     * @param primarykey 字段名，一般为主键，多字段以sql形式链接
     * @return 
     */
    public static boolean delete(String idString,String tableName,String primarykey) {
        idString = StringUtils.StringToOutLastCharacter(idString);
        //bug,sql中in关键字只能使用字符串拼接，使用多参数会将开始逗号后面的参数丢失
        return Validate.isNotNull(idString) && Db.update("delete from "+tableName+" where "+primarykey+" in ("+idString+")")>0;
    }
}
