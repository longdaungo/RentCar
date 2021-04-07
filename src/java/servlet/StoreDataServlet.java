/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import bill.BillDAO;
import cart.CartObject;
import discount.DiscountDAO;
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

/**
 *
 * @author IT
 */
@WebServlet(name = "StoreDataServlet", urlPatterns = {"/StoreDataServlet"})
public class StoreDataServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CreateAccountServlet.class);
    private final String ERROR = "Error";
    private final String VIEWCART = "ViewCart";
    private final String SEARCH = "";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext appScope = request.getServletContext();
        Map<String, String> listResource = (Map<String, String>) appScope.getAttribute("LISTRESOURCE");
        String url = ERROR;
        String stringtotal = request.getParameter("total");
        String discountID = request.getParameter("discoutID");
        int total = (int)Float.parseFloat(stringtotal);
        try {
            /* TODO output your page here. You may use following sample code. */
            HttpSession session = request.getSession();
            CartObject cart = (CartObject) session.getAttribute("CART");
            String email= (String)session.getAttribute("ID");
            cart.checkQuantityCars();
            Date date = new Date();
            SimpleDateFormat fortmatter = new SimpleDateFormat("yyyy-MM-dd");
            String currentdate = fortmatter.format(date);
            BillDAO billDAO = new BillDAO();
            int rows = billDAO.countBills();
            int idBill = ++rows;
            String stringidBill = String.valueOf(idBill);
            if(!discountID.equals("")){
                DiscountDAO discountdao = new DiscountDAO();
                billDAO.insertBillWithDiscount(stringidBill, total, currentdate, email,discountID);
                discountdao.updateStatus(discountID);
            }
            else
                billDAO.insertBillWithoutDiscount(stringidBill, total, currentdate, email);
            ///insert detailBill
            cart.insertDetailBill(stringidBill);
            url = SEARCH;
            request.setAttribute("NOTIFICATION", "Rent is succesfully");
            session.removeAttribute("CART");
        }
        catch(SQLException ex){
         
           LOGGER.error(ex.getMessage());
        }
        catch(NamingException ex){
            LOGGER.error(ex.getMessage());
        }
        catch(Exception ex){
            LOGGER.error(ex.getMessage());
            request.setAttribute("ERROR", ex.getMessage());
            url = VIEWCART;
        }
        finally{
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
