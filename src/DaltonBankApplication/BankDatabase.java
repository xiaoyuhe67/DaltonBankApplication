package DaltonBankApplication;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import DBUtility.DBUtility;

/** 
 * 
 * @author Xiaoyu He
 * This class provides methods to work with a bank database in Oracle
 *
 */
public class BankDatabase {
	
	private static int col=0;
	
	 private static List<String> getsavingsaccount=new ArrayList<String>(); 
	
	/** 
	 * This method creates a new bank account
	 * @param accountnumber
	 * @param holdername
	 * @param accounttype
	 * @param opendate
	 * @throws SQLException
	 */
	public void createAccount(String accountnumber, String holdername, String accounttype, String opendate) throws SQLException
	{
		String sql=" insert into Bank_Accounts (ACCT_NO,HOLDER_NAME, ACCOUNT_TYPE,open_date) "
				+ "values ('"+accountnumber+"','"+holdername+"','"+accounttype+"','"+opendate+"')";
		 DBUtility.insert(sql);		 
		 
	}
	
	public int getAccountID(String accountnumber) throws SQLException
	{
		String sql="select acct_id from bank_accounts where acct_no='"+accountnumber+"'";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int id=0;
		 if(mylist.size()==0)
		 {
			 id=0;
		 }
		 else
		 {
			 id=Integer.parseInt(mylist.get(0));
		 }
		 
		 
		 return id;
		 
	}
	public String getAccountNumber(int Accountid) throws SQLException
	{
		String sql="select acct_no from bank_accounts where acct_id="+Accountid+"";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 String accountnumber=(mylist.get(0));
		 
		 return accountnumber;
		 
	}
	
	public int getTransactionTypeID(String transactionname) throws SQLException
	{
		String sql="select trans_type_id from trans_types where trans_name='"+transactionname+"'";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int id=Integer.parseInt(mylist.get(0));
		 
		 return id;
		 
	}
	
	public String getTransactionName(int transactiontypeid) throws SQLException
	{
		String sql="select trans_name from trans_types where trans_type_id="+transactiontypeid+"";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		  String transname=(mylist.get(0));
		 
		 return transname;
		 
	}
	
	public String getAccountType(int accountid) throws SQLException
	{
		String sql="select account_type from Bank_Accounts where ACCt_id="+accountid+"";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		  String accounttype=(mylist.get(0));
		 
		 return accounttype;
		 
	}
	public String getHoldername(int accountid) throws SQLException
	{
		String sql="select holder_name from Bank_Accounts where ACCt_id="+accountid+"";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		  String holdername=(mylist.get(0));
		 
		 return holdername;
		 
	}
	
	public int getSavingAccountidbyHoldername(String holdername) throws SQLException
	{
		String sql="select ACCt_id, acct_no from Bank_Accounts where holder_name='"+holdername+"' and Account_type='Savings'";
			 
		getsavingsaccount=DBUtility.select(sql);
		 	 
		 
		 int accountid=0;
		 
		 if(getsavingsaccount.size()==0)
		 {
			 accountid=0;
			 
		 }
		 else
		 {
			  accountid=Integer.parseInt((getsavingsaccount.get(0)));
			  
			  
		 }	
		 
		 return accountid;
		 
	}
	
	public void createTransaction(int accountid, int transactiontypeid, String amount, String date) throws SQLException
	{ 
		int transactiontype=getTransactionType(transactiontypeid);
		
		double intamount=0;
		 
		 if(transactiontype==0)
		 {
			 intamount=0-Double.parseDouble(amount);
			 
		 }
		 else if(transactiontype==1)
		 {
			 intamount=Double.parseDouble(amount);
			 
		 }
		String sql=" insert into Bank_Trans (Trans_type_id,acct_id, amount, Trans_date) "
				+ " values ("+transactiontypeid+","+accountid+","+intamount+",'"+date+"')";
		
		DBUtility.insert(sql);	
		 
		 
	}
	
	public int getTransactionType(int transactiontypeid) throws SQLException
	{
		String sql="select trans_type from trans_types where trans_type_id="+transactiontypeid+"";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int id=Integer.parseInt(mylist.get(0));
		 
		 return id;
		 
	}
	
	public double getbalance(int accountid) throws SQLException
	{
		String sql="select sum(amount) as sum from bank_trans where acct_id="+accountid+"";
		List<String> mylist = new ArrayList<String>();
		 
		
		 mylist=DBUtility.select(sql);
		 
		 double balance=0;
		 if(mylist.size()==0)
		 {
			 balance=0;
		 }
		 else
		 {
			 balance=Double.parseDouble(mylist.get(0));
		 }
		 
		  
		 return balance;
		
	}
	public double getSum(int accountid, int transactiontypeid) throws SQLException
	{
		
		String sql="select sum(amount)as sum from bank_trans where acct_id="+accountid+" and trans_type_id="+transactiontypeid;
		
		List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 double balance=0;
		 
		 if(mylist.get(0)==null)
		 {
			 balance=0;
		 }
		 else
		 {
			 balance=Double.parseDouble(mylist.get(0));
		 }
		 return balance;
	}
	public List<String> searchRecordsBetweenDate(String date1, String date2, int accountid) throws SQLException

	{
		List<String> mylist=new ArrayList();

		String sql=" select b.trans_date, b.Trans_id,t.Trans_Name,a.acct_no,a.holder_name, a.account_type, b.amount  from Bank_trans b , Trans_types t , bank_accounts a "
				+ "where b.trans_type_id=t.trans_type_id and b.acct_id=a.acct_id "
				+ "and to_date(trans_date,'MM/DD/YYYY') between to_date('"+date1+"','MM/DD/YYYY') and to_date('"+date2+"','MM/DD/YYYY')";

		mylist.add(columnname(sql)+"\n");
		for(String d: DBUtility.select(sql))
		{
			mylist.add(d);
		}
		
		return mylist;	

	}
	
	public String columnname(String query) throws SQLException
	{
		
		ResultSet rs;
		String content="";
		rs=DBUtility.getResultSet(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		col=DBUtility.columnnum(query);
		
		for(int i=1; i<=col;i++)
		{
			content+=rsmd.getColumnLabel(i)+"   ";
		}
		content+="\n";
		for(int i=0; i<col;i++)
		{
			content+="-------------";
		}
		return content;
	}
	
	public String printlist(List<String> mylist, int colu)
	{
		String content="";      
		int i=0;
		colu=col;
		for(String d:mylist) {	
            
            if((i%colu)==0)
            {
            	content+=d+"     "+"\n";
            }
            else
            {
            	content+=d+"     ";
            }
            i++;
            
        }
		return content;
		
	}

	public static int getCol() {
		return col;
	}

	public static void setCol(int col) {
		BankDatabase.col = col;
	}
	public int getcountbyAccountid(int accountid) throws SQLException
	{
		String sql="select count(*) as total from bank_trans where acct_id="+accountid+"";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int total=Integer.parseInt(mylist.get(0));
		 
		 return total;
		 
	}
	public int getcountofdepositsbyAccountid(int accountid) throws SQLException
	{
		String sql="select count(*) as total from bank_trans where acct_id="+accountid+" and trans_type_id=4";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int total=Integer.parseInt(mylist.get(0));
		 
		 return total;
		 
	}
	public int getcountofdeposits() throws SQLException
	{
		String sql="select count(*) as total from bank_trans where trans_type_id=4";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int total=Integer.parseInt(mylist.get(0));
		 
		 return total;
		 
	}
	public int getcountofwithdrawalsbyAccountid(int accountid) throws SQLException
	{
		String sql="select count(*) as total from bank_trans where acct_id="+accountid+" and trans_type_id=1 or trans_type_id=2 or trans_type_id=3";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int total=Integer.parseInt(mylist.get(0));
		 
		 return total;
		 
	}
	public int getcountalltransactions() throws SQLException
	{
		String sql="select count(*) as total from bank_trans";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int total=Integer.parseInt(mylist.get(0));
		 
		 return total;
		 
	}
	public double getSumAll(int transactiontypeid) throws SQLException
	{
		
		String sql="select sum(amount)as sum from bank_trans where trans_type_id="+transactiontypeid;
		
		List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 double balance=0;
		 
		 if(mylist.get(0)==null)
		 {
			 balance=0;
		 }
		 else
		 {
			 balance=Double.parseDouble(mylist.get(0));
		 }
		 return balance;
	}
	
	public int getcountofwithdrawals() throws SQLException
	{
		String sql="select count(*) as total from bank_trans where trans_type_id=1 or trans_type_id=2 or trans_type_id=3";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int total=Integer.parseInt(mylist.get(0));
		 
		 return total;
		 
	}

	public int getcountofaccounts(String accounttype) throws SQLException
	{
		String sql="select count(*) from bank_accounts where account_type='"+accounttype+"'";
		
		 List<String> mylist = new ArrayList<String>();
		 
		 mylist=DBUtility.select(sql);
		 
		 int total=Integer.parseInt(mylist.get(0));
		 
		 return total;
		 
	}
	
	public boolean validdate(String date)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");   
		boolean valid=true;
		try{	 
		     java.util.Date da = sdf.parse(date);
		     valid=true;
		     
		  }
		  catch(Exception e){
			 
			  valid=false;
		  }
		return valid;
	}

	public static List<String> getGetsavingsaccount() {
		return getsavingsaccount;
	}

	public static void setGetsavingsaccount(List<String> getsavingsaccount) {
		BankDatabase.getsavingsaccount = getsavingsaccount;
	}

	
	
	

	
	
	
	
	
	
	
	

}
