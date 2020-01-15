package edu.pucmm.Handlers;

import edu.pucmm.Models.User;
import edu.pucmm.services.Users;
import org.h2.tools.Server;

import java.sql.SQLException;
import java.util.UUID;

public class DB {
    public static void startDb() throws SQLException {
        Server.createTcpServer("-tcpPort",
                "9092",
                "-tcpAllowOthers",
                "-tcpDaemon").start();
    }

    public static void stopDb() throws SQLException {
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }

    public static void initDatabase() throws SQLException {
        Users.getInstance().create(new User(UUID.randomUUID().toString(), "admin","Admin","admin","admin"));
    }
}
