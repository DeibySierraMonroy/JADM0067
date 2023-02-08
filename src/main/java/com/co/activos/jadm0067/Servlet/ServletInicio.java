package com.co.activos.jadm0067.Servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ServletInicio", value = "/ServletInicio")
public class ServletInicio extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sessionUsuario;
       // sessionUsuario = request.getParameter("USS_ID_SESSION");
       sessionUsuario = "lVEKkOjAntPUbOcejimzfzWARXLWpVlYwfywNyShcuGHoekSpRFvucHCtQhZkVZkYNQoNuLLcsGTdHSGpQSjxsapvfVxTrRBIWvBZveSKLPYjaIjhzJiNAaTtmmgmcNlOCVDCwYzciHgSjYceffhrrVBIiiLXrDZOkblWgiJhRPgjywY";
        
        if (sessionUsuario == null) {
            response.sendRedirect("/Resource/error/errorSesion.xhtml");
        }

        session.setAttribute("USS_ID_SESSION", sessionUsuario);
        response.sendRedirect("inicioAplicativo.xhtml");

    }

}
