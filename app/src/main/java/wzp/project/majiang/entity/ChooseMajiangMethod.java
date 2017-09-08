package wzp.project.majiang.entity;

import java.util.List;

/**
 * Created by zippe on 2017/9/5.
 */

public class ChooseMajiangMethod {

    private boolean isSelected; // 是否被选中
    private int loopTimes; // 循环次数
    private List<SingleChooseMajiangMethod> methods; // 玩法集合

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

    public List<SingleChooseMajiangMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<SingleChooseMajiangMethod> methods) {
        this.methods = methods;
    }
}
