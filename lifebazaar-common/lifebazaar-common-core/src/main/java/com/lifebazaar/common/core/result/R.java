package com.lifebazaar.common.core.result;

import lombok.Data;
import java.io.Serializable;

@Data
public class R<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public static <T> R<T> ok(T data) {
        RO> r = new RO>();
        r.setCode(200);
        r.setMsg("success");
        r.setData(data);
        return r;
    }

    public static <T> RO> fail(String msg) {
        R<T> r = new R<();
        r.setCode(500);
        r.setMsg(msg);
        return r;
    }
}
