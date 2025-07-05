package controller;

import dao.SalesReport;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class SalesReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CORS headers
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Set content type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Fetch data
        SalesReport reportDAO = new SalesReport();
        List<SalesReport.ReportEntry> reportList = reportDAO.getSalesReport();

        // Convert to JSON
        String json = new Gson().toJson(reportList);
        response.getWriter().write(json);
    }
}
