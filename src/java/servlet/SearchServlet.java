/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import car.CarDAO;
import car.CarDTO;
import detailbill.DetailBillDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author IT
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final Logger LOGGER = Logger.getLogger(SearchServlet.class);
    private final String VIEWSEARCH = "ViewSearch";
    private final String ERROR = "Error";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String searchName = request.getParameter("txtSearchName");
        ServletContext appScope = request.getServletContext();
        Map<String, String> listResource = (Map<String, String>) appScope.getAttribute("LISTRESOURCE");
        if (searchName == null) {
            searchName = "";
        }
        String searchCategory = request.getParameter("txtCategory");
        if (searchCategory == null) {
            searchCategory = "";
        }
        String stringpageIndex = request.getParameter("pageIndex");
        if (stringpageIndex == null) {
            stringpageIndex = "1";
        }
        int pageIndex = Integer.parseInt(stringpageIndex);
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        if (dateStart == null | dateEnd == null) {
            Date dateCurrent = new Date();
            SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
            dateStart = pattern.format(dateCurrent);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1);  // number of days to add      
            dateEnd = (String) (pattern.format(c.getTime()));
        }
        String url = listResource.get(ERROR);
        try {
            /* TODO output your page here. You may use following sample code. */
            DetailBillDAO detailBillDAO = new DetailBillDAO();
            Map<String, CarDTO> map = detailBillDAO.getMapCarReducedAmount(dateStart, dateEnd);

            CarDAO carDAO = new CarDAO();
            List<CarDTO> listCars = carDAO.getListCarWithNameAndCategory(pageIndex, searchName, searchCategory, map);
            int cars = carDAO.countCarsWithNameAndCategory(searchName, searchCategory);
            int pageNumber = countPage(cars);
            List<String> listCategory = carDAO.getAllCategory();
            request.setAttribute("LISTREQUEST", listCars);
            request.setAttribute("pageIndex", pageIndex);
            request.setAttribute("PAGENUMBER", pageNumber);
            request.setAttribute("LISTCATEGORY", listCategory);
            request.setAttribute("DATESTART", dateStart);
            request.setAttribute("DATEEND", dateEnd);
            url = listResource.get(VIEWSEARCH);
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error(ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }

    }

    public int countPage(int numberQuestions) {
        int numberPages = 0;
        int lastPage = 0;
        int numberPagesNearLastPage = 0;
        if (numberQuestions % 3 != 0) {
            lastPage = 1;
        }
        numberPagesNearLastPage = numberQuestions / 3;
        numberPages = numberPagesNearLastPage + lastPage;
        return numberPages;
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
