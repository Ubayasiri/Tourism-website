/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import beans.loginbean;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author F.R.I.D.A.Y
 */
@WebServlet(name = "login_local", urlPatterns = {"/login_local"})
public class login_local extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            loginbean ub = new loginbean();
            ub.setEmail(request.getParameter("username"));
            ub.setPassword(request.getParameter("pass"));
            
            String pass = ub.getPassword();
            String uname = ub.getEmail();
            

            
            String sql = "SELECT * FROM `locals` WHERE `fname` ='"+uname+"' AND `pwd` = '"+pass+"' AND `status`='yes'";
            ResultSet search = DB.search(sql);
            if (search.next()) {

                HttpSession session = request.getSession(true);
                session.setAttribute("user", uname);
                response.sendRedirect("user_admin.jsp?message=Hello+" + uname + "");
                
            } else {
                RequestDispatcher rd = getServletContext().getRequestDispatcher("/login_local.jsp?message=<font color=red>Either user name or password is wrong or You havent accepted by the admin to the system</font>");
                rd.include(request, response);
          
            }
        } catch (Exception ex) {
             PrintWriter out = response.getWriter();
             out.print(ex);
        }
    }

}
