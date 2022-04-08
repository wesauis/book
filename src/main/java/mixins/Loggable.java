package mixins;

import java.util.logging.Logger;

/**
 *
 * @author w2gam
 */
public interface Loggable {
    default Logger logger() {
        return Logger.getLogger(getClass().getName());
    }
}
