package src.main.java.com.ornn.sso.filter.provider;

import src.main.java.com.ornn.sso.utils.Md5Utils;
import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * @author: CANHUI.WANG * @create: 2022-07-29
 * 自定义实现用户账号/密码验证的功能类
 */

@Data
public class UserNameAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";
    private PasswordEncoder passwordEncoder;
    private volatile String userNotFoundEncodePassword;
    private UserDetailsService userDetailsService;
    private UserDetailsPasswordService userDetailsPasswordService;

    public UserNameAuthenticationProvider() {
        this.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    /**
     * 重写授权认证的检查方法，实现通过用户账号和密码进行登录验证的功能
     * @param userDetails
     * @param usernamePasswordAuthenticationToken
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        if (usernamePasswordAuthenticationToken.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            // 获取用户输入的密码
            String presentedPassword = usernamePasswordAuthenticationToken.getCredentials().toString();
            // 约定输入密码信息，拆分加密值
            String[] strArray = userDetails.getPassword().split(",");
            String userPasswordEncodeValue = strArray[0];
            String presentedPasswordEncodeValue = Md5Utils.md5Hex(presentedPassword + "&" + strArray[1], "UTF-8");
            if (!userPasswordEncodeValue.equals(presentedPasswordEncodeValue)) {
                this.logger.debug("Authentication failed: passwords do not match");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        this.prepareTimingAttackProtection();

        try {
            UserDetails userDetails = this.getUserDetailsService().loadUserByUsername(username);
            if (userDetails == null) {
                throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an inteterface contract violation");
            } else {
                return userDetails;
            }
        } catch (UsernameNotFoundException e) {
            this.mitigateAgainstTimingAttack(usernamePasswordAuthenticationToken);
            throw e;
        } catch (InternalAuthenticationServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e);
        }
    }

    @Override
    protected void doAfterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must not be null.");
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        boolean upgradeEncoding = this.userDetailsPasswordService != null && this.passwordEncoder.upgradeEncoding(user.getPassword());
        if (upgradeEncoding) {
            String presentedPassword = authentication.getCredentials().toString();
            String newPassword = this.passwordEncoder.encode(presentedPassword);
            user = this.userDetailsPasswordService.updatePassword(user, newPassword);
        }
        return super.createSuccessAuthentication(principal, authentication, user);
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodePassword != null) {
            this.userNotFoundEncodePassword = this.passwordEncoder.encode("userNotFoundPassword");
        }
    }

    private void mitigateAgainstTimingAttack(UsernamePasswordAuthenticationToken authenticationToken) {
        if (authenticationToken.getCredentials() != null) {
            String presentedPassword = authenticationToken.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodePassword);
        }
    }

    public void setPassword(PasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");
        this.passwordEncoder = passwordEncoder;;
        this.userNotFoundEncodePassword = null;
    }

    protected PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public void setUserDetailsPasswordService(UserDetailsPasswordService userDetailsPasswordService) {
        this.userDetailsPasswordService = userDetailsPasswordService;
    }
}
