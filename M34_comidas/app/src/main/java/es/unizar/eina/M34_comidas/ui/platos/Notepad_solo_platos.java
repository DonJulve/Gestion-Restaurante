package es.unizar.eina.M34_comidas.ui.platos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unizar.eina.M34_comidas.R;
import es.unizar.eina.M34_comidas.database.Platos.Plato;

/** Pantalla principal de la aplicación M34_comidas */
public class Notepad_solo_platos extends AppCompatActivity {
    private PlatoViewModel mPlatoViewModel;

    public static final int ACTIVITY_CREATE = 1;

    public static final int ACTIVITY_EDIT = 2;

    public static final int ACTIVITY_DELETE = 3;

    static final int INSERT_ID = Menu.FIRST;
    static final int DELETE_ID = Menu.FIRST + 1;
    static final int EDIT_ID = Menu.FIRST + 2;

    static final int ORDENAR_NOMBRE = Menu.FIRST + 3;

    static final int ORDENAR_CATEGORIA = Menu.FIRST + 4;

    static final int ORDENAR_NOMBRE_CATEGORIA = Menu.FIRST + 5;
    RecyclerView mRecyclerView;

    PlatoListAdapter mAdapter;

    FloatingActionButton mFab;

    TextView menuOrdenar;

    RelativeLayout relativeLayout;

    Plato PlatoSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new PlatoListAdapter(new PlatoListAdapter.PlatoDiff());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mPlatoViewModel = new ViewModelProvider(this).get(PlatoViewModel.class);

        mPlatoViewModel.getAllPlatos().observe(this, notes -> {
            // Update the cached copy of the notes in the adapter.
            mAdapter.submitList(notes);
        });


        menuOrdenar = (TextView) findViewById(R.id.establecer_filtro);
        relativeLayout = (RelativeLayout) findViewById(R.id.relLayout);

        mFab = findViewById(R.id.boton_crear);
        mFab.setOnClickListener(view -> {
            createPlato();
        });

        // It doesn't affect if we comment the following instruction
        //registerForContextMenu(mRecyclerView);
        registerForContextMenu(menuOrdenar);            // "Inicializa" el menu

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, INSERT_ID, Menu.NONE, R.string.add_note);
        return result;
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

        if (resultCode != RESULT_OK) {
            Toast.makeText(
                    getApplicationContext(),
                    "Se ha cancelado la accion",
                    Toast.LENGTH_LONG).show();

        }
        else if (resultCode == RESULT_OK) {

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
                            getApplicationContext(),
                            "Deleting " + PlatoSeleccionado.getNombre(),
                            Toast.LENGTH_LONG).show();
                    mPlatoViewModel.delete(PlatoSeleccionado);
                    break;
            }
        }
    }


    public boolean onContextItemSelected(MenuItem item) {
        Plato current = mAdapter.getCurrent();
        Log.d("prueba",current.getNombre());
        Log.d("Seleccionar", Integer.toString(item.getItemId()));
        switch (item.getItemId()) {
            case DELETE_ID:
                Log.d("Eliminar", "llega");
                PlatoSeleccionado = current;
                Intent intent = new Intent(this, EliminarPlato.class);
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

            case EDIT_ID:
                Log.d("Editar", "llega");
                editPlato(current);
                return true;

            case ORDENAR_NOMBRE:
                Log.d("Nombre", "llega");
                menuOrdenar.setText("Nombre");
                return true;

            case ORDENAR_CATEGORIA:
                Log.d("Categoria", "llega");
                menuOrdenar.setText("Categoria");
                return true;

            case ORDENAR_NOMBRE_CATEGORIA:
                Log.d("Nombre y Categoria", "llega");
                menuOrdenar.setText("Ambos");
                return true;

        }

        return super.onContextItemSelected(item);
    }

    private void createPlato() {
        Intent intent = new Intent(this, PlatoEdit.class);
        startActivityForResult(intent, ACTIVITY_CREATE);
    }


    private void editPlato(Plato current) {
        Intent intent = new Intent(this, PlatoEdit.class);
        intent.putExtra(PlatoEdit.PLATO_TITLE, current.getNombre());
        Log.d ("editar", current.getNombre());
        intent.putExtra(PlatoEdit.PLATO_BODY, current.getDescripcion());
        Log.d ("editar", current.getDescripcion());
        intent.putExtra(PlatoEdit.PLATO_ID, current.getPlatoId());
        Log.d ("editar", Integer.toString(current.getPlatoId()));
        intent.putExtra(PlatoEdit.PLATO_PRECIO, Double.toString(current.getPrecio()));
        Log.d ("editar", Double.toString(current.getPrecio()));
        intent.putExtra(PlatoEdit.PLATO_CATEGORIA, current.getCategoria());
        String text = current.getCategoria();
        Log.d ("editar", current.getCategoria());
        startActivityForResult(intent, ACTIVITY_EDIT);
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, ORDENAR_NOMBRE, Menu.NONE, "Nombre");
        menu.add(Menu.NONE, ORDENAR_CATEGORIA, Menu.NONE, "Categoria");
        menu.add(Menu.NONE, ORDENAR_NOMBRE_CATEGORIA, Menu.NONE, "Ambos");
    }

    public void platoPulsado(View view) {
        Log.d("Pulsado", "llega");
        Plato current = mAdapter.getCurrent();
        Log.d("prueba",current.getNombre());
        editPlato(current);
    }

}