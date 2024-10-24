package view;

import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

import javax.sql.rowset.*;

import util.*;

/**
 * This program uses metadata to display arbitrary tables in a database.
 * @version 1.0 2023-08-16
 * @author Cay Horstmann
 */
public class ViewDB2
{
   private CachedRowSet crs;
   private Scanner console;

   record Column(String label, int width, String columnClass) {}
   private List<Column> columns = new ArrayList<>();
   
   public static void main(String[] args)
   {
      new ViewDB2().run();
   }
   
   public void run()
   {
      try (Connection conn = getConnection(readDatabaseProperties()))
      {
         console = new Scanner(System.in);
         DatabaseMetaData meta = conn.getMetaData();
         var tableNames = new ArrayList<String>();

         try (ResultSet mrs = meta.getTables(null, null, null, new String[] { "TABLE" }))
         {
            while (mrs.next())
               tableNames.add(mrs.getString(3));
         }
         System.out.println("Choose a table");
         String selected = Choices.choose(console, tableNames);
         showTable(selected, conn);
         executeCommands();         
         crs.acceptChanges(conn);
      }
      catch (SQLException e)
      {
         for (Throwable t : e)
            t.printStackTrace();
      }      
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }      
      
   /**
    * Prepares the columns list for showing a new table, and shows the first row.
    * @param tableName the name of the table to display
    * @param conn the database connection
    */
   public void showTable(String tableName, Connection conn) throws SQLException
   {
      try (Statement stat = conn.createStatement();
            ResultSet result = stat.executeQuery("SELECT * FROM " + tableName))
      {
         // copy into cached row set
         RowSetFactory factory = RowSetProvider.newFactory();            
         crs = factory.createCachedRowSet();
         crs.setTableName(tableName);
         crs.populate(result);
         
         ResultSetMetaData rsmd = result.getMetaData();
         for (int i = 1; i <= rsmd.getColumnCount(); i++)
         {
            columns.add(new Column(rsmd.getColumnLabel(i), 
                  rsmd.getColumnDisplaySize(i), 
                  rsmd.getColumnClassName(i)));
         }
         
         showNextRow();
      }
   }

   public void executeCommands() throws SQLException
   {
      while (true)
      {
         String selection 
            = Choices.choose(console, "Next", "Previous", "Edit", "Delete", "Quit");
         switch (selection)
         {
            case "Next" -> showNextRow();
            case "Previous" -> showPreviousRow();
            case "Edit" -> editRow();
            case "Delete" -> deleteRow();
            default -> { return; }
         }
      }
   }

   /**
    * Shows a database row by populating all text fields with the column values.
    */
   public void showRow(ResultSet rs) throws SQLException
   {
      for (int i = 1; i <= columns.size(); i++)
      {
         String format = "%%s [%%%ds]%%n".formatted(columns.get(i - 1).width());
         System.out.printf(format, columns.get(i - 1).label(), 
            rs == null ? "" : rs.getString(i));
      }
   }
   
   /**
    * Moves to the previous table row.
    */
   public void showPreviousRow() throws SQLException
   {
      if (crs == null || crs.isFirst()) return;
      crs.previous();
      showRow(crs);
   }

   /**
    * Moves to the next table row.
    */
   public void showNextRow() throws SQLException
   {
      if (crs == null || crs.isLast()) return;
      crs.next();
      showRow(crs);
   }

   /**
    * Deletes current table row.
    */
   public void deleteRow() throws SQLException 
   {
      if (crs == null) return;
      crs.deleteRow();
      if (crs.isAfterLast())
      {         
         if (!crs.last()) crs = null;
      }
      showRow(crs);
   }

   /**
    * Updates changed data into the current row of the row set.
    */
   public void editRow() throws SQLException
   {
      List<String> editableColumns = columns.stream()
            .filter(c -> c.columnClass.equals("java.lang.String"))
            .map(Column::label)
            .toList();
      System.out.println("Choose column");
      String label = Choices.choose(console, editableColumns);
      System.out.print("New value:  ");
      String newValue = console.nextLine();
      crs.updateString(label, newValue);
      crs.updateRow();
   }
   
   private Properties readDatabaseProperties() throws IOException
   {
      var props = new Properties();
      try (Reader in = Files.newBufferedReader(Path.of("database.properties")))
      {
         props.load(in);
      }
      String drivers = props.getProperty("jdbc.drivers");
      if (drivers != null) System.setProperty("jdbc.drivers", drivers);
      return props;
   }
   
   /**
    * Gets a connection from the properties specified in the file database.properties.
    * @return the database connection
    */
   private Connection getConnection(Properties props) throws SQLException
   {
      String url = props.getProperty("jdbc.url");
      String username = props.getProperty("jdbc.username");
      String password = props.getProperty("jdbc.password");

      return DriverManager.getConnection(url, username, password);
   }
}