[\[超级链接：Java并发学习系列-绪论\]](http://blog.csdn.net/hanchao5272/article/details/79437370)

----------
由于私人原因，暂时没有太多时间用于<font color=darkcyan>**并发集合类型**</font>的实例学习上面。

所以从本章开始，后续<font color=darkcyan>**并发集合类型**</font>相关文章都是转载文章，特此说明。

这些转载文章的叙述角度各不相同，不过不影响我们通过这些文章对并发集合有一个初步的理解。

----------
<font color=darkc>**集合**</font>

编程，离不开<font color=darkcyan>**数据结构**</font>。

JDK提供了Java<font color=darkcyan>**集合框架**</font>(Java Collections framework)，它包括可以用来实现多种不同的数据结构的接口、类和算法，如HaspMap、ArrayList等等。

我们在使用集合框架的时候，需要十分小心以保证其多线程的<font color=darkcyan>**安全性**</font>，因为大多数集合类并没有对并发访问进行控制。

-----
<font color=darkc>**并发集合**

为了解决这些集合框架造成的安全性问题，JDK逐渐提供了越来越多的<font color=darkcyan>**并发集合类型**</font>。

我们在<font color=darkcyan>**并发环境中**</font>，使用这些并发集合，不会产生数据不一致的问题。

-----
<font color=darkc>**阻塞与非阻塞**

JDK提供我们的<font color=darkcyan>**并发集合类型**</font>，按照<font color=darkcyan>**阻塞方式**</font>分为两种：

- <font color=darkcyan>**阻塞队列**</font>
	- 包含添加操作：如果不能立即进行添加，则是因为集合已满；执行该操作的线程将<font color=darkcyan>**被阻塞，直到添加成功**</font>。
	- 包含删除操作：如果不能立即进行删除，则是因为集合已空；执行该操作的线程将<font color=darkcyan>**被阻塞，直到删除成功**</font>。
- <font color=darkcyan>**非阻塞队列**</font>
	- 包含添加操作：如果不能立即进行添加，<font color=darkcyan>**则将返回null值或抛出异常**</font>。
	- 包含删除操作：如果不能立即进行删除，<font color=darkcyan>**则将返回null值或抛出异常**</font>。

-----
<font color=darkc>**集合类型**

JDK提供我们的<font color=darkcyan>**并发集合类型**</font>，按照集合类型分为以下五种：

- <font color=darkcyan>**List 列表**</font>
	- **CopyOnWriteArrayList** :
		- 基于<font color=green>**写时复制(Copy-On-Write)思想**</font>实现。
		- 适用于<font color=green>**读多写少**</font>的并发场景，如黑名单，白名单等等。
		- <font color=green>**并发读的性能高**</font>；<font color=red>**并发写的空间消耗大**</font>。
		- 只能保证数据的<font color=green>**最终一致性**</font>，而非<font color=red>**实时一致性**</font>。		
		- <font color=green>**读不会阻塞**</font>，<font color=red>**写会阻塞**</font>。
		- 链接：[Java并发44:并发集合系列-基于写时复制的CopyOnWriteArrayList和CopyOnWriteArraySet](https://blog.csdn.net/hanchao5272/article/details/79846293)
- <font color=darkcyan>**Set 集合**</font>
	- **CopyOnWriteArraySet** : 
		- 基于<font color=green>**写时复制(Copy-On-Write)思想**</font>实现。
		- 适用于<font color=green>**读多写少**</font>的并发场景，如黑名单，白名单等等。
		- <font color=green>**并发读的性能高**</font>；<font color=red>**并发写的空间消耗大**</font>。
		- 只能保证数据的<font color=green>**最终一致性**</font>，而非<font color=red>**实时一致性**</font>。		
		- <font color=green>**读不会阻塞**</font>，<font color=red>**写会阻塞**</font>。
		- 链接：[Java并发44:并发集合系列-基于写时复制的CopyOnWriteArrayList和CopyOnWriteArraySet](https://blog.csdn.net/hanchao5272/article/details/79846293)
	- **ConcurrentSkipListSet :** 
		- 基于<font color=green>**跳表(SkipList)**</font>实现。
		- <font color=green>**跳表(SkipList)**</font>可以用来替代红黑树，使用概率均衡技术，插入、删除操作更简单、更快。
		- <font color=green>**跳表(SkipList)**</font>本质上是<font color=red>**以空间换取时间**</font>。
		- <font color=green>**ConcurrentSkipListSet**</font>是通过<font color=red>**ConcurrentSkipListMap**</font>实现的。
		- <font color=green>**ConcurrentSkipListMap**</font>中的元素是<font color=green>**key-value键值对**</font>，而<font color=red>**ConcurrentSkipListSet**</font>只用到了前者中的<font color=red>**key**</font>。
		- 链接：[Java并发45:并发集合系列-基于跳表的ConcurrentSkipListSet和ConcurrentSkipListMap](https://blog.csdn.net/hanchao5272/article/details/79859087)

- <font color=darkcyan>**Map 映射**</font>
	- ConcurrentSkipListMap : 
		- 基于<font color=green>**跳表(SkipList)**</font>实现。
		- <font color=green>**跳表(SkipList)**</font>可以用来替代红黑树，使用概率均衡技术，插入、删除操作更简单、更快。
		- <font color=green>**跳表(SkipList)**</font>本质上是<font color=red>**以空间换取时间**</font>。
		- <font color=green>**ConcurrentSkipListSet**</font>是通过<font color=red>**ConcurrentSkipListMap**</font>实现的。
		- <font color=green>**ConcurrentSkipListMap**</font>中的元素是<font color=green>**key-value键值对**</font>，而<font color=red>**ConcurrentSkipListSet**</font>只用到了前者中的<font color=red>**key**</font>。
		- 链接：[Java并发45:并发集合系列-基于跳表的ConcurrentSkipListSet和ConcurrentSkipListMap](https://blog.csdn.net/hanchao5272/article/details/79859087)
	- ConcurrentHashMap : 
		- 基于<font color=red>**所使用的锁分段技术**</font>实现，减小锁粒度，减少数据竞争的概率。
		- <font color=green>**get操作高效**</font>之处在于整个get过程<font color=red>**不需要加锁**</font>，除非读到的值是空的才会加锁重读。
		- <font color=green>**get操作的高效**</font>的根本原因在于采用了<font color=green>**volatile关键字来保持多线程可见性**</font>。
		- <font color=green>**put操作**</font>之前首先需要进行<font color=red>**Segment加锁**</font>；但不会影响其他<font color=red>**Segment锁**</font>。
		- <font color=green>**put操作**</font>造成的扩容时，<font color=red>**不是对整个容器进行双倍扩容**</font>，而<font color=red>**只对某个segment进行双倍扩容**</font>，节省了大量空间。
		- <font color=green>**size操作**</font>：先尝试2次通过不锁住Segment的方式来统计各个Segment大小，如果统计的过程中，容器的count发生了变化，则再采用加锁的方式来统计所有Segment的大小。
		- 链接：[Java并发46:并发集合系列-基于锁分段技术的ConcurrentHashMap](http://blog.csdn.net/hanchao5272/article/details/79859688)
- <font color=darkcyan>**Deque 双端队列**</font>
	- ConcurrentLinkedDeque : 
		- 基于<font color=green>**非阻塞CAS算法**</font>的<font color=red>**非阻塞双向无界列表**</font>。
		- <font color=green>**算法与ConcurrentLinkedQueue类似**</font>。
		- 不仅支持<font color=green>**FIFO**</font>操作，而且也支持<font color=red>**FILO**</font>操作。
		- 链接：[Java并发48:并发集合系列-基于CAS算法的非阻塞双向无界队列ConcurrentLinkedDueue](https://blog.csdn.net/hanchao5272/article/details/79947785)
	- LinkedBlockingDeque : 
		- 基于<font color=green>**独占锁ReenTratLock+链表**</font>的<font color=red>**阻塞双向无界队列**</font>。
		- 有<font color=green>**两个ReentrantLock的独占锁**</font>，分别用来控制元素<font color=red>**入队加锁和出队加锁**</font>。
		- 不仅支持<font color=green>**FIFO**</font>操作，而且也支持<font color=red>**FILO**</font>操作。
		- <font color=green>**size()方法和remove()方法**</font><font color=red>**在并发环境中较为精确**</font>。
		- <font color=green>**实现原来**</font>：<font color=red>**LinkedBlockingQueue 类似**</font>。
		- 如何实现<font color=green>**线程安全**</font>：<font color=red>**volatile变量 + ReenTrantLock独占锁**</font>。
		- 链接：[Java并发50:并发集合系列-基于独占锁实现的双向阻塞队列LinkedBlockingDeque](https://blog.csdn.net/hanchao5272/article/details/79948823)
- <font color=darkcyan>**Queue 队列**</font>
	- ConcurrentLinkedQueue : 
		- 基于<font color=green>**非阻塞CAS算法**</font>的<font color=red>**非阻塞单向无界列表**</font>。
		- 只支持<font color=green>**FIFO**</font>操作。
		- <font color=green>**size()方法和contains()方法**</font><font color=red>**在并发环境中并不精确**</font>。
		- <font color=green>**实际应用**</font>：<font color=red>**Tomcat中NioEndPoint**</font>。
		- 如何实现<font color=green>**线程安全**</font>：<font color=red>**volatile变量(head，tail)保证可见性，CAS操作保证原子性和有序性**</font>。
		- 链接：[Java并发47:并发集合系列-基于CAS算法的非阻塞单向无界列表ConcurrentLinkedQueue](https://blog.csdn.net/hanchao5272/article/details/79947143)
	- LinkedBlockingQueue : 
		- 基于<font color=green>**独占锁ReenTratLock+链表**</font>的<font color=red>**阻塞单向无界队列**</font>。
		- 有<font color=green>**两个ReentrantLock的独占锁**</font>，分别用来控制元素<font color=red>**入队加锁和出队加锁**</font>。
		- 只支持<font color=green>**FIFO**</font>操作。
		- <font color=green>**size()方法和remove()方法**</font><font color=red>**在并发环境中较为精确**</font>。
		- <font color=green>**实际应用**</font>：<font color=red>**tomcat中任务队列TaskQueue**</font>。
		- 如何实现<font color=green>**线程安全**</font>：<font color=red>**volatile变量 + ReenTrantLock独占锁**</font>。
		- 链接：[Java并发49:并发集合系列-基于独占锁实现的单向阻塞队列LinkedBlockingQueue](https://blog.csdn.net/hanchao5272/article/details/79948018)
	- ArrayBlockingQueue : 
		- 基于<font color=green>**独占锁ReenTratLock+数组**</font>的<font color=red>**阻塞单向有界队列**</font>。
		- 只有<font color=green>**一个全局ReentrantLock的独占锁**</font>，用来控制元素<font color=red>**入队加锁和出队加锁**</font>。
		- 只支持<font color=green>**FIFO**</font>操作。
		- <font color=green>**size()方法和remove()方法**</font><font color=red>**在并发环境中较为精确**</font>。
		- 如何实现<font color=green>**线程安全**</font>：<font color=red>**ReenTrantLock全局独占锁**</font>。
		- 链接：[Java并发51:并发集合系列-基于独占锁+数组实现的单向阻塞有界队列ArrayBlockingQueue](https://blog.csdn.net/hanchao5272/article/details/79949020)
	- PriorityBlockingQueue : 
		- 基于<font color=green>**独占锁ReenTratLock+二叉树最小堆**</font>的<font color=red>**阻塞单向无界优先级队列**</font>。
		- 只有<font color=green>**一个全局ReentrantLock的独占锁**</font>，用来控制元素<font color=red>**入队加锁和出队加锁**</font>。
		- <font color=green>**size()方法**</font><font color=red>**在并发环境中精确的**</font>。
		- 如何实现<font color=green>**线程安全**</font>：<font color=red>**ReenTrantLock全局独占锁**</font>。
		- 链接：[Java并发52:并发集合系列-基于独占锁+二叉树最小堆实现的单向阻塞无界优先级队列PriorityBlockingQueue](https://blog.csdn.net/hanchao5272/article/details/79949170)
	- DelayQueue : 
		- 基于<font color=green>**独占锁ReenTratLock+优先级阻塞队列PriorityBlockingQueue**</font>的<font color=red>**阻塞单向无界延时队列**</font>。
		- <font color=green>**最先过期的元素具有最高优先级**</font>。
		- 只有<font color=green>**一个全局ReentrantLock的独占锁**</font>，用来控制元素<font color=red>**入队加锁和出队加锁**</font>。
		- 如何实现<font color=green>**线程安全**</font>：<font color=red>**ReenTrantLock全局独占锁**</font>。
		- <font color=green>**主要用途**</font>：<font color=red>**缓存队列和超时任务处理**</font>。
		- 链接：[Java并发53:并发集合系列-基于独占锁+PriorityBlockingQueue实现的单向阻塞无界延时队列DelayQueue](https://blog.csdn.net/hanchao5272/article/details/79949737)
	- SynchronousQueue : 
		- 基于<font color=green>**CAS操作**</font>的<font color=red>**非阻塞无数据缓冲队列**</font>。
		- <font color=green>**不存在数据空间**</font>：<font color=red>**队列头元素是第一个排队要插入数据的线程，而不是要交换的数据**</font>。
		- 因为没有数据空间，<font color=green>**很多方法不可用**</font>：<font color=red>**peek、clear、contains、isEmpty、size、remove**</font>等等。
		- <font color=green>**应用场景**</font>：生产者的线程和消费者的<font color=green>**线程同步以传递某些信息、事件或者任务**</font>。
		- <font color=green>**应用实例**</font>：<font color=red>**Executors.newCachedThreadPool()**</font>。
		- <font color=green>**可以这样来理解**</font>：<font color=red>**生产者和消费者互相等待对方，握手，然后一起离开**</font>。
		- 链接：[Java并发54:并发集合系列-基于CAS算法的非阻塞无数据缓冲队列SynchronousQueue](https://blog.csdn.net/hanchao5272/article/details/79950112)
	- LinkedTransferQueue :
		- 基于<font color=green>**预占模式+链表**</font>的<font color=red>**单向阻塞无界队列**</font>。
		- <font color=green>**预占模式**</font>：<font color=red>**有就拿货走人，没有就占个位置等着，等到或超时**</font>。
		- <font color=green>**transfer算法**</font>采用所谓双重数据结构(dual data structures)：<font color=red>**保留与完成**</font>。
		- 只支持<font color=green>**FIFO**</font>操作。
		- <font color=green>**应用实例**</font>：<font color=red>**生产者与消费者**</font>。
		- 链接：[Java并发55:并发集合系列-基于预占模式+链表的单向阻塞无界队列LinkedTransferQueue](https://blog.csdn.net/hanchao5272/article/details/79950628)
 