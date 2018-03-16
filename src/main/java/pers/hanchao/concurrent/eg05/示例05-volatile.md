# 示例05-volatile

volatile 易变类型
轻量级synchronized，编码少，开销小，功能少

volatile 保障可见性，不保障原子性和有序性

使用volatile的前提，不受原子性和有序性影响--》原子性操作、状态独立

使用volatile而非synchronized的主要原因：1.编写简单，2.性能较优，3.非阻塞


https://www.ibm.com/developerworks/cn/java/j-jtp06197.html

