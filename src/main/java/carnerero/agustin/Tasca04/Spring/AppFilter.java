package carnerero.agustin.Tasca04.Spring;


	import org.springframework.stereotype.Component;
	import javax.servlet.*;  
	import javax.servlet.annotation.WebFilter;  
	import javax.servlet.http.HttpServletResponse;  
	import java.io.IOException;

	@WebFilter("/empleados/*")
	@Component
	public class AppFilter implements Filter {  
	    @Override
	    public void init(FilterConfig filterConfig) throws ServletException {

	    }

	    @Override
	    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
	        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
	        httpServletResponse.setHeader("IT-Academy-Exercise", "Simple-Service");
	        filterChain.doFilter(servletRequest, servletResponse);
	    }

	    @Override
	    public void destroy() {

	    }
	}


