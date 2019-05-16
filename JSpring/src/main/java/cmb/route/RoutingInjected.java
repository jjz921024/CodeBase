package cmb.route;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Created by Jun on 2018/8/20.
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RoutingInjected {
}
