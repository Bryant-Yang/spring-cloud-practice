接触Spring Cloud皮毛后，做了一些基本尝试来对基于Spring Cloud技术栈实施微服务开发有个初步了解

其实就是阅读了 [Spring Cloud学习-Eureka、Ribbon和Feign](https://www.jianshu.com/p/0aef3724e6bc) 之后，自己通过idea完全新建和手过了一遍而已，完全没有什么业务逻辑，就是看看“是什么”，和“怎么用“。

因为偏好python, php, 以及js之类的脚本语言，自己打造全栈产品一般不打算重度使用java技术栈，不过netflix以springboot为基础，确实在微服务领域做了很多工作，很多前言架构思想值得学习。

不打算做个学究，仅就个人理解简单的梳理一下，不负责展开和深入，不确保与官方权威一致。

##1. eureka
后端要做的各种业务，按照业务或代码逻辑等进行分类后，拆分成一个独立的服务，可以分布式的部署在任何服务器，本质上还是解耦和复用。eureka为这些细分的服务提供服务注册和发现等支持。
### eureka-server
首先做一个服务注册中心，idea可以很方便的通过springboot-Initializr可以很方便的new一个这样的应用，在依赖中勾选eureka server即可，手动敲得话也就是maven或者gradle的包管理中加入spring-cloud-starter-netflix-eureka-server依赖（这种带有spring-cloud-starter的东西就是把常见的依赖包分类整合，不用一个一个找而已，用python pip包好像从来没纠结过这些...）。注册中心管理注册的各个服务实例，通过心跳等机制进行基本的监控，可以直接用浏览器打开应用的web地址，看到eureka注册中心后台页面。
### eureka-client
建立注册中心后会有一个url开放给其他应用服务来进行注册，新建其他服务的应用，并在application配置文件中写入注册服务的url，运行后就可以注册进来。这个注册中心相对于其他服务，相当于一个server，其他服务对于这个server就是client，虽然它们对于前端来说都是server端。

这里注册的服务，往往分为服务提供者，和服务消费者，并非严格区分，仅仅是彼此执行业务逻辑过程中用到对方而已。你都微服务了，既要为前端充当服务端，也为自己依赖的其他服务充当客户端。服务注册在中心后，就能被彼此发现（不需要彼此通过写死url和端口找到对方，而是通过注册的服务名）

## 2. Ribbon
主要用于负载均衡，比如注册多个相同的服务来执行商品任务（多个相同的springboot应用，不同的地址或端口，相同的服务名），通过同样的一个服务名组成的url，ribbon自动根据负载均衡策略找合适的服务实例执行请求。具体形式一般是，某个服务去调用其他服务执行任务时，代码不是简单的做个http请求要数据，而是通过@LoadBalanced注解加持的 RestTemplate bean高大上的自动搞定这件事。嗯，就是几乎自动的，没什么神秘的，要是自己写大概也是查一下各服务器的负载健康指标，然后找到合适的发http请求拿数据呗；又或者你自己定个策略轮询平均分配请求给不同的服务资源，大概就是这样，总之你自己再怎么写也没人家搞得这套厉害就是了。写顺了就用吧，事实上大概很多产品压根活不到让你真的需要负载均衡的时候。

## 3.Feign
简单理解就是把还需要http://xxx这种url的调用，彻底变成java的接口方法，用的时候压根不用了解网络请求细节。Feign是基于Ribbon封装的，负载均衡特性自然也支持，当然展开了说肯定还有一大堆细节和特性。一句话的话就是用@FeignClient注解加持一个interface，通过服务名，人家把这个服务的http调用替你实现了。
（吐槽一下：怎么感觉无非就是一堆牛逼哄哄的语法糖么）

## 4. Zuul / Spring Cloud Gateway
上面 1，2，3 其实本质上来看，只是服务端彼此调用服务的机制和方法。（或者说java实现的服务端彼此调用服务，目前大概没几个人用python写了web服务给eureka注册玩吧）。整体来讲，对外还得提供一个相对统一的网关来提供web api，不管是前端也好，别人家的服务调用你也好。Zuul 现在有1，2两个版本，同样的工具还有Spring Cloud Gateway。Zuul1看了介绍是请求处理是同步阻塞式的，以及不支持长连接（例如提供websocket网关服务），Spring Cloud Gateway是异步处理请求并支持长连接的。作用与nginx有点相似，说起来对于那些玩openresty（nginx+lua）的人即便这辈子没听过spring cloud，一样能搞定他们的事。



## 末了吐槽
最后还是感慨一下：技术这种东西是无涯的，优质高效的解决当下的问题是王道，具备解决问题的思路和评估价值的眼光为佳。如果为了技术去折腾，就爱玩茴字6种写法，只能说汝生也有涯，而知也无涯，以有涯随无涯，die啦！