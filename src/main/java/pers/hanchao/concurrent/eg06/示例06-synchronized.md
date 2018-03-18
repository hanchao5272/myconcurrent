# 示例06-synchronized

synchronized关键字：同步关键字，用来创建同步代码块和同步方法。

几种使用方式：

0 无同步        no
10 同步代码块-加锁对象是类普通变量      类的全部对象 no
11 同步代码块-加锁对象是类普通变量      类的单个对象 ok
12 同步代码块-加锁对象是类静态变量      类的全部对象 ok
13 同步代码块-加锁对象是共享变量        类的全部对象 ok
14 同步代码块-加锁对象是类对象          类的全部对象 ok
20 普通同步方法     类的全部对象 no
21 普通同步方法     类的单个对象 ok
21 静态同步方法     类的全部对象 ok

### 2.2.同步代码块和同步方法
**相同点：**

- **同步代码块**和**同步方法**都通过**synchronized关键字**实现
- **同步代码块**和**同步方法**都能够保证代码的同步性，即：原子性、有序性和可见性。

**不同的：**

- **同步代码块**比**同步方法**的锁住的范围更小，所以性能更好。
- **同步方法**比**同步代码块**的编写更简单，只需要在方法定义是加上**synchronized关键字**即可，而后者还需要确定加锁对象。

## 5.synchronized关键字的用法总结

通过前面的实际学习和测试，现在将<font color=darkc>synchronized关键字的用法</font>总结如下：

|序号|大类|小类（锁定对象）|锁定范围|可同步对象|不同步对象|
|---|---|---|---|---|---|
|1|<font color=green>同步代码块|加锁对象是<font color=darkc>本地变量|方法块{}内的代码|<font color=blue>单个</font>对象|<font color=red>多个对象|
|2|<font color=green>同步代码块|加锁对象是<font color=darkc>类静态变量|方法块{}内的代码|多个对象|-|
|3|<font color=green>同步代码块|加锁对象是<font color=darkc>共享变量|方法块{}内的代码|多个对象|-|
|4|<font color=green>同步代码块|加锁对象是<font color=darkc>类对象|方法块{}内的代码|多个对象|-|
||||||
|5|<font color=darkcyan>同步方法|修饰的是<font color=cyan>普通方法|整个方法|<font color=blue>单个</font>对象|<font color=red>多个对象|
|6|<font color=darkcyan>同步方法|修饰的是<font color=cyan>静态方法|整个方法|多个对象|-|



