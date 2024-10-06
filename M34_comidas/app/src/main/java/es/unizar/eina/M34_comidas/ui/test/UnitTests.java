package es.unizar.eina.M34_comidas.ui.test;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import es.unizar.eina.M34_comidas.R;
import es.unizar.eina.M34_comidas.database.Platos.Plato;
import es.unizar.eina.M34_comidas.database.pedidos.Pedido;
import es.unizar.eina.M34_comidas.database.platos_pedidos.PlatosPedidosRel;
import es.unizar.eina.M34_comidas.ui.pedidos.PedidoViewModel;
import es.unizar.eina.M34_comidas.ui.pedidos.Pedidos;
import es.unizar.eina.M34_comidas.ui.platos.PlatoListAdapter;
import es.unizar.eina.M34_comidas.ui.platos.PlatoViewModel;
import es.unizar.eina.M34_comidas.ui.platos.Platos;
import es.unizar.eina.M34_comidas.ui.raciones.PlatoPedidoRelViewModel;

public class UnitTests  extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

     PlatoViewModel mPlatoViewModel;
     PedidoViewModel mPedidoViewModel;

     PlatoPedidoRelViewModel mPlatoPedidoRelViewModel;

     int primerPlato;
     int primerPedido;

     Button botonTest;
     Button botonSobrecarga;

    public static Platos newInstance(String param1, String param2) {
        Platos fragment = new Platos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFragment = inflater.inflate(R.layout.fragment_test, container, false);

        mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);
        mPedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);
        mPlatoPedidoRelViewModel = new ViewModelProvider(this).get(PlatoPedidoRelViewModel.class);

        botonTest = viewFragment.findViewById(R.id.boton_ejecutar_test);
        botonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ejuctarTestsPlato();
                ejuctarTestsPedido();
                ejecutarTestRaciones();
            }
        });

        botonSobrecarga = viewFragment.findViewById(R.id.boton_sobrecarga);
        botonSobrecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pruebaSobreCarga();
            }
        });
        return viewFragment;

    }

    private void ejuctarTestsPlato(){
        int id;
        // Pruebas Crear
        // Valida
        Plato platoPrueba = new Plato("pulpo", "Ricos", 1.34, "primero");
        Log.d("Prueba crear 1", Long.toString(mPlatoViewModel.insert(platoPrueba)));
        id = platoPrueba.getPlatoId();
        primerPlato = id;

        // No valida
        platoPrueba = new Plato(null, "Ricos", 1.34, "primero");
        Log.d("Prueba crear plato 2", Long.toString(mPlatoViewModel.insert(platoPrueba)));

        platoPrueba = new Plato("", "Ricos", 1.34, "primero");
        Log.d("Prueba crear plato 3", Long.toString(mPlatoViewModel.insert(platoPrueba)));

        platoPrueba = new Plato("pulpo", null, 1.34, "primero");
        Log.d("Prueba crear plato 4", Long.toString(mPlatoViewModel.insert(platoPrueba)));

        platoPrueba = new Plato("pulpo", "", 1.34, "primero");
        Log.d("Prueba crear plato 5", Long.toString(mPlatoViewModel.insert(platoPrueba)));

        platoPrueba = new Plato("pulpo", "Ricos", null, "primero");
        Log.d("Prueba crear plato 6", Long.toString(mPlatoViewModel.insert(platoPrueba)));

        platoPrueba = new Plato("pulpo", "Ricos", 1.34, null);
        Log.d("Prueba crear plato 7", Long.toString(mPlatoViewModel.insert(platoPrueba)));

        platoPrueba = new Plato("pulpo", "Ricos", 1.34, "");
        Log.d("Prueba crear plato 8", Long.toString(mPlatoViewModel.insert(platoPrueba)));


        // Pruebas Editar
        // Valido
        platoPrueba = new Plato("pulpo", "Cambio", 1.34, "primero");
        id = (int) mPlatoViewModel.insert(platoPrueba);
        platoPrueba.setPlatoId(id);
        Log.d("Prueba editar plato 1", Integer.toString(mPlatoViewModel.update(platoPrueba)));

        // No valido
        platoPrueba = new Plato(null, "Ricos", 1.34, "primero");
        id = (int) mPlatoViewModel.insert(platoPrueba);
        platoPrueba.setPlatoId(id);
        Log.d("Prueba editar plato 2", Integer.toString(mPlatoViewModel.update(platoPrueba)));

        platoPrueba = new Plato("", "Ricos", 1.34, "primero");
        id = (int) mPlatoViewModel.insert(platoPrueba);
        platoPrueba.setPlatoId(id);
        Log.d("Prueba editar plato 3", Integer.toString(mPlatoViewModel.update(platoPrueba)));

        platoPrueba = new Plato("pulpo", null, 1.34, "primero");
        id = (int) mPlatoViewModel.insert(platoPrueba);
        platoPrueba.setPlatoId(id);
        Log.d("Prueba editar plato 4", Integer.toString(mPlatoViewModel.update(platoPrueba)));

        platoPrueba = new Plato("pulpo", "", 1.34, "primero");
        id = (int) mPlatoViewModel.insert(platoPrueba);
        platoPrueba.setPlatoId(id);
        Log.d("Prueba editar plato 5", Integer.toString(mPlatoViewModel.update(platoPrueba)));

        platoPrueba = new Plato("pulpo", "Ricos", null, "primero");
        id = (int) mPlatoViewModel.insert(platoPrueba);
        platoPrueba.setPlatoId(id);
        Log.d("Prueba editar plato 6", Integer.toString(mPlatoViewModel.update(platoPrueba)));

        platoPrueba = new Plato("pulpo", "Ricos", 1.34, null);
        id = (int) mPlatoViewModel.insert(platoPrueba);
        platoPrueba.setPlatoId(id);
        Log.d("Prueba editar plato 7", Integer.toString(mPlatoViewModel.update(platoPrueba)));

        platoPrueba = new Plato("pulpo", "Ricos", 1.34, "");
        id = (int) mPlatoViewModel.insert(platoPrueba);
        platoPrueba.setPlatoId(id);
        Log.d("Prueba editar plato 8", Integer.toString(mPlatoViewModel.update(platoPrueba)));

        platoPrueba = new Plato("pulpo", "Ricos", 1.34, "primero");
        platoPrueba.setPlatoId(-4);
        Log.d("Prueba editar plato 9", Integer.toString(mPlatoViewModel.update(platoPrueba)));

        // Pruebas de borrado
        // Valida
        platoPrueba = new Plato("Calamares", "Ricos", 1.34, "segundo");
        id = (int) mPlatoViewModel.insert(platoPrueba);
        platoPrueba.setPlatoId(id);
        Log.d("Prueba borrar plato 1", Integer.toString(mPlatoViewModel.delete(platoPrueba)));

        // No valida
        platoPrueba = new Plato("Calamares", "Ricos", 1.34, "segundo");
        id = -3;
        platoPrueba.setPlatoId(id);
        Log.d("Prueba borrar plato 2", Integer.toString(mPlatoViewModel.delete(platoPrueba)));
    }


    public void ejuctarTestsPedido(){
        int id;


        // Pruebas Crear
        // Valida
        Pedido pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "20:30", "solicitado");
        Log.d("Prueba crear pedido 1", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));
        id = pedidoPrueba.getPedidoId();
        primerPedido = id;

        // No valida
        pedidoPrueba = new Pedido(null, "6458293", "13/01/2024", "20:30", "solicitado");
        Log.d("Prueba crear pedido 2", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));

        pedidoPrueba = new Pedido("", "6458293", "13/01/2024", "20:30", "solicitado");
        Log.d("Prueba crear pedido 3", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", null, "13/01/2024", "20:30", "solicitado");
        Log.d("Prueba crear pedido 4", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "", "13/01/2024", "20:30", "solicitado");
        Log.d("Prueba crear pedido 5", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", null, "20:30", "solicitado");
        Log.d("Prueba crear pedido 6", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "", "20:30", "solicitado");
        Log.d("Prueba crear pedido 7", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", null, "solicitado");
        Log.d("Prueba crear pedido 8", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "", "solicitado");
        Log.d("Prueba crear pedido 9", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "20:30", null);
        Log.d("Prueba crear pedido 10", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "20:30", "");
        Log.d("Prueba crear pedido 11", Long.toString(mPedidoViewModel.insert(pedidoPrueba)));


        // Pruebas Editar
        // Valido
        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "20:30", "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 1", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        // No valido
        pedidoPrueba = new Pedido(null, "6458293", "13/01/2024", "20:30", "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 2", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("", "6458293", "13/01/2024", "20:30", "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 3", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", null, "13/01/2024", "20:30", "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 4", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "", "13/01/2024", "20:30", "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 5", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", null, "20:30", "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 6", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "", "20:30", "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 7", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", null, "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 8", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "", "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 9", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "20:30", null);
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 10", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "20:30", "");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba editar pedido 11", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));

        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "20:30", "solicitado");
        pedidoPrueba.setPedidoId(-4);
        Log.d("Prueba editar pedido 12", Integer.toString(mPedidoViewModel.update(pedidoPrueba)));


        // Pruebas de borrado
        // Valida
        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "20:30", "solicitado");
        id = (int) mPedidoViewModel.insert(pedidoPrueba);
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba Borrar pedido 1", Integer.toString(mPedidoViewModel.delete(pedidoPrueba)));

        // No valida
        pedidoPrueba = new Pedido("Pedro", "6458293", "13/01/2024", "20:30", "solicitado");
        id = -4;
        pedidoPrueba.setPedidoId(id);
        Log.d("Prueba Borrar pedido 2", Integer.toString(mPedidoViewModel.delete(pedidoPrueba)));

    }

    public void ejecutarTestRaciones(){

        // Pruebas crear y actualizar
        // Valido
        PlatosPedidosRel platosPedidosRelPrueba = new PlatosPedidosRel(1, 1, 4, 13.32);
        Log.d("Prueba Crear racion 1", Long.toString(mPlatoPedidoRelViewModel.insert(platosPedidosRelPrueba)));

        // No valido
        platosPedidosRelPrueba = new PlatosPedidosRel(1, 1, -4, 13.32);
        Log.d("Prueba Crear Editar racion 2", Long.toString(mPlatoPedidoRelViewModel.insert(platosPedidosRelPrueba)));

        platosPedidosRelPrueba = new PlatosPedidosRel(1, 1, 4, null);
        Log.d("Prueba Crear Editar racion 3", Long.toString(mPlatoPedidoRelViewModel.insert(platosPedidosRelPrueba)));

        platosPedidosRelPrueba = new PlatosPedidosRel(1, 1, 4, -4.0);
        Log.d("Prueba Crear Editar racion 5", Long.toString(mPlatoPedidoRelViewModel.delete(platosPedidosRelPrueba)));

        // Prueba de borrado
        // Valido
        platosPedidosRelPrueba = new PlatosPedidosRel(1, 1, 4, 13.32);
        Log.d("Prueba Borrar racion 1", Long.toString(mPlatoPedidoRelViewModel.delete(platosPedidosRelPrueba)));
    }

    private void eliminar() {
        List<Plato> platos = mPlatoViewModel.getAllPlatos().getValue();
        for (Plato plato : platos){
            mPlatoViewModel.delete(plato);
        }

        List<Pedido> pedidos = mPedidoViewModel.getAllPedidos().getValue();
        for (Pedido pedido : pedidos){
            mPedidoViewModel.delete(pedido);
        }
    }

    private void pruebaSobreCarga(){
        Plato platoPrueba;
        String cadena = "a";
        for (int i = 0; i < 10000000; i++){
            platoPrueba = new Plato(cadena, "prueba", 4.3, "primero");
            Log.d("Numero de caracteres " + Integer.toString(i),
                    Long.toString(mPlatoViewModel.insert(platoPrueba)));
            cadena = cadena + "a";
        }
    }
}
