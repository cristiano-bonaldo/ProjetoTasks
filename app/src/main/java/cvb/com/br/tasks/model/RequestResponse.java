package cvb.com.br.tasks.model;

public class RequestResponse {

    public String json;
    public int statusCode;

    public RequestResponse(String json, int statusCode) {
        this.json = json;
        this.statusCode = statusCode;
    }
}
