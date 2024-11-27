package cn.houtaroy.authorization.server.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/**
 * 复合 WebAuthenticationDetailsSource
 * <p>
 * 构建 {@link CompositeWebAuthenticationDetails}
 */
public class CompositeWebAuthenticationDetailsSource extends WebAuthenticationDetailsSource {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new CompositeWebAuthenticationDetails(context);
    }
}
