package id.co.myproject.gozakat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("value")
    @Expose
    private int value;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("id_user")
    @Expose
    private int idUser;

    public Value(int value, String message, int idUser) {
        this.value = value;
        this.message = message;
        this.idUser = idUser;
    }

    public int getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }

    public int getIdUser() {
        return idUser;
    }
}
