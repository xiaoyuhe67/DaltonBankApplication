package DaltonBankApplication;

public class BankTransactionType {
	
	private int TransTypeId;
	private String TransName;
	
	private int TransType;
	
	
	public String getTransName() {
		return TransName;
	}
	public void setTransName(String transName) {
		TransName = transName;
	}
	public int getTransType() {
		return TransType;
	}
	public void setTransType(int transType) {
		TransType = transType;
	}
	public int getTransTypeId() {
		return TransTypeId;
	}
	public void setTransTypeId(int transTypeId) {
		TransTypeId = transTypeId;
	}
	

}
