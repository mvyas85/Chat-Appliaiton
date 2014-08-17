package db;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class ChatAppDataSource {
	private static String jdbcPropFile = "jdbc_config.properties";
	private static Properties jdbcProps;
	private static BasicDataSource dataSrc = null;
	public static DataSource setupDataSource() {
		if (jdbcProps == null) {
			jdbcProps = loadJdbcProperties();
		}
		
		String maxActiveStr = jdbcProps.getProperty("jdbc.maxActiveConns", "10");
        dataSrc = new BasicDataSource();
        dataSrc.setDriverClassName(getProp("jdbc.driver"));
        dataSrc.setUsername(getProp("jdbc.user"));
        dataSrc.setPassword(getProp("jdbc.dbpassword"));
        dataSrc.setUrl(getProp("jdbc.url"));
        dataSrc.setMaxActive(Integer.parseInt(maxActiveStr));
        return dataSrc;
    }
	
	public static Connection getConnection() throws SQLException {
		if (dataSrc == null) {
			setupDataSource();
		}
		return dataSrc.getConnection();
	}

	private static Properties loadJdbcProperties() {
		Properties jdbcProps = new Properties();
		InputStream jdbcPropStrm;
		
		try {
			//load a properties file from class path, inside static method
			jdbcPropStrm = ChatAppDataSource.class.getClassLoader().getResourceAsStream("jdbc_config.properties");
			jdbcProps.load(jdbcPropStrm);
		} catch (Exception ex) {
			System.out.println("Unable to load JDBC properties file:  " + jdbcPropFile + " Exception: " + ex);
		}
		
		return jdbcProps;
	}
	
	private static String getProp(String key) {
		return jdbcProps.getProperty(key);
	}
	
    public static void printDataSourceStats(DataSource dataSrc) {
    		BasicDataSource basicDataSrc = (BasicDataSource) dataSrc;
			System.out.println("NumActive: " + basicDataSrc.getNumActive());
			System.out.println("NumIdle: " + basicDataSrc.getNumIdle());
			return;
    }

    public static void shutdownDataSource() throws SQLException {
    	if (dataSrc == null) return;
    	dataSrc.close();
    }
}
