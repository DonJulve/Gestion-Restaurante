package es.unizar.eina.M34_comidas.ui.raciones;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.M34_comidas.database.platos_pedidos.PlatosPedidosRel;
import es.unizar.eina.M34_comidas.database.platos_pedidos.PlatosPedidosRelRepository;

public class PlatoPedidoRelViewModel extends AndroidViewModel {

    private PlatosPedidosRelRepository mRepository;

    private LiveData<List<PlatosPedidosRel>> mAllRaciones;

    public PlatoPedidoRelViewModel(Application application) {
        super(application);
        mRepository = new PlatosPedidosRelRepository(application);
        mAllRaciones = mRepository.getAllRaciones(1);
    }

    LiveData<List<PlatosPedidosRel>> getAllRaciones(int id) {
        mAllRaciones = mRepository.getAllRaciones(id);
        return mAllRaciones;
    }

    LiveData<List<PlatosPedidosRel>> getAllRaciones(){
        return mAllRaciones;
    }

    public long insert(PlatosPedidosRel platosPedidosRel) {
        comprobarEntradas(platosPedidosRel);
        return mRepository.insert(platosPedidosRel);
    }

    public int update(PlatosPedidosRel platosPedidosRel) {
        comprobarEntradas(platosPedidosRel);
        return mRepository.update(platosPedidosRel);
    }

    public int delete(PlatosPedidosRel platosPedidosRel) {
        comprobarEntradas(platosPedidosRel);
         return mRepository.delete(platosPedidosRel);
    }

    private void comprobarEntradas(PlatosPedidosRel platosPedidosRel){
        if (platosPedidosRel.getPrecioCrear() != null && platosPedidosRel.getPrecioCrear() < 0.0){
            platosPedidosRel.setPrecioCrear(null);
        }

    }
}
