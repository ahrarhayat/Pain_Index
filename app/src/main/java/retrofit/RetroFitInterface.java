package retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroFitInterface {
    @GET("weather?q=Melbourne, Au&appid=86a60f7f3e5d7f17a0e00e18f811ebc9&units=metric")
    Call<SearchResponse> getWeatherInfo(@Query("q") String API_KEY);
}
