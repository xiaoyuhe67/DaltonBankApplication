package DaltonBankApplication;

import java.util.Date;

public class BankTransaction {
	
	private int TransactionId;
	private int TransTypeid;
	private int AccountId;
	private long Amount;
	private Date TransactionDate;
	
	private String TransType;
	private String AccountNumber;
	
	public int getTransactionId() {
		return TransactionId;
	}
	public void setTransactionId(int transactionId) {
		TransactionId = transactionId;
	}
	public int getTransTypeid() {
		return TransTypeid;
	}
	public void setTransTypeid(int transTypeid) {
		TransTypeid = transTypeid;
	}
	public int getAccountId() {
		return AccountId;
	}
	public void setAccountId(int accountId) {
		AccountId = accountId;
	}
	public long getAmount() {
		return Amount;
	}
	public void setAmount(long amount) {
		Amount = amount;
	}
	public Date getTransactionDate() {
		return TransactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		TransactionDate = transactionDate;
	}
	public String getTransType() {
		return TransType;
	}
	public void setTransType(String transType) {
		TransType = transType;
	}
	public String getAccountNumber() {
		return AccountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		AccountNumber = accountNumber;
	}

}
