package PainEntryDAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import entity.PainEntryData;

@Dao
public interface PainEntryDAO {
    @Query("SELECT * FROM painentrydata ORDER BY date ASC")
    LiveData<List<PainEntryData>> getAll();

    @Query("SELECT * FROM painentrydata WHERE uid = :painId LIMIT 1")
    PainEntryData findByID(int painId);

    @Insert
    void insert(PainEntryData painEntryData);

    @Delete
    void delete(PainEntryData painEntryData);

    @Update
    void updateCustomer(PainEntryData painEntryData);

    @Query("DELETE FROM painentrydata")
    void deleteAll();
}
