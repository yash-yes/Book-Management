package com.idiot.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.sql.ResultSet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;


@WebServlet("/editScreen")
public class EditScreenServlet extends HttpServlet {
    private static final String query = "SELECT BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOKDATA WHERE ID = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            pw.println("<h1>Error: Missing 'id' parameter in the request</h1>");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            pw.println("<h1>Error: Invalid 'id' parameter. Please provide a valid number.</h1>");
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection("jdbc:mysql:///bookmanagementapp", "root", "Chunnu@1");
                 PreparedStatement ps = con.prepareStatement(query)) {

                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    pw.println("<form action='editurl?id=" + id + "' method='post'>");
                    pw.println("<table align='center'>");
                    pw.println("<tr><td>Book Name</td><td><input type='text' name='bookname' value='" + rs.getString(1) + "'></td></tr>");
                    pw.println("<tr><td>Book Edition</td><td><input type='text' name='bookEdition' value='" + rs.getString(2) + "'></td></tr>");
                    pw.println("<tr><td>Book Price</td><td><input type='text' name='bookPrice' value='" + rs.getFloat(3) + "'></td></tr>");
                    pw.println("<tr><td><input type='submit' value='Edit'></td><td><input type='reset' value='Cancel'></td></tr>");
                    pw.println("</table>");
                    pw.println("</form>");
                } else {
                    pw.println("<h1>No record found for ID: " + id + "</h1>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>Error: " + e.getMessage() + "</h1>");
        }

        pw.println("<a href='home.html'>Home</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}

