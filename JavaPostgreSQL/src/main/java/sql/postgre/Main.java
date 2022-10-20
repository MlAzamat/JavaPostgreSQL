package sql.postgre;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class Main {
    //  Database credentials
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
    static final String USER = "postgres";
    static final String PASS = "1234";

    public static void main(String[] args) {




            System.out.println("Testing connection to PostgreSQL JDBC");

            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
                e.printStackTrace();
                return;
            }

            System.out.println("PostgreSQL JDBC Driver successfully connected");
            Connection connection = null;

            try {
                connection = DriverManager
                        .getConnection(DB_URL, USER, PASS);

                // INSERT - TABLE METRICS
                long current_time;
                int value_metric = 1;
                int randomInt = 127;

                int count_iteration = 100000; // ----======== УКАЗАТЬ КОЛИЧЕСТВО СТРОК В БД
                int time_sleep = 170; // ----======== УКАЗАТЬ ВРЕМЯ ЗАДЕРЖКИ В МИЛСЕК

                for (int i = 0; i < count_iteration; i++) {
                    current_time = System.currentTimeMillis() / 1000;

                    System.out.println("number_iteration = " + i);
                    System.out.println("current_time = " + current_time);

                    if (value_metric < randomInt) {
                        value_metric++;
                    } else {
                        randomInt = generateRandomIntIntRange(7, 222);
                        System.out.println("randomInt = " + randomInt);
                        value_metric = generateRandomIntIntRange(0, 17);
                    }

                    System.out.println("value_metric = " + value_metric);

                    PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO metrics" +
                            "  (time, value) VALUES " + " (" + current_time + ", " + value_metric + ");");
                    System.out.println(preparedStatement);
                    preparedStatement.executeUpdate();

                    Thread.sleep(time_sleep);

                }
                // END INSERT




            } catch (SQLException e) {
                System.out.println("Connection Failed");
                e.printStackTrace();
                return;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        if (connection != null) {
                System.out.println("You successfully connected to database now");
            } else {
                System.out.println("Failed to make connection to database");
            }
        }
    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}