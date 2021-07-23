package app.riju.retrofit_crud.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {

    String error;
    @SerializedName("message")
    String msg;

    public RegisterResponse() {
    }

    public RegisterResponse(String error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}