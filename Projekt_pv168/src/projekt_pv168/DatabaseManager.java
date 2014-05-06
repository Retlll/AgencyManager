/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_pv168;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.sql.DataSource;
import projekt_pv168.common.ServiceFailureException;

/**
 *
 * @author Sebastián
 */
public class DatabaseManager {
    
    private DataSource dataSource;
    
    private static final Logger logger = Logger.getLogger(ContractManagerImpl.class.getName());
    
    public DatabaseManager(DataSource dataSource) {
        this.dataSource = dataSource;
        loggerOutput();
    }
    
    public void dropTable(String tableName) throws ServiceFailureException{
        checkDataSource();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("drop table " + tableName);) {
                connection.setAutoCommit(true);
                st.executeUpdate();
            } catch (SQLException ex) {
                connection.rollback();
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        }        
    }
    
    public void clearTable(String tableName) throws ServiceFailureException{
        checkDataSource();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("delete from " + tableName);) {
                connection.setAutoCommit(true);
                st.executeUpdate();
            } catch (SQLException ex) {
                connection.rollback();
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        } 
        
    }
    
    public void createTable(String tableData) throws ServiceFailureException{
        checkDataSource();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement("create table " + tableData);) {
                connection.setAutoCommit(true);
                st.executeUpdate();
            } catch (SQLException ex) {
                connection.rollback();
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        } 
        
    }
    
    public void callSQLComand(String sqlQuery) throws ServiceFailureException{
        checkDataSource();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement st = connection.prepareStatement(sqlQuery);) {
                connection.setAutoCommit(true);
                st.setString(1, sqlQuery);
                st.executeUpdate();
            } catch (SQLException ex) {
                connection.rollback();
                logger.log(Level.SEVERE, null, ex);
                throw new ServiceFailureException("Internal error with databese - PreparedStatement", ex);
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, null, ex);
            throw new ServiceFailureException("Internal error with databese - DataSource", ex);
        } 
        
    }
    
    private void checkDataSource() throws ServiceFailureException{
        if (dataSource == null) {
            throw new ServiceFailureException("DataSource is not set");
        }
    }
    
    private void loggerOutput() {
        FileHandler fh = null;
        try {
            fh = new FileHandler("MyLogFile.log");
        } catch (IOException | SecurityException ex) {
            logger.log(Level.SEVERE, null, ex);
            return;
        }
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);
        logger.setLevel(Level.ALL);
    }
}
