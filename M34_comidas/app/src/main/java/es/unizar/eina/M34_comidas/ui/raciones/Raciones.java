

package es.unizar.eina.M34_comidas.ui.raciones;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import es.unizar.eina.M34_comidas.R;


import es.unizar.eina.M34_comidas.database.Platos.Plato;
import es.unizar.eina.M34_comidas.database.platos_pedidos.PlatosPedidosRel;
import es.unizar.eina.M34_comidas.ui.pedidos.PedidoEdit;
import es.unizar.eina.M34_comidas.ui.platos.PlatoViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Raciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Raciones extends AppCompatActivity {

    RecyclerView mRecyclerView;

    PlatoPedidoRelListAdapter mAdapter;

    Button mBotonConfirmar;
    Button mBotonVolver;

    TextView textoPrecioTotal;

    private static PlatoPedidoRelViewModel mPlatoPedidoRelViewModel;
    private PlatoViewModel mPlatoViewModel;

    private int pedidoId;   // Se guarda el id del pedido que esta siendo creado o editado

    static List<Plato> platosCreados = new ArrayList<>(); // Aqui se guardan todos los platos creados hasta ahora

    Double precioTotal; // Precio total del pedido

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_raciones);

        mRecyclerView = findViewById(R.id.recyclerview);
        textoPrecioTotal = findViewById(R.id.texto_precio_total);

        mAdapter = new PlatoPedidoRelListAdapter(new PlatoPedidoRelListAdapter.PlatoPedidoRelDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Model para las raciones
        mPlatoPedidoRelViewModel = new ViewModelProvider(this).get(PlatoPedidoRelViewModel.class);
        mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);

        // Botones
        mBotonConfirmar = findViewById(R.id.boton_confirmar_platos);
        mBotonVolver = findViewById(R.id.boton_volver);

        // Obtenemos el pid del pedido que lo llama
        Bundle extras = getIntent().getExtras();
        pedidoId = extras.getInt(PedidoEdit.PEDIDO_ID);

        // Añadimos todos los platos con 0 raciones
        mPlatoViewModel.getAllPlatos().observe(this, platos -> {
            // Update the cached copy of the notes in the adapter.
            for (Plato plato : platos){
                PlatosPedidosRel nuevaRacion = new PlatosPedidosRel(plato.getPlatoId(), pedidoId,
                        0, plato.getPrecio());
                Log.d ("Inserta racion", Integer.toString(nuevaRacion.getPlatoId())+ " "
                        + Integer.toString(nuevaRacion.getPedidoId())+ " "
                        + Integer.toString(nuevaRacion.getCantidad())+ " "
                        + Double.toString(nuevaRacion.getPrecioCrear()));
                mPlatoPedidoRelViewModel.insert(nuevaRacion);
            }
            platosCreados.clear();
            platosCreados.addAll(platos);
        });

        // Mostrar lista de las raciones y calcular precio
        Observer<List<PlatosPedidosRel>> racionesObserver = new Observer<List<PlatosPedidosRel>>() {
            @Override
            public void onChanged(List<PlatosPedidosRel> raciones) {
                // Update the cached copy of the notes in the adapter.
                mAdapter.submitList(raciones);

                // Calcular precio total;
                precioTotal = calcularPrecioTotal(raciones);
                textoPrecioTotal.setText("Precio total: " + Double.toString(precioTotal) + " €");
            }
        };
        mPlatoPedidoRelViewModel.getAllRaciones(pedidoId).observe(this, racionesObserver);

        // Boton continuar
        mBotonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                setResult(RESULT_OK, replyIntent);
                replyIntent.putExtra(PedidoEdit.PEDIDO_PRECIO_TOTAL, precioTotal);
                finish();
            }
        });

        // Boton volver
        mBotonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                setResult(RESULT_CANCELED, replyIntent);
                finish();
            }
        });
    }

    /**
     *
     * @param platosPedidosRel Es la racion seleccionado, para aumentar la cantidad
     * @return Devuelve la cantidad actualizada
     */
    public static float aumentarRacion(PlatosPedidosRel platosPedidosRel){
        int cantidadAntigua = platosPedidosRel.getCantidad();
        platosPedidosRel.setCantidad(cantidadAntigua + 1);
        mPlatoPedidoRelViewModel.update(platosPedidosRel);
        return cantidadAntigua + 1;
    }

    /**
     *
     * @param platosPedidosRel Es la racion seleccionado para disminuir la cantidad
     * @return Devuelve la cantidad actualizada
     */
    public static float disminuirRacion(PlatosPedidosRel platosPedidosRel){
        int cantidadAntigua = platosPedidosRel.getCantidad();
        if (cantidadAntigua != 0){
            platosPedidosRel.setCantidad(cantidadAntigua - 1);
            mPlatoPedidoRelViewModel.update(platosPedidosRel);
            return cantidadAntigua - 1;
        }
        return  0;
    }

    /**
     * @param platoId
     * @return Devuelve el plato que tenga como id "platoId"
     *
     * platosCreados contiene todos los platos creados hasta ahora
     */
    private static Plato encontrPlato(int platoId){
        int i = 0;
        boolean encontrado = false;
        while (i < platosCreados.size() && !encontrado){
            if (platosCreados.get(i).getPlatoId() == platoId){
                encontrado = true;
            }
            else {
                i++;
            }
        }
        return platosCreados.get(i);
    }

    /**
     *
     * @param platoId Id de un plato
     * @return Devuelve el precio del plato
     */
    private Double encontrarPrecio(int platoId){
        return encontrPlato(platoId).getPrecio();
    }

    /**
     *
     * @param platoId Id de un plato
     * @return Devuelve el nombre del plato
     */
    static String encontrarNombrePlato(int platoId){
        return encontrPlato(platoId).getNombre();
    }

    private static Double calcularPrecioTotal(List<PlatosPedidosRel> raciones){
        Double precioTotal = 0.0;
        for (PlatosPedidosRel racion : raciones) {
            Log.d ("Buscar Plato", Integer.toString(racion.getPlatoId()));
            precioTotal = precioTotal + (racion.getCantidad() * racion.getPrecioCrear());
        }
        return precioTotal;
    }

    public static Double calcularPrecioPedido(int pedidoId){
        mPlatoPedidoRelViewModel.getAllRaciones(pedidoId);
         return calcularPrecioTotal(mPlatoPedidoRelViewModel.getAllRaciones(pedidoId).getValue());
    }
}