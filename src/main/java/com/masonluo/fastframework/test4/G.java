package com.masonluo.fastframework.test4;

import com.masonluo.fastframework.beans.annotation.Bean;
import com.masonluo.fastframework.beans.stereotype.Component;

/**
 * @author masonluo
 * @date 2020/7/3 7:16 PM
 */
@Component
public class G {
    @Bean
    public F h(G g) {
        F f = new F();
        f.setG(g);
        return f;
    }
}
