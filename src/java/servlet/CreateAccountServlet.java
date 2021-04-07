/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import user.UserDAO;

/**
 *
 * @author IT
 */
@WebServlet(name = "CreateAccountServlet", urlPatterns = {"/CreateAccountServlet"})
public class CreateAccountServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final Logger LOGGER = Logger.getLogger(CreateAccountServlet.class);
    private final String ERROR = "Error";
    private final String LOGIN = "ViewLogin";
    private final String VIEWVERIFYCODE = "ViewVerifyEmail";
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String name = request.getParameter("txtName");
        String phone = request.getParameter("txtPhone");
        String address = request.getParameter("txtAddress");
        String inputCode = request.getParameter("code");
        ServletContext appScope = request.getServletContext();
        Map<String, String> listResource = (Map<String, String>) appScope.getAttribute("LISTRESOURCE");
        String url = ERROR;
        try {

            HttpSession session = request.getSession();
            String code = (String) session.getAttribute("CODE");
            if (!code.equals(inputCode)) {
                request.setAttribute("NOTIFICATION", "This code is not correct");
                url = VIEWVERIFYCODE;
            } else {
                /* TODO output your page here. You may use following sample code. */
                Date currentDate = new Date();
                SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String stringcurrentDate = pattern.format(currentDate);
                UserDAO dao = new UserDAO();
                dao.insertUser(email, password, true, name, true, phone, address, stringcurrentDate);
                url = LOGIN;
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(listResource.get(url));
            rd.forward(request, response);
            out.close();
        }
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
