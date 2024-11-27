package cn.houtaroy.authorization.server.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 表单登录验证器
 * <p>
 * 根据类型进行认证检查和推断用户名
 * <p>
 * 例如使用手机短信登录时, 需要根据手机号查找对应的账号
 */
public interface FormLoginAuthenticator {

    String getFormLoginType();

    void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException;

    String determineUsername(Authentication authentication);
}
