package com.billow.sql.constant;

import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * ST模板名称
 *
 * @author 千面
 * @date 2022/8/3 9:41
 */
public interface StConstants
{
    /**
     * 公用sql
     */
    STGroup COMMON_ST_GROUP = new STGroupFile(StConstants.class.getResource("/template/common.stg"));

    /**
     * common 模板中的方法名：
     */
    interface commonStGroupTemplate
    {

        /**
         * st模板：判断表是否存在
         */
        String JUDGE_TABLE_EXISTS = "judgeTableExistsTemplate";

        /**
         * st模板：获取表的创建时间
         */
        String GET_TABLE_CREATE_DATE = "getTableCreateDateTemplate";

        /**
         * st模板：修改表名
         */
        String ALTER_TABLE_NAME = "alterTableNameTemplate";


        /**
         * st模板：清空表数据
         */
        String TRUNCATE_TABLE_NAME = "truncateTableTemplate";

    }

    /**
     * st模板【修改表名】参数：
     */
    interface tableNameParam
    {

        String TABLE_NAME = "tableName";

    }

    /**
     * st模板【修改表名】参数：
     */
    interface alterTableNameParam
    {

        String OLD_TABLE_NAME = "oldTableName";

        String NEW_TABLE_NAME = "newTableName";
    }
}
