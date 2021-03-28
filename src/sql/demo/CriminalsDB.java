package sql.demo;

import java.sql.*;
import java.util.Scanner;

public class CriminalsDB {
        private static final String UserName = "root";
        private static final String Password = "root";
        private static final String URL = "jdbc:mysql://localhost:3306/criminalbase";
        private static final String DB_DRIVER = "lib/h2-1.4.200.jar";
        public static Statement statement;
        public static Connection connection;


        /*private static void createDbUserTable() throws SQLException {
            Connection dbConnection = null;
            Statement statement = null;

            String createTableSQL = "CREATE TABLE Criminalbase("
                    + "USER_ID NUMBER(5) NOT NULL, "
                    + "user_name VARCHAR(20) NOT NULL, "
                    + "CREATED_BY VARCHAR(20) NOT NULL, "
                    + "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
                    + ")";

            try {
                statement = dbConnection.createStatement();

                // выполнить SQL запрос
                statement.execute(createTableSQL);
                System.out.println("Table \"criminalDataBase\" is created!");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        }*/

        private static void getInfoFromDatabase() throws SQLException {

            String selectTableSQL = "SELECT id, name from Criminals";

            try {
                statement = connection.createStatement();

                // выбираем данные с БД
                ResultSet rs = statement.executeQuery(selectTableSQL);

                // И если что то было получено то цикл while сработает
                while (rs.next()) {
                String id = rs.getString("id");
                String name = rs.getString("name");

                System.out.println("userid : " + id);
                System.out.println("username : " + name);
                }
            } catch (SQLException e) {
            System.out.println(e.getLocalizedMessage());
            } finally {
                if (statement != null) {
                statement.close();
                }
            }
        }


        //Добавление записей в БД

        public static void AddingRecords (String FIO, int ages) throws SQLException
        {
            try {
                statement = connection.createStatement();
                int rows = statement.executeUpdate("INSERT Criminals(name, age) VALUES (FIO, ages)");
                System.out.printf("Added %d rows", rows);
            } catch (SQLException e) {
            e.getLocalizedMessage();
            }
            finally {
                if (statement != null)
                {statement.close();}
            }
        }

        //Удаление записей из БД

        public static void deletingRecords(String tableName, int idNum) {
            try {
                statement = connection.createStatement();
                int rows = statement.executeUpdate("DELETE FROM this.tableName WHERE Id = this.idNum");
                System.out.printf("%d row(s) deleted", rows);
            } catch (SQLException e) {
                e.getLocalizedMessage();
            }
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e){e.getLocalizedMessage();}
        }

        //Изменение столбца в БД

        public static void updatingRecords(String tableName, String columnName, char variable) throws SQLException {

            try {
                statement = connection.createStatement();
                int rows = statement.executeUpdate("UPDATE criminals SET age = age + 6");
                System.out.printf("Updated %d rows", rows);
            } catch (SQLException e) {
                e.getLocalizedMessage();
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        }

        public static void main(String[] args) {

            /*Scanner in = new Scanner(System.in);
            System.out.print("Input a nameTable: ");
            String FIO = in.nextLine();
            System.out.print("Input id:");
            int ages = in.nextInt();
            in.close();*/

            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("Where is your MySQL JDBC Driver?");
                e.printStackTrace();
                return;
            }

            try
            {
                connection = DriverManager.getConnection(URL, UserName, Password);
            } catch (SQLException e){e.getLocalizedMessage();}

            try{getInfoFromDatabase();}catch (SQLException e) {e.getLocalizedMessage();}


        }
    }


