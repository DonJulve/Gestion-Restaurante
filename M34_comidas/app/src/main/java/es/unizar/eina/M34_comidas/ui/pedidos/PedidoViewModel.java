package es.unizar.eina.M34_comidas.ui.pedidos;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import es.unizar.eina.M34_comidas.database.Platos.Plato;
import es.unizar.eina.M34_comidas.database.pedidos.Pedido;
import es.unizar.eina.M34_comidas.database.pedidos.PedidoRepository;
import es.unizar.eina.M34_comidas.database.platos_pedidos.PlatosPedidosRel;
import es.unizar.eina.M34_comidas.database.platos_pedidos.PlatosPedidosRelRepository;

public class PedidoViewModel extends AndroidViewModel {

    private PedidoRepository mRepository;

    private LiveData<List<Pedido>> mAllPedidos;

    public PedidoViewModel(Application application) {
        super(application);
        mRepository = new PedidoRepository(application);
        mAllPedidos = mRepository.getAllPedidos();
    }

    LiveData<List<Pedido>> ordenarFiltrar(String ordenar, String filtrar) {
        mRepository.ordenar(ordenar, filtrar);
        mAllPedidos = mRepository.getAllPedidos();
        return mAllPedidos;
    }

    public LiveData<List<Pedido>> getAllPedidos() { return mAllPedidos; }

    public long insert(Pedido pedido) {
        comprobarEntradas(pedido);
        return mRepository.insert(pedido);
    }

    public int update(Pedido pedido) {
        comprobarEntradas(pedido);
        return mRepository.update(pedido);
    }

    public int delete(Pedido pedido) {
        comprobarEntradas(pedido);
        return mRepository.delete(pedido);
    }

    private void comprobarEntradas(Pedido pedido){
        if(pedido.getNombre() == ""){
            pedido.setNombre(null);
        }

        if(pedido.getTelefono() == ""){
            pedido.setTelefono(null);
        }

        if(pedido.getFecha() == ""){
            pedido.setFecha(null);
        }

        if(pedido.getHora() == ""){
            pedido.setHora(null);
        }

        if(pedido.getEstado() == ""){
            pedido.setEstado(null);
        }
    }
}

