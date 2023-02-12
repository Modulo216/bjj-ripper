package de.jonashackt.springbootvuejs.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class HostFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(HostFilter.class);
    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
            throws IOException, ServletException {
        String path = new UrlPathHelper().getPathWithinApplication((HttpServletRequest) request);
        if(!path.contains("/getTorrents")) {
            logger.debug("Remote Host:" + request.getRemoteHost() + " Remote Address:" + request.getRemoteAddr() + " Path:" + path);
        }
        filterchain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterconfig) throws ServletException {}
}
