
package com.cops.bloodbankclone.data.model.register;

import com.cops.bloodbankclone.data.model.login.Client;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("client")
    @Expose
    private Client client;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

}
