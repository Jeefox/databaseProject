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


        public static void createDbUserTable() throws SQLException {

            Scanner in = new Scanner(System.in);
            System.out.print("Input a nameTable: ");
            String nameTable = in.nextLine();
            in.close();

            String createTableSQL = "CREATE TABLE "+ nameTable
                    + "USER_ID NUMBER(5) NOT NULL, "
                    + "user_name VARCHAR(20) NOT NULL, "
                    + "CREATED_BY VARCHAR(20) NOT NULL, "
                    + "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
                    + ")";

            try {
                statement = connection.createStatement();

                // выполнить SQL запрос
                statement.execute(createTableSQL);
                System.out.println("Table \"criminalDataBase\" is created!");
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            } finally {
                if (statement != null) {
                    statement.close();
                }
            }
        }

        private static void getInfoFromDatabase() throws SQLException {

            Scanner in = new Scanner(System.in);
            System.out.print("Input a nameTable: ");
            String nameTable = in.nextLine();
            in.close();

            String selectTableSQL = "SELECT * FROM " + nameTable;

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

        public static void addingRecords () throws SQLException
        {
            Scanner in = new Scanner(System.in);
            System.out.print("Input a nameTable: ");
            String nameTable = in.nextLine();
            System.out.print("Input a FIO: ");
            String FIO = in.nextLine();
            System.out.print("Input ages: ");
            int ages = in.nextInt();
            in.close();

            String sqlCommand = "INSERT " + nameTable + " (name , age) Values (" + FIO + " , " + ages + ");";

            try {
                statement = connection.createStatement();
                statement.executeUpdate (sqlCommand);
            } catch (SQLException e) {
            e.getLocalizedMessage();
            }
            finally {
                if (statement != null)
                {statement.close();}
            }
        }

        //Удаление записей из БД

        public static void deletingRecords() throws SQLException {
            Scanner in = new Scanner(System.in);
            System.out.print("Input a nameTable: ");
            String nameTable = in.toString();
            System.out.print("Input id: ");
            int idNum = in.nextInt();
            in.close();
            try {
                statement = connection.createStatement();
                int rows = statement.executeUpdate("DELETE FROM " + nameTable + " Where Id = " + idNum);
                System.out.printf("%d row(s) deleted", rows);
            } catch (SQLException e) {
                e.getLocalizedMessage();
            }
            finally {
                if (statement != null) {
                    statement.close();
                }
            }
        }

        //Изменение столбца в БД

        public static void updatingRecords() throws SQLException {

            Scanner in = new Scanner(System.in);
            System.out.print("Input a nameTable: ");
            String nameTable = in.nextLine();
            System.out.print("Input name of column: ");
            String column = in.nextLine();
            System.out.print("Input a new line: ");
            String newRow = in.nextLine();
            System.out.print("Input id of a row:");
            if (in.hasNextInt()) {
                int idNum = in.nextInt();
                try {
                statement = connection.createStatement();
                int rows = statement.executeUpdate("UPDATE "+ nameTable+ " SET " + column + "=" + newRow + " Where id = " + idNum);
                System.out.printf("Updated %d rows", rows);
                } catch (SQLException e) {
                e.getLocalizedMessage();
            } finally {
                    if (statement != null) { statement.close(); }}
            }else { System.out.print("You wrote not a id");
            in.close();
            }
        }

        public static void main(String[] args) {

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

            Scanner scanner = new Scanner(System.in);
            System.out.print(
                    "Hello, what do you want to do: " +'\n'+
                    "1. Get Info about some table" +'\n'+
                    "2. Create some table" +'\n'+
                    "3. Add rows in table" +'\n'+
                    "4. Update rows in table" +'\n'+
                    "5. Delete rows in table" +'\n'+
                    "Write only a number: like 1 or 4: ");
            if (scanner.hasNextInt()) {
                int answer = scanner.nextInt();
                switch (answer) {
                    case 1:
                    try {
                    getInfoFromDatabase();
                    } catch (SQLException e) {
                    e.getLocalizedMessage();
                    }
                    break;
                    case 2:
                    try {
                    createDbUserTable();
                    } catch (SQLException e) {
                    e.getLocalizedMessage();
                    }
                    break;
                    case 3:
                    try {
                    addingRecords();
                    } catch (SQLException e) {
                    e.getLocalizedMessage();
                    }
                    break;
                    case 4:
                    try {
                    updatingRecords();
                    } catch (SQLException e) {
                    e.getLocalizedMessage();
                    }
                    break;
                    case 5:
                    try {
                    deletingRecords();
                    } catch (SQLException e) {
                    e.getLocalizedMessage();
                    }
                    break;
                }

            } else {
            System.out.print("You Wrote not a number");}

        }
    }


