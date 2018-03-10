package pers.hanchao.concurrent.eg99.common;

import java.util.List;

/**
 * <p>快速搜索接口</p>
 * @author hanchao 2018/3/7 22:52
 **/
public interface IFastSearch {
    /** 调用多个搜索引擎进行问题，获取最快得到的答案 */
    String getFirstResult(String question, List<String> engines);
}
