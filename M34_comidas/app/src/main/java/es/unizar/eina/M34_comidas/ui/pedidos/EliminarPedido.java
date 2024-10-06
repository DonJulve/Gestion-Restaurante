package es.unizar.eina.M34_comidas.ui.pedidos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import es.unizar.eina.M34_comidas.R;

/** Pantalla principal de la aplicación M34_comidas */
public class EliminarPedido extends AppCompatActivity {


    Button boton_cancelar;
    Button boton_confirmar;
    TextView texto_eliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eliminar_plato);

        boton_cancelar = (Button) findViewById(R.id.boton_eliminar_cancelar);
        boton_confirmar = (Button) findViewById(R.id.boton_eliminar_confirmar);
        texto_eliminar = (TextView) findViewById(R.id.texto_eliminar);
        texto_eliminar.setText("¿Seguro que quiere eliminar el pedido?");
    }
    public void botonCancelarPulsado(View view){
        Intent replyIntent = new Intent();
        Log.d("cancelar", "llega");
        setResult(RESULT_CANCELED, replyIntent);
        finish();
    }

    public void botonConfirmarPulsado(View view){
        Intent replyIntent = new Intent();
        Log.d("confirmar", "llega");
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}