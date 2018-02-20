package cvb.com.br.tasks.gui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cvb.com.br.tasks.R;
import cvb.com.br.tasks.dao.DAOPriority;
import cvb.com.br.tasks.model.Priority;
import cvb.com.br.tasks.model.Task;

public class ActCadastroTask extends AppCompatActivity {

    private class ViewHolder {

        private EditText etDescricao;
        private CheckBox cbCompleta;
        private Spinner spPrioridade;

        private Button btData;
        private Button btAdicionar;

        private void init(Activity act) {
            etDescricao  = act.findViewById(R.id.et_descricao);
            cbCompleta   = act.findViewById(R.id.cb_completa);
            spPrioridade = act.findViewById(R.id.sp_prioridade);

            btData      = act.findViewById(R.id.bt_data);
            btAdicionar = act.findViewById(R.id.bt_adicionar);
        }
    }

    private ViewHolder vh = new ViewHolder();

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_task);

        vh.init(this);

        loadSpinner();

        vh.btData.setOnClickListener(btDataOnClickListener);
        vh.btAdicionar.setOnClickListener(btAdicionarOnClickListener);
   }

    private void loadSpinner() {
        List<String> lista = new ArrayList<>();
        for (Priority priority : (new DAOPriority(this)).getList())
            lista.add(priority.getDescription());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista);
        vh.spPrioridade.setAdapter(adapter);
    }

    View.OnClickListener btDataOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int year;
            int month;
            int day;

            if (!vh.btData.getText().toString().toUpperCase().contains("SELECIONE")){
                day   = Integer.valueOf(vh.btData.getText().toString().substring(0, 2));
                month = Integer.valueOf(vh.btData.getText().toString().substring(3, 5)) - 1;
                year  = Integer.valueOf(vh.btData.getText().toString().substring(6));
            }
            else {
                Calendar c = Calendar.getInstance();
                day   = c.get(Calendar.DAY_OF_MONTH);
                month = c.get(Calendar.MONTH);
                year  = c.get(Calendar.YEAR);
            }

            new DatePickerDialog(getContext(), dateListener, year, month, day).show();
        }
    };

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int y, int m, int d) {
            Calendar c = Calendar.getInstance();
            c.set(y, m, d);

            vh.btData.setText(dateFormat.format(c.getTime()));
        }
    };

    View.OnClickListener btAdicionarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!validaTask())
                return;

            int completa = (vh.cbCompleta.isChecked() ? 1 : 0);

            vh.spPrioridade.getSelectedItem();

            Task task = new Task(vh.etDescricao.getText().toString(),
                        vh.btData.getText().toString(),
                        completa,

                    );
        }
    };

    private Context getContext() {
        return this;
    }

    private boolean validaTask() {
        if (vh.etDescricao.getText().toString().trim().length() == 0) {
            vh.etDescricao.setError("Descrição inválida! Informe um NOME para a Tarefa!");
            return false;
        }

        return true;
    }
}
