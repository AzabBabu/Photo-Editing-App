package com.example.knowledgegain;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class WithdrawsRequest {
    public WithdrawsRequest() {
    }

    String userId ,bkashNo, requestBy;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBkashNo() {
        return bkashNo;
    }

    public void setBkashNo(String bkashNo) {
        this.bkashNo = bkashNo;
    }

    public String getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(String requestBy) {
        this.requestBy = requestBy;
    }

    public Date getWithdrawsReqTime() {
        return withdrawsReqTime;
    }

    public void setWithdrawsReqTime(Date withdrawsReqTime) {
        this.withdrawsReqTime = withdrawsReqTime;
    }

    @ServerTimestamp
    Date withdrawsReqTime;

    public WithdrawsRequest(String userId, String bkashNo, String requestBy) {
        this.userId = userId;
        this.bkashNo = bkashNo;
        this.requestBy = requestBy;

    }
}
