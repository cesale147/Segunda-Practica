package pweb.libros;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pweb.business.Libro;
import pweb.data.LibroDB;

public class LibrosServlet extends HttpServlet {

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
        
        String url = "";            
        String message = "";
        
        // reuperar accion actual 
        String action = request.getParameter("action");
        if (action == null) 
        {
            url = "/index.jsp";
            action = "";  
        }

        // realizar accion y establecer la URL a una pagina apropiada
        if (action.equals("login")) 
        {
            // obteniendo parametros
            String user = request.getParameter("user");        
            String pass  = request.getParameter("pass");    
            
            if (pweb.data.LoginDB.Validar(user, pass)) 
            {
                ArrayList<Libro> listaLibros;
                
                ServletContext sc = getServletContext();
                String path = sc.getRealPath("/WEB-INF/libros.txt");
                
                listaLibros = LibroDB.getLibros(path);

                request.setAttribute("listaLibros", listaLibros);
                
                url = "/libros.jsp";            
            }
            else
            {
                url = "/index.jsp";            
                message = "Usuario o contraseña incorrecta, por favor ingrese de nuevo.";                        
                request.setAttribute("message", message);
            }            
        }
        else if(action.equals("comprar"))
        {
            String codigo = request.getParameter("codigo");
            request.setAttribute("codigo", codigo);            
            url = "/pago.jsp";
        }
        else if(action.equals("pagar"))
        {            
            String codigo = request.getParameter("codigo");                                   
            String nombres = request.getParameter("nombres");
            String numtarjeta = request.getParameter("numtarjeta");
            
            ServletContext sc = getServletContext();
            String path = sc.getRealPath("/WEB-INF/libros.txt");                                       
            Libro libro = LibroDB.getLibro(codigo, path);            
            
            request.setAttribute("libro", libro);
            request.setAttribute("nombres", nombres);
            request.setAttribute("numtarjeta", "************" + numtarjeta.substring(12));
            
            url = "/confirmacion.jsp";                        
        }        
        
        // forward request and response objects to specified URL
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
