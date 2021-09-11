package viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import entity.PainEntryData;
import repository.PainEntryRepository;

public class PainEntryViewModel extends AndroidViewModel {

    private PainEntryRepository painEntryRepository;
    private LiveData<List<PainEntryData>> allPainEntry;

    public PainEntryViewModel(@NonNull Application application) {
        super(application);
        painEntryRepository = new PainEntryRepository(application);
        allPainEntry = painEntryRepository.getAllPainEntries();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CompletableFuture<PainEntryData> findByIDFuture(final int painID){
        return painEntryRepository.findByIDFuture(painID);
    }

    public LiveData<List<PainEntryData>> getAllPainEntry() {
        return allPainEntry;
    }

    public void insert(PainEntryData painEntryData) {
        painEntryRepository.insert(painEntryData);
    }

    public void deleteAll() {
        painEntryRepository.deleteAll();
    }

    public void update(PainEntryData painEntryData) {
        painEntryRepository.updatePainEntry(painEntryData);
    }


}
