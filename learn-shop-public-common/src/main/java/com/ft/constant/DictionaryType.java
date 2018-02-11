package com.ft.constant;

/**
 * 数据字典中模块CODE与字段CODE<br/>
 * 由DictionaryType统一管理<br>
 * 模块：MMM_MODEL_CODE_MMM<br>
 * 字段：MMM_FIELD_CODE_FFF<br>
 * 
 * @author XiaoY
 * @date: 2015年10月1日 下午10:56:58
 */
public class DictionaryType {

	/**
	 * 模块： dictionary数据字典模块
	 */
	public static final String DICTIONARY_MODEL_CODE_DICTIONARY = "dictionary";
	/**
	 * 字段：field 字段字段
	 */
	public static final String DICTIONARY_FIELD_CODE_FIELD = "field";
	/**
	 * 字段：dictionary 数据字典字段
	 */
	public static final String DICTIONARY_FIELD_CODE_DICTIONARY = "dictionary";

	/**************************************************************************************/

	/**
	 * 模块：bug bug模块
	 */
	public static final String BUG_MODEL_CODE_BUG = "bug";
	/**
	 * 字段：bugType bug类型
	 */
	public static final String BUG_FIELD_CODE_BUGTYPE = "bugType";
	/**
	 * 字段：priority 优先级
	 */
	public static final String BUG_FIELD_CODE_PRIORITY = "priority";
	/**
	 * 字段：reappear 重现规律
	 */
	public static final String BUG_FIELD_CODE_REAPPEAR = "reappear";
	/**
	 * 字段：severity 严重程度
	 */
	public static final String BUG_FIELD_CODE_SEVERITY = "severity";
	/**
	 * 字段：status 修改状态
	 */
	public static final String BUG_FIELD_CODE_STATUS = "status";

	/**************************************************************************************/
	/**
	 * 模块：log 日志模块
	 */
	public static final String LOG_MODEL_CODE_LOG = "log";
	/**
	 * 字段：operation 操作类型
	 */
	public static final String LOG_FIELD_CODE_OPERATION = "operation";

	/**************************************************************************************/
	/**
	 * 模块：menu 菜单模块
	 */
	public static final String MENU_MODEL_CODE_MENU = "menu";
	/**
	 * 字段：menuType 菜单类型
	 */
	public static final String MENU_FIELD_CODE_MENUTYPE = "menuType";
	/**************************************************************************************/
	/**
	 * 模块：home 主页模块
	 */
	public static final String HOME_MODEL_CODE_HOME = "home";
	/**
	 * 字段：theme 主题
	 */
	public static final String HOME_FIELD_CODE_THEME = "theme";
	/**************************************************************************************/
	/**
	 * 模块：role 角色管理
	 */
	public static final String ROLE_MODEL_CODE_ROLE = "role";
	/**
	 * 字段：authorizeStatus 授权状态
	 */
	public static final String ROLE_FIELD_CODE_AUTHORIZESTATUS = "authorizeStatus";

}
