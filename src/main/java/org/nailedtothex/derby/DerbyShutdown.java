package org.nailedtothex.derby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DerbyShutdown implements DerbyShutdownMBean {
    private static final Logger log = Logger.getLogger(DerbyShutdown.class.getName());

    public void create() throws Exception {
        log.info("create DerbyShutdown MBean");
    }

    public void start() throws Exception {
    }

    public void stop() throws Exception {
    }

    public void destroy() throws Exception {
        shutdown("jdbc:derby:;shutdown=true");
    }

    private void shutdown(String url) {
        Connection cn = null;
        try {
            cn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            if ("XJ015".equals(e.getSQLState())) {
                log.log(Level.INFO, "Derby shutdown succeeded. SQLState={0}", e.getSQLState());
                return;
            }
            log.log(Level.SEVERE, "Derby shutdown failed", e);
        } finally {
            if (cn != null) {
                try {
                    cn.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
