package es.unizar.eina.M34_comidas.database.platos_pedidos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.unizar.eina.M34_comidas.database.Platos.Plato;

@Dao
public interface PlatosPedidosRelDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(PlatosPedidosRel platosPedidosRel);

    @Update
    int update(PlatosPedidosRel platosPedidosRel);

    @Delete
    int delete(PlatosPedidosRel platosPedidosRel);

    @Query("DELETE FROM PlatosPedidosRel")
    void deleteAll();

    @Query("SELECT * FROM platosPedidosRel WHERE pedidoId = :id")
    LiveData<List<PlatosPedidosRel>> getPlatosPedidosRel(int id);


}
