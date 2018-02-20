package cvb.com.br.tasks.control;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;

import cvb.com.br.tasks.api.Request;
import cvb.com.br.tasks.api.RequestResult;
import cvb.com.br.tasks.dao.DAOPriority;
import cvb.com.br.tasks.model.Priority;
import cvb.com.br.tasks.model.RequestHeader;
import cvb.com.br.tasks.model.RequestParameter;
import cvb.com.br.tasks.model.RequestResponse;
import cvb.com.br.tasks.model.User;
import cvb.com.br.tasks.util.Constant;
import cvb.com.br.tasks.util.SecurityPreferences;
import cvb.com.br.tasks.util.URLBuilder;

public class PriorityControl extends Control {
    private Request request;
    private Context ctx;

    public PriorityControl(Context ctx) {
        super(ctx);

        this.ctx     = ctx;
        this.request = new Request(ctx);
    }

    public RequestResult<Boolean> getList() {
        RequestResult<Boolean> result = new RequestResult<>();

        String url = URLBuilder.getURL(Constant.ENDPOINT.ROOT, Constant.ENDPOINT.PRIORITY_GET);

        try {
            AbstractMap<String, String> headerParams = super.getHeaderParams();

            RequestParameter requestParameter = new RequestParameter(Constant.OPERATION_METHOD.GET, url, null, (HashMap<String, String>) headerParams);

            RequestResponse resp = request.execute(requestParameter);

            if (resp.statusCode == Constant.STATUS_CODE.SUCCESS) {

                Type collectionType = new TypeToken<List<Priority>>(){}.getType();
                List<Priority> list = (new Gson()).fromJson(resp.json, collectionType);

                DAOPriority dao = new DAOPriority(this.ctx);
                dao.clear();
                dao.load(list);

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
