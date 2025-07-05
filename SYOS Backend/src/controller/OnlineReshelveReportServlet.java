package controller;

import com.google.gson.Gson;
import dao.OnlineReshelveReport;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class OnlineReshelveReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");


        OnlineReshelveReport reportDAO = new OnlineReshelveReport();
        List<OnlineReshelveReport.ReshelveEntry> reportList = reportDAO.generateReshelveReport();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = new Gson().toJson(reportList);
        response.getWriter().write(json);
    }
}
