package controller;

import com.google.gson.Gson;
import dao.OnsiteSalesReport;
import dao.SalesReport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OnsiteSalesReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // CORS headers
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        // Set content type
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Fetch data
        OnsiteSalesReport reportDAO = new OnsiteSalesReport();
        List<OnsiteSalesReport.ReportEntry> reportList = reportDAO.getOnsiteSalesReport();

        // Convert to JSON
        String json = new Gson().toJson(reportList);
        response.getWriter().write(json);
    }
}
