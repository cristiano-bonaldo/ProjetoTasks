package cvb.com.br.tasks.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cvb.com.br.tasks.R;
import cvb.com.br.tasks.api.RequestManager;
import cvb.com.br.tasks.api.UserRequestManager;
import cvb.com.br.tasks.model.User;
import cvb.com.br.tasks.util.Constant;
import cvb.com.br.tasks.util.SecurityPreferences;
import cvb.com.br.tasks.util.ToastUtil;

public class ActLogin extends AppCompatActivity {

    private class ViewHolder {
        private EditText edEmail;
        private EditText edSenha;

        private TextView tvCadastrar;

        private Button btOk;

        private void init(Activity act) {
            edEmail = act.findViewById(R.id.ed_email);
            edSenha = act.findViewById(R.id.ed_senha);

            btOk = act.findViewById(R.id.bt_ok);

            tvCadastrar = act.findViewById(R.id.tv_cadastrar);
        }
    }

    //=====================================

    private ViewHolder vh = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vh.init(this);

        vh.btOk.setOnClickListener(btOKListener);

        vh.tvCadastrar.setText(Html.fromHtml(getString(R.string.casdastrar_usuario)));
        vh.tvCadastrar.setOnClickListener(tvCadastrarListener);

        userLogedIN();
    }

    private void userLogedIN() {
        SecurityPreferences pref = new SecurityPreferences(this);
        String pkey = pref.getStoredString(Constant.HEADER.KEY_PERSON);
        String tkey = pref.getStoredString(Constant.HEADER.KEY_TOKEN);

        if (pkey.trim().length() > 0 && tkey.trim().length() > 0)
            abreTelaPrincipal();
    }

    private void abreTelaPrincipal() {
        Intent it = new Intent(getContext(), MainActivity.class);
        startActivity(it);

        finish();
    }

    private void abreTelaCadastro() {
        Intent it = new Intent(getContext(), ActCadastroUsuario.class);
        startActivity(it);
    }

    private View.OnClickListener tvCadastrarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            abreTelaCadastro();
        }
    };

    private View.OnClickListener btOKListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            login();
        }
    };

    private Context getContext() {
        return this;
    }

    private void login() {
        if (!userValidation())
            return;

        User user = new User(
                null,
                vh.edEmail.getText().toString(),
                vh.edSenha.getText().toString()
        );

        (new UserRequestManager(this)).login(user, rm);
    }

    private boolean userValidation() {
        if (vh.edEmail.getText().toString().trim().length() == 0) {
            vh.edEmail.setError("E-mail inválido. Informe o e-mail.");
            return false;
        }

        if (vh.edSenha.getText().toString().trim().length() == 0) {
            vh.edSenha.setError("Senha inválida. Informe a senha.");
            return false;
        }

        return true;
    }

    private RequestManager rm = new RequestManager() {
        @Override
        public void onSuccess(Object result) {
            abreTelaPrincipal();
        }

        @Override
        public void onError(int errorCod, String errorMsg) {
            ToastUtil.showMessage(getContext(), errorMsg);
        }
    };
}
