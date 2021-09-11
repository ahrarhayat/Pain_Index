package repository;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import PainEntryDAO.PainEntryDAO;
import database.PainEntryDatabase;
import entity.PainEntryData;

public class PainEntryRepository {
    private PainEntryDAO painEntryDAO;
    private LiveData<List<PainEntryData>> allPainEntries;

    public PainEntryRepository(Application application) {
        PainEntryDatabase db = PainEntryDatabase.getInstance(application);
        painEntryDAO = db.painEntryDAO();
        allPainEntries = painEntryDAO.getAll();
    }

        public LiveData<List<PainEntryData>> getAllPainEntries() {
            return allPainEntries;
        }


    public void insert(final PainEntryData painEntryData){
        PainEntryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painEntryDAO.insert(painEntryData);
            }
        });
    }

    public void deleteAll(){
        PainEntryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painEntryDAO.deleteAll();
            }
        });
    }

    public void delete(final PainEntryData painEntryData){
        PainEntryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painEntryDAO.delete(painEntryData);
            }
        });
    }

    public void updatePainEntry(final PainEntryData painEntryData){
        PainEntryDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                painEntryDAO.updateCustomer(painEntryData);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<PainEntryData> findByIDFuture(final int painId) {

        return CompletableFuture.supplyAsync(new Supplier<PainEntryData>() {
            @Override
            public PainEntryData get() {
                return painEntryDAO.findByID(painId);
            }
        }, PainEntryDatabase.databaseWriteExecutor);
    }
}

