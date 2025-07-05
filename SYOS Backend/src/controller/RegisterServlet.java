package controller;

import dao.RegisterDAO;
import dao.RegisterDAOImpl;
import db.DBConnection;
import model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {

    private RegisterDAO registerDAO;

    @Override
    public void init() throws ServletException {
        registerDAO = new RegisterDAOImpl(DBConnection.getInstance());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setContentType("application/json");

        String action = request.getParameter("action");

        if ("register".equalsIgnoreCase(action)) {
            try {
                registerUser(request, response);
            } catch (Exception e) {
                e.printStackTrace();
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"message\": \"Server error during registration\"}");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"message\": \"Invalid action\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>RegisterServlet is working!</h2>");
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void registerUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User(0, name, email, phone, address, username, password); // userID will be generated automatically

        // Use DAO to register the user
        boolean isRegistered = registerDAO.registerUser(user);

        if (isRegistered) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"message\": \"User registered successfully\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("{\"message\": \"Username already taken or registration failed\"}");
        }
    }
}
