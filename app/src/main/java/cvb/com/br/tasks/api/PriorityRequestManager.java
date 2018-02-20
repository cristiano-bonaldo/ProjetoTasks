package cvb.com.br.tasks.api;

import android.content.Context;
import android.os.AsyncTask;

import cvb.com.br.tasks.control.PriorityControl;

public class PriorityRequestManager {

    private Context ctx;

    public PriorityRequestManager(Context ctx) {
        this.ctx = ctx;
    }

    public void getList(RequestManager rm) {
        AsyncTask<Void, Void, RequestResult<Boolean>> task = new PriorityRequest(ctx, rm);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    //----------------------

    private static class PriorityRequest extends AsyncTask<Void, Void, RequestResult<Boolean>> {
        private RequestManager rm;

        private PriorityControl priorityControl;

        public PriorityRequest(Context ctx, RequestManager rm) {
            this.rm = rm;

            priorityControl = new PriorityControl(ctx);
        }

        @Override
        protected RequestResult<Boolean> doInBackground(Void... voids) {
            return priorityControl.getList();
        }

        @Override
        protected void onPostExecute(RequestResult<Boolean> result) {
            int error = result.getError();

            if (error != RequestResult.NO_ERROR)
                rm.onError(result.getError(), result.getErrorMessage());
            else
                rm.onSuccess(result.getResult());
        }
    }
}
