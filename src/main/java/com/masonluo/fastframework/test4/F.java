package com.masonluo.fastframework.test4;

import com.masonluo.fastframework.beans.stereotype.Component;

/**
 * @author masonluo
 * @date 2020/7/3 7:16 PM
 */
@Component
public class F {
    public G g;

    public G getG() {
        return g;
    }

    public void setG(G g) {
        this.g = g;
    }
}
