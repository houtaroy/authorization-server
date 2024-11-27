package cn.houtaroy.authorization.server.authentication;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * 复合 WebAuthenticationDetails
 * <p>
 * 从 {@link HttpServletRequest} 中获取type属性用于判断表单登录类型
 */
public class CompositeWebAuthenticationDetails extends WebAuthenticationDetails {

    private final HttpServletRequest request;
    private final String type;

    public CompositeWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.request = request;
        this.type = request.getParameter("type");
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public String getType() {
        return type;
    }
}
