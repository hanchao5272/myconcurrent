package pers.hanchao.concurrent.eg13.helloworld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * <p>Actor框架入门示例-HelloWorld的角色</p>
 *
 * @author hanchao 2018/4/16 21:07
 **/
public class HelloActor extends UntypedAbstractActor {

    //定义日志，很重要
    LoggingAdapter log = Logging.getLogger(getContext().getSystem(),this);
    /**
     * <p>重写接收方法</p>
     * @author hanchao 2018/4/16 21:31
     **/
    @Override
    public void onReceive(Object message){
        log.info("HelloActor receive message : " + message);
        //如果消息类型是HelloMessage，则进行处理
        if (message instanceof HelloMessage){
            log.info("Hello " + ((HelloMessage) message).getName() + "!");
        }
    }

    /**
     * <p>Actor入门示例</p>
     * @author hanchao 2018/4/16 21:37
     **/
    public static void main(String[] args) {
        //创建actor系统
        ActorSystem system = ActorSystem.create("hello-system");
        //定义Actor引用
        ActorRef helloActor = system.actorOf(Props.create(HelloActor.class),"hello-actor");

        //向HelloActor发送消息
        helloActor.tell(new HelloMessage("World"),null);
        helloActor.tell(new HelloMessage("Akka Actor"),null);

        //终止Actor系统
        system.terminate();
    }
}
