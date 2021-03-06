package cvb.com.br.tasks.control;

import android.content.Context;

import com.google.gson.Gson;

import java.util.AbstractMap;
import java.util.HashMap;

import cvb.com.br.tasks.R;
import cvb.com.br.tasks.exception.InternetNotAvailableException;
import cvb.com.br.tasks.util.Constant;
import cvb.com.br.tasks.util.SecurityPreferences;

public class Control {
    private Context ctx;

    public Control(Context ctx) {
        this.ctx = ctx;
    }

    protected int handleExceptionCode(Exception E) {
        if (E instanceof InternetNotAvailableException)
            return Constant.STATUS_CODE.INTERNET_NOT_AVAILABLE;

        return Constant.STATUS_CODE.INTERNAL_SERVER_ERROR;
    }

    protected String handleExceptionMessage(Exception E) {
        if (E instanceof InternetNotAvailableException)
            return ctx.getString(R.string.error_network_not_available);

        return ctx.getString(R.string.error_unexpected);
    }

    protected String handleErrorMessage(String json) {
        return (new Gson()).fromJson(json, String.class);
    }

    protected int handleErrorCode(int errorCode) {
        return (errorCode == Constant.STATUS_CODE.FORBIDDEN ||
                errorCode == Constant.STATUS_CODE.NOT_FOUND ?
                errorCode :
                Constant.STATUS_CODE.INTERNAL_SERVER_ERROR);
    }

    public AbstractMap<String,String> getHeaderParams() {
        AbstractMap<String, String> headerParams = new HashMap<>();

        SecurityPreferences preferences = new SecurityPreferences(this.ctx);

        headerParams.put(Constant.HEADER.KEY_PERSON, preferences.getStoredString(Constant.HEADER.KEY_PERSON));
        headerParams.put(Constant.HEADER.KEY_TOKEN, preferences.getStoredString(Constant.HEADER.KEY_TOKEN));

        return headerParams;
    }
}
