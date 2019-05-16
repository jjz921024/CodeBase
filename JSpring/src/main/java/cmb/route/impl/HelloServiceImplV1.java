package cmb.route.impl;

import cmb.route.HelloService;
import org.springframework.stereotype.Service;

/**
 * Created by Jun on 2018/8/20.
 */
@Service
public class HelloServiceImplV1 implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("Hello from V1");
    }

    @Override
    public void sayHi() {
        System.out.println("Hi from V1");
    }

}
