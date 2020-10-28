测试1： 修改xmx对并行gc的影响

设置xmx分别为128m, 256m，均发生OOM。
设置xmx512m, 发生youngGC 25次，fullGC 13次，生成对象9265，
             每次young gc执行时间1-10ms，full gc执行时间29-32ms。

设置xmx1g，发生youngGC 25次，fullGC 2次，生成对象15686
           每次young gc执行时间4-19ms，full gc执行时间41-42ms。

设置xmx2g，发生youngGC 13次，fullGC 0次，生成对象18260
           每次young gc执行时间20-30ms。

设置xmx4g，发生youngGC 4次，fullGC 0次，生成对象16187
           每次young gc执行时间34-46ms。

结论：

1、当堆内存设置过小时，容易产生OOM
2、随着堆内存扩大，并行GC的发生次数明显减少，但单次gc耗费时间有所增加。
3、随着堆内存扩大，内存分配的速率逐渐提升，但到一定程度后趋于稳定。
4、从log可以看出，并行GC每执行一次full gc，young区的空间会被清空。



测试2：修改xmx对串行gc的影响

设置xmx分别为128m, 256m，均发生OOM
设置xmx为512m, 发生youngGC 18次，fullGC 1次，生成对象10102，每次gc执行时间20-40ms
设置xmx为2g, 发生youngGC 5次，fullGC 0次，生成对象12218，每次gc执行时间70-100ms

结论：

1、随着堆内存扩大，GC的发生次数明显减少，但单次gc执行时间明显增加。
2、堆内存的变化，对串行GC的内存分配速率影响不明显。
3、从log可以看出，对于串行GC，每次young区或old区只有一个发生GC。



测试3：修改xmx对CMS GC的影响

设置xmx为512m, 发生youngGC 23次，fullGC 12次，生成对象12248，
    每次yong gc执行时间7-27ms，每次full gc STW时间1-2ms
设置xmx为2g, 发生youngGC 6次，fullGC 1次，生成对象16352，
	每次yong gc执行时间23-64ms，每次full gc STW时间1-2ms
设置xmx为4g, 发生youngGC 7次，fullGC 0次，生成对象14499，
	每次yong gc执行时间26-79ms

结论：

1、随着堆内存扩大，GC的发生次数明显减少，但到一定程度后趋于稳定。
2、随着堆内存扩大，内存分配的速率逐渐提升，但到一定程度后趋于稳定。





测试4：修改xmx对G1 GC的影响

设置xmx为512m, 发生GC pause 69次 每次在1-12ms以内，生成对象9300，
设置xmx为2g, 发生GC pause 15次 每次pause时间在7-23ms以内，生成对象16957，
设置xmx为4g, 发生GC pause 14次 每次pause时间在11-34ms以内，生成对象16783，

结论：

1、随着堆内存扩大，GC的发生次数明显减少，但到一定程度后趋于稳定。
2、随着堆内存扩大，每次GC pause时间逐步上升。
2、随着堆内存扩大，内存分配的速率逐渐提升，但到一定程度后趋于稳定。




横向对比几个GC。结论：

1、当xmx为512m，串行GC、并行GC、CMS、G1对象生成效率很接近，CMS、G1的单次停顿时间明显低于其它GC。

2、内存变大到2G，串行GC的对象生成效率提升有限，并行GC、CMS、G1提升明显，并行GC > G1 >= CMS。

3、随着内存变大到4G 并行GC、CMS、G1对象生成效率趋于平缓。
