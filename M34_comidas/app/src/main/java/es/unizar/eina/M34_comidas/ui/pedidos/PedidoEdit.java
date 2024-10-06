package es.unizar.eina.M34_comidas.ui.pedidos;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.M34_comidas.R;
import es.unizar.eina.M34_comidas.ui.platos.PlatoEdit;

/** Pantalla utilizada para la creación o edición de una nota */
public class PedidoEdit extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String PEDIDO_ID = "id";
    public static final String PEDIDO_NOMBRE = "nombre";
    public static final String PEDIDO_TELEFONO = "telefono";
    public static final String PEDIDO_FECHA = "fecha";
    public static final String PEDIDO_HORA = "hora";
    public static final String PEDIDO_ESTADO = "estado";

    public static final String PEDIDO_PRECIO_TOTAL = "precioTotal";

    private Integer mRowId;

    private EditText mNombreText;

    private EditText mTelefonoText;

    private TextView mFechaText;

    private TextView mHoraText;

    private String estado;

    Button mSaveButton;

    Button mBotonCancelar;

    private RadioGroup mGrupoEstado;    // Estado

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_pedido);

        mNombreText = findViewById(R.id.insertar_nombre);
        mTelefonoText = findViewById(R.id.insertar_telefono);
        mFechaText = findViewById(R.id.insertar_fecha);
        mHoraText = findViewById(R.id.insertar_hora);
        //mEstadoText = findViewById(R.id.inser)

        mSaveButton = findViewById(R.id.boton_confirmar);
        mBotonCancelar = findViewById(R.id.boton_cancelar);

        mGrupoEstado = findViewById(R.id.estado_elegir);

        findViewById(R.id.insertar_fecha).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePickerDialog();
            }
        });

        findViewById(R.id.insertar_hora).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimePickerDialog();
            }
        });

        // Boton continuar
        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (faltaPorRellenar()) {
                //setResult(RESULT_CANCELED, replyIntent);
                Toast.makeText(
                        this.getApplicationContext(),
                        "Rellene todos los campos",
                        Toast.LENGTH_LONG).show();
            } else {
                replyIntent.putExtra(PedidoEdit.PEDIDO_NOMBRE, mNombreText.getText().toString());
                replyIntent.putExtra(PedidoEdit.PEDIDO_TELEFONO, mTelefonoText.getText().toString());
                replyIntent.putExtra(PedidoEdit.PEDIDO_FECHA, mFechaText.getText().toString());
                replyIntent.putExtra(PedidoEdit.PEDIDO_HORA, mHoraText.getText().toString());
                replyIntent.putExtra(PedidoEdit.PEDIDO_ESTADO, estado);
                if (mRowId!=null) {
                    replyIntent.putExtra(PedidoEdit.PEDIDO_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

        // Boton volver
        mBotonCancelar.setOnClickListener(View -> {
            Intent replyIntent = new Intent();
            setResult(RESULT_CANCELED, replyIntent);
            finish();
        });

        populateFields();

    }

    private void populateFields () {
        mRowId = null;
        Bundle extras = getIntent().getExtras();
        if (extras!=null) {
            mNombreText.setText(extras.getString(PedidoEdit.PEDIDO_NOMBRE));
            mTelefonoText.setText(extras.getString(PedidoEdit.PEDIDO_TELEFONO));
            mFechaText.setText(extras.getString(PedidoEdit.PEDIDO_FECHA));
            mRowId = extras.getInt(PedidoEdit.PEDIDO_ID);
            mHoraText.setText(extras.getString(PedidoEdit.PEDIDO_HORA));
            estado = extras.getString(PedidoEdit.PEDIDO_ESTADO);
            Log.d("Populate fields", estado);
            switch(estado){
                case "solicitado":
                    mGrupoEstado.check(mGrupoEstado.getChildAt(0).getId());
                    Log.d("Solicitado", "llega");
                    break;
                case "preparado":
                    mGrupoEstado.check(mGrupoEstado.getChildAt(1).getId());
                    Log.d("Preparado", "llega");
                    break;
                case "recogido":
                    mGrupoEstado.check(mGrupoEstado.getChildAt(2).getId());
                    Log.d("Recogido", "llega");
                    break;
                default:
                    mGrupoEstado.check(mGrupoEstado.getChildAt(0).getId());
                    break;
            }
        }
        else {
            estado = "solicitado";
            mGrupoEstado.check(mGrupoEstado.getChildAt(0).getId());
        }
    }

    public void onRadioButtonClicked(View view) {
        Log.d("funcion", "llega");
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int id = view.getId();
        Log.d("funcion", Integer.toString(id));
        // Check which radio button was clicked
        estado = "solicitado";

        if (id == R.id.boton_solicitado){
            if (checked) {
                Log.d("Solicitado", "llega");
                //radioButton = (RadioButton) view;
                estado = "solicitado";
            }
        }
        else if (id == R.id.boton_preparado){
            if (checked) {
                Log.d("Preparado", "llega");
                //radioButton = (RadioButton) view;
                estado = "preparado";
            }
        }
        else if (id == R.id.boton_recogido){
            if (checked) {
                Log.d("Recogido", "llega");
                // radioButton = (RadioButton) view;
                estado = "recogido";
            }
        }
    }

    private void ShowDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                (Context) this,
                (DatePickerDialog.OnDateSetListener) this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void ShowTimePickerDialog(){
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                (Context) this,
                (TimePickerDialog.OnTimeSetListener) this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                DateFormat.is24HourFormat(this)
        );
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Obtener el dia de la semana de la entrega
        Calendar fechaEntrega = Calendar.getInstance();
        fechaEntrega.set(year,month,dayOfMonth); // vairables int
        int DiaSemana =  fechaEntrega.get(Calendar.DAY_OF_WEEK);   // Dia de la semana

        // Obtener el dia actual
        Calendar fechaActual = Calendar.getInstance();
        int anioActual = fechaActual.get(Calendar.YEAR);
        int mesActual = fechaActual.get(Calendar.MONTH) + 1; // Se suma 1 ya que los meses en Calendar se indexan desde 0
        int diaActual = fechaActual.get(Calendar.DAY_OF_MONTH);

        if(DiaSemana==Calendar.MONDAY){ // No se puede entregar en lunes
            // Solo se permite recogidas de martes a domingos
            Toast.makeText(
                    this,
                    "No se hacen entregas los lunes",
                    Toast.LENGTH_LONG).show();
        }
        else if (fechaEntrega.before(fechaActual) || fechaEntrega.equals(fechaActual)){   // La fecha tiene que ser posterior a la actual
            // La fecha de sentregas tiene que ser superior a al actual
            Toast.makeText(
                    this,
                    "La fecha de entrega tiene que ser posterior a la actual",
                    Toast.LENGTH_LONG).show();
        }
        else {  // La fecha es valida
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            mFechaText.setText(date);
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if ((hourOfDay < 19) || (hourOfDay > 23) || (hourOfDay == 19 && minute < 30)){ // Fuera del rango de entregas
            Toast.makeText(
                    this,
                    "Solo se puede recoger los pedidos entre las 19:30 y 23:00",
                    Toast.LENGTH_LONG).show();
        }
        else {  // Hora valida
            String time = hourOfDay + ":" + minute;
            mHoraText.setText(time);
        }
    }

    private boolean faltaPorRellenar(){
        return TextUtils.isEmpty(mNombreText.getText()) || TextUtils.isEmpty(mTelefonoText.getText())
                || mFechaText.getText().equals("Pulse aqui para insertar fecha") || mHoraText.getText().equals("Pulse aqui para insertar hora");
    }
}
