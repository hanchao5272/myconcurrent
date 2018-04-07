# 示例12-ConcurrentCollections

# List 列表

- CopyOnWriteArrayList                      1.+++
	- {修改时进行拷贝}的数组列表
	- 数组的拷贝开销很大。
	- 尤其适用于极少更新，但是存在大量查询操作的场景。
	- http://ifeve.com/java-copy-on-write/
	
# Set 集合

- CopyOnWriteArraySet 	                    1.---
	- 采用{修改时进行拷贝的数组}实现的并发集合
	- 数组的拷贝开销很大。
	- 尤其适用于极少更新，但是存在大量查询操作的场景。
	- http://ifeve.com/java-copy-on-write/
	
- ConcurrentSkipListSet                     2.+++
	- 采用{跳表}实现的并发集合
	- http://kenby.iteye.com/blog/1187303
	- http://ifeve.com/concurrent-collections-6/

# Map 映射

- ConcurrentHashMap                         3.+++
    - 非阻塞映射
    - http://ifeve.com/concurrenthashmap/
    
- ConcurrentSkipListMap                     2.---
	- 采用{跳表}实现的并发映射
    - http://kenby.iteye.com/blog/1187303
    - http://ifeve.com/concurrent-collections-6/
	
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









