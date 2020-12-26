package com.kts.cultural_content.constants;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityContextConstants {

    private SecurityContextConstants() { }

    public static SecurityContext returnMockedSecurityContext() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(UserConstants.returnLoggedUser());
        when(authentication.getName()).thenReturn("MarkoMarkovic12");
        return securityContext;
    }
}
