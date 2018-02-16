package cvb.com.br.tasks.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import cvb.com.br.tasks.control.UserControl;
import cvb.com.br.tasks.model.User;

public class UserRequestManager {

    private Context ctx;

    public UserRequestManager(Context ctx) {
        this.ctx = ctx;
    }

    public void create(User user, RequestManager rm) {
        AsyncTask<Void, Void, RequestResult<Boolean>> task = new UserRequestCreate(ctx, user, rm);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void login(User user, RequestManager rm) {
        AsyncTask<Void, Void, RequestResult<Boolean>> task = new UserRequestLogin(ctx, user, rm);
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    //----------------------

    private static class UserRequestCreate extends AsyncTask<Void, Void, RequestResult<Boolean>> {
        private User user;
        private RequestManager rm;

        private UserControl userControl;

        public UserRequestCreate(Context ctx, User user, RequestManager rm) {
            this.rm   = rm;
            this.user = user;

            userControl = new UserControl(ctx);
        }

        @Override
        protected RequestResult<Boolean> doInBackground(Void... voids) {
            return userControl.create(this.user);
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

    //----------------------

    private static class UserRequestLogin extends AsyncTask<Void, Void, RequestResult<Boolean>> {
        private User user;
        private RequestManager rm;

        private UserControl userControl;

        public UserRequestLogin(Context ctx, User user, RequestManager rm) {
            this.rm   = rm;
            this.user = user;

            userControl = new UserControl(ctx);
        }

        @Override
        protected RequestResult<Boolean> doInBackground(Void... voids) {
            return userControl.login(this.user);
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
