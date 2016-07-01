package DaltonBankApplication;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BankAPP {
	
	static Scanner sc=new Scanner(System.in);
	static BankDatabase bd = new BankDatabase();
	static int FEE = 5;
	static String accountnumber;
	static String fromdate;
	static String todate;
	
	public static void main(String[] args) throws SQLException, ParseException
	{
		System.out.println("Welcome to the bank!");
		System.out.println("Please create your account!");
		
		while(true)
		{
			System.out.println("Enter an account#  or -1 to stop entering accounts:");
		
			accountnumber=sc.next();
		
			if(accountnumber.equals("-1"))
			{
				break;
			}
			else 
			{
				System.out.println("Enter the name for acct # "+accountnumber+": ");
				String holdername=sc.next();
				System.out.println("Enter the type of account (checking or savings): ");
				String accounttype=sc.next();
				System.out.println("Enter the opendate of account: ");
				String opendate=sc.next();
				bd.createAccount(accountnumber, holdername, accounttype,opendate);		
			}
		}
		
		while(true)
		{
			System.out.println("Enter an account # to start your transaction or enter (summary) for all accounts:");
			accountnumber=sc.next();
			
			if (accountnumber.equals("summary"))
			{
				System.out.println("Total number of transactions: "+bd.getcountalltransactions());
				System.out.println("Number of Deposits: "+bd.getcountofdeposits());
				System.out.println("Total amount of Deposits: "+bd.getSumAll(4));
				System.out.println("Number of Withdrawals: "+bd.getcountofwithdrawals());
				System.out.println("Total amount of Withdrawals: "+(bd.getSumAll(1)+bd.getSumAll(2)+bd.getSumAll(3)));
				System.out.println("Total amount of Fees charged: "+bd.getSumAll(5));
				System.out.println("Number of checking accounts: "+bd.getcountofaccounts("Checking"));
				System.out.println("Number of savings accounts: "+bd.getcountofaccounts("Savings"));
				System.out.println("");
				
			}
			else
			{
			int accountid = bd.getAccountID(accountnumber);
			if(accountid==0)
			{
				System.out.println("There is no match!");
			}
			else
			{
				System.out.println("Enter a transaction type (Check, Debit card, Deposit or Withdrawal) or enter -1 to quit or enter report to get the report: ");	
				sc.nextLine();
				String transname = sc.nextLine();	
				
				if(transname.equals("-1"))
				{
					System.out.println("Your balance is "+bd.getbalance(accountid));
					break;
				}
				else if(transname.equals("report"))
				{
					System.out.println("Do you want to see  an account summary(1)or transaction history by date(2):");
					
					int report=sc.nextInt();
					
					if(report==1)
					{
						System.out.println("The summary of your accct#: "+accountnumber+"");
						System.out.println("Total number of the transactions: "+bd.getcountbyAccountid(accountid));
						System.out.println("Number of Deposits: "+bd.getcountofdepositsbyAccountid(accountid));
						System.out.println("Total amount of Deposits: "+bd.getSum(accountid, 4));
						System.out.println("Number of Withdrawals: "+bd.getcountofwithdrawalsbyAccountid(accountid));
						System.out.println("Total amount of Withdrawals: "+(bd.getSum(accountid, 1)+bd.getSum(accountid, 2)+bd.getSum(accountid, 3)));
						System.out.println("Total amount of Fees charged: "+bd.getSum(accountid, 5));
						System.out.println("");
						
						
					}
					else if (report==2)
					{
						while(true)
						{
							System.out.println("Please enter the from date (mm/dd/yyyy):");
						
							fromdate=sc.next();
						
							if(bd.validdate(fromdate)==false)
							{
								System.out.println("The date is invalid");
							
							}
							else
							{
								break;
							}
						}
						while (true)
						{
							System.out.println("Please enter the to date (mm/dd/yyyy):");
						
							todate=sc.next();
							
							if(bd.validdate(todate)==false)
							{
								System.out.println("The date is invalid");
							
							}
							else
							{
								break;
							}			
						
						}
						List<String> mytransbetweendate=new ArrayList();
						
						mytransbetweendate=bd.searchRecordsBetweenDate(fromdate, todate, accountid);
						
						System.out.println(bd.printlist(mytransbetweendate, bd.getCol()));
						
					}
					else
					{
						System.out.println("You input wrong number.");
					}
					
					
				}
				else 
				{			
				System.out.println("Enter the amount: ");
				String amount = sc.next();
				System.out.println("Enter the date of the check: ");
				String date = sc.next();
				int transactiontypeid = bd.getTransactionTypeID(transname);	
				
				bd.createTransaction(accountid, transactiontypeid, amount, date);
				
				//System.out.println("Your balance is "+Double.toString(bd.getbalance(accountid)));
				
				if (bd.getbalance(accountid) < 0)
				{
					System.out.println("Your balance is negative now!");
					String accounttype=bd.getAccountType(accountid);
					if(accounttype.equals("Checking"))
					{
						String holdername=bd.getHoldername(accountid);
						bd.createTransaction(accountid, FEE, "35.00", date);
						System.out.println("FEE: You were charged a $35 overdraft fee on your checking account!");
						
						if(bd.getSavingAccountidbyHoldername(holdername)!=0)
						{
							System.out.println("Your balance for account#: "+accountnumber+"is "+bd.getbalance(accountid));
							System.out.println("We will transfer your money from your savings account to your checking account");			
							String withdrawlfromsaving=Double.toString(bd.getbalance(accountid));
							double positiveamount=0-bd.getbalance(accountid);
							String depositepositiveamount=Double.toString(positiveamount);	
							bd.createTransaction(bd.getSavingAccountidbyHoldername(holdername), 3, depositepositiveamount, date);	
							bd.createTransaction(accountid, 4, depositepositiveamount, date);
							bd.createTransaction(bd.getSavingAccountidbyHoldername(holdername), FEE, "15.00", date);
							System.out.println("FEE: You were charged a instant transfer fee on your savings account! ");
							String savingaccountnumber=bd.getAccountNumber(bd.getSavingAccountidbyHoldername(holdername));
							System.out.println("Your withdrawal amount from your saving account: "+savingaccountnumber+"is "+positiveamount);
							System.out.println("Your balance of account#: "+bd.getAccountNumber(accountid)+" is "+bd.getbalance(accountid));
							System.out.println("Your balance of account#: "+savingaccountnumber+" is "+bd.getbalance(bd.getAccountID(savingaccountnumber)));				
						}
						else
						{
							
							continue;
						}
					}
					else
					{
						bd.createTransaction(accountid, FEE, "35.00", date);
						System.out.println("FEE: You were charged a $35 overdraft fee on your savings account");
					}		
					
				}
				else
				{
					continue;
				}
				
				
			}
			
		}
		}
		
		}
		
		
	}

}
