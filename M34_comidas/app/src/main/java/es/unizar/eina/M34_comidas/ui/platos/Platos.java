package es.unizar.eina.M34_comidas.ui.platos;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.M34_comidas.R;
import es.unizar.eina.M34_comidas.database.Platos.Plato;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Platos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Platos extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private PlatoViewModel mPlatoViewModel;

    public static final int ACTIVITY_CREATE = 1;

    public static final int ACTIVITY_EDIT = 2;

    public static final int ACTIVITY_DELETE = 3;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;

    RecyclerView mRecyclerView;

    PlatoListAdapter mAdapter;

    FloatingActionButton mFab;

    TextView menuOrdenar;

    RelativeLayout relativeLayout;

    Plato PlatoSeleccionado;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Platos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Platos.
     */
    // TODO: Rename and change types and number of parameters
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
        View viewFragment = inflater.inflate(R.layout.fragment_platos, container, false);
        //setContentView(R.layout.activity_notepad);
        mRecyclerView = viewFragment.findViewById(R.id.recyclerview);
        mAdapter = new PlatoListAdapter(new PlatoListAdapter.PlatoDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);

        // Lista de platos
        mPlatoViewModel.getAllPlatos().observe(getActivity(), platos -> {
            // Update the cached copy of the notes in the adapter.
            mAdapter.submitList(platos);
        });

        // Texto menu ordenar
        menuOrdenar = (TextView) viewFragment.findViewById(R.id.establecer_filtro);

        relativeLayout = (RelativeLayout) viewFragment.findViewById(R.id.relLayout);

        // Boton crear plato
        mFab = viewFragment.findViewById(R.id.boton_crear);
        mFab.setOnClickListener(view -> {
            createPlato();
        });

        // Mostrar menu de ordenar
        menuOrdenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarMenuOrdenar(v);
            }
        });

        // It doesn't affect if we comment the following instruction
        //registerForContextMenu(mRecyclerView);

        return viewFragment;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case INSERT_ID:
                createPlato();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle extras = data.getExtras();

        if (resultCode != getActivity().RESULT_OK) {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    "Se ha cancelado la accion",
                    Toast.LENGTH_LONG).show();

        }
        else if (resultCode == getActivity().RESULT_OK) {

            switch (requestCode) {
                case ACTIVITY_CREATE:
                    Log.d("crear", "llega");
                    Plato newPlato = new Plato(extras.getString(PlatoEdit.PLATO_TITLE)
                            , extras.getString(PlatoEdit.PLATO_BODY), Double.valueOf(extras.getString(PlatoEdit.PLATO_PRECIO)),
                            extras.getString(PlatoEdit.PLATO_CATEGORIA));
                    mPlatoViewModel.insert(newPlato);
                    break;
                case ACTIVITY_EDIT:

                    int id = extras.getInt(PlatoEdit.PLATO_ID);
                    Plato updatedPlato = new Plato(extras.getString(PlatoEdit.PLATO_TITLE)
                            , extras.getString(PlatoEdit.PLATO_BODY), Double.valueOf(extras.getString(PlatoEdit.PLATO_PRECIO)),
                            extras.getString(PlatoEdit.PLATO_CATEGORIA));
                    updatedPlato.setPlatoId(id);
                    mPlatoViewModel.update(updatedPlato);
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
                            "Deleting " + PlatoSeleccionado.getNombre(),
                            Toast.LENGTH_LONG).show();
                    mPlatoViewModel.delete(PlatoSeleccionado);
                    break;
            }
        }
    }


    /**
     * Este metodo es llamado al mantener pulsado cualquiera de los platos
     * @param item The context menu item that was selected.
     * @return Devuelve "true" si se ha seleccionado una opcion
     */
    public boolean onContextItemSelected(MenuItem item) {
        Plato current = mAdapter.getCurrent();
        Log.d("prueba",current.getNombre());
        Log.d("Seleccionar", Integer.toString(item.getItemId()));

        switch (item.getItemId()) {
            case DELETE_ID:     // Borra el plato seleccionado
                Log.d("Eliminar", "llega");
                PlatoSeleccionado = current;
                Intent intent = new Intent(getActivity(), EliminarPlato.class);
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
                        "Deleting " + current.getNombre(),
                        Toast.LENGTH_LONG).show();
                mPlatoViewModel.delete(current);
                 */
                return true;

            case EDIT_ID:   // Edita el plato seleccionado
                Log.d("Editar", "llega");
                editPlato(current);
                return true;
        }

        return super.onContextItemSelected(item);
    }


    /**
     * Inicia una actividad para crear un nuevo plato.
     * Al volver a esta activad ejecutara el metodo "onActivityResult"
     */
    private void createPlato() {
        Intent intent = new Intent(getActivity(), PlatoEdit.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }


    /**
     * @param current Plato que se esta editando
     * Inicia la actividad para editar el plato
     */
    private void editPlato(Plato current) {
        Intent intent = new Intent(getActivity(), PlatoEdit.class);
        intent.putExtra(PlatoEdit.PLATO_TITLE, current.getNombre());
        Log.d ("editar Plato", current.getNombre());
        intent.putExtra(PlatoEdit.PLATO_BODY, current.getDescripcion());
        Log.d ("editar Plato", current.getDescripcion());
        intent.putExtra(PlatoEdit.PLATO_ID, current.getPlatoId());
        Log.d ("editar Plato", Integer.toString(current.getPlatoId()));
        intent.putExtra(PlatoEdit.PLATO_PRECIO, Double.toString(current.getPrecio()));
        Log.d ("editar Plato", Double.toString(current.getPrecio()));
        intent.putExtra(PlatoEdit.PLATO_CATEGORIA, current.getCategoria());
        String text = current.getCategoria();
        Log.d ("editar Plato", current.getCategoria());
        startActivityForResult(intent, ACTIVITY_EDIT);
    }

    /**
     * @param view Parameter 1.
     * Muestra el menu para ordenar y ordena segun el criterio seleccionado
     */
    private void mostrarMenuOrdenar(View view) {
        PopupMenu menu = new PopupMenu(this.getContext(), view);
        menu.inflate(R.menu.menu_platos_ordenar);

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                boolean encontrado = false;
                if(menuItem.getItemId() == R.id.nombre){ // Ordenar por nombre
                    Log.d("Nombre", "llega");
                    menuOrdenar.setText("Nombre");
                    mPlatoViewModel.ordenar("nombre");
                    encontrado = true;
                }
                else if(menuItem.getItemId() == R.id.categoria){   // Ordenar por categoria
                    Log.d("Categoria", "llega");
                    menuOrdenar.setText("Categoria");
                    mPlatoViewModel.ordenar("categoria");
                    encontrado = true;
                }
                else if (menuItem.getItemId() == R.id.ambos) {   // Ordenar por categoria y nombre
                    Log.d("Nombre y Categoria", "llega");
                    menuOrdenar.setText("Ambos");
                    mPlatoViewModel.ordenar("ambos");
                    encontrado = true;
                }
                mPlatoViewModel.getAllPlatos().observe(getActivity(), platos_ambos -> {
                    // Update the cached copy of the notes in the adapter.
                    mAdapter.submitList(platos_ambos);
                });
                return encontrado;
            }
        });
        menu.show();
    }

}