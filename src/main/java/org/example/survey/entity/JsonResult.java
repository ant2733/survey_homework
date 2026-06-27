package org.example.survey.entity;

import lombok.Data;

@Data
public class JsonResult {
    private Integer code;
    private String msg;
    private Object data;
    public static JsonResult success(Object data) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(200);
        jsonResult.setMsg("success");
        jsonResult.setData(data);
        return jsonResult;
    }
    public static JsonResult error(Integer code, String msg) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCode(code);
        jsonResult.setMsg(msg);
        return jsonResult;
    }
}
