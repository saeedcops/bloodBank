
package com.cops.bloodbankclone.data.model.post;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private PostPagination data;
//    @SerializedName("data")
//    @Expose
//    private PostData postData;

//    public PostData getPostData() {
//        return postData;
//    }
//
//    public void setPostData(PostData postData) {
//        this.postData = postData;
//    }

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

    public PostPagination getData() {
        return data;
    }

    public void setData(PostPagination data) {
        this.data = data;
    }

}
