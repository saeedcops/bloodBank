
package com.cops.bloodbankclone.data.model.requestDonation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestDonation {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private RequestDonationData data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RequestDonationData getData() {
        return data;
    }

    public void setData(RequestDonationData data) {
        this.data = data;
    }

}
