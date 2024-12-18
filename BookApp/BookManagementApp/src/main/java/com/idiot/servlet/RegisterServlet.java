package com.idiot.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO BOOKDATA ( BOOKNAME , BOOKEDITION , BOOKPRICE) VALUES (?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        // Get the book Info
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cls) {
            cls.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///bookmanagementapp", "root", "Chunnu@1");
             PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>  Record Register Successfully </h2");
            } else {
                pw.println("<h2>  Record not Register </h2>");
            }


        } catch (SQLDataException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");

        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }
        pw.println("<a href='home.html'> Home</a>");
        pw.println("<br>");
        pw.println("<a href='bookList'> Book List</a>");
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
