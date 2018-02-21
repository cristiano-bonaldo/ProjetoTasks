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
import cvb.com.br.tasks.model.RequestParameter;
import cvb.com.br.tasks.model.RequestResponse;
import cvb.com.br.tasks.model.Task;
import cvb.com.br.tasks.util.Constant;
import cvb.com.br.tasks.util.Format;
import cvb.com.br.tasks.util.URLBuilder;

public class TaskControl extends Control {
    private Request request;
    private Context ctx;

    public TaskControl(Context ctx) {
        super(ctx);

        this.ctx     = ctx;
        this.request = new Request(ctx);
    }

    public RequestResult<Boolean> insert(Task task) {
        RequestResult<Boolean> result = new RequestResult<>();

        String url = URLBuilder.getURL(Constant.ENDPOINT.ROOT, Constant.ENDPOINT.TASK_INSERT);

        try {
            AbstractMap<String, String> headerParams = super.getHeaderParams();

            AbstractMap<String, String> params = new HashMap<>();
            params.put(Constant.TASK_PARAMETER.PRIORITY_ID, String.valueOf(task.getIdPrioridade()));
            params.put(Constant.TASK_PARAMETER.DESCRIPTION, task.getDescricao());
            params.put(Constant.TASK_PARAMETER.DUEDATE, Format.formatDate(task.getData(), "yyyy-MM-dd"));
            params.put(Constant.TASK_PARAMETER.COMPLETE, Format.formatBoolean(task.getCompleta()));

            RequestParameter requestParameter = new RequestParameter(Constant.OPERATION_METHOD.POST, url, (HashMap<String, String>) params, (HashMap<String, String>) headerParams);

            RequestResponse resp = request.execute(requestParameter);

            if (resp.statusCode == Constant.STATUS_CODE.SUCCESS) {

                Boolean status = (new Gson()).fromJson(resp.json, Boolean.class);

                result.setResult(status);
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
