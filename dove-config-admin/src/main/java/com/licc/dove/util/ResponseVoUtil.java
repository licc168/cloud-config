package com.licc.dove.util;


import com.licc.dove.enums.EResultCode;

/**
 * @author lichangchao
 * @功能 返回json格式数据
 *
 */
public class ResponseVoUtil {
    public static ResponseVo failResult(String message) {
        return ResponseVo.BUILDER().setCode(EResultCode.FAIL.getKey()).setDesc(message);
    }

    public static ResponseVo failResult(String Message, Object b) {
        return ResponseVo.BUILDER().setCode(EResultCode.FAIL.getKey()).setDesc(Message).setData(b);
    }

    public static ResponseVo successMsg(String Message) {
        return ResponseVo.BUILDER().setCode(EResultCode.SUCCESS.getKey()).setDesc(Message);
    }

    public static ResponseVo successData(Object data) {
        return ResponseVo.BUILDER().setCode(EResultCode.SUCCESS.getKey()).setData(data);
    }

    public static ResponseVo successResult(String Message, Object data) {
        return ResponseVo.BUILDER().setCode(EResultCode.SUCCESS.getKey()).setData(data).setDesc(Message);
    }
}
