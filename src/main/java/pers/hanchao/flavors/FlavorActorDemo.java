package pers.hanchao.flavors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 通过多个搜索引擎查询多个条件，并返回第一条查询结果
 * Created by 韩超 on 2018/3/6.
 */
public class FlavorActorDemo implements IFlavorDemo {
    private final static Logger LOGGER = Logger.getLogger(FlavorActorDemo.class);

    /**
     * <p>Title: 定义查询条件类，用于传递消息</p>
     *
     * @author 韩超 2018/3/6 16:16
     */
    static class QueryTerms {
        /**
         * 问题
         */
        private String question;
        /**
         * 搜索引擎
         */
        private String engine;

        @Override
        public String toString() {
            return "QueryTerms{" +
                    "question='" + question + '\'' +
                    ", engine='" + engine + '\'' +
                    '}';
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getEngine() {
            return engine;
        }

        public void setEngine(String engine) {
            this.engine = engine;
        }

        public QueryTerms(String question, String engin) {
            this.question = question;
            this.engine = engin;
        }
    }

    /**
     * <p>Title: 定义查询结果类，用于消息传递</p>
     *
     * @author 韩超 2018/3/6 16:17
     */
    static class QueryResult {
        /**
         * 查询结果
         */
        private String result;

        @Override
        public String toString() {
            return "QueryResult{" +
                    "result='" + result + '\'' +
                    '}';
        }

        public QueryResult(String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    /**
     * <p>Title:搜索引擎Actor </br>
     * 继承UntypedAbstractActor成为一个Actor</p>
     *
     * @author 韩超 2018/3/6 14:42
     */
    static class SearchEngineAcotr extends UntypedAbstractActor {
        //定义Actor日志
        private LoggingAdapter log = Logging.getLogger(getContext().getSystem(),this);
        /**
         * <p>Title: Actor都需要重写消息接收处理方法</p>
         *
         * @author 韩超 2018/3/6 14:42
         */
        @Override
        public void onReceive(Object message) throws Throwable {
            //如果消息是指定的类型Message，则进行处理，否则不处理
            if (message instanceof QueryTerms) {
                log.info("接收到搜索条件：" + ((QueryTerms) message).getQuestion());
                //通过工具类进行一次搜索引擎查询
                String result = EngineUtils.searchByEngine(((QueryTerms) message).getQuestion(), ((QueryTerms) message).getEngine());
                //通过getSender().tell(result,actor)将actor的 处理结果[result] 发送消息的发送者[getSender()]
                //通过getSender获取消息的发送方
                //通过getSelf()获取当前Actor
                getSender().tell(new QueryResult(result), getSelf());
            } else {
                unhandled(message);
            }
        }
    }

    /**
     * <p>Title: 问题查询器Actor</br>
     * 继承自UntypedAbstractActor</p>
     *
     * @author 韩超 2018/3/6 16:31
     */
    static class QuestionQuerier extends UntypedAbstractActor {
        //定义Actor日志
        private LoggingAdapter log = Logging.getLogger(getContext().getSystem(),this);
        /**
         * 搜索引擎列表
         */
        private List<String> engines;
        /**
         * 搜索结果
         */
        private AtomicReference<String> result;
        /**
         * 问题
         */
        private String question;

        public QuestionQuerier(String question, List<String> engines, AtomicReference<String> result) {
            this.question = question;
            this.engines = engines;
            this.result = result;
        }

        /**
         * <p>Title: Actor都需要重写消息接收处理方法</p>
         *
         * @author 韩超 2018/3/6 16:35
         */
        @Override
        public void onReceive(Object message) throws Throwable {
            //如果收到查询结果，则对查询结果进行处理
            if (message instanceof QueryResult) {//如果消息是指定的类型Result，则进行处理，否则不处理
                log.info("接收到搜索结果：" + ((QueryResult) message).getResult());
                //通过CAS设置原子引用的值
                result.compareAndSet(null, ((QueryResult) message).getResult());
                //如果已经查询到了结果，则停止Actor
                //通过getContext()获取ActorSystem的上下文环境
                //通过getContext().stop(self())停止当前Actor
                getContext().stop(self());
            } else {//如果没有收到处理结果，则创建搜索引擎Actor进行查询
                log.info("开始创建搜索引擎进行查询");

                //使用原子变量去测试Actor的创建是否有序
                AtomicInteger count = new AtomicInteger(1);

                //针对每一个搜索引擎，都创建一个Actor
                for (String engine : engines) {
                    log.info("为" + engine + "创建第" + count + "个搜索引擎Actor....");
                    count.getAndIncrement();

                    //通过actorOf(Props,name)创建Actor
                    //通过Props.create(Actor.class)创建Props
                    ActorRef fetcher = this.getContext().actorOf(Props.create(SearchEngineAcotr.class), "fetcher-" + engine.hashCode());
                    //创建查询条件
                    QueryTerms msg = new QueryTerms(question, engine);
                    //将查询条件告诉Actor
                    fetcher.tell(msg, self());
                }
            }
        }
    }

    /**
     * 通过多个搜索引擎查询，并返回第一条查询结果
     *
     * @param question 查询问题
     * @param engines  查询条件数组
     * @return 最先查出的结果
     * @author 韩超 2018/3/6 16:44
     */
    @Override
    public String getFirstResult(String question, List<String> engines) {
        //创建一个Actor系统
        ActorSystem system = ActorSystem.create("search-system");
        //创建一个原子引用用于保存查询结果
        AtomicReference<String> result = new AtomicReference<>();
        //通过静态方法，调用Props的构造器，创建Props对象
        Props props = Props.create(QuestionQuerier.class, question, engines, result);
        //通过system.actorOf(props,name)创建一个 问题查询器Actor
        final ActorRef querier = system.actorOf(props, "master");
        //告诉问题查询器开始查询
        querier.tell(new Object(), ActorRef.noSender());

        //通过while无限循环 等待actor进行查询，知道产生结果
        while (null == result.get()) ;
        //关闭 Actor系统
        system.terminate();
        //返回结果
        return result.get();
    }

    /**
     * <p>Title:通过多个搜索引擎查询多个条件，并返回第一条查询结果 </p>
     *
     * @author 韩超 2018/3/6 14:15
     */
    public static void main(String[] args) {
        //通过工具类获取搜索引擎列表
        List<String> engines = EngineUtils.getEngineList();
        //通过 Actor 进行并发查询，获取最先查到的答案
        String result = new FlavorActorDemo().getFirstResult("今天你吃了吗？", engines);
        //打印结果
//        LOGGER.info(result);
    }
}
