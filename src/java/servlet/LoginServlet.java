/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import user.UserDAO;
import utils.VerifyRecaptcha;

/**
 *
 * @author IT
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final Logger LOGGER = Logger.getLogger(LoginServlet.class);
    private final String INVALID = "Invalid";
    private final String SEARCH = "/J3.L.P0015/";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = INVALID;
        /* TODO output your page here. You may use following sample code. */
        String id = request.getParameter("userid");
        String password = request.getParameter("password");

        try {
            /* TODO output your page here. You may use following sample code. */
            UserDAO dao = new UserDAO();
            String name = dao.checkLogin(id, password);
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            if (name != null & verify == true) {
                url = SEARCH;
                HttpSession session = request.getSession();
                session.setAttribute("NAME", name);
                session.setAttribute("ID", id);
                boolean role = dao.checkAdmin(id);
                if (role == true) {
                    String stringrole = "customer";
                    session.setAttribute("ROLE", stringrole);
                } else {
                    String stringrole = "admin";
                    session.setAttribute("ROLE", stringrole);
                }
            }
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());       
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            response.sendRedirect(url);
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
