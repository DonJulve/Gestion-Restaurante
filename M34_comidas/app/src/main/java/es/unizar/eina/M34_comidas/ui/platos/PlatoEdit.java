package es.unizar.eina.M34_comidas.ui.platos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import es.unizar.eina.M34_comidas.R;

/** Pantalla utilizada para la creación o edición de una nota */
public class PlatoEdit extends AppCompatActivity {

    public static final String PLATO_TITLE = "title";
    public static final String PLATO_BODY = "body";
    public static final String PLATO_ID = "id";
    public static final String PLATO_PRECIO = "precio";
    public static final String PLATO_CATEGORIA = "categoria";

    private EditText mNombreText;

    private EditText mDescripcionText;

    private EditText mPrecioText;

    private TextView mCategoriaRadioButton;

    private TextView mTextoPrincipal;

    private Integer mRowId;

    private String categoria;

    private int precio;

    Button mSaveButton;

    RadioGroup mGrupoCategoria;

    Button mBotonCancelar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editplato);

        mNombreText = findViewById(R.id.nombre);
        mDescripcionText = findViewById(R.id.insertar_descripcion);
        mPrecioText = findViewById(R.id.escribir_precio);
        mCategoriaRadioButton = findViewById(R.id.categoria);
        mGrupoCategoria = findViewById(R.id.estado_elegir);
        mBotonCancelar = findViewById(R.id.cancelar);

        mSaveButton = findViewById(R.id.confirmar);
        mTextoPrincipal = findViewById(R.id.texto_principal);

        // Por defecto primero
        categoria = "primero";

        mSaveButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            if (faltaPorRellenar()) {
                Toast.makeText(
                        this.getApplicationContext(),
                        "Rellene todos los campos",
                        Toast.LENGTH_LONG).show();
            } else {
                replyIntent.putExtra(PlatoEdit.PLATO_TITLE, mNombreText.getText().toString());
                replyIntent.putExtra(PlatoEdit.PLATO_BODY, mDescripcionText.getText().toString());
                replyIntent.putExtra(PlatoEdit.PLATO_PRECIO, mPrecioText.getText().toString());
                Log.d("Edit", mPrecioText.getText().toString());
                replyIntent.putExtra(PlatoEdit.PLATO_CATEGORIA, categoria);
                if (mRowId!=null) {
                    replyIntent.putExtra(PlatoEdit.PLATO_ID, mRowId.intValue());
                }
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });

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
            mTextoPrincipal.setText("Editar plato");
            mNombreText.setText(extras.getString(PlatoEdit.PLATO_TITLE));
            mDescripcionText.setText(extras.getString(PlatoEdit.PLATO_BODY));
            mPrecioText.setText(extras.getString(PlatoEdit.PLATO_PRECIO));
            mRowId = extras.getInt(PlatoEdit.PLATO_ID);
            categoria = extras.getString(PlatoEdit.PLATO_CATEGORIA);
            switch(categoria){
                case "primero":
                    mGrupoCategoria.check(mGrupoCategoria.getChildAt(0).getId());
                    break;
                case "segundo":
                    mGrupoCategoria.check(mGrupoCategoria.getChildAt(1).getId());
                    break;
                case "tercero":
                    mGrupoCategoria.check(mGrupoCategoria.getChildAt(2).getId());
                    break;
                default:
                    mGrupoCategoria.check(mGrupoCategoria.getChildAt(0).getId());
                    break;
            }
        }
        else {
            categoria = "primero";
            mGrupoCategoria.check(mGrupoCategoria.getChildAt(0).getId());
        }
    }

    public void onRadioButtonClicked(View view) {
        Log.d("funcion", "llega");
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        int id = view.getId();
        Log.d("funcion", Integer.toString(id));
        // Check which radio button was clicked
        categoria = "primero";

        if (id == R.id.primero){
            if (checked) {
                Log.d("primero", "llega");
                //radioButton = (RadioButton) view;
                categoria = "primero";
                Log.d("primero", categoria);
            }
        }
        else if (id == R.id.segundo){
            if (checked) {
                Log.d("segundo", "llega");
                //radioButton = (RadioButton) view;
                categoria = "segundo";
            }
        }
        else if (id == R.id.tercero){
            if (checked) {
                Log.d("tercero", "llega");
                // radioButton = (RadioButton) view;
                categoria = "tercero";
            }
        }
    }

    private boolean faltaPorRellenar(){
        return TextUtils.isEmpty(mNombreText.getText()) || TextUtils.isEmpty(mDescripcionText.getText())
                ||TextUtils.isEmpty(mPrecioText.getText());
    }

}
