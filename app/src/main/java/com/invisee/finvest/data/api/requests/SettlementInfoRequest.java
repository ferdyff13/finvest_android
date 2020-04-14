package com.invisee.finvest.data.api.requests;

/**
 * Created by fajarfatur on 2/10/16.
 */
public class SettlementInfoRequest {

    private String bankId;
    private String branchId;
    private String email;
    private String settlementAccountName;
    private String settlementAccountNo;
    private String token;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSettlementAccountName() {
        return settlementAccountName;
    }

    public void setSettlementAccountName(String settlementAccountName) {
        this.settlementAccountName = settlementAccountName;
    }

    public String getSettlementAccountNo() {
        return settlementAccountNo;
    }

    public void setSettlementAccountNo(String settlementAccountNo) {
        this.settlementAccountNo = settlementAccountNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
