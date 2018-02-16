package cvb.com.br.tasks.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cvb.com.br.tasks.R;

public class ToastUtil {

    public static void showMessage(Context ctx, String info) {
        Toast toast = Toast.makeText(ctx, null, Toast.LENGTH_LONG);

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(R.layout.toast_util, null);

        TextView t = v.findViewById(R.id.tv_info);
        t.setText(info);

        toast.setView(v);
        toast.show();
    }
}
