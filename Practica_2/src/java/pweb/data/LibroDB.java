package pweb.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import pweb.business.Libro;

public class LibroDB {
    
    public static Libro getLibro(String codigo) 
    {
        Connection connection = null;
        PreparedStatement ps = null;                     
        ResultSet rs = null;
        
        try 
        {            
            // cargar el controlador
            Class.forName("org.sqlite.JDBC");
            
            // crear una conexion                         
            String dbURL = "jdbc:sqlite:D:/sqlite3/ExamPar.db";
            connection = DriverManager.getConnection(dbURL); 
            
            String query = "SELECT * FROM LIBRO";
            
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            
            Libro libro = null;
            
            if (rs.next()) 
            {
                if(codigo.equals(libro.getCodigo())){
                libro = new Libro();
                libro.setCodigo(rs.getString("COD_LIB"));
                libro.setTitulo(rs.getString("TITULO"));
                libro.setAutor(rs.getString("AUTOR"));
                libro.setGenero(rs.getString("GENERO"));
                libro.setPrecio(rs.getDouble("PRECIO"));
                }
            }
            return libro;
        }
        catch (ClassNotFoundException e) {
            System.out.println(e);
            return null;
        }  
        catch (SQLException e) {
            System.out.println(e);
            return null;
        } 
        finally {
            DBUtill.closeResultSet(rs);
            DBUtill.closePreparedStatement(ps);
            DBUtill.closeConnection(connection);  
        }
    }
    
    public static String getHtmlTable(ResultSet results) throws SQLException 
    {        
        StringBuilder htmlTable = new StringBuilder();
        ResultSetMetaData metaData = results.getMetaData();
        int columnCount = metaData.getColumnCount();

        htmlTable.append("<table>");

        // agregar fila de titulos 
        htmlTable.append("<tr>");
        for (int i = 1; i <= columnCount; i++) 
        {
            htmlTable.append("<th>");
            htmlTable.append(metaData.getColumnName(i));
            htmlTable.append("</th>");
        }
        htmlTable.append("</tr>");

        // agregar otras filas
        while (results.next()) 
        {
            htmlTable.append("<tr>");
            for (int i = 1; i <= columnCount; i++) {
                htmlTable.append("<td>");
                htmlTable.append(results.getString(i));
                htmlTable.append("</td>");
            }
            htmlTable.append("</tr>");
        }

        htmlTable.append("</table>");
        return htmlTable.toString();
    }
    
}
