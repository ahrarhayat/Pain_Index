package database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import PainEntryDAO.PainEntryDAO;
import entity.PainEntryData;

@Database(entities = {PainEntryData.class}, version = 1, exportSchema = false)
public abstract class PainEntryDatabase extends RoomDatabase {

    public abstract PainEntryDAO painEntryDAO();
    private static PainEntryDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized PainEntryDatabase getInstance(final Context
                                                                    context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    PainEntryDatabase.class, "PainEntryDatabase")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

}
