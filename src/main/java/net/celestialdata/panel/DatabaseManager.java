package net.celestialdata.panel;

import java.sql.*;

public class DatabaseManager {

    /**
     * Create a connection to the settings database
     * @return Database connection
     */
    private static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:settings.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Get the IP address of the panel.
     * @return The panels IP address.
     */
    public static String getApiServerIP() {
        String sql = "SELECT setting_value FROM Settings WHERE setting_name = 'api_ip_address'";
        String ip = "";

        try(ResultSet result = getConnection().createStatement().executeQuery(sql)) {
            while (result.next()) {
                ip = result.getString("setting_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ip;
    }

    /**
     * Update the IP address of the panel.
     * @param ip The new IP address.
     */
    public static void setApiServerIP(String ip) {
        String sql = "UPDATE Settings SET setting_value = ? WHERE setting_name = 'api_ip_address'";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, ip);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getSSID() {
        String sql = "SELECT setting_value FROM Settings WHERE setting_name = 'ssid_name'";
        String ssid = "";

        try(ResultSet result = getConnection().createStatement().executeQuery(sql)) {
            while (result.next()) {
                ssid = result.getString("setting_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ssid;
    }

    public static void setSSIDPassword(String password) {
        String sql = "UPDATE Settings SET setting_value = ? WHERE setting_name = 'ssid_password'";

        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getSSIDPassword() {
        String sql = "SELECT setting_value FROM Settings WHERE setting_name = 'ssid_password'";
        String password = "";

        try(ResultSet result = getConnection().createStatement().executeQuery(sql)) {
            while (result.next()) {
                password = result.getString("setting_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return password;
    }

    public static void setSSID(String ssid) {
        String sql = "UPDATE Settings SET setting_value = ? WHERE setting_name = 'ssid_name'";

        try(PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, ssid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
