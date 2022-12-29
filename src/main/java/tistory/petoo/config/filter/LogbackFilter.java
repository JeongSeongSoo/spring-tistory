package tistory.petoo.config.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

public class LogbackFilter extends Filter<ILoggingEvent> {

    // 2022.12.30[프뚜]: 정규식을 사용하여 해당 Class Path 를 제외
    private String[] excludeClassPathPattern = {
        "tistory.petoo.controller.([^/]+)",
    };

    @Override
    public FilterReply decide(ILoggingEvent event) {
        for (String pattern : excludeClassPathPattern) {
            if (Pattern.matches(pattern, event.getLoggerName())) {
                return FilterReply.DENY;
            }
        }

        return FilterReply.ACCEPT;
    }

}