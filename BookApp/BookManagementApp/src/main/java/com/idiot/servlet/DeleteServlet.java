package com.idiot.servlet;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {


    private static final String query = "delete from  BOOKDATA  WHERE ID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

       int id = Integer.parseInt(req.getParameter("id"));


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cls) {
            pw.println("<h2>Error: JDBC Driver not found</h2>");
            return;
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///bookmanagementapp", "root", "Chunnu@1");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1,id);

            int count = ps.executeUpdate();
            if (count == 1) {
                pw.println("<h2>Record deleted successfully!</h2>");
            } else {
                pw.println("<h2>No record was updated. Check the ID.</h2>");
            }

        } catch (Exception e) {
            System.err.println("Error editing record: " + e.getMessage());
            e.printStackTrace();
            pw.println("<h2>Error occurred while updating the record.</h2>");
        }

        pw.println("<a href='home.html'>Home</a><br>");
        pw.println("<a href='bookList'>Book List</a>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doGet(req, res);
    }

}
