package com.wzp.majiang.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 选牌方式
 */
public class ChooseCardMethod implements Cloneable {

    private boolean isSelected; // 是否被选中
    private int loopTimes; // 循环次数
    private List<ChooseCardPlayMethod> methods; // 玩法集合

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getLoopTimes() {
        return loopTimes;
    }

    public void setLoopTimes(int loopTimes) {
        this.loopTimes = loopTimes;
    }

    public List<ChooseCardPlayMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<ChooseCardPlayMethod> methods) {
        this.methods = methods;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ChooseCardMethod chooseCardMethod;
        chooseCardMethod = (ChooseCardMethod) super.clone();
        chooseCardMethod.methods = (ArrayList) ((ArrayList) methods).clone();
        return chooseCardMethod;
    }
}
