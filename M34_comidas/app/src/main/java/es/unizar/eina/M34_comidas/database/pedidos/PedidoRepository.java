package es.unizar.eina.M34_comidas.database.pedidos;


import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Query;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import es.unizar.eina.M34_comidas.database.ComidasRoomDatabase;

public class PedidoRepository {

    private PedidoDao mPedidoDao;
    private LiveData<List<Pedido>> mAllPedidos;

    private final long TIMEOUT = 15000;

    ComidasRoomDatabase db;
    // Note that in order to unit test the NoteRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public PedidoRepository(Application application) {
        db = ComidasRoomDatabase.getDatabase(application);
        mPedidoDao = db.pedidoDao();
        mAllPedidos = mPedidoDao.getOrderedPedidosName();

    }
    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Pedido>> getAllPedidos() {
        return mAllPedidos;
    }

    public void ordenar(String ordenar, String filtrar) {
        if (filtrar == "sinFiltro"){
            Log.d("ordenar pedidos", "Ordenar: " + ordenar + " Filtrar: " + filtrar);
            switch (ordenar){
                case "nombre" :
                    Log.d("ordenar pedidos no filtro", "nombre");
                    mAllPedidos = mPedidoDao.getOrderedPedidosNombreSinFiltro();
                    break;
                case "telefono" :
                    Log.d("ordenar pedidos no filtro", "telefono");
                    mAllPedidos = mPedidoDao.getOrderedPedidosTelefonoSinFiltro();
                    break;
                case "fecha" :
                    Log.d("ordenar pedidos no filtro", "fecha");
                    mAllPedidos = mPedidoDao.getOrderedPedidosFechaSinFiltro();
                    break;
                case "hora" :
                    Log.d("ordenar pedidos no filtro", "hora");
                    mAllPedidos = mPedidoDao.getOrderedPedidosHoraSinFiltro();
                    break;
            }
        }
        else {
            switch (ordenar){
                case "nombre" :
                    Log.d("ordenar pedidos con filtro", "nombre");
                    mAllPedidos = mPedidoDao.getOrderedPedidosNombreConFiltro(filtrar);
                    break;
                case "telefono" :
                    Log.d("ordenar pedidos con filtro", "telefono");
                    mAllPedidos = mPedidoDao.getOrderedPedidosTelefonoConFiltro(filtrar);
                    break;
                case "fecha" :
                    Log.d("ordenar pedidos con filtro", "fecha");
                    mAllPedidos = mPedidoDao.getOrderedPedidosFechaConFiltro(filtrar);
                    break;
                case "hora" :
                    Log.d("ordenar pedidos con filtro", "hora");
                    mAllPedidos = mPedidoDao.getOrderedPedidosHoraConFiltro(filtrar);
                    break;
            }
        }
    }


    /** Inserta una nota
     * @param pedido
     * @return un valor entero largo con el identificador de la nota que se ha creado.
     */
    public long insert(Pedido pedido) {
        final long[] result = {0};
        final CountDownLatch latch = new CountDownLatch(1);
        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            result[0] = mPedidoDao.insert(pedido);
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
     * @param pedido
     * @return un valor entero con el número de filas modificadas.
     */
    public int update(Pedido pedido) {
        //final int[] result = {0};
        AtomicInteger result = new AtomicInteger();
        Semaphore resource = new Semaphore(0);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = mPedidoDao.update(pedido);
            result.set(value);
            resource.release();
            //result[0] = mPedidoDao.update(pedido);
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
     * @param pedido
     * @return un valor entero con el número de filas eliminadas.
     */
    public int delete(Pedido pedido) {
        //final int[] result = {0};
        AtomicInteger result = new AtomicInteger();
        Semaphore resource = new Semaphore(0);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = mPedidoDao.delete(pedido);
            result.set(value);
            resource.release();
            //result[0] = mPedidoDao.delete(pedido);
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
