package sql.demo;

import java.io.*;
import java.sql.*;
import java.util.Scanner;

    public class CriminalsDB {
    private static final String UserName = "root";
    private static final String Password = "root";
    private static final String URL = "jdbc:h2:C:\\JavaPrj\\dataBase_H2/criminalBase;";
    private static final String DB_DRIVER = "org.h2.Driver";
    public static Statement statement = null;
    public static Connection connection = null;

        public void readingTextFile() {
            try {
                File file = new File("/Users/prologistic/file.txt");
                FileReader fr = new FileReader(file);
                BufferedReader reader = new BufferedReader(fr);
                String userName = reader.readLine();
                String password = reader.readLine();
                String url = reader.readLine();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void createDbUserTable() throws SQLException {
            String createTableSQL = "CREATE SCHEMA myDatabase ; " ;
            try {
                // выполнить SQL запрос
                statement.execute(createTableSQL);
                System.out.println("Table \"criminalDataBase\" is created!");
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
            statement.close();
        }

        private static void dbCreation () throws SQLException {
            statement = connection.createStatement();
            try {
                statement.executeUpdate(" CREATE Database CriminalsDB ");
            } catch (SQLException e) {e.getLocalizedMessage();}

            statement.close();
        }

        private static void getInfoAboutDatabase() throws SQLException {

            Scanner in = new Scanner(System.in);
            System.out.print("Input name of Schema");
            String schemaName = in.nextLine();
            ResultSet rs = statement.executeQuery("SHOW Tables from "  + schemaName);

            try {
                statement = connection.createStatement();
                while (rs.next()) {
                    for (int i = 1; i< rs.getMetaData().getColumnCount(); i++) {
                        System.out.println(rs.getString(1));
                    }
                }
            } catch (SQLException e)
            { e.getLocalizedMessage(); }

            rs.close();
            statement.close();
        };

        private static void getInfoFromDatabase() throws SQLException {
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("Select*from Public.Criminals");
            Scanner in = new Scanner(System.in);
            System.out.print("Input a nameTable: ");
            String nameTable = in.nextLine();
            in.close();
            int id = 0;

            switch (nameTable) {
                case "criminals":
                    try {
                    while (rs.next()) {
                    for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                    System.out.println(rs.getMetaData().getColumnName(i));
                    System.out.println(rs.getString(i)); }
                    }

                } catch (SQLException e) {
                e.getLocalizedMessage();
                }break;
            }
            rs.close();
            statement.close();
        }

        //Добавление записей в БД

        public static void addingRecords () throws SQLException {
            Scanner in = new Scanner(System.in);
            System.out.print("Input a nameTable: ");
            String nameTable = in.nextLine();
            String values = null;
            String parameters = null;
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(" Select * From " + " nameTable ");

            while (rs.next()) {

                for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                    System.out.print("Input a " + rs.getMetaData().getColumnName(i));
                    values = values + in.nextLine() + " , ";
                    parameters = parameters + rs.getMetaData().getColumnName(i) + " , ";
                }
            }
                in.close();
                values.replaceFirst(".$", "");
                parameters.replaceFirst(".$", "");
                String sqlCommand = "INSERT " + nameTable + " " + "( " + parameters + " )" + " Values ( " + values + " );";
            rs = statement.executeQuery(sqlCommand);
            rs.close();
            statement.close();
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

        public static void main(String[] args) throws SQLException {

            try {
            Class.forName (DB_DRIVER);
            } catch (ClassNotFoundException e) {
            System.out.println("Where is your JDBC Driver?");
            e.printStackTrace();
            return; }

            try
            {
            connection = DriverManager.getConnection(URL, UserName, Password);
            statement = connection.createStatement();
            } catch (SQLException e)
            {e.getLocalizedMessage();
            dbCreation();
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print(
                    "Hello, what do you want to do: " +'\n'+
                    "1. What tables are contained in some schema?" +'\n'+
                    "2. Output some table" +'\n'+
                    "2. Update rows in table" +'\n'+
                    "3. Add rows in table" +'\n'+
                    "4. Delete rows in table" +'\n'+
                    "Write only a number: like 1 or 4: ");
            if (scanner.hasNextInt()) {
                int answer = scanner.nextInt();
                switch (answer) {
                    case 1:
                    try {
                    getInfoAboutDatabase();
                    } catch (SQLException e) {
                    e.getLocalizedMessage();
                    }
                    break;

                    case 2:
                    try {
                    getInfoFromDatabase();
                    } catch (SQLException e) {
                    e.getLocalizedMessage();
                    }
                    break;
                    case 3:
                    try {
                    updatingRecords();
                    } catch (SQLException e) {
                    e.getLocalizedMessage();
                    }
                    break;
                    case 4:
                    try {
                    addingRecords();
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


