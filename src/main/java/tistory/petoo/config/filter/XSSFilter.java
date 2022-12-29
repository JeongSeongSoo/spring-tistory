package tistory.petoo.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tistory.petoo.config.filter.wrapper.XSSFilterWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
@Component
public class XSSFilter implements Filter {

    // 2022.12.26[프뚜]: path 제외시킬 URI
    private String[] excludePathPatterns = {
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 2022.12.26[프뚜]: 정규식을 통해 excludePath 문자를 변경
        String asterisk = "([^/]+)";
        String doubleAsterisk = "(.+)";

        for (int i = 0; i < excludePathPatterns.length; i++) {
            excludePathPatterns[i] = excludePathPatterns[i].replaceAll("[*][*]", doubleAsterisk)
                                                .replaceAll("[*]", asterisk);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (isExcludePath(httpServletRequest)) {
            chain.doFilter(request, response);
        } else {
            // 2022.12.26[프뚜]: Response Wrapper
            XSSFilterWrapper responseWrapper = new XSSFilterWrapper(httpServletResponse);
            chain.doFilter(request, responseWrapper);

            // 2022.12.26[프뚜]: 최종 데이터 처리
            byte[] bytes = responseWrapper.replaceXSS();
            int byteLength = bytes.length;
            if (byteLength > 0) {
                response.setContentLength(byteLength);
                response.getOutputStream().write(bytes);
                response.flushBuffer();
            }
        }
    }

    private boolean isExcludePath(HttpServletRequest httpServletRequest) {
        for (String pattern : excludePathPatterns) {
            // 2022.12.26[프뚜]: 정규식으로 변한 path를 Pattern.matches를 통해 비교
            if (Pattern.matches(pattern, httpServletRequest.getRequestURI())) {
                return true;
            }
        }

        return false;
    }

}