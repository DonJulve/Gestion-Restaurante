package es.unizar.eina.M34_comidas.database.pedidos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/** Definición de un Data Access Object para las notas */
@Dao
public interface PedidoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Pedido pedido);

    @Update
    int update(Pedido pedido);

    @Delete
    int delete(Pedido pedido);

    @Query("DELETE FROM pedido")
    void deleteAll();

    @Query("SELECT * FROM pedido ORDER BY nombre ASC")
    LiveData<List<Pedido>> getOrderedPedidosName();

    @Query("SELECT * FROM pedido WHERE estado = :filtrar ORDER BY :ordenar ASC")
    LiveData<List<Pedido>> getOrderedPedidosFiltro(String ordenar, String filtrar);

    // Sin filtro
    @Query("SELECT * FROM pedido ORDER BY nombre ASC")
    LiveData<List<Pedido>> getOrderedPedidosNombreSinFiltro();

    @Query("SELECT * FROM pedido ORDER BY telefono ASC")
    LiveData<List<Pedido>> getOrderedPedidosTelefonoSinFiltro();

    @Query("SELECT * FROM pedido ORDER BY fecha ASC")
    LiveData<List<Pedido>> getOrderedPedidosFechaSinFiltro();

    @Query("SELECT * FROM pedido ORDER BY hora ASC")
    LiveData<List<Pedido>> getOrderedPedidosHoraSinFiltro();

    // Con filtro
    @Query("SELECT * FROM pedido WHERE estado = :filtrar ORDER BY nombre ASC")
    LiveData<List<Pedido>> getOrderedPedidosNombreConFiltro(String filtrar);

    @Query("SELECT * FROM pedido WHERE estado = :filtrar ORDER BY telefono ASC")
    LiveData<List<Pedido>> getOrderedPedidosTelefonoConFiltro(String filtrar);

    @Query("SELECT * FROM pedido WHERE estado = :filtrar ORDER BY fecha ASC")
    LiveData<List<Pedido>> getOrderedPedidosFechaConFiltro(String filtrar);

    @Query("SELECT * FROM pedido WHERE estado = :filtrar ORDER BY hora ASC")
    LiveData<List<Pedido>> getOrderedPedidosHoraConFiltro(String filtrar);

}

