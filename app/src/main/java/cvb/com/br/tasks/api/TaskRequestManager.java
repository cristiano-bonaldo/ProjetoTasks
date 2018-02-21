package cvb.com.br.tasks.api;

import android.content.Context;
import android.os.AsyncTask;

import cvb.com.br.tasks.control.PriorityControl;
import cvb.com.br.tasks.control.TaskControl;
import cvb.com.br.tasks.model.Task;

public class TaskRequestManager {

    private Context ctx;

    public TaskRequestManager(Context ctx) {
        this.ctx = ctx;
    }

    public void insert(RequestManager rm, Task task) {
        AsyncTask<Void, Void, RequestResult<Boolean>> asyncTask = new TaskInsertRequest(ctx, rm, task);
        asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    //----------------------

    private static class TaskInsertRequest extends AsyncTask<Void, Void, RequestResult<Boolean>> {
        private RequestManager rm;
        private Task task;
        private TaskControl taskControl;

        public TaskInsertRequest(Context ctx, RequestManager rm, Task task) {
            this.rm   = rm;
            this.task = task;

            taskControl = new TaskControl(ctx);
        }

        @Override
        protected RequestResult<Boolean> doInBackground(Void... voids) {
            return taskControl.insert(task);
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
