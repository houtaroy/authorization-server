package cn.houtaroy.authorization.server.authentication;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Map;

/**
 * 复合 WebAuthenticationDetails
 * <p>
 * 从 {@link HttpServletRequest} 中获取type属性用于判断表单登录类型
 */
@Getter
public class CompositeWebAuthenticationDetails extends WebAuthenticationDetails {

    private final Map<String, String[]> parameters;
    private final String type;

    public CompositeWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        this.parameters = request.getParameterMap();
        this.type = request.getParameter("type");
    }

    public String getParameter(String name) {
        String[] values = parameters.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

}
