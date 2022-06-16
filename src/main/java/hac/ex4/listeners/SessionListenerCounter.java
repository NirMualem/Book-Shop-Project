package hac.ex4.listeners;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * a @WebListener class for session count
 * the @Component is needed only if we INJECT beans
 */
@Component
@WebListener
public class SessionListenerCounter implements HttpSessionListener {
    private final AtomicInteger activeSessions;

    /**
     * ctor
     */
    public SessionListenerCounter() {
        super();
        activeSessions = new AtomicInteger();
    }

    /**
     * function to get all session active
     * @return activeSessions.get() all session active
     */
    public int getTotalActiveSession() {
        return activeSessions.get();
    }

    /**
     * function to increase counter when session create and print to console
     * @param event get event session create
     */
    public void sessionCreated(final HttpSessionEvent event) {
        activeSessions.incrementAndGet();
        System.out.println("SessionListenerCounter +++ Total active session are " + activeSessions.get());
    }

    /**
     * function to decrease counter when session destroyed and print to console
     * @param event get event session destroyed
     */
    public void sessionDestroyed(final HttpSessionEvent event) {
        activeSessions.decrementAndGet();
        System.out.println("SessionListenerCounter --- Total active session are " + activeSessions.get());
    }
}
