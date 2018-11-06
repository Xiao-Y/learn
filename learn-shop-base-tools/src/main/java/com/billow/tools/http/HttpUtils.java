package com.billow.tools.http;

import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.resData.BaseResponse;

/**
 * http处理工具
 *
 * @author liuyongtao
 * @create 2018-11-06 9:50
 */
public class HttpUtils {

    /**
     * 获取返回对象中的返回值
     *
     * @param baseResponse 返回对象
     * @return T 返回值
     * @author LiuYongTao
     * @date 2018/11/6 10:02
     */
    public static <T> T getResData(BaseResponse<T> baseResponse) {
        if (baseResponse != null) {
            String resCode = baseResponse.getResCode();
            if (!ResCodeEnum.OK.equals(resCode)) {
                throw new RuntimeException(baseResponse.toString());
            } else {
                return baseResponse.getResData();
            }
        }
        return null;
    }
}
