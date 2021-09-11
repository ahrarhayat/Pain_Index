package retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroFitClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL="https://api.openweathermap.org/data/2.5/";

    public static Retrofit getRetrofitService(){

        if (retrofit == null){
            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;

    }
}
