package ua.nure.bratchun.summary_task4.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

import ua.nure.bratchun.summary_task4.exception.DBException;
import ua.nure.bratchun.summary_task4.exception.Messages;

/**
 * DB manager. Only the required DAO methods are defined!
 * 
 * @author D.Bratchun
 *
 */
public abstract class AbstractDAO {
	
	// Use JNDI flag
	protected boolean isUseJNDI;
	
	private static final Logger LOG = Logger.getLogger(AbstractDAO.class);
	
	protected DataSource ds;
	
	/**
	 * Constructor without parameters use JNDI
	 */
	protected AbstractDAO() throws DBException {
		this(true);
	}
	/**
	 * Constructor with the ability to disable JNDI for Junit
	 * @param isUseJNDI
	 */
	protected AbstractDAO(boolean isUseJNDI) throws DBException {
		// ST4DB - the name of data source
		this.isUseJNDI = isUseJNDI;
		if(isUseJNDI) {
			try {
				Context initContext = new InitialContext();
				Context envContext = (Context) initContext.lookup("java:/comp/env");
				// ST4DB - the name of data source
				ds = (DataSource) envContext.lookup("jdbc/ST4DB");
				LOG.trace("Data source ==> " + ds);
			} catch (NamingException ex) {
				LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, ex);
			}
		}else {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				LOG.error(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_DATA_SOURCE, e);
			}
			MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
			dataSource
			.setURL("jdbc:mysql://localhost:3306/summary_task4");
			dataSource.setUser("junit");
			dataSource.setPassword("junit");
			ds = dataSource;
		}
		LOG.trace("Data source ==> " + ds);
	}
	
	public boolean isUseJNDI() {
		return isUseJNDI;
	}

	public void setUseJNDI(boolean isUseJNDI) {
		this.isUseJNDI = isUseJNDI;
	}
	/**
	 * Connection to BD
	 */
	public Connection getConnection() throws DBException{
		Connection conn = null;
			try {
				conn = ds.getConnection();
			} catch (SQLException e) {
				LOG.error(Messages.ERR_CANNOT_OBTAIN_CONNECTION,e);
				throw new DBException(Messages.ERR_CANNOT_OBTAIN_CONNECTION, e);
			}
		return conn;
	}
	
	/**
	 * Close a connection object
	 */
	protected void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			LOG.error(Messages.ERR_CANNOT_CLOSE_CONNECTION, e);
		}
	}

	/**
	 * Closes a statement object.
	 */
	protected void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_STATEMENT, ex);
			}
		}
	}

	/**
	 * Closes a result set object.
	 */
	protected void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
				LOG.error(Messages.ERR_CANNOT_CLOSE_RESULTSET, ex);
			}
		}
	}

	/**
	 * Closes resources.
	 */
	protected void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
		close(rs);
		close(pstmt);
		close(con);
		
	}
	/**
	 * Rollback changes
	 */
	protected void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException ex) {
				LOG.error("Cannot rollback transaction", ex);
			}
		}
	}
	
}
