package disburse.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import disburse.vo.HouseDisburseDetail;


@Repository
public class JdbcHouseDisburseDAO {
	@AutoWired
	private DataSource dataSource; 
	
	
	/*public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	} */
	
	public List<HouseDisburseDetail> selectQuery(String query) {
	
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<HouseDisburseDetail> hddList = new ArrayList<HouseDisburseDetail>();

		try {
			conn = dataSource.getConnection();
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();

			while (rs.next()) {
				HouseDisburseDetail hdd = new HouseDisburseDetail();
				hdd.setBIOGUIDE_ID(rs.getString("BIOGUIDE_ID"));
				hdd.setOFFICE(rs.getString("OFFICE"));
				hdd.setPURPOSE(rs.getString("PURPOSE"));
				hdd.setPAYEE(rs.getString("PAYEE"));
				hdd.setAMOUNT(Double.parseDouble(rs.getString("AMOUNT")));
				hddList.add(hdd);
			}
			if (hddList.isEmpty()) {
				// no rows returned - throw an empty result exception
				throw new EmptyResultDataAccessException(1);
			}

		} catch (SQLException sqle) {
			throw new RuntimeException("SQL exception occurred while selecting House Disburse Detail - ", sqle);
		} finally {
			if (rs != null) {
				try {
					// Close to prevent database cursor exhaustion
					rs.close();
				} catch (Exception e) {

				}
			}
			if (conn != null) {
				try {
					// Close to prevent database connection exhaustion
					conn.close();
				} catch (SQLException ex) {
				}
			}
		}
		return hddList;
	}
	
	
}