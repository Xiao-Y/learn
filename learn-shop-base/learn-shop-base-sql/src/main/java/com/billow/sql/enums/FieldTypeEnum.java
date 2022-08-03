package com.billow.sql.enums;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import java.util.Arrays;

/**
 * 字段类型枚举
 *
 * @author 千面
 * @date 2022/8/3 10:51
 */
public enum FieldTypeEnum
{
    CHAR("char", "字符", 255),
    VARCHAR("varchar", "字符串", 5000),
    TINYINT("tinyint", "小整数", 5, 127, -128),
    INT("int", "大整数", 15, 2147483647, -2147483648),
    BIGINT("bigint", "极大整数", 20),
    DOUBLE("double", "双精度浮点数", 20),
    DECIMAL("decimal", "小数", 50),
    DATE_TIME("datetime", "日期", 8),
    DATE("date", "日期date类型", 8),
    TEXT("text", "文本", 65535),
    BLOB("blob", "字节", 65),
    MEDIUM_TEXT("mediumtext", "文本", 16777215);

    /**
     * 编码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

    /**
     * 最大长度
     */
    private int maxLength;

    /**
     * 最大值
     */
    private int maxNum;

    /**
     * 最小值
     */
    private int minNum;

    FieldTypeEnum(String code, String desc, int maxLength)
    {
        this.code = code;
        this.desc = desc;
        this.maxLength = maxLength;
    }

    FieldTypeEnum(String code, String desc, int maxLength, int maxNum, int minNum)
    {
        this.code = code;
        this.desc = desc;
        this.maxLength = maxLength;
        this.maxNum = maxNum;
        this.minNum = minNum;
    }

    public static FieldTypeEnum getEnumByCode(String code)
    {
        if (StringUtils.isNotBlank(code))
        {
            FieldTypeEnum[] regs = FieldTypeEnum.values();
            FieldTypeEnum findReg = Arrays.stream(regs)
                    .filter(reg -> reg.getCode().equals(code))
                    .findAny()
                    .orElse(null);
            if (findReg != null)
            {
                return findReg;
            }
        }
        return null;
    }

    public static String getDescByCode(String code)
    {
        if (StringUtils.isNotBlank(code))
        {
            FieldTypeEnum[] regs = FieldTypeEnum.values();
            FieldTypeEnum findReg = Arrays.stream(regs)
                    .filter(reg -> reg.getCode().equals(code))
                    .findAny()
                    .orElse(null);
            if (findReg != null)
            {
                return findReg.getDesc();
            }
        }
        return "";
    }

    public static int getMaxLengthByCode(String code)
    {
        if (StringUtils.isNotBlank(code))
        {
            FieldTypeEnum[] regs = FieldTypeEnum.values();
            FieldTypeEnum findReg = Arrays.stream(regs)
                    .filter(reg -> reg.getCode().equals(code))
                    .findAny()
                    .orElse(null);
            if (findReg != null)
            {
                return findReg.getMaxLength();
            }
        }
        return 0;
    }

    public String getCode()
    {
        return code;
    }

    public String getDesc()
    {
        return desc;
    }

    public int getMaxLength()
    {
        return maxLength;
    }

    public int getMaxNum()
    {
        return maxNum;
    }

    public int getMinNum()
    {
        return minNum;
    }
}
