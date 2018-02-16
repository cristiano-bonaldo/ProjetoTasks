package cvb.com.br.tasks.model;

import java.util.AbstractMap;
import java.util.HashMap;

public class RequestParameter {

    public String method;
    public String url;
    public AbstractMap<String, String> params;
    public AbstractMap<String, String> headerParams;

    public RequestParameter(String method, String url, HashMap<String, String> params, HashMap<String, String> headerParams) {
        this.method = method;
        this.url = url;
        this.params = params;
        this.headerParams = headerParams;
    }
}
