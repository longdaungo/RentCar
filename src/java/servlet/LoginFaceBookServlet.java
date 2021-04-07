/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.restfb.types.User;
import common.RestFB;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.naming.NamingException;
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
@WebServlet(name = "LoginFaceBookServlet", urlPatterns = {"/LoginFaceBookServlet"})
public class LoginFaceBookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String VIEWLOGIN = "ViewLogin";
    private final String SERCH = "/J3.L.P0015/";
    private static final Logger LOGGER = Logger.getLogger(LoginFaceBookServlet.class);
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = VIEWLOGIN;
        String code = request.getParameter("code");
        try {

            /* TODO output your page here. You may use following sample code. */
            if (code == null || code.isEmpty()) {
                url = VIEWLOGIN;
            } else {
                String accessToken = RestFB.getToken(code);
                HttpSession session = request.getSession();
                User user = RestFB.getUserInfo(accessToken);
                session.setAttribute("ID", user.getId());
                session.setAttribute("NAME", user.getName());
                String stringrole = "customer";
                session.setAttribute("ROLE", stringrole);
                UserDAO dao = new UserDAO();
                boolean rs = dao.getUser(user.getId());
                if (rs == false) {
                    Date currentDate = new Date();
                    SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String stringcurrentDate = pattern.format(currentDate);
                    dao.insertUserByFaceBook(user.getId(), true, user.getName(), true, stringcurrentDate);
                }
                url = SERCH;
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } catch (NamingException ex) {
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
