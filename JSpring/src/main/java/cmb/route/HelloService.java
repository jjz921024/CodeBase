package cmb.route;

/**
 * Created by Jun on 2018/8/20.
 */
public interface HelloService {

    @RoutingSwitch("A")
    void sayHello();

    @RoutingSwitch("B")
    void sayHi();
}
