/*package web;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CookiesServlet")
public class CookiesServlet extends HttpServlet {

    @Override //anotación es opcional
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Suponemos que el usuario visita por primera vez nuestro sitio web
        boolean nuevoUsuario = true;

        // Obtenemos el arreglo de cookies
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("visitanteRecurrente") && c.getValue().equals("si")) {
                    nuevoUsuario = false;
                    break;
                }
            }
        }

        String mensaje;
        if (nuevoUsuario) {
            Cookie visitanteCookie = new Cookie("visitanteRecurrente", "si");
            response.addCookie(visitanteCookie);
            mensaje = "Gracias por visitar nuestro sitio por primera vez";
        } else {
            mensaje = "Gracias por visitar nuevamente nuestro sitio";
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("Mensaje: " + mensaje);
        out.close();
    }
}*/
package web;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CookiesServlet")
public class CookiesServlet extends HttpServlet {

    @Override //anotación es opcional
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Obtenemos el arreglo de cookies
        Cookie[] cookies = request.getCookies();
        Cookie visitCountCookie = null;

        // Buscar cookie de visitas
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("visitCount")) {
                    visitCountCookie = cookie;
                    break;
                }
            }
        }

        // Determinar el número de visitas
        int visitCount = 0;
        if (visitCountCookie == null) {
            // Primera visita
            visitCount = 1;
            visitCountCookie = new Cookie("visitCount", Integer.toString(visitCount));
        } else {
            // Incrementar el contador de visitas
            visitCount = Integer.parseInt(visitCountCookie.getValue()) + 1;
            visitCountCookie.setValue(Integer.toString(visitCount));
        }

        // Establecer la cookie con el nuevo valor del contador
        visitCountCookie.setMaxAge(60 * 60 * 24 * 365); // La cookie expira en un año
        response.addCookie(visitCountCookie);

        // Preparar el mensaje para el usuario
        String mensaje;
        if (visitCount == 1) {
            mensaje = "Gracias por visitar nuestro sitio por primera vez";
        } else {
            mensaje = "Esta es tu visita número " + visitCount + " a nuestro sitio";
        }

        // Preparar la respuesta
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Contador de Visitas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Contador de Visitas</h1>");
            out.println("<p>" + mensaje + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
