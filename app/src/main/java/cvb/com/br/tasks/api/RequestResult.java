package cvb.com.br.tasks.api;

public class RequestResult<T> {

    public static final int NO_ERROR = -1;

    private int error = NO_ERROR;

    private String errorMessage = "";

    private T result;

    public int getError() {
        return error;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setError(int error, String errorMessage) {
        this.error        = error;
        this.errorMessage = errorMessage;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
