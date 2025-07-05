package controller;

import com.google.gson.Gson;
import dao.ItemDAO;
import dao.ItemDAOImpl;
import model.Item;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ItemServlet extends HttpServlet {

    private final ItemDAO itemDAO=new ItemDAOImpl();
    private final Gson gson=new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String action = request.getParameter("action");

        switch (action) {
            case "getPrice":
                String code = request.getParameter("itemCode");
                Float price = itemDAO.getItemPrice(code);
                if (price != null) {
                    out.write(gson.toJson(price));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.write("{\"error\": \"Item not found\"}");
                }
                break;

            case "getAll":
                List<Item> items = itemDAO.getAllItems();
                out.write(gson.toJson(items));
                break;

            case "validateCode":
                String validateCode = request.getParameter("itemCode");
                boolean isValid = itemDAO.isItemCodeValid(validateCode);
                out.write("{\"valid\": " + isValid + "}");
                break;


            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.write("{\"error\": \"Invalid action\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
