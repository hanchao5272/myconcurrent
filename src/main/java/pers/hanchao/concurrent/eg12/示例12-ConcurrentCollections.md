# 示例12-ConcurrentCollections

# List 列表

- CopyOnWriteArrayList                      1.+++
	- {修改时进行拷贝}的数组列表
	- 数组的拷贝开销很大。
	- 尤其适用于极少更新，但是存在大量查询操作的场景。
	
# Set 集合

- CopyOnWriteArraySet 	                    1.---
	- 采用{修改时进行拷贝的数组}实现的并发集合
	- 数组的拷贝开销很大。
	- 尤其适用于极少更新，但是存在大量查询操作的场景。
	
- ConcurrentSkipListSet                     2.+++
	- 采用{跳表}实现的并发集合

# Map 映射

- ConcurrentHashMap                         3.+++
    - 非阻塞映射
    
- ConcurrentSkipListMap                     2.---
	- 采用{跳表}实现的并发映射

- ConcurrentNavigableMap                    4.+++
	- 非阻塞
	- 可导航
	
# Deque 双端队列
    
- ConcurrentLinkedDeque                     3.---
	- 非阻塞队列
	
- LinkedBlockingDeque                       5.+++
	- 阻塞队列
	- 链表形式
	- 无界队列
	
# Queue 队列

- ConcurrentLinkedQueue                     3.---
	- 非阻塞队列
	
- LinkedTransferQueue                       6.+++
	- 阻塞队列，专用于生产者与消费者
	
- DelayQueue                                7.+++
	- 阻塞队列，存储延迟元
	
- SynchronousQueue                          8.+++
	- 阻塞队列
	- 直传队列
	
- ArrayBlockingQueue                        5.---
	- 阻塞队列
	- 数组形式
	- 有界队列
	
- LinkedBlockingQueue                       5.---
	- 阻塞队列
	- 链表形式
	- 无界队列
	
- PriorityBlockingQueue                     5.---
	- 阻塞列表
	- 使用优先级排序元素









