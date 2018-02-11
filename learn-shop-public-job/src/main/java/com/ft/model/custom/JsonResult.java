package com.ft.model.custom;

public class JsonResult {
    private boolean success = false;
    private String message;
    private Long total;
    private Object root;
    private String hint = "提示";
    // success,error,tip
    private String type;
    private Object data;

    public JsonResult() {
        super();
    }

    public JsonResult(Long total, Object root) {
        super();
        this.total = total;
        this.root = root;
    }

    public JsonResult(String message, Long total, Object root) {
        super();
        this.message = message;
        this.total = total;
        this.root = root;
    }

    public JsonResult(boolean success, String message, Long total, Object root) {
        super();
        this.success = success;
        this.message = message;
        this.total = total;
        this.root = root;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Object getRoot() {
        return root;
    }

    public void setRoot(Object root) {
        this.root = root;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    /**
     * success,error,tip
     *
     * @return
     * @author XiaoY
     * @date: 2017年5月28日 下午6:41:34
     */
    public String getType() {
        return type;
    }

    /**
     * success,error,tip
     *
     * @param type
     * @author XiaoY
     * @date: 2017年5月28日 下午6:41:37
     */
    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
