package cn.houtaroy.authorization.server.authentication;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 复合 DaoAuthenticationProvider
 * <p>
 * 内部抽象了 {@link FormLoginAuthenticator}, 实现多种模式的表单登录校验
 */
@Component
public class CompositeDaoAuthenticationProvider extends DaoAuthenticationProvider {

    private final List<FormLoginAuthenticator> authenticators;

    public CompositeDaoAuthenticationProvider(List<FormLoginAuthenticator> authenticators,
                                              UserDetailsService userDetailsService) {
        this.authenticators = authenticators;
        this.setUserDetailsService(userDetailsService);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                () -> this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
                        "Only UsernamePasswordAuthenticationToken is supported"));
        String username = determineUsername(authentication);
        boolean cacheWasUsed = true;
        UserDetails user = this.getUserCache().getUserFromCache(username);
        if (user == null) {
            cacheWasUsed = false;
            try {
                user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
            } catch (UsernameNotFoundException ex) {
                this.logger.debug("Failed to find user '" + username + "'");
                if (!this.hideUserNotFoundExceptions) {
                    throw ex;
                }
                throw new BadCredentialsException(this.messages
                        .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
            Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
        }
        try {
            this.getPreAuthenticationChecks().check(user);
            additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
        } catch (AuthenticationException ex) {
            if (!cacheWasUsed) {
                throw ex;
            }
            // There was a problem, so try again after checking
            // we're using latest data (i.e. not from the cache)
            cacheWasUsed = false;
            user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
            this.getPreAuthenticationChecks().check(user);
            additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
        }
        this.getPostAuthenticationChecks().check(user);
        if (!cacheWasUsed) {
            this.getUserCache().putUserInCache(user);
        }
        Object principalToReturn = user;
        if (this.isForcePrincipalAsString()) {
            principalToReturn = user.getUsername();
        }
        return createSuccessAuthentication(principalToReturn, authentication, user);
    }

    private String determineUsername(Authentication authentication) {
        if (authentication.getDetails() instanceof CompositeWebAuthenticationDetails details) {
            FormLoginAuthenticator authenticator = getAuthenticator(details.getType());
            if (authenticator != null) {
                return authenticator.determineUsername(authentication);
            }
        }
        return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getDetails() instanceof CompositeWebAuthenticationDetails details) {
            FormLoginAuthenticator authenticator = getAuthenticator(details.getType());
            if (authenticator != null) {
                authenticator.additionalAuthenticationChecks(userDetails, authentication);
            }
        } else {
            super.additionalAuthenticationChecks(userDetails, authentication);
        }
    }

    private FormLoginAuthenticator getAuthenticator(String type) {
        for (FormLoginAuthenticator authenticator : authenticators) {
            if (type.equals(authenticator.getFormLoginType())) {
                return authenticator;
            }
        }
        return null;
    }

}
