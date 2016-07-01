import static org.junit.Assert.*;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import DaltonBankApplication.BankDatabase;

public class Test {

	@org.junit.Test
	public void test() throws SQLException, ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		//GregorianCalendar gc = new GregorianCalendar();
		    String d="12/12/2014";
		try{	 
		     java.util.Date da = sdf.parse(d);
		     
		  }
		  catch(Exception e){
			  
			  System.out.println("bad");
		  }
		 
		
		
		    
	}

}
