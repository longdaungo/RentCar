/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import bill.BillDAO;
import bill.BillDTO;
import car.CarDAO;
import detailbill.DetailBillDAO;
import detailbill.DetailBillDTO;
import discount.DiscountDAO;
import discount.DiscountDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

/**
 *
 * @author IT
 */
@WebServlet(name = "TakeOverServlet", urlPatterns = {"/TakeOverServlet"})
public class TakeOverServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final Logger LOGGER = Logger.getLogger(TakeOverServlet.class);
    private final String ERROR = "Error";
    private final String VIEWHISTORY = "VIEWHISTORY";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("searchName");
        if(name == null){
            name = "";
        }
        String date = request.getParameter("searchDate");
        if(date == null){
            date = "";
        }
        ServletContext appScope = request.getServletContext();
        Map<String, String> listResource = (Map<String, String>) appScope.getAttribute("LISTRESOURCE");
        String url = ERROR;
        try {
            /* TODO output your page here. You may use following sample code. */
           HttpSession session = request.getSession();
           String email= (String) session.getAttribute("ID");
           BillDAO billDAO = new BillDAO();
           List<BillDTO> listBills = billDAO.takeOverHistory(email,date);
           
           //getDetailBill
           DetailBillDAO detailBill = new DetailBillDAO();
           Map<String,List<DetailBillDTO>> map = detailBill.getMapDetailBill(listBills);
           
           //replace idCar by Name
           CarDAO carDAO = new CarDAO();
           map = carDAO.replaceCarIDbyCarName(map,name);
           
           //filterbill
            listBills = filterBillByName(map, listBills);
           
           //get mapDiscount
           DiscountDAO discountDAO = new DiscountDAO();
           Map<String, DiscountDTO> mapDiscuont = discountDAO.getMapDiscount(listBills);
           request.setAttribute("LISTBILLS", listBills);
           request.setAttribute("MAPDETAILBILL", map);
           request.setAttribute("MAPDISCOUNT", mapDiscuont);
           
           url = VIEWHISTORY;
        }
        catch(SQLException ex){
            LOGGER.error(ex.getMessage());
           
        }
        catch(NamingException ex){
            LOGGER.error(ex.getMessage());
          
        }
        finally{
            RequestDispatcher rd = request.getRequestDispatcher(listResource.get(url));
            rd.forward(request, response);
            out.close();
        }
    }
    
    public List filterBillByName(Map<String,List<DetailBillDTO>> map, List<BillDTO> listBill){
        List list = new ArrayList();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            for (BillDTO dto : listBill) {
                if(dto.getIdBill().equals(key)){
                    list.add(dto);
                }
            }
        }
        return list;
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
