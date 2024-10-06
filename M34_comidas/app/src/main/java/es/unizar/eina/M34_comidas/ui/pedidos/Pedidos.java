package es.unizar.eina.M34_comidas.ui.pedidos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.M34_comidas.R;
import es.unizar.eina.M34_comidas.database.pedidos.Pedido;
import es.unizar.eina.M34_comidas.send.SendAbstractionImpl;
import es.unizar.eina.M34_comidas.ui.raciones.Raciones;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pedidos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pedidos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private PedidoViewModel mPedidoViewModel;

    public static final int ACTIVITY_CREATE = 1;

    public static final int ACTIVITY_EDIT = 2;

    public static final int ACTIVITY_DELETE = 3;

    public static final int ACTIVITY_RACIONES = 4;

    static final int DELETE_ID = Menu.FIRST + 6;
    static final int EDIT_ID = Menu.FIRST + 7;
    static final int NOTIFICAR_SMS_ID = Menu.FIRST + 8;

    static final int NOTIFICAR_W_ID = Menu.FIRST + 9;

    RecyclerView mRecyclerView;

    PedidoListAdapter mAdapter;

    FloatingActionButton mFab;

    TextView textoOrdenar;
    TextView textoFiltrar;

    Pedido PedidoSeleccionado;

    Pedido ultimoPedido;

    Boolean esNuevoPedido;

    String ordenarPor;
    String filtrarPor;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pedidos.
     */
    // TODO: Rename and change types and number of parameters
    public static Pedidos newInstance(String param1, String param2) {
        Pedidos fragment = new Pedidos();
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
        View viewFragment = inflater.inflate(R.layout.fragment_pedidos, container, false);
        //setContentView(R.layout.activity_notepad);
        mRecyclerView = viewFragment.findViewById(R.id.recyclerview);
        mAdapter = new PedidoListAdapter(new PedidoListAdapter.PedidoDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPedidoViewModel = new ViewModelProvider(this).get(PedidoViewModel.class);

        // Texto ordenar y filtrar.
        textoOrdenar = (TextView) viewFragment.findViewById(R.id.establecer_ordenamiento);
        textoFiltrar = (TextView) viewFragment.findViewById(R.id.establecer_filtro);
        //relativeLayout = (RelativeLayout) viewFragment.findViewById(R.id.relLayout);

        mFab = viewFragment.findViewById(R.id.boton_crear);

        ordenarPor = "nombre";
        filtrarPor = "sinFiltro";

        mPedidoViewModel.getAllPedidos().observe(getActivity(), pedido -> {
            // Update the cached copy of the notes in the adapter.
            mAdapter.submitList(pedido);
        });

        mFab.setOnClickListener(view -> {
            createPedido();
        });

        textoOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMenuOrdenar(v);
            }
        });

        textoFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMenuFiltrar(v);
            }
        });

        // It doesn't affect if we comment the following instruction
        //registerForContextMenu(viewFragment);
        //registerForContextMenu(mRecyclerView);
        return viewFragment;
    }

    private void createPedido() {
        Intent intent = new Intent(getActivity(), PedidoEdit.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();

        if (resultCode != getActivity().RESULT_OK) {
            switch (requestCode) {
                case ACTIVITY_RACIONES:
                    editPedido(ultimoPedido);
                    break;
                case ACTIVITY_EDIT:
                    if (esNuevoPedido){
                        mPedidoViewModel.delete(ultimoPedido);
                    }
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Se ha cancelado la accion",
                            Toast.LENGTH_LONG).show();
                    break;
            }

        }
        else if (resultCode == getActivity().RESULT_OK) {

            switch (requestCode) {
                case ACTIVITY_CREATE:
                    Log.d("crear Pedido", "llega");
                    Pedido newPedido = new Pedido(extras.getString(PedidoEdit.PEDIDO_NOMBRE)
                            , extras.getString(PedidoEdit.PEDIDO_TELEFONO), extras.getString(PedidoEdit.PEDIDO_FECHA),
                            extras.getString(PedidoEdit.PEDIDO_HORA), extras.getString(PedidoEdit.PEDIDO_ESTADO));
                    Log.d ("Crear Pedido", newPedido.getNombre());
                    Log.d ("Crear Pedido", newPedido.getTelefono());
                    Log.d ("Crear Pedido", newPedido.getFecha());
                    Log.d ("Crear Pedido", newPedido.getHora());
                    //Log.d ("Crear Pedido", current.getNombre());

                    int nuevoId = (int) mPedidoViewModel.insert(newPedido);
                    Log.d("Id antiguo pedido", "ID: " + newPedido.getPedidoId());
                    Log.d("Id nuevo pedido", "ID insertado: " + nuevoId);
                    newPedido.setPedidoId(nuevoId);
                    ultimoPedido = newPedido;
                    esNuevoPedido =  true;
                    aniadirRaciones(nuevoId);
                    break;

                case ACTIVITY_EDIT:
                    int id = extras.getInt(PedidoEdit.PEDIDO_ID);
                    Pedido updatedPedido = new Pedido(extras.getString(PedidoEdit.PEDIDO_NOMBRE),
                            extras.getString(PedidoEdit.PEDIDO_TELEFONO), extras.getString(PedidoEdit.PEDIDO_FECHA),
                            extras.getString(PedidoEdit.PEDIDO_HORA), extras.getString(PedidoEdit.PEDIDO_ESTADO));
                    updatedPedido.setPedidoId(id);
                    mPedidoViewModel.update(updatedPedido);
                    ultimoPedido = updatedPedido;
                    aniadirRaciones(id);
                    break;

                case ACTIVITY_DELETE:
                    /*
                    Pasando todos los parametros a la actividad y luego recuperarlos.
                    Note platoEliminar = new Note(extras.getString(PlatoEdit.NOTE_TITLE)
                            , extras.getString(PlatoEdit.NOTE_BODY), Integer.valueOf(extras.getString(PlatoEdit.PLATO_PRECIO)),
                            extras.getString(PlatoEdit.PLATO_CATEGORIA));
                     */

                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            "Deleting " + PedidoSeleccionado.getNombre(),
                            Toast.LENGTH_LONG).show();
                    mPedidoViewModel.delete(PedidoSeleccionado);

                    break;

                case ACTIVITY_RACIONES:
                    Double precioTotal = extras.getDouble(PedidoEdit.PEDIDO_PRECIO_TOTAL);
                    ultimoPedido.setPrecioTotal(precioTotal);
                    mPedidoViewModel.update(ultimoPedido);
                    notificarPedido(ultimoPedido, "SMS");
                    break;
            }
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        Pedido current = mAdapter.getCurrent();
        Log.d("prueba Pedido",current.getNombre());
        Log.d("Seleccionar Pedido", Integer.toString(item.getItemId()));
        switch (item.getItemId()) {
            case Pedidos.DELETE_ID:
                Log.d("Eliminar Pedido", "llega");
                PedidoSeleccionado = current;
                Intent intent = new Intent(getActivity(), EliminarPedido.class);
                /*
                Pasando todos los argumentos del plato
                intent.putExtra(PlatoEdit.NOTE_TITLE, current.getNombre());
                intent.putExtra(PlatoEdit.NOTE_BODY, current.getDescripcion());
                intent.putExtra(PlatoEdit.PLATO_PRECIO, current.getPrecio());
                intent.putExtra(PlatoEdit.PLATO_CATEGORIA, current.getCategoria());
                 */
                startActivityForResult(intent, ACTIVITY_DELETE);

                /*
                Toast.makeText(
                        getApplicationContext(),
                        Toast.LENGTH_LONG).show();
                mPedidoViewModel.delete(current);
                 */
                return true;

            case Pedidos.EDIT_ID:
                Log.d("Editar", "llega");
                esNuevoPedido = false;
                editPedido(current);
                return true;

            case Pedidos.NOTIFICAR_SMS_ID:
                Log.d("Notificar SMS", "llega");
                notificarPedido(current, "SMS");
                return true;

            case Pedidos.NOTIFICAR_W_ID:
                Log.d("Notificar W", "llega");
                notificarPedido(current, "WhatsApp");
                return true;

        }
        return super.onContextItemSelected(item);
    }

        private void editPedido(Pedido current) {
            Intent intent = new Intent(getActivity(), PedidoEdit.class);
            intent.putExtra(PedidoEdit.PEDIDO_NOMBRE, current.getNombre());
            Log.d ("editar Pedido", current.getNombre());
            intent.putExtra(PedidoEdit.PEDIDO_TELEFONO, current.getTelefono());
            Log.d ("editar Pedido", (current.getTelefono()));
            intent.putExtra(PedidoEdit.PEDIDO_ID, current.getPedidoId());
            Log.d ("editar Pedido", Integer.toString(current.getPedidoId()));
            intent.putExtra(PedidoEdit.PEDIDO_FECHA, current.getFecha());
            Log.d ("editar Pedido", current.getFecha());
            intent.putExtra(PedidoEdit.PEDIDO_HORA, current.getHora());
            Log.d ("editar Pedido", current.getHora());
            intent.putExtra(PedidoEdit.PEDIDO_ESTADO, current.getEstado());
            Log.d ("editar Pedido", current.getEstado());
            startActivityForResult(intent, ACTIVITY_EDIT);
        }

        private void aniadirRaciones(int id){
            Intent intentCrear = new Intent(getActivity(), Raciones.class);
            intentCrear.putExtra(PedidoEdit.PEDIDO_ID, id);
            Log.d ("Crear Raciones", Integer.toString(id));
            startActivityForResult(intentCrear, ACTIVITY_RACIONES);
        }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    private void mostrarMenuOrdenar(View view) {
        PopupMenu menuOrdenar = new PopupMenu(this.getContext(), view);
        menuOrdenar.inflate(R.menu.menu_pedidos_ordenar);

        menuOrdenar.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                boolean encontrado = false;
                if(menuItem.getItemId() == R.id.nombre){
                    ordenarPor = "nombre";
                    textoOrdenar.setText(" Nombre");
                    encontrado = true;
                }
                else if(menuItem.getItemId() == R.id.movil){
                    ordenarPor = "telefono";
                    textoOrdenar.setText(" Telefono");
                    encontrado = true;
                }
                else if (menuItem.getItemId() == R.id.fecha){
                    ordenarPor = "fecha";
                    textoOrdenar.setText(" Fecha");
                    encontrado = true;
                } else if (menuItem.getItemId() == R.id.hora) {
                    ordenarPor = "hora";
                    textoOrdenar.setText(" Hora");
                    encontrado = true;
                }
                mPedidoViewModel.ordenarFiltrar(ordenarPor, filtrarPor);
                mPedidoViewModel.getAllPedidos().observe(getActivity(), pedido -> {
                    // Update the cached copy of the notes in the adapter.
                    mAdapter.submitList(pedido);
                });
                return encontrado;
            }
        });
        menuOrdenar.show();
    }

    private void mostrarMenuFiltrar(View view) {
        PopupMenu menuFiltrar = new PopupMenu(this.getContext(), view);
        menuFiltrar.inflate(R.menu.menu_pedidos_filtrar);

        menuFiltrar.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                boolean encontrado = false;
                if(menuItem.getItemId() == R.id.sinFiltro){
                    filtrarPor = "sinFiltro";
                    textoFiltrar.setText(" Filtrar");
                    encontrado = true;
                }
                else if(menuItem.getItemId() == R.id.solicitado){
                    filtrarPor = "solicitado";
                    textoFiltrar.setText(" Solicitado");
                    encontrado = true;
                }
                else if (menuItem.getItemId() == R.id.preparado){
                    filtrarPor = "preparado";
                    textoFiltrar.setText(" Preparado");
                    encontrado = true;
                } else if (menuItem.getItemId() == R.id.recogido) {
                    filtrarPor = "recogido";
                    textoFiltrar.setText(" Recogido");
                    encontrado = true;
                }
                mPedidoViewModel.ordenarFiltrar(ordenarPor, filtrarPor);
                mPedidoViewModel.getAllPedidos().observe(getActivity(), pedido -> {
                    // Update the cached copy of the notes in the adapter.
                    mAdapter.submitList(pedido);
                });
                return encontrado;
            }
        });
        menuFiltrar.show();
    }

    private void notificarPedido(Pedido pedido, String metodo){
        SendAbstractionImpl sendAbstraction = new SendAbstractionImpl(getActivity(), metodo);
        String mensaje = "INFORMACION DEL PEDIDO: \n" +
                "Nombre: " + pedido.getNombre() + "\n" +
                "Telefono: " + pedido.getTelefono() + "\n" +
                "Fecha de recogida: " + pedido.getFecha() + "\n" +
                "Hora de recogida: " + pedido.getHora() + "\n" +
                "Precio Total: " + Double.toString(pedido.getPrecioTotal());
        sendAbstraction.send(pedido.getTelefono(), mensaje);
    }
}