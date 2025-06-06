package co.edu.unbosque.software_electroadonai.config;

import co.edu.unbosque.software_electroadonai.model.RecaptchaDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CaptchaValidationFilter extends OncePerRequestFilter {

    private final GoogleRecaptchaService captchaService;

    public CaptchaValidationFilter(GoogleRecaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Validar solo si la ruta es /login y es un POST
        if ("/login".equals(request.getServletPath()) && "POST".equalsIgnoreCase(request.getMethod())) {

            String captchaResponse = request.getParameter("g-recaptcha-response");
            String ip = request.getRemoteAddr();

            // Si no hay respuesta del captcha o es inválido
            if (captchaResponse == null || captchaResponse.isEmpty()) {
                response.sendRedirect("/login?error=captcha");
                return;
            }

            RecaptchaDto result = captchaService.verify(ip, captchaResponse);

            if (!result.isSuccess()) {
                response.sendRedirect("/login?error=captcha");
                return;
            }
        }

        // Continuar con la cadena de filtros si el captcha es válido
        filterChain.doFilter(request, response);
    }

}
