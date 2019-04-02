import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import disburse.dao.JdbcHouseDisburseDAO;

public class DisburseApp {
	
	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("HouseDisburseConfig.xml");
		JdbcHouseDisburseDAO hdDAO = (JdbcHouseDisburseDAO)ctx.getBean("jdbcHouseDisburseDAO");
		List<HouseDisburseDetail> hddList = hdDAO.selectQuery
				("Select BIOGUIDE_ID, OFFICE, PURPOSE, PAYEE, AMOUNT from T_2013Q4_HOUSE_DISBURSE LIMIT 1");
		for (HouseDisburseDetail hdd : hddList) {
			System.out.println(hdd.getBIOGUIDE_ID() + "\t" + hdd.getOFFICE() + "\t" + hdd.getAMOUNT());
		}
	}
} 

