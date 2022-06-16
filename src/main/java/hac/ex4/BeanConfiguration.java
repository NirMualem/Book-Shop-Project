package hac.ex4;

import hac.ex4.listeners.SessionListenerCounter;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * class that add listener class to track the total sessions active
 */
@Configuration
public class BeanConfiguration {

    /**
     *we declare a bean 'sessionListenerWithMetrics' to count sessions
     * @return listenerRegBean
     */
    @Bean
    public ServletListenerRegistrationBean<SessionListenerCounter> sessionListenerWithMetrics() {
        ServletListenerRegistrationBean<SessionListenerCounter> listenerRegBean = new ServletListenerRegistrationBean<>();

        listenerRegBean.setListener(new SessionListenerCounter()); //set listener
        return listenerRegBean;
    }
}
