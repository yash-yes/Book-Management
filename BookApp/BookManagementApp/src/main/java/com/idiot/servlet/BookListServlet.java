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

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet{
    private static final String query = "Select ID ,BOOKNAME,BOOKEDITION,BOOKPRICE FROM  BOOKDATA";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");
        // Get the book Info

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cls) {
            cls.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection("jdbc:mysql:///bookmanagementapp", "root", "Chunnu@1");
             PreparedStatement ps = con.prepareStatement(query);) {

            ResultSet rs = ps.executeQuery();
            pw.println("<table border= '1'  align ='center'> ");
            pw.println("<tr>");
            pw.println("<th> Book Id </th>");
            pw.println("<th> Book Name </th>");
            pw.println("<th> Book Edition </th>");
            pw.println("<th> Book Price </th>");
            pw.println("<th> Edit </th>");
            pw.println("<th> Delete </th>");
            pw.println("</tr>");
            while(rs.next())
            {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt (1) +"</td>");
                pw.println("<td>"+rs.getString (2) +"</td>");
                pw.println("<td>"+ rs.getString(3) +"</td>");
                pw.println("<td>"+ rs.getFloat(4) +"</td>");
                pw.println("<td> <a href='editScreen?id=" + rs.getInt(1) + "'>Edit</a> </td>");
                pw.println("<td> <a href='deleteurl?id=" + rs.getInt(1) + "'>Delete</a> </td>");

                pw.println("</tr>");
            }
            pw.println("</table>");

        } catch (SQLDataException se) {
            se.printStackTrace();
            pw.println("<h1>" + se.getMessage() + "</h1>");

        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>" + e.getMessage() + "</h1>");
        }

        pw.println("<a href='home.html'> Home</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }
}
