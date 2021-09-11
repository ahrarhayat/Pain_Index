package retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResponse {
    //No need to map all keys, only those the fields you need
    @SerializedName("main")
    private Items items;

    public Items getItems() {
        return items;
    }

    public void setItems(Items items) {
        this.items = items;
    }
}
