package cvb.com.br.tasks.gui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cvb.com.br.tasks.R;
import cvb.com.br.tasks.api.RequestManager;
import cvb.com.br.tasks.api.UserRequestManager;
import cvb.com.br.tasks.control.UserControl;
import cvb.com.br.tasks.model.User;
import cvb.com.br.tasks.util.ToastUtil;

public class ActCadastroUsuario extends AppCompatActivity {

    private class ViewHolder {

        private EditText edNome;
        private EditText edEmail;
        private EditText edSenha;

        private Button btGravar;

        public void init(Activity act) {
            edNome  = act.findViewById(R.id.ed_nome);
            edEmail = act.findViewById(R.id.ed_email);
            edSenha = act.findViewById(R.id.ed_senha);

            btGravar = act.findViewById(R.id.bt_salvar);
        }
    }

    //==============

    private ViewHolder vh = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        vh.init(this);

        vh.btGravar.setOnClickListener(btGravarListener);
    }

    private View.OnClickListener btGravarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            saveUser();
        }
    };

    private void saveUser() {
        if (!userValidation())
            return;

        User user = new User(
                vh.edNome.getText().toString(),
                vh.edEmail.getText().toString(),
                vh.edSenha.getText().toString()
        );

        (new UserRequestManager(this)).create(user, rm);
    }

    private boolean userValidation() {
        if (vh.edNome.getText().toString().trim().length() == 0) {
            vh.edNome.setError("Nome inválido. Informe o nome.");
            return false;
        }

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

    private Context getContext() {
        return this;
    }

    private RequestManager rm = new RequestManager() {
        @Override
        public void onSuccess(Object result) {
            startActivity(new Intent(getContext(), MainActivity.class));
            finish();
        }

        @Override
        public void onError(int errorCod, String errorMsg) {
            ToastUtil.showMessage(getContext(), errorMsg);
        }
    };
}
