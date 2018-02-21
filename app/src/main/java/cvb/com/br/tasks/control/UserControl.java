package cvb.com.br.tasks.control;

import android.content.Context;

import com.google.gson.Gson;

import java.util.AbstractMap;
import java.util.HashMap;

import cvb.com.br.tasks.api.Request;
import cvb.com.br.tasks.api.RequestResult;
import cvb.com.br.tasks.model.RequestHeader;
import cvb.com.br.tasks.model.RequestParameter;
import cvb.com.br.tasks.model.RequestResponse;
import cvb.com.br.tasks.model.User;
import cvb.com.br.tasks.util.Constant;
import cvb.com.br.tasks.util.SecurityPreferences;
import cvb.com.br.tasks.util.URLBuilder;

public class UserControl extends Control {
    private Request request;
    private Context ctx;

    public UserControl(Context ctx) {
        super(ctx);

        this.ctx     = ctx;
        this.request = new Request(ctx);
    }

    public RequestResult<Boolean> create(User user) {
        RequestResult<Boolean> result = new RequestResult<>();

        String url = URLBuilder.getURL(Constant.ENDPOINT.ROOT, Constant.ENDPOINT.AUTHENTICATION_CREATE);

        try {
            AbstractMap<String, String> params = new HashMap<>();
            params.put(Constant.USER_PARAMETER.NAME, user.getNome());
            params.put(Constant.USER_PARAMETER.EMAIL, user.getEmail());
            params.put(Constant.USER_PARAMETER.PASSWORD, user.getSenha());
            params.put(Constant.USER_PARAMETER.RECEIVE_NEWS, "false");

            RequestParameter requestParameter = new RequestParameter(Constant.OPERATION_METHOD.POST, url, (HashMap<String, String>) params, null);

            RequestResponse resp = request.execute(requestParameter);

            if (resp.statusCode == Constant.STATUS_CODE.SUCCESS) {
                RequestHeader requestHeader = (new Gson()).fromJson(resp.json, RequestHeader.class);

                SecurityPreferences pref = new SecurityPreferences(ctx);

                pref.storeString(Constant.HEADER.KEY_PERSON, requestHeader.personKey);
                pref.storeString(Constant.HEADER.KEY_TOKEN, requestHeader.token);
                pref.storeString(Constant.USER.NAME,  requestHeader.name);
                pref.storeString(Constant.USER.EMAIL, user.getEmail());

                result.setResult(true);
            }
            else {
                result.setError(
                        super.handleErrorCode(resp.statusCode),
                        super.handleErrorMessage(resp.json));
            }
        }
        catch (Exception E) {
            result.setError(
                    super.handleExceptionCode(E),
                    super.handleExceptionMessage(E));
        }

        return result;
    }

    public RequestResult<Boolean> login(User user) {
        RequestResult<Boolean> result = new RequestResult<>();

        String url = URLBuilder.getURL(Constant.ENDPOINT.ROOT, Constant.ENDPOINT.AUTHENTICATION_LOGIN);

        try {
            AbstractMap<String, String> params = new HashMap<>();
            params.put(Constant.USER_PARAMETER.EMAIL, user.getEmail());
            params.put(Constant.USER_PARAMETER.PASSWORD, user.getSenha());

            RequestParameter requestParameter = new RequestParameter(Constant.OPERATION_METHOD.POST, url, (HashMap<String, String>) params, null);

            RequestResponse resp = request.execute(requestParameter);

            if (resp.statusCode == Constant.STATUS_CODE.SUCCESS) {
                RequestHeader requestHeader = (new Gson()).fromJson(resp.json, RequestHeader.class);

                SecurityPreferences pref = new SecurityPreferences(ctx);

                pref.storeString(Constant.HEADER.KEY_PERSON, requestHeader.personKey);
                pref.storeString(Constant.HEADER.KEY_TOKEN, requestHeader.token);
                pref.storeString(Constant.USER.NAME,  requestHeader.name);
                pref.storeString(Constant.USER.EMAIL, user.getEmail());

                result.setResult(true);
            }
            else {
                result.setError(
                        super.handleErrorCode(resp.statusCode),
                        super.handleErrorMessage(resp.json));
            }
        }
        catch (Exception E) {
            result.setError(
                    super.handleExceptionCode(E),
                    super.handleExceptionMessage(E));
        }

        return result;
    }
}
