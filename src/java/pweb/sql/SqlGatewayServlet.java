package pweb.sql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SqlGatewayServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String sqlStatement = request.getParameter("sqlStatement");
        String sqlResult = "";
        
        try 
        {                        
            // cargar el controlador
            Class.forName("org.sqlite.JDBC");
            
            // crear una conexion             
            String dbURL = "jdbc:sqlite:C:/sqlite3/MyDB.db";
            Connection connection = DriverManager.getConnection(dbURL);

            // crear una sentencia 
            Statement statement = connection.createStatement();
            
            // transformar la cadena SQL
            sqlStatement = sqlStatement.trim();
            
            if (sqlStatement.length() >= 6) 
            {
                // Obteniendo tipo de sentencia 
                String sqlType = sqlStatement.substring(0, 6);
                
                if (sqlType.equalsIgnoreCase("select")) 
                {
                    // crear el HTML a partir del conjunto de resultados (resultSet)
                    ResultSet resultSet = statement.executeQuery(sqlStatement);
                    sqlResult = SqlUtil.getHtmlTable(resultSet);
                    resultSet.close();
                } 
                else 
                {
                    int i = statement.executeUpdate(sqlStatement);
                    
                    if (i == 0) 
                    { 
                        // una sentencia DDL
                        sqlResult = "<p>La sentencia fue ejecutada satisfactoriamente.</p>";
                    } 
                    else 
                    { 
                        // una sentecia INSERT, UPDATE, o DELETE 
                        sqlResult = "<p>La sentencia fue ejecutada satisfactoriamente.<br>" + i + " fila(s) afectada(s).</p>";
                    }
                }
            }
            statement.close();
            connection.close();
            
        } 
        catch (ClassNotFoundException e) 
        {
            sqlResult = "<p>Error al cargar el controlador de base de datos: <br>" + e.getMessage() + "</p>";
        } 
        catch (SQLException e) 
        {
            sqlResult = "<p>Error al ejectuar la sentencia SQL: <br>" + e.getMessage() + "</p>";
        }

        request.setAttribute("sqlResult", sqlResult);
        request.setAttribute("sqlStatement", sqlStatement);
        
        String url = "/index.jsp";        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}