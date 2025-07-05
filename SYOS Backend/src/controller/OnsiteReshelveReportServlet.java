package controller;

import com.google.gson.Gson;
import dao.OnlineReshelveReport;
import dao.OnsiteReshelveReport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class OnsiteReshelveReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");


        OnsiteReshelveReport reportDAO = new OnsiteReshelveReport();
        List<OnsiteReshelveReport.ReshelveEntry> reportList = reportDAO.generateReshelveReport();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String json = new Gson().toJson(reportList);
        response.getWriter().write(json);
    }
}