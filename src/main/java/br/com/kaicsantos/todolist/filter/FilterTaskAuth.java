package br.com.kaicsantos.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.kaicsantos.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    var servPath = request.getServletPath();
    if (servPath.startsWith("/tasks/")) {
      //receber os dados
      var authorization = request.getHeader("Authorization");
   

      var user_pw_encoded = authorization.substring("Basic".length()).trim();

      byte[] pw_decoded = Base64.getDecoder().decode(user_pw_encoded);

      var pw_String = new String(pw_decoded);

      String[] credentials = pw_String.split(":");
      String username = credentials[0];
      String password = credentials[1];
      //validar o usuário
      var user = this.userRepository.findByUsername(username);
      if (user == null) {
        response.sendError(401);
      } else {
        var pw_verified = BCrypt
          .verifyer()
          .verify(password.toCharArray(), user.getPassword());
        if (pw_verified.verified) {
          request.setAttribute("idUser", user.getId());
          filterChain.doFilter(request, response);
        } else {
          response.sendError(401);
        }
      }
      //validar a senha

      //continuar
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
