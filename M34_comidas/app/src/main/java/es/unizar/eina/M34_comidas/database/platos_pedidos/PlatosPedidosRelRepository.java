package es.unizar.eina.M34_comidas.database.platos_pedidos;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import es.unizar.eina.M34_comidas.database.ComidasRoomDatabase;
import es.unizar.eina.M34_comidas.database.pedidos.Pedido;
import es.unizar.eina.M34_comidas.database.pedidos.PedidoDao;

public class PlatosPedidosRelRepository {

    private PlatosPedidosRelDao mPlatosPedidoRelDao;
    private LiveData<List<PlatosPedidosRel>> mAllPlatoPedidosRel;

    private final long TIMEOUT = 15000;

    // Note that in order to unit test the NoteRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public PlatosPedidosRelRepository(Application application) {
        ComidasRoomDatabase db = ComidasRoomDatabase.getDatabase(application);
        mPlatosPedidoRelDao = db.platosPedidosRelDao();
        mAllPlatoPedidosRel = mPlatosPedidoRelDao.getPlatosPedidosRel(1);
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<PlatosPedidosRel>> getAllRaciones(int id) {
        final CountDownLatch latch = new CountDownLatch(1);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            mAllPlatoPedidosRel = mPlatosPedidoRelDao.getPlatosPedidosRel(id);
            latch.countDown(); // Indica que la operación ha terminado.
        });

        try {
            latch.await(); // Espera hasta que la operación haya terminado.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mAllPlatoPedidosRel;

    }


    /** Inserta una nota
     * @param platosPedidosRel
     * @return un valor entero largo con el identificador de la nota que se ha creado.
     */
    public long insert(PlatosPedidosRel platosPedidosRel) {
        final long[] result = {0};
        final CountDownLatch latch = new CountDownLatch(1);
        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPlatosPedidoRelDao.insert(platosPedidosRel);
            latch.countDown(); // Indica que la operación ha terminado.
        });

        try {
            latch.await(); // Espera hasta que la operación haya terminado.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result[0];
    }

    /** Modifica una nota
     * @param platosPedidosRel
     * @return un valor entero con el número de filas modificadas.
     */
    public int update(PlatosPedidosRel platosPedidosRel) {
        //final int[] result = {0};
        AtomicInteger result = new AtomicInteger();
        Semaphore resource = new Semaphore(0);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = mPlatosPedidoRelDao.delete(platosPedidosRel);
            result.set(value);
            resource.release();
            //result[0] = mPlatosPedidoRelDao.update(platosPedidosRel);
        });
        try {
            resource.tryAcquire(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Log.d("PlatoRepository", "InterruptedException: " + e.getMessage());
        }
        return result.get();
        //return result[0];
    }

    /** Elimina una nota
     * @param platosPedidosRel
     * @return un valor entero con el número de filas eliminadas.
     */
    public int delete(PlatosPedidosRel platosPedidosRel) {
        //final int[] result = {0};
        AtomicInteger result = new AtomicInteger();
        Semaphore resource = new Semaphore(0);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = mPlatosPedidoRelDao.delete(platosPedidosRel);
            result.set(value);
            resource.release();
            //result[0] = mPlatosPedidoRelDao.delete(platosPedidosRel);
        });
        try {
            resource.tryAcquire(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Log.d("PlatoRepository", "InterruptedException: " + e.getMessage());
        }
        return result.get();
        //return result[0];
    }
}
