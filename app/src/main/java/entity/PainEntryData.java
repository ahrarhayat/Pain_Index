package entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class PainEntryData {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "pain_intensity_level")
    @NonNull
    public String painIntensityLevel;


    @ColumnInfo(name = "pain_location")
    @NonNull
    public String painLocation;

    @ColumnInfo(name = "mood_level")
    @NonNull
    public String moodLevel;

    @ColumnInfo(name = "steps_taken")
    @NonNull
    public String stepTaken;

    @ColumnInfo(name = "date")
    @NonNull
    public String date;


    @ColumnInfo(name = "temperature")
    @NonNull
    public String temperature;


    @ColumnInfo(name = "humidity")
    @NonNull
    public String humidity;

    @ColumnInfo(name = "pressure")
    @NonNull
    public String pressure;

    @ColumnInfo(name = "user_email")
    @NonNull
    public String userEmail;






    public PainEntryData( @NonNull String painIntensityLevel, @NonNull String painLocation , @NonNull String moodLevel
            ,@NonNull String stepTaken,@NonNull String date,@NonNull String temperature,@NonNull String humidity ,@NonNull String pressure,
    @NonNull String userEmail)
    {

        this.painIntensityLevel = painIntensityLevel;
        this.painLocation = painLocation;
        this.moodLevel = moodLevel;
        this.stepTaken = stepTaken;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.userEmail = userEmail;
    }

    @Ignore
    public PainEntryData(int uid, String  painIntensityLevel,  String painLocation ,  String moodLevel
            , String stepTaken, String date, String temperature, String humidity , String pressure,
                         String userEmail)
    {
        this.uid = uid;
        this.painIntensityLevel = painIntensityLevel;
        this.painLocation = painLocation;
        this.moodLevel = moodLevel;
        this.stepTaken = stepTaken;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        this.userEmail = userEmail;

    }



    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @NonNull
    public String getPainIntensityLevel() {
        return painIntensityLevel;
    }

    public void setPainIntensityLevel(@NonNull String painIntensityLevel) {
        this.painIntensityLevel = painIntensityLevel;
    }

    @NonNull
    public String getPainLocation() {
        return painLocation;
    }

    public void setPainLocation(@NonNull String painLocation) {
        this.painLocation = painLocation;
    }

    @NonNull
    public String getMoodLevel() {
        return moodLevel;
    }

    public void setMoodLevel(@NonNull String moodLevel) {
        this.moodLevel = moodLevel;
    }

    @NonNull
    public String getStepTaken() {
        return stepTaken;
    }

    public void setStepTaken(@NonNull String stepTaken) {
        this.stepTaken = stepTaken;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(@NonNull String temperature) {
        this.temperature = temperature;
    }

    @NonNull
    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(@NonNull String humidity) {
        this.humidity = humidity;
    }

    @NonNull
    public String getPressure() {
        return pressure;
    }

    public void setPressure(@NonNull String pressure) {
        this.pressure = pressure;
    }

    @NonNull
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(@NonNull String userEmail) {
        this.userEmail = userEmail;
    }
}
