package gdut.pojo;

/**
 * Created by Jun on 2018/2/11.
 */
public enum GenderEnum implements BaseCodeEnum{
    // 枚举值是枚举类的对象，要符合构造函数和实现抽象类
    MALE(11),
    FEMALE(00);

    //枚举类可以有成员和构造函数
    private int code;
    GenderEnum(int code) {
        this.code = code;
    }

    @Override
    public int getCode() {
        return this.code;
    }



    /*public static void main(String[] args) {
        System.out.println(GenderEnum.FEMALE.ordinal());
    }*/
}
