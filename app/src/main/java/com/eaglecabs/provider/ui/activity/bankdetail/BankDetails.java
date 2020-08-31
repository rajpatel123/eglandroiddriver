package com.eaglecabs.provider.ui.activity.bankdetail;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDetails {

@SerializedName("u_name")
@Expose
private String uName;
@SerializedName("b_name")
@Expose
private String bName;
@SerializedName("b_account")
@Expose
private String bAccount;
@SerializedName("b_ifsc_code")
@Expose
private String bIfscCode;
@SerializedName("cheque_url")
@Expose
private String chequeUrl;

public String getUName() {
return uName;
}

public void setUName(String uName) {
this.uName = uName;
}

public String getBName() {
return bName;
}

public void setBName(String bName) {
this.bName = bName;
}

public String getBAccount() {
return bAccount;
}

public void setBAccount(String bAccount) {
this.bAccount = bAccount;
}

public String getBIfscCode() {
return bIfscCode;
}

public void setBIfscCode(String bIfscCode) {
this.bIfscCode = bIfscCode;
}

public String getChequeUrl() {
return chequeUrl;
}

public void setChequeUrl(String chequeUrl) {
this.chequeUrl = chequeUrl;
}

}