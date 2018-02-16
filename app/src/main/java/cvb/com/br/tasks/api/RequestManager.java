package cvb.com.br.tasks.api;

public class RequestManager<T> {
    public void onSuccess(T result) {};
    public void onError(int errorCod, String errorMsg) {};
}

