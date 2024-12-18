package cn.houtaroy.authorization.server.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * 手机短信登录验证器
 */
@Component
public class SmsFormLoginAuthenticator implements FormLoginAuthenticator {

    @Override
    public String getFormLoginType() {
        return "sms";
    }

    @Override
    public void additionalAuthenticationChecks(UserDetails userDetails,
                                               UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        
        if (authentication.getDetails() instanceof CompositeWebAuthenticationDetails compositeWebAuthenticationDetails) {
            String code = compositeWebAuthenticationDetails.getParameter("code");
            if (!"123456".equals(code)) {
                throw new BadCredentialsException("短信验证码错误");
            }
        }
    }

    @Override
    public String determineUsername(Authentication authentication) {
        return "user";
    }
}
