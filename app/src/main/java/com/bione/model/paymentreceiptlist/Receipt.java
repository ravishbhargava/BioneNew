package com.bione.model.paymentreceiptlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Receipt {

@SerializedName("id")
@Expose
private String id;
@SerializedName("first_name")
@Expose
private String firstName;
@SerializedName("last_name")
@Expose
private String lastName;
@SerializedName("test_name")
@Expose
private String testName;
@SerializedName("test_amount")
@Expose
private Object testAmount;
@SerializedName("amount")
@Expose
private String amount;
@SerializedName("amount_words")
@Expose
private String amountWords;
@SerializedName("sales_person")
@Expose
private String salesPerson;
@SerializedName("sales_person_email")
@Expose
private String salesPersonEmail;
@SerializedName("report_manager_email")
@Expose
private String reportManagerEmail;
@SerializedName("paid_amount")
@Expose
private String paidAmount;
@SerializedName("balance_amount")
@Expose
private String balanceAmount;
@SerializedName("balance_amount_paid_date")
@Expose
private String balanceAmountPaidDate;
@SerializedName("payment_type")
@Expose
private String paymentType;
@SerializedName("payment_mode")
@Expose
private String paymentMode;
@SerializedName("remarks")
@Expose
private String remarks;
@SerializedName("receipt_url")
@Expose
private Object receiptUrl;
@SerializedName("receipt_id")
@Expose
private Object receiptId;

public String getId() {
return id;
}

public void setId(String id) {
this.id = id;
}

public String getFirstName() {
return firstName;
}

public void setFirstName(String firstName) {
this.firstName = firstName;
}

public String getLastName() {
return lastName;
}

public void setLastName(String lastName) {
this.lastName = lastName;
}

public String getTestName() {
return testName;
}

public void setTestName(String testName) {
this.testName = testName;
}

public Object getTestAmount() {
return testAmount;
}

public void setTestAmount(Object testAmount) {
this.testAmount = testAmount;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

public String getAmountWords() {
return amountWords;
}

public void setAmountWords(String amountWords) {
this.amountWords = amountWords;
}

public String getSalesPerson() {
return salesPerson;
}

public void setSalesPerson(String salesPerson) {
this.salesPerson = salesPerson;
}

public String getSalesPersonEmail() {
return salesPersonEmail;
}

public void setSalesPersonEmail(String salesPersonEmail) {
this.salesPersonEmail = salesPersonEmail;
}

public String getReportManagerEmail() {
return reportManagerEmail;
}

public void setReportManagerEmail(String reportManagerEmail) {
this.reportManagerEmail = reportManagerEmail;
}

public String getPaidAmount() {
return paidAmount;
}

public void setPaidAmount(String paidAmount) {
this.paidAmount = paidAmount;
}

public String getBalanceAmount() {
return balanceAmount;
}

public void setBalanceAmount(String balanceAmount) {
this.balanceAmount = balanceAmount;
}

public String getBalanceAmountPaidDate() {
return balanceAmountPaidDate;
}

public void setBalanceAmountPaidDate(String balanceAmountPaidDate) {
this.balanceAmountPaidDate = balanceAmountPaidDate;
}

public String getPaymentType() {
return paymentType;
}

public void setPaymentType(String paymentType) {
this.paymentType = paymentType;
}

public String getPaymentMode() {
return paymentMode;
}

public void setPaymentMode(String paymentMode) {
this.paymentMode = paymentMode;
}

public String getRemarks() {
return remarks;
}

public void setRemarks(String remarks) {
this.remarks = remarks;
}

public Object getReceiptUrl() {
return receiptUrl;
}

public void setReceiptUrl(Object receiptUrl) {
this.receiptUrl = receiptUrl;
}

public Object getReceiptId() {
return receiptId;
}

public void setReceiptId(Object receiptId) {
this.receiptId = receiptId;
}

}