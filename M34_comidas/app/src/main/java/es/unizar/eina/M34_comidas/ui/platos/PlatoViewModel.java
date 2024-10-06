package es.unizar.eina.M34_comidas.ui.platos;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.M34_comidas.database.Platos.Plato;
import es.unizar.eina.M34_comidas.database.Platos.PlatoRepository;

public class PlatoViewModel extends AndroidViewModel {

    private PlatoRepository mRepository;

    private LiveData<List<Plato>> mAllPlatos;

    public PlatoViewModel(Application application) {
        super(application);
        mRepository = new PlatoRepository(application);
        mAllPlatos = mRepository.getAllPlatos();
    }

    public LiveData<List<Plato>> getAllPlatos() { return mAllPlatos; }

    public LiveData<List<Plato>> getAllPlatosOrderedId() {
        mAllPlatos = mRepository.getAllPlatos();
        return mAllPlatos;
    }

    public Plato getPlatoId(int id){
        Plato mPlato = mRepository.getPlatoiD(id);
        return mPlato;
    }

    public LiveData<List<Plato>> ordenar(String criterio) {
        mRepository.ordenar(criterio);
        mAllPlatos = mRepository.getAllPlatos();
        return mAllPlatos;
    }

    public long insert(Plato plato) {
        comprobarEntradas(plato);
        return mRepository.insert(plato);
    }

    public int update(Plato plato) {
        comprobarEntradas(plato);
        return mRepository.update(plato);
    }

    public int delete(Plato plato) { return mRepository.delete(plato); }

    public void comprobarEntradas(Plato plato){
        if (plato.getNombre() == ""){
            plato.setNombre(null);
        }

        if (plato.getDescripcion() == ""){
            plato.setDescripcion(null);
        }

        if (!(plato.getPrecio() == null) && plato.getPrecio() < 0.0){
            plato.setPrecio(null);
        }

        if (/*(plato.getCategoria() != "primero" && plato.getCategoria() != "segundo"
                && plato.getCategoria() != "tercero") || */ (plato.getCategoria() == "")) {
            plato.setCategoria(null);
        }

    }
}
