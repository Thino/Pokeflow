package isima.farouzza

import javax.servlet.*
import javax.servlet.http.*

class CorsFilterFilters implements Filter {

    public void init(FilterConfig fConfig) throws ServletException { }

    public void destroy() { }

    public void doFilter(
            ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        ((HttpServletResponse) response).addHeader(
                "Access-Control-Allow-Origin", "*"
        )
        chain.doFilter(request, response)
    }
}
