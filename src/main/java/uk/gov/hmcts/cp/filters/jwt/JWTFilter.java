package uk.gov.hmcts.cp.filters.jwt;

import java.io.IOException;
import java.util.stream.Stream;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.filter.OncePerRequestFilter;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Component
@Slf4j
public class JWTFilter extends OncePerRequestFilter {
    public static final String JWT_TOKEN_HEADER = "jwt";

    private final JWTService jwtService;
    private final PathMatcher pathMatcher;
    private final ObjectProvider<AuthDetails> jwtProvider;
    private final boolean jwFilterEnabled;

    public JWTFilter(final JWTService jwtService, final PathMatcher pathMatcher, final ObjectProvider<AuthDetails> jwtProvider, @Value("${jwt.filter.enabled}") final boolean jwFilterEnabled) {
        this.jwtService = jwtService;
        this.pathMatcher = pathMatcher;
        this.jwtProvider = jwtProvider;
        this.jwFilterEnabled = jwFilterEnabled;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {

        final String jwt = request.getHeader(JWT_TOKEN_HEADER);

        if (jwt == null) {
            log.atError().log("JWTFilter expected header {} not passed", JWT_TOKEN_HEADER);
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "No jwt token passed");
        }

        try {
            final AuthDetails extractedToken = jwtService.extract(jwt);

            final AuthDetails requestScopedToken = jwtProvider.getObject(); // current request instance
            requestScopedToken.setUserName(extractedToken.getUserName());
            requestScopedToken.setScope(extractedToken.getScope());
        } catch (InvalidJWTException e) {
            log.atError().log(e.getMessage());
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) {
        // Skip filtering entirely when disabled, and for specific paths
        boolean shouldNotFilter = true;
        if (jwFilterEnabled) {
            log.atInfo().log("JWT Filter is enabled");
            shouldNotFilter = Stream.of("/health")
                    .anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
        }
        return shouldNotFilter;
    }
}
