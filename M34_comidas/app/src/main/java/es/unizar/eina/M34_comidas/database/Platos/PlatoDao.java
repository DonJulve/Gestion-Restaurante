package es.unizar.eina.M34_comidas.database.Platos;

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
public interface PlatoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Plato plato);

    @Update
    int update(Plato plato);

    @Delete
    int delete(Plato plato);

    @Query("DELETE FROM plato")
    void deleteAll();

    @Query("SELECT * FROM plato ORDER BY nombre ASC")
    LiveData<List<Plato>> getOrderedNotesName();

    @Query("SELECT * FROM plato ORDER BY categoria ASC")
    LiveData<List<Plato>> getOrderedNotesCategory();

    @Query("SELECT * FROM plato ORDER BY categoria ASC, nombre ASC")
    LiveData<List<Plato>> getOrderedNotesNameCategory();

    @Query("SELECT * FROM plato WHERE platoId = :id")
    Plato getPlatoiD(int id);
}

