# 示例12-ConcurrentCollections

# List 列表

- CopyOnWriteArrayList                      done
	
# Set 集合

- CopyOnWriteArraySet 	                    done
	
- ConcurrentSkipListSet                     done

# Map 映射

- ConcurrentHashMap                         done
    
- ConcurrentSkipListMap                     done
	
# Deque 双端队列
    
- ConcurrentLinkedDeque                     3.---
	- 非阻塞队列
	- http://ifeve.com/concurrent-collections-2/
	
- LinkedBlockingDeque                       4.+++
	- 阻塞队列
	- 链表形式
	- 无界队列
	- http://ifeve.com/concurrent-collections-3/
	
# Queue 队列

- ConcurrentLinkedQueue                     3.---
	- 非阻塞队列
	- https://www.jianshu.com/p/9e73b9216322
	- http://ifeve.com/concurrent-collections-2/
	
	
- LinkedTransferQueue                       5.+++
	- 阻塞队列，专用于生产者与消费者
	- http://cmsblogs.com/?p=2433
	
- DelayQueue                                6.+++
	- 阻塞队列，存储延迟元
	- https://www.jianshu.com/p/2659eb72134b
	
- SynchronousQueue                          7.+++
	- 阻塞队列
	- 直传队列
	- http://ifeve.com/java-synchronousqueue/
	
- ArrayBlockingQueue                        4.---
	- 阻塞队列
	- 数组形式
	- 有界队列
	- http://www.importnew.com/25566.html
	
- LinkedBlockingQueue                       4.---
	- 阻塞队列
	- 链表形式
	- 无界队列
	- http://www.importnew.com/25583.html
	
- PriorityBlockingQueue                     4.---
	- 阻塞列表
	- 使用优先级排序元素
	- http://www.importnew.com/25541.html

----------
由于私人原因，暂时没有太多时间用于<font color=darkcyan>**并发集合类型**</font>的实例学习上面。

所以从本章开始，后续<font color=darkcyan>**并发集合类型**</font>相关文章都是转载文章，特此说明。

本文还在完善中，只能作为大概参考。

----------
### 并发集合简介
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
		- 链接：[Java并发43:并发集合系列-基于写时复制的CopyOnWriteArrayList和CopyOnWriteArraySet](https://blog.csdn.net/hanchao5272/article/details/79846293)
- <font color=darkcyan>**Set 集合**</font>
	- **CopyOnWriteArraySet** : 
		- 基于<font color=green>**写时复制(Copy-On-Write)思想**</font>实现。
		- 适用于<font color=green>**读多写少**</font>的并发场景，如黑名单，白名单等等。
		- <font color=green>**并发读的性能高**</font>；<font color=red>**并发写的空间消耗大**</font>。
		- 只能保证数据的<font color=green>**最终一致性**</font>，而非<font color=red>**实时一致性**</font>。		
		- <font color=green>**读不会阻塞**</font>，<font color=red>**写会阻塞**</font>。
		- 链接：[Java并发43:并发集合系列-基于写时复制的CopyOnWriteArrayList和CopyOnWriteArraySet](https://blog.csdn.net/hanchao5272/article/details/79846293)
	- **ConcurrentSkipListSet :** 
		- 基于<font color=green>**跳表(SkipList)**</font>实现。
		- <font color=green>**跳表(SkipList)**</font>可以用来替代红黑树，使用概率均衡技术，插入、删除操作更简单、更快。
		- <font color=green>**跳表(SkipList)**</font>本质上是<font color=red>**以空间换取时间**</font>。
		- <font color=green>**ConcurrentSkipListSet**</font>是通过<font color=red>**ConcurrentSkipListMap**</font>实现的。
		- <font color=green>**ConcurrentSkipListMap**</font>中的元素是<font color=green>**key-value键值对**</font>，而<font color=red>**ConcurrentSkipListSet**</font>只用到了前者中的<font color=red>**key**</font>。
		- 连接：[Java并发45:并发集合系列-基于跳表的ConcurrentSkipListSet和ConcurrentSkipListMap](https://blog.csdn.net/hanchao5272/article/details/79859087)
- <font color=darkcyan>**Map 映射**</font>
	- ConcurrentHashMap : 
		- 基于<font color=red>**所使用的锁分段技术**</font>实现，减小锁粒度，减少数据竞争的概率。
		- <font color=green>**get操作高效**</font>之处在于整个get过程<font color=red>**不需要加锁**</font>，除非读到的值是空的才会加锁重读。
		- <font color=green>**get操作的高效**</font>的根本原因在于采用了<font color=green>**volatile关键字来保持多线程可见性**</font>。
		- <font color=green>**put操作**</font>之前首先需要进行<font color=red>**Segment加锁**</font>；但不会影响其他<font color=red>**Segment锁**</font>。
		- <font color=green>**put操作**</font>造成的扩容时，<font color=red>**不是对整个容器进行双倍扩容**</font>，而<font color=red>**只对某个segment进行双倍扩容**</font>，节省了大量空间。
		- <font color=green>**size操作**</font>：先尝试2次通过不锁住Segment的方式来统计各个Segment大小，如果统计的过程中，容器的count发生了变化，则再采用加锁的方式来统计所有Segment的大小。
	- ConcurrentSkipListMap : 
		- 基于<font color=green>**跳表(SkipList)**</font>实现。
		- <font color=green>**跳表(SkipList)**</font>可以用来替代红黑树，使用概率均衡技术，插入、删除操作更简单、更快。
		- <font color=green>**跳表(SkipList)**</font>本质上是<font color=red>**以空间换取时间**</font>。
		- <font color=green>**ConcurrentSkipListSet**</font>是通过<font color=red>**ConcurrentSkipListMap**</font>实现的。
		- <font color=green>**ConcurrentSkipListMap**</font>中的元素是<font color=green>**key-value键值对**</font>，而<font color=red>**ConcurrentSkipListSet**</font>只用到了前者中的<font color=red>**key**</font>。
		- 连接：[Java并发45:并发集合系列-基于跳表的ConcurrentSkipListSet和ConcurrentSkipListMap](https://blog.csdn.net/hanchao5272/article/details/79859087)
- <font color=darkcyan>**Deque 双端队列**</font>
	- ConcurrentLinkedDeque : 
	- LinkedBlockingDeque : 
- <font color=darkcyan>**Queue 队列**</font>
	- ConcurrentLinkedQueue : 
	- LinkedTransferQueue : 
	- DelayQueue : 
	- SynchronousQueue : 
	- ArrayBlockingQueue : 
	- LinkedBlockingQueue : 
	- PriorityBlockingQueue : 

-----
在后续章节中，将分别对这些<font color=darkcyan>**并发集合类型**</font>进行学习。




