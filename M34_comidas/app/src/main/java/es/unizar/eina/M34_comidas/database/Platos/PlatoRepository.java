package es.unizar.eina.M34_comidas.database.Platos;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import es.unizar.eina.M34_comidas.database.ComidasRoomDatabase;

public class PlatoRepository {

    private PlatoDao mPlatoDao;
    private LiveData<List<Plato>> mAllPlatos;

    private final long TIMEOUT = 15000;

    // Note that in order to unit test the NoteRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    public PlatoRepository(Application application) {
        ComidasRoomDatabase db = ComidasRoomDatabase.getDatabase(application);
        mPlatoDao = db.noteDao();
        mAllPlatos = mPlatoDao.getOrderedNotesName();
    }

    public void ordenar(String criterio) {
        switch (criterio){
            case "nombre":
                Log.d("ordenar_noteRepository", "nombre");
                mAllPlatos = mPlatoDao.getOrderedNotesName();
                break;
            case "categoria":
                Log.d("ordenar_noteRepository", "categoria");
                mAllPlatos = mPlatoDao.getOrderedNotesCategory();
                break;
            case "ambos":
                Log.d("ordenar_noteRepository", "ambos");
                mAllPlatos = mPlatoDao.getOrderedNotesNameCategory();
                break;
        }
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Plato>> getAllPlatos() {
        return mAllPlatos;
    }

    public Plato getPlatoiD(int id){
        Plato mPlato = mPlatoDao.getPlatoiD(id);
        return mPlato;
    }

    /** Inserta una nota
     * @param plato
     * @return un valor entero largo con el identificador de la nota que se ha creado.
     */
    public long insert(Plato plato) {
        //final long[] result = {0};
        // You must call this on a non-UI thread or your app will throw an exception. Room ensures
        // that you're not doing any long running operations on the main thread, blocking the UI.
        AtomicLong result = new AtomicLong();
        Semaphore resource = new Semaphore(0);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            long value = mPlatoDao.insert(plato);
            result.set(value);
            resource.release();
            //result[0] = mPlatoDao.insert(plato);
        });
        try {
            resource.tryAcquire(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Log.d("PlatoRepository", "InterruptedException: " + e.getMessage());
        }
        return result.get();
        //return result[0];
    }

    /** Modifica una nota
     * @param plato
     * @return un valor entero con el número de filas modificadas.
     */
    public int update(Plato plato) {
        //final int[] result = {0};
        AtomicInteger result = new AtomicInteger();
        Semaphore resource = new Semaphore(0);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = mPlatoDao.update(plato);
            result.set(value);
            resource.release();
            //result[0] = mPlatoDao.update(plato);
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
     * @param plato
     * @return un valor entero con el número de filas eliminadas.
     */
    public int delete(Plato plato) {
        //final int[] result = {0};
        AtomicInteger result = new AtomicInteger();
        Semaphore resource = new Semaphore(0);
        ComidasRoomDatabase.databaseWriteExecutor.execute(() -> {
            int value = mPlatoDao.delete(plato);
            result.set(value);
            resource.release();
            //result[0] = mPlatoDao.delete(plato);
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
