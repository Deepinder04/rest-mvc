package project.first.spring.Utilities.auth.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.first.spring.Utilities.Constants;

@Service
public class AuthService {

    public void getDetailsFromAuth(HttpServletRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        request.setAttribute(Constants.USER_DETAILS, userDetails);
     }
}
