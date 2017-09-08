package wzp.project.majiang.entity;

/**
 * Created by zippe on 2017/9/5.
 */

public class SingleChooseMajiangMethod {

    private String name; // 玩法名称
    private int numIndex; // 基本张数（索引值）
    private int specialRuleIndex; // 特殊规则（索引值）

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumIndex() {
        return numIndex;
    }

    public void setNumIndex(int numIndex) {
        this.numIndex = numIndex;
    }

    public int getSpecialRuleIndex() {
        return specialRuleIndex;
    }

    public void setSpecialRuleIndex(int specialRuleIndex) {
        this.specialRuleIndex = specialRuleIndex;
    }
}
