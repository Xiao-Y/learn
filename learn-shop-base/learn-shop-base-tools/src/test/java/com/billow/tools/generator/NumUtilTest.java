package com.billow.tools.generator;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NumUtil 测试类
 * 
 * @author billow
 */
public class NumUtilTest {

    /**
     * 测试订单号生成
     * 
     * @author billow
     */
    @Test
    public void testMakeNum() {
        // 测试默认前缀
        String orderSn1 = NumUtil.makeNum();
        assertNotNull(orderSn1);
        assertTrue(orderSn1.startsWith("B"));
        
        // 测试自定义前缀
        String orderSn2 = NumUtil.makeNum("ORD");
        assertNotNull(orderSn2);
        assertTrue(orderSn2.startsWith("ORD"));
        
        // 测试生成的订单号不重复
        String orderSn3 = NumUtil.makeNum("TEST");
        String orderSn4 = NumUtil.makeNum("TEST");
        assertNotEquals(orderSn3, orderSn4);
    }

    /**
     * 测试从订单号提取完整日期时间
     * 
     * @author billow
     */
    @Test
    public void testExtractDateTimeFromOrderSn() {
        // 生成一个测试订单号
        String orderSn = NumUtil.makeNum("TEST");
        
        // 提取日期时间
        LocalDateTime extractedDateTime = NumUtil.extractDateTimeFromOrderSn(orderSn);
        assertNotNull(extractedDateTime);
        
        // 验证提取的日期是今天
        LocalDate today = LocalDate.now();
        assertEquals(today, extractedDateTime.toLocalDate());
        
        // 测试异常情况
        assertThrows(IllegalArgumentException.class, () -> NumUtil.extractDateTimeFromOrderSn(""));
        assertThrows(IllegalArgumentException.class, () -> NumUtil.extractDateTimeFromOrderSn(null));
        assertThrows(IllegalArgumentException.class, () -> NumUtil.extractDateTimeFromOrderSn("invalid"));
    }
}