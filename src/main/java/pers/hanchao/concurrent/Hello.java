package pers.hanchao.concurrent;

import org.apache.log4j.Logger;

public class Hello {
    /** log4j */
    private static final Logger LOGGER = Logger.getLogger(Hello.class);
    /**
    * <p>测试junit4和log4j</p>
    * @author hanchao
    */
    public static Integer getOne(){
        LOGGER.info("Log4j is Ok!");
        return 1;
    }
}
