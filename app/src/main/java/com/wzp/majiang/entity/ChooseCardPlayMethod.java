package com.wzp.majiang.entity;

/**
 * 选牌方式下的玩法
 */
public class ChooseCardPlayMethod implements Cloneable {

    private int name; // 玩法名称的ID
    private int num; // 基本张数
    private int specialRule; // 特殊规则


    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSpecialRule() {
        return specialRule;
    }

    public void setSpecialRule(int specialRule) {
        this.specialRule = specialRule;
    }

    @Override
    public int hashCode() {
        return name;
    }

    @Override
    public boolean equals(Object otherObject) {       //第一步
        if (this == otherObject)                      //第二步
            return true;
        if (otherObject == null)                      //第三步
            return false;
        if (getClass() != otherObject.getClass())     //第四步      因为在子类中比较的内容会有变化，所以使用getClass
            return false;
        ChooseCardPlayMethod other = (ChooseCardPlayMethod) otherObject;      //第五步
        return getName() == other.getName();//第六步
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
