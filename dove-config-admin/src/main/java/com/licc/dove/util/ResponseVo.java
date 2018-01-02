package com.licc.dove.util;

/**
 * @author lichangchao
 * @功能 返回json格式数据
 *
 */
public class ResponseVo {
    private static final long serialVersionUID = -3819569459544701549L;
    private Integer           code;
    private Integer            draw;
    private String            desc;
    private Object            data;

    private ResponseVo() {
    }

    public Integer getCode() {
        return this.code;
    }

    public ResponseVo setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getDesc() {
        return this.desc;
    }

    public ResponseVo setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public ResponseVo setData(Object data) {
        this.data = data;
        return this;
    }

    public Integer getDraw() {
        return draw;
    }

    public ResponseVo setDraw(Integer draw) {
        this.draw = draw;
        return this;
    }

     static ResponseVo BUILDER() {
        return new ResponseVo();
    }
}
