package es.unizar.eina.M34_comidas.database;

import android.content.Context;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import es.unizar.eina.M34_comidas.database.Platos.Plato;
import es.unizar.eina.M34_comidas.database.Platos.PlatoDao;
import es.unizar.eina.M34_comidas.database.pedidos.Pedido;
import es.unizar.eina.M34_comidas.database.pedidos.PedidoDao;
import es.unizar.eina.M34_comidas.database.platos_pedidos.PlatosPedidosRel;
import es.unizar.eina.M34_comidas.database.platos_pedidos.PlatosPedidosRelDao;

@Database(entities = {Plato.class, Pedido.class, PlatosPedidosRel.class}, version = 1, exportSchema = false)
public abstract class ComidasRoomDatabase extends RoomDatabase {

    public abstract PlatoDao noteDao();
    public abstract PedidoDao pedidoDao();
    public abstract PlatosPedidosRelDao platosPedidosRelDao();

    private static volatile ComidasRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static ComidasRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ComidasRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ComidasRoomDatabase.class, "comidas_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);


            // If you want to keep data through app restarts,
            // comment out the following block

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more notes, just add them.
                PlatoDao platoDao = INSTANCE.noteDao();
                PedidoDao pedidoDao = INSTANCE.pedidoDao();

                // Eliminar todo
                platoDao.deleteAll();
                pedidoDao.deleteAll();

                // Creacion platos de preuba
                Plato plato = new Plato("Note 1's title", "Note 1's body", 0.0, "listo");
                platoDao.insert(plato);
                plato = new Plato("Note 2's title", "Note 2's body", 0.0, "listo");
                platoDao.insert(plato);

                // Creacion pedidos de prueba
                Pedido pedido = new Pedido("Pedido 1's title", "1", "00/00/0000", "00:00", "solicitado");
                pedidoDao.insert(pedido);
            });


        }
    };
}
