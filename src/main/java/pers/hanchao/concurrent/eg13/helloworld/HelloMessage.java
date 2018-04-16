package pers.hanchao.concurrent.eg13.helloworld;

/**
 * <p>Hello的消息类</p>
 * @author hanchao 2018/4/16 21:34
 **/
public class HelloMessage{
    /** 欢迎的对象 */
    private String name;

    @Override
    public String toString() {
        return "HelloMessage{" +
                "name='" + name + '\'' +
                '}';
    }

    public HelloMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
