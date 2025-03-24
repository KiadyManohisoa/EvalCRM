package site.easy.to.build.crm.util;

public class RenderResponse<T> {
    private int code;
    private T data;
    private String message;
    private String error;

    public RenderResponse(int code, T data, String message, String error) {
        this.setCode(code);
        this.setData(data);
        this.setMessage(message);
        this.setError(error);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
