# 准备工作

1、创建一个空工程 maven_dependency_conflict_demo，在 maven_dependency_conflict_demo 创建不同的 Maven 工程模块，用于演示本文的一些点。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MmMxZDAzNTNkNjk2MzQ0YjRiYzQwMmQ2YmY0YTAzMTZfZ2lUZEtaZzI0RjF0bG8xdHBtWjE3Y1hURFFWSHo2Vk5fVG9rZW46QjczZmJoV01ibzVpcTZ4YVBlNGM3UlZTbkdjXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

2、创建本地仓库，用作版本管理。将 .idea 文件夹添加到 .gitignore。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZWFiOThiN2E5MzkwOTE0Y2UyYzMxZjViYjI1NGM3YWZfMjhHcko5N0ZFMVBQbmZtR2ZKMHR5c3R5QlNpbDFvamNfVG9rZW46T3pYc2IxVTlibzY2RHZ4anBPWmN4bGhZbmZmXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

3、在 GitHub 上创建同名远程仓库 maven_dependency_conflict_demo，建立本地仓库与远程仓库的关联，方便以后 push 代码。

# 什么是依赖冲突？

- 当**引入同一个依赖的多个不同版本**时，就会发生依赖冲突。

## 演示 1

步骤：

1、在 maven_dependency_conflict_demo 中创建 2 个基于 Maven 的 JavaSE 项目 maven_00 和 maven_01。

2、在 maven_01 的 pom.xml 文件中引入 junit 4.12 版本。

3、将 maven_01 打包安装到私服。

4、在 maven_00 的 pom.xml 文件中引入 junit 4.13 版本和 maven_01。

结果如下图所示，可以看到，的的确确发生了依赖冲突，而且 Maven 自己处理了依赖冲突。Maven 处理冲突依据的是短路径优先原则，后面会提到。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=NmE0OTM3ZDhiMzc0MzE5YzE2MmZmYjk2M2E5NzM4MGNfZkdjWFpoWnVLODNXbDR0SjkzU2VFd2Y2WGdsNTBBSjNfVG9rZW46R1p4bmJvVlVqbzJySDh4TjQzRWN5RzhRbmtmXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

# Maven的依赖调解原则

Maven 在遇到依赖冲突时，会先根据**路径最短优先**和**声明顺序优先**两大原则处理冲突。

## 1、**短路径优先**

当一个间接依赖存在多条引入路径时，引入路径短的会被解析使用。

见演示 1，依赖关系图如下图所示：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=YmQ0NmQyMjA2M2YzY2M2OWY4ZTI5ZTYxMDJhNzg2NTNfMlJIMldYOG5PNXcyU3l2TE5nbklVelpqUmpZZEpTY0ZfVG9rZW46RDVBbWJNSXZ4b1ZSREx4Zmo3aWNUNmxqbmNjXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

> 附：代码分支 2411291-larryla-maven-name-first。

## 2、**声明顺序优先**

如果存在短路径，则优先选择短路径。如果路径相同，则先声明者优先。

### 演示 2

步骤：

1、在 maven_dependency_conflict_demo 中创建 3 个基于 Maven 的 JavaSE 项目 maven_02、maven_03 和 maven_04。

2、在 maven_03 中引入 junit 4.12 版本。

3、将 maven_03 打包安装到私服。

4、在 maven_04 中引入 junit 4.13 版本。

5、将 maven_04 打包安装到私服。

6、在 maven_02 中引入 maven_03 和 maven_04，查看 junit 版本引入情况。

上面步骤对应的依赖关系图如下：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZmM4OTI0NGQwZjM1N2VjYjBjM2FkNjAxYjhmOTJkNTFfdEhOZkxhZTBhelNVdHZvU0VTRjdlVDRPRjc1U3o2cFlfVG9rZW46VnZPdmJwSmRsb05vQlB4cXJKWmNLNlBZbkRoXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

结果如下：

(1) 若 maven_03 在 maven_04 前面，则 maven_02 中**引入的是** **junit** **4.12**

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MjVmMjFmZmY3OTNhY2ZmNDhkZGE3NDczMTVlN2EyNzRfdExGSEVDMTVyNWh4MDE4NTA1VnVFc2RtTHlkVkFQNG1fVG9rZW46TUpMRGJPcTlWb1owb1F4WjAyQmNrNnlKbmhjXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

(2) 若 maven_04 在 maven_03 前面，则 maven_02 中**引入的是** **junit** **4.13**

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjFjNmE3YmQ1MzY3ZTg2NzA0ZjZlNzMxYWM3NmQwMmNfV09ucGd5THNOQUtWaDUxRjJWa0JweDN4SmZLN0Z3NklfVG9rZW46S3FaTGJaUU9zb1d1OVN4dVp0cWN0Y1RWbjNmXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

> 附：代码分支 2411291-larryla-maven-name-first。

## 3、特殊情况

**(1) 子** **pom** **覆盖父 pom。**

如果在父工程和子工程引入了相同依赖的不同版本，那么子工程中的版本会覆盖覆盖父工程中的版本。

步骤：

1、在 maven_dependency_conflict_demo 中创建 2 个基于 Maven 的 JavaSE 项目 maven_07 和 maven_08。

2、在 maven_07 中引入 junit 4.13 版本。

3、在 maven_08 重新指定的 junit 为 4.12 版本。

结果如下图所示，可以看到，子工程引入的是 junit 4.12 版本，而不是继承自父工程的 4.13 版本，这说明子工程覆盖了父工程的版本。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=OWNkZDNhNjEwNjJhZjRlNWM2NzY3ZDA4NWY0YWViYjNfVUNTbU5hcm1takJHbExBNlA0YXRhVHE4Zmt5MTk4NkVfVG9rZW46STdMV2JJa1hub1hqb2p4VXMxcWNSRDBvbjVlXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

**(2) 同一个 pom.xml 文件里配置相同资源的不同版本，后配置的覆盖先配置的。**

在 maven_07 的 pom.xml 文件中先后引入 MyBatis 3.5.11 和 3.5.7 版本，结果如下图。可以看到，后声明的 3.5.7 版本覆盖了先声明的 3.5.11 版本。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=OGQ2NzVjMjcyMjdkNmYxYTNjNGQyMmVhZmIwNGM5NTVfRGJsdHluVHpvQjdGNDVrSDBSTmJSMVZid3IzaGdmOFpfVG9rZW46TkxwY2IwWVFqb01HT1F4eUdOWWNFOWhMblFmXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

# 排除依赖

## 情景

如果仅仅依靠 Maven 来进行依赖调解，在很多情况下是不适用的，这时需要我们手动排除依赖。

举个例子，当前项目存在下面的依赖关系：

```Java
依赖链路一：A -> B -> C -> X(2.0.0) // dist = 3
依赖链路二：A -> D -> X(1.0.0) // dist = 2
```

根据路径最短优先原则，X(1.0.0) 会被解析使用，也就是说 A 中实际用的是 1.0.0 版本的 X。但是，这样可能会导致错误。

**假如 X(2.0.0) 相较于 X(1.0.0) 新增了 1 个类和 1 个方法，并且 A 中使用了新增的类和方法，编译不通过。**

## 问题演示

步骤：

1、在 maven_dependency_conflict_demo 中创建 5 个基于 Maven 的 JavaSE 项目 maven_a、maven_b、maven_c 、maven_d 和 maven_x。

2、在 maven_x 的 1.0.0 版本打包安装到私服的 Release 仓库。

3、将 maven_x 的 2.0.0 版本打包安装到私服的 Release 仓库。

4、在 maven_c 中引入 maven_x 的 2.0.0 版本；将 maven_c 打包安装到私服的 Release 仓库。在 maven_b 中引入 maven_c；将 maven_b 打包安装到私服的 Release 仓库。在 maven_a 中引入 maven_b。

5、在 maven_d 中引入 maven_x 的 1.0.0 版本，将 maven_d 打包安装到私服的 Release 仓库。在 maven_a 中引入 maven_d。

上面步骤对应的依赖关系图如下：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=NGNiOTc0ZDYzZTU5N2VlMzVhMWQ5YWRhYjFlZTkwYjBfZHdqTzd5bDdVajVhSGpxWjVITkxkN2F1UG94cmRCS1hfVG9rZW46VHZrOGJPMWxIb2RXN1Z4SHVnZmNaWWxhbm5oXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

maven_x 1.0.0 版本：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjBlY2EwZGE5YjRjZDRmZTgyYzMxYWYwZGY5MzAxMDNfQjB1R3djZ1prOTdxR2k5NnhYNXNTMDhtUjAweEhzTlZfVG9rZW46STc4MGJHVVQ5b1psZkd4RVFsTmMzb1ZxbjlnXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

在 2.0.0 版本中，变化如下：

- 在 BallSports 类中，新增方法 playFootBall。
- 新增类 SwimmingSports，类中包含方法 FreeStyle。

结果如下图，依据短路径优先原则，在 maven_a 模块中引入了 maven_x 1.0.0 版本，舍弃了 maven_x 2.0.0 版本，所以**无法使用 2.0.0 版本中新增的类和方法**。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MDQ4MTkxMTA2NmIxNWRmMDlkNjkwZjU5Mjg2NWI3YThfS0s2UW9wNTNSRVNhNHJhZUZidG9CT2pCUHhDSTc2MjFfVG9rZW46SVExUWJaODc0bzcxWDh4NVdvT2NiYzlzblRjXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

## 解决方案

一般在解决依赖冲突的时候，我们都会**优先保留版本较高的**，这是因为大部分 jar 在升级的时候都会做到向下兼容。

### 1、exclusions标签

具体做法是，在 maven_a 的 pom.xml 文件中使用 exclusion 标签显式排除掉 maven_x 1.0.0 版本的依赖。结果如下图所示，成功引入 maven_x 2.0.0 版本，舍弃 1.0.0 版本。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=Zjk2ZTNhNmQ0NWI1NGEzNDc1MjU2ODcwNzQ4MDUyMjdfbEpPTllBRm5DSWM0alJXOU5sMGVuV2pxbjVPM2hsZkxfVG9rZW46UDZJaWIza296b3RBaUZ4R1VaZmMwdVdWbmlnXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

### 2、optional标签

我们还可以使用 optional 标签，对外隐藏当前所依赖的资源。

具体做法是，在 maven_d 中引入 maven_x 1.0.0 版本时指定 optional 标签值为 true。

结果如下图所示，可以看到，使用 optional 标签可以使 maven_x 1.0.0 版本的 jar 包不再通过 maven_d 传递，也就是 maven_a 在引入 maven_b 时，得不到 maven_x 1.0.0 的 jar 包。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MDFjZTMxZGI2ZmUzZjQ0N2QyZGNkYzA3YzUwZTQzZjlfQ3NzbnozNmdEUDlaYnpOZjZGVE1DMmpzR1lRbW1zNmhfVG9rZW46TnN0bmIyVzZIb2ltakZ4OTVWd2NUb3publh1XzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

> 附：代码分支 2411292-larryla-maven-dependency-exclusion。

### 对比：<exclusions>标签和<optional>标签的区别

由上可知，在 maven_a → maven_d → maven_x 这条分支上，我们有两种解决依赖冲突的方法，即 exclusions 标签和 optional 标签。两种方法的本质都是不将 maven_x 1.0.0 jar 包传递到 maven_a 中。但区别在于：

- 排除依赖是在 maven_a 引入 maven_d 依赖时，同时设置`<exclusions>`，maven_a  知道 maven_x 1.0.0 的存在，主动将其排除掉。这种适用于**不能修改 maven_d 的配置文件的情况**。
- 可选依赖是在 maven_d 上为其依赖项 maven_x 设置`<optional>`，是 maven_d 不让"外界"知道依赖 maven_x 的存在，不再传递依赖 maven_x，此时，maven_a 不知道 maven_x 1.0.0 的存在。这种适用于**可以修改 maven_d 的配置文件的情况**。

### 思考 1

在"问题演示"中，我们复现了短路径优先可能导致的问题，即 maven_a 会引入 maven_x 1.0.0 版本，舍弃 maven_x 2.0.0 版本。那么问题来了，既然引入了 maven_x 1.0.0 版本，那么 maven_b 和 maven_c 会用哪个版本的 maven_x 呢？

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjNjOTAwMjdhODZjYTc5NDhjNzgwMzk5MDQ2NDQ4ZTVfV1M3b3FSd0dIVE5rZVBIdXc1RkdWRExTMm9Scnl1QlRfVG9rZW46VFFSSGJYeVRmb2s4ZjB4UnY4UGNySElSblZlXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

结果如下，由编辑区的方法调用和右侧的依赖管理可以得知，maven_a 为了解决依赖冲突选择引入 maven_x 1.0.0 版本，而 maven_b 和 maven_c 不受 maven_a 的影响，引入的依旧是 maven_x 2.0.0 版本：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MDNlYzkxYzBkYjY4ZWJlZTFjMGQ5MzRjZDAwOWFlZDVfWXZLc2NWRUU4V3o4b2pROTRiVEM0ZTBRaVZDS2hhQmNfVG9rZW46RzltMmJESjBmb1ZNSkx4cnBUQWNZdGprblZiXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

因此，上面给出的依赖传递图不是很精确的，应该如下：

- maven_a 引入 maven_x 1.0.0
- maven_b、maven_c 引入 maven_x 2.0.0

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjFiYTJlNmM5NWU3MTRjMTdmMmM2YmIyZTAxODg0Y2VfQ0VhdThSMlJxNlVlQ0dVY2ZyOEg1N3NnczI4aTcydlFfVG9rZW46VkZXaGJJZnJ0b3FXcDN4UFV4WWNyd1k2bnNlXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

> 附：代码分支 2411292-larryla-maven-dependency-exclusion。

### 思考 2

对于"情景"一节，Maven 在依据短路径优先原则调解冲突时可能会带来一些问题。假如 X(2.0.0) 相较于 X(1.0.0) 新增了 1 个类和 1 个方法：

- A 中使用了新增的类，编译不通过。
- A 中使用了新增的方法，编译不通过。

我们在"问题演示"一节复现了上面两个问题，并且在"解决方案"一节中使用两种方案 exclusions 和 optional，引入了高版本 maven_x 2.0.0 解决了这个问题。

**但是，引入高版本就没有问题了吗？**

还是下图的依赖关系，假如 maven_a 引入了 maven_x 2.0.0 版本。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MWJiYWNkYTFhMDhmOWE5MWE5Y2NiYjQ0NmRkNTYwOWFfbFNWOHNEd2xUN3Y0Vkh3azdWM2RmSlRWQ1NGWGZoc0dfVG9rZW46U2VJZGJIM1dGb0pMS2d4S3J0Q2NFM1hhbnpnXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

假如 maven_x 2.0.0 相比于 maven_x 1.0.0 版本删掉了一个类，但 maven_a 已经引用了，这时会报错；maven_x 2.0.0 相比于 maven_x 1.0.0 版本删掉了一个方法，但 maven_a 已经引用了，同样也会报错。

#### 演示

我们重新组织 maven_x 1.0.0 和 maven_x 2.0.0 版本包括的内容。

maven_x 1.0.0 版本内容如下：

- BallSports：playBasketBall 方法、playFootBall 方法
- SwimmingSports：FreeStyle 方法

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MWQ1ZWRmN2E4MzVkYWJkZjUyZDI0Y2Y2ODM4ZTFkMTBfMDFTRElJWUlQOW01TjlTWlVUbDM0WHk4ZU03YWpmVW5fVG9rZW46TGlJdmJsRDVSb3hvV3B4ZHlhNWNTNHdxbmhiXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

maven_x 1.0.0 版本内容变化如下：

- 删掉 BallSports 类中的 playBasketBall 方法
- 删掉 SwimmingSports 类

步骤：

1、在 maven_x 的 1.0.0 版本打包安装到私服的 Release 仓库。

2、将 maven_x 的 2.0.0 版本打包安装到私服的 Release 仓库。

3、在 maven_c 中引入 maven_x 的 2.0.0 版本；将 maven_c 打包安装到私服的 Release 仓库。在 maven_b 中引入 maven_c；将 maven_b 打包安装到私服的 Release 仓库。在 maven_a 中引入 maven_b。

4、在 maven_d 中引入 maven_x 的 1.0.0 版本，将 maven_d 打包安装到私服的 Release 仓库。在 maven_a 中引入 maven_d。

结果如下，可以看到 maven_a 中引入的是 maven_x 2.0.0 版本，无法使用 2.0.0 版本相比 1.0.0 版本删掉的方法和类，直接编译不通过。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MDE4NjY2NDY4NTUzODE5NDY5ZDQxY2MxNTc0NWEyNTNfZjNzU0JlYzltYWFlc0dRV2RsaHRMQW1KdWF5ZUNoVWZfVG9rZW46WE1QeGJaaUdab0xLMjl4Vk5qV2NXS210bjJmXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

> 附：代码分支 2411301-larryla-maven-dependency-exclusion-high-ver-problem。

# 聚合工程，统一管理版本

聚合工程：一个项目允许创建多个子模块，多个子模块组成一个整体，可以统一进行项目的构建。要弄明白聚合工程，得先清楚"父子工程"的概念：

- 父工程：没有任何代码、仅有`pom.xml`的空项目，用来定义公共依赖、插件和配置；
- 子工程：编写具体代码的子项目，可以继承父工程的依赖项、配置，还可以独立拓展。

而 Maven 聚合工程，就是基于父子工程结构，将一个完整项目，划分出不同的层次。这种方式可以很好的管理多模块之间的依赖关系，以及构建顺序，大大提高了开发效率、维护性。

**为了防止不同子工程引入不同版本的依赖，在父工程中，统一对依赖的版本进行控制，规定所有子工程都使用同一版本的依赖**，可以使用`<dependencyManagement>`标签来管理。

子工程在使用`<dependencyManagement>`中已有的依赖项时，不需要写`<version>`版本号，版本号在父工程中统一管理，这样做的好处在于：以后为项目的技术栈升级版本时，不需要单独修改每个子工程的`POM`，只需要修改父`POM`文件即可，减少了版本冲突的可能性。

## 演示

步骤：

1、在 maven_dependency_conflict_demo 中创建 2 个基于 Maven 的 JavaSE 项目 maven_05 和 maven_06。

2、在 maven_05 中引入 junit 4.13 版本，使用 dependencyManagement 标签统一管理。

3、在 maven_06 继承 maven_05 的 junit 4.13 版本。

上面步骤对应的依赖关系图如下：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=YzcxMTk3ZmRkOTk0OGRiNTk4YTY0OWNkMDJiYmYxNGJfVUJLTlVpeFZ5ZEd6N1I2YnV1T0lmS3J1VHNTc3paTk5fVG9rZW46TGp6VWJCb001bzVTejZ4emZ2NGNDV2NVbmdmXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

结果如下图所示，可以看到，在 maven_06 工程中，只需要指定 groupId 和 artifactId，不需要指定 version，就可以从父工程 maven_05 中继承 junit 4.13 依赖。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZGZhODhlOTVkYjdlZDY1ZDM1ZjNkMGU1Nzk5MmFlMjdfSmtCVVVLQ00yUXE2d3gxaTQyM1ZlUGR5dWtTMExMVU5fVG9rZW46TmhaOWJMTGxYbzRrSUd4Z2tzb2N5M3JwbmtkXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

## 其他

1、子工程继承父工程的依赖时，可以指定版本，这时子工程的版本会覆盖父工程的版本。

演示：

还是使用"演示"一节的模块。在 maven_06 模块中指定 junit 的版本为 4.12，那么 maven_06 引入的 junit 将是 4.12 版本，而不是父工程的 4.13 版本：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZjVmMTVlYjNiOTg5NTgxMmZmZDA1ZmUyY2JlMjgzZTZfWW9TT1FacW02UzRXekF6OGtWS2p5dlROZkVKQ1o1V1NfVG9rZW46WWk3eGI3UGpibzYyVVd4VWwzU2NSR1M5bm9mXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

虽然可以在子工程中指定其他版本，但不建议这么做。如果这样做的话，使用 dependencyManagement 的意义就不大了。

2、父工程可以使用 properties 标签指定依赖的版本，然后在 GAV 坐标中引入该版本。这样在升级依赖版本时，只需要修改 properties 标签中变量的值，而无需修改 GAV 中的 version 标签的值。

演示：

还是使用"演示"一节的模块。在 maven_05 的 pom.xml 文件中，在 properties 标签中自定义一个 junit-version 标签，用于指定 junit 依赖的版本号。然后，在 version 标签中使用 ${junit-version} 的形式声明版本号。如下图所示，maven_06 依旧成功引入了 junit 4.13 版本。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZTE3MTNlZmU3MjViZjdhOTI4NTMzNWIxNzkyODQyOThfWE4xblJQNTlsSm1menBOWkRFM3VlSDhZMzdySTJNdGpfVG9rZW46UnZKVGJ1eU1Lb1JteXp4d0RHSmNrMzRkblliXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

3、dependencyManagement 标签只是用于声明依赖，不会在父工程中引入依赖。

如下图所示，只是在聚合工程的子工程中引入了 junit 依赖，父工程并未引入依赖。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZDM4OTM2ZjY5M2VhZDkyMjM0YmY4MzNjYjVlNTQ3N2RfT3dXRDl3dHBRZXNNSlhDVTRyR2E5a1lSTjh4VWZNV2VfVG9rZW46WmE1ZWJCWUNSb2ptMnB4N2ViNWMzVjI2bjZnXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

4、如果在父工程中使用`dependencyManagement`，则需要在子工程中使用`dependencies`显式地引入依赖，否则，子工程将无法继承父工程声明的依赖。

5、dependencyManagement 和 dependencies 的区别

- `<dependencies>`：定义强制性依赖，写在该标签里的依赖项，子工程必须继承。
- `<dependencyManagement>`：定义可选性依赖，该标签里的依赖项，子工程可选择性地继承。

> 附：代码分支 2411302-larryla-maven-dependency-uni-manage

# 排查依赖冲突

## 1、使用Maven Helper插件

在 MySQL 中，我们会借助一些工具进行慢 SQL 分析。那么，在 Maven 中，我们是否可以借助一些工具进行依赖冲突定位呢？

我们可以在插件市场下载 "Maven Helper" 插件辅助排查依赖冲突。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=M2ZiYzZjMzQwYjQ2NDVlMWY3MThkODc0ODEwMDQ5OTVfSzZKZ0dLWUxmTngzWEZRWE1neFhsYUJiRG8xMFpCQzVfVG9rZW46T3JpTWI4TVcxb3VCam54TFBJYWNRU0plbjdiXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

在"排除依赖-情景"一节中，我们分析了如下依赖冲突情况，得到的结论是 Maven 会依据短路径优先原则引入 X(1.0) 版本。

```Java
依赖链路一：A -> B -> C -> X(2.0.0) // dist = 3
依赖链路二：A -> D -> X(1.0.0) // dist = 2
```

接下来，我们使用 Maven Helper 插件来验证一下。

打开 maven_a 的 pom.xml 文件，在下方点击 Dependency Analyzer，然后点击 Refresh UI，会弹出如下界面：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MTMzNWQxNzVkMzIyZmU0NDgxYjhkY2E1OGRlMmZlNzZfTTNBc0c0Y3A3cW1SM3doN0MwOXVoMWU1UHhMME9RNUtfVG9rZW46UDZzdWJSYk1zb1d1UUp4aXNnYmN0Q3k1blBiXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

提示有一个依赖冲突，即 maven_x: 1.0.0。我们点开看看，发现与之冲突的是 maven_x 2.0.0。这就和上面呼应上了，Maven 依据短路径优先原则，选择了 1.0.0 版本，舍弃了 2.0.0 版本。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=OGZhZWJhM2Y1NDBiODZlNzliZDA0NDI0ZDY3YWVjYThfRnlSWHB5WjRrZ2FEcWZ2MUx2aUdyczF0dktBQlE4b0lfVG9rZW46TUlpYmJOUExabzNuQjd4RjlSNmNMTXdxbkRkXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

在"排除依赖-解决方案"一节中，我们基于排除依赖引入了 2.0.0 版本，这里，我们同样保留 2.0.0 版本。在 1.0.0 上右键，选择 exclude，表示排除掉该版本。然后点击上面的 Reimport，再点击 Refresh UI。

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=NjEyZGRhZDQxZmU5OTU2YWQwMzcwZDQxN2UyNWEyNDFfdWFDTTRqc04xMHJieUJCUWNmM1VNcTdqRFZ5Vm9VMGtfVG9rZW46UUJCQ2JrOTAxb1pJSzl4bHJSemNkYUxnbkdGXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

返回到 pom.xml 文件，发现点击上面的 exclude 按钮，就是在 pom.xml 文件中使用了 exclusions 标签：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=MzRiMTFiNWI0ZmIwNTFkYzI3ZjVhZDA0YjllNjMxYjNfa1EyNGxobThObjFBNWdHM0syRmtKdEhIT0FRVm1ocm5fVG9rZW46TXhqOGI2aVR2b3J6Vzd4Vzh5ZGNlZmM1bndjXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

## 2、查看项目的依赖树

可以使用 maven-dependency-plugin 插件的 tree 目标列出当前项目的依赖树（dependency tree）。

### 准备工作

(1) 在 maven_dependency_conflict_demo 中创建 2 个基于 Maven 的 JavaSE 项目 maven_09 和 maven_10。

(2) 在 maven_10 的 pom.xml 文件中引入 junit 4.12 版本。

(3) 将 maven_10 打包安装到私服。

(4) 在 maven_09 的 pom.xml 文件中引入 junit 4.13 版本和 maven_10。

(5) 在 maven_09 的 pom.xml 文件中引入如下依赖：

```XML
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.22</version>
</dependency>
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson-spring-boot-starter</artifactId>
    <version>3.23.4</version>
</dependency>
```

(6) 查看 maven_09 模块的依赖树。

依赖关系如下图所示：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=ZWU0ZjA1ZGI5ZWVhMjAxMWQ4ZjgwNTI2MzRmODIzZWVfT2RBQ1BOZVdHemFOOGM2ZTh6Z0VHeWFzV1RsWGJadFBfVG9rZW46VzZzRWJqeEdQb0htVnJ4WnR6c2MyNEFnbjhzXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

项目 pom 如下图：

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=NGE4NGNlZmZjZTY3YjQzNTk5ODc2NDJlMmFhYzE2NDJfbmJIOFpCTXBBc1E4UXlHQjI4d0w4YkVFYng5Z3BiS1dfVG9rZW46UVI1ZWIxdTQyb0d0Rlp4U1RqdmN2QnhmbmpmXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

### 演示

执行 mvn dependency:tree，查看 maven_09 模块的依赖树：

```Shell
(base) PS C:\...\maven_09> mvn dependency:tree                  
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------------< cn.myphoenix:maven_09 >------------------------
[INFO] Building maven_09 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ maven_09 ---
[INFO] cn.myphoenix:maven_09:jar:1.0-SNAPSHOT
[INFO] +- junit:junit:jar:4.13:compile
[INFO] |  \- org.hamcrest:hamcrest-core:jar:1.3:compile
[INFO] +- mysql:mysql-connector-java:jar:8.0.22:compile
[INFO] |  \- com.google.protobuf:protobuf-java:jar:3.11.4:compile
[INFO] +- org.redisson:redisson-spring-boot-starter:jar:3.23.4:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-actuator:jar:3.1.1:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-starter:jar:3.1.1:compile
[INFO] |  |  |  +- org.springframework.boot:spring-boot:jar:3.1.1:compile
[INFO] |  |  |  |  \- org.springframework:spring-context:jar:6.0.10:compile
[INFO] |  |  |  |     \- org.springframework:spring-expression:jar:6.0.10:compile
[INFO] |  |  |  +- org.springframework.boot:spring-boot-autoconfigure:jar:3.1.1:compile
[INFO] |  |  |  +- org.springframework.boot:spring-boot-starter-logging:jar:3.1.1:compile
[INFO] |  |  |  |  +- ch.qos.logback:logback-classic:jar:1.4.8:compile
[INFO] |  |  |  |  |  \- ch.qos.logback:logback-core:jar:1.4.8:compile
[INFO] |  |  |  |  +- org.apache.logging.log4j:log4j-to-slf4j:jar:2.20.0:compile
[INFO] |  |  |  |  |  \- org.apache.logging.log4j:log4j-api:jar:2.20.0:compile
[INFO] |  |  |  |  \- org.slf4j:jul-to-slf4j:jar:2.0.7:compile
[INFO] |  |  |  +- jakarta.annotation:jakarta.annotation-api:jar:2.1.1:compile
[INFO] |  |  |  +- org.springframework:spring-core:jar:6.0.10:compile
[INFO] |  |  |  |  \- org.springframework:spring-jcl:jar:6.0.10:compile
[INFO] |  |  |  \- org.yaml:snakeyaml:jar:1.33:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-actuator-autoconfigure:jar:3.1.1:compile
[INFO] |  |  |  +- org.springframework.boot:spring-boot-actuator:jar:3.1.1:compile
[INFO] |  |  |  \- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.15.2:runtime
[INFO] |  |  +- io.micrometer:micrometer-observation:jar:1.11.1:compile
[INFO] |  |  |  \- io.micrometer:micrometer-commons:jar:1.11.1:compile
[INFO] |  |  \- io.micrometer:micrometer-core:jar:1.11.1:compile
[INFO] |  |     +- org.hdrhistogram:HdrHistogram:jar:2.1.12:runtime
[INFO] |  |     \- org.latencyutils:LatencyUtils:jar:2.0.3:runtime
[INFO] |  +- org.springframework.boot:spring-boot-starter-data-redis:jar:3.1.1:compile
[INFO] |  |  \- org.springframework.data:spring-data-redis:jar:3.1.1:compile
[INFO] |  |     +- org.springframework.data:spring-data-keyvalue:jar:3.1.1:compile
[INFO] |  |     |  \- org.springframework.data:spring-data-commons:jar:3.1.1:compile
[INFO] |  |     +- org.springframework:spring-tx:jar:6.0.10:compile
[INFO] |  |     |  \- org.springframework:spring-beans:jar:6.0.10:compile
[INFO] |  |     +- org.springframework:spring-oxm:jar:6.0.10:compile
[INFO] |  |     +- org.springframework:spring-aop:jar:6.0.10:compile
[INFO] |  |     \- org.springframework:spring-context-support:jar:6.0.10:compile
[INFO] |  +- org.redisson:redisson:jar:3.23.4:compile
[INFO] |  |  +- io.netty:netty-common:jar:4.1.96.Final:compile
[INFO] |  |  +- io.netty:netty-codec:jar:4.1.96.Final:compile
[INFO] |  |  +- io.netty:netty-buffer:jar:4.1.96.Final:compile
[INFO] |  |  +- io.netty:netty-transport:jar:4.1.96.Final:compile
[INFO] |  |  +- io.netty:netty-resolver:jar:4.1.96.Final:compile
[INFO] |  |  +- io.netty:netty-resolver-dns:jar:4.1.96.Final:compile
[INFO] |  |  |  \- io.netty:netty-codec-dns:jar:4.1.96.Final:compile
[INFO] |  |  +- io.netty:netty-handler:jar:4.1.96.Final:compile
[INFO] |  |  |  \- io.netty:netty-transport-native-unix-common:jar:4.1.96.Final:compile
[INFO] |  |  +- javax.cache:cache-api:jar:1.1.1:compile
[INFO] |  |  +- io.projectreactor:reactor-core:jar:3.5.3:compile
[INFO] |  |  +- org.reactivestreams:reactive-streams:jar:1.0.4:compile
[INFO] |  |  +- io.reactivex.rxjava3:rxjava:jar:3.1.6:compile
[INFO] |  |  +- org.jboss.marshalling:jboss-marshalling:jar:2.0.11.Final:compile
[INFO] |  |  +- org.jboss.marshalling:jboss-marshalling-river:jar:2.0.11.Final:compile
[INFO] |  |  +- com.esotericsoftware:kryo:jar:5.5.0:compile
[INFO] |  |  |  +- com.esotericsoftware:reflectasm:jar:1.11.9:compile
[INFO] |  |  |  +- org.objenesis:objenesis:jar:3.3:compile
[INFO] |  |  |  \- com.esotericsoftware:minlog:jar:1.3.1:compile
[INFO] |  |  +- org.slf4j:slf4j-api:jar:1.7.36:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.15.2:compile
[INFO] |  |  +- com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:jar:2.15.2:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-core:jar:2.15.2:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.15.2:compile
[INFO] |  |  +- net.bytebuddy:byte-buddy:jar:1.14.5:compile
[INFO] |  |  \- org.jodd:jodd-bean:jar:5.1.6:compile
[INFO] |  |     \- org.jodd:jodd-core:jar:5.1.6:compile
[INFO] |  \- org.redisson:redisson-spring-data-31:jar:3.23.4:compile
[INFO] \- cn.myphoenix:maven_10:jar:1.0-SNAPSHOT:compile
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.058 s
[INFO] Finished at: 2024-12-12T19:57:59+08:00
[INFO] ------------------------------------------------------------------------
```

junit 会根据短路径优先原则引入 4.13 版本，而对于 maven_10 中的传递依赖 junit 4.12 版本直接没有显示。也就是说，**只会显示当前项目实际使用的依赖，对于有冲突的依赖则不会显示**。

### 其他

**1、-Dverbose 参数**

使用`-Dverbose`参数获取更详细的信息。如果想要更详细的输出，包括传递**是否被排除**等信息，可以添加`-Dverbose`参数：

```Shell
mvn dependency:tree -Dverbose
```

如下所示，标红的依赖是冲突的依赖，Maven 自己舍弃了这些版本：

```Shell
(base) PS C:\...\maven_09> mvn dependency:tree -Dverbose
[INFO] Scanning for projects...
[INFO]
[INFO] -----------------------< cn.myphoenix:maven_09 >------------------------
[INFO] Building maven_09 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO]
[INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ maven_09 ---
[INFO] cn.myphoenix:maven_09:jar:1.0-SNAPSHOT
[INFO] +- junit:junit:jar:4.13:compile
[INFO] |  \- org.hamcrest:hamcrest-core:jar:1.3:compile
[INFO] +- mysql:mysql-connector-java:jar:8.0.22:compile
[INFO] |  \- com.google.protobuf:protobuf-java:jar:3.11.4:compile
[INFO] +- org.redisson:redisson-spring-boot-starter:jar:3.23.4:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-actuator:jar:3.1.1:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-starter:jar:3.1.1:compile
[INFO] |  |  |  +- org.springframework.boot:spring-boot:jar:3.1.1:compile
[INFO] |  |  |  |  +- (org.springframework:spring-core:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |  |  |  \- org.springframework:spring-context:jar:6.0.10:compile
[INFO] |  |  |  |     +- (org.springframework:spring-aop:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |  |  |     +- (org.springframework:spring-beans:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |  |  |     +- (org.springframework:spring-core:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |  |  |     \- org.springframework:spring-expression:jar:6.0.10:compile
[INFO] |  |  |  |        \- (org.springframework:spring-core:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |  |  +- org.springframework.boot:spring-boot-autoconfigure:jar:3.1.1:compile
[INFO] |  |  |  |  \- (org.springframework.boot:spring-boot:jar:3.1.1:compile - omitted for duplicate)
[INFO] |  |  |  +- org.springframework.boot:spring-boot-starter-logging:jar:3.1.1:compile
[INFO] |  |  |  |  +- ch.qos.logback:logback-classic:jar:1.4.8:compile
[INFO] |  |  |  |  |  +- ch.qos.logback:logback-core:jar:1.4.8:compile
[INFO] |  |  |  |  |  \- (org.slf4j:slf4j-api:jar:2.0.7:compile - omitted for conflict with 2.0.2)
[INFO] |  |  |  |  +- org.apache.logging.log4j:log4j-to-slf4j:jar:2.20.0:compile
[INFO] |  |  |  |  |  +- org.apache.logging.log4j:log4j-api:jar:2.20.0:compile
[INFO] |  |  |  |  |  \- (org.slf4j:slf4j-api:jar:1.7.36:compile - omitted for conflict with 2.0.7)
[INFO] |  |  |  |  \- org.slf4j:jul-to-slf4j:jar:2.0.7:compile
[INFO] |  |  |  |     \- (org.slf4j:slf4j-api:jar:2.0.7:compile - omitted for duplicate)
[INFO] |  |  |  +- jakarta.annotation:jakarta.annotation-api:jar:2.1.1:compile
[INFO] |  |  |  +- org.springframework:spring-core:jar:6.0.10:compile
[INFO] |  |  |  |  \- org.springframework:spring-jcl:jar:6.0.10:compile
[INFO] |  |  |  \- org.yaml:snakeyaml:jar:1.33:compile
[INFO] |  |  +- org.springframework.boot:spring-boot-actuator-autoconfigure:jar:3.1.1:compile
[INFO] |  |  |  +- org.springframework.boot:spring-boot-actuator:jar:3.1.1:compile
[INFO] |  |  |  |  \- (org.springframework.boot:spring-boot:jar:3.1.1:compile - omitted for duplicate)
[INFO] |  |  |  +- (org.springframework.boot:spring-boot:jar:3.1.1:compile - omitted for duplicate)
[INFO] |  |  |  +- (org.springframework.boot:spring-boot-autoconfigure:jar:3.1.1:compile - omitted for duplicate)
[INFO] |  |  |  +- (com.fasterxml.jackson.core:jackson-databind:jar:2.15.2:compile - scope updated from runtime; omitted for duplicate)
[INFO] |  |  |  \- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.15.2:runtime
[INFO] |  |  |     +- (com.fasterxml.jackson.core:jackson-annotations:jar:2.15.2:runtime - omitted for duplicate)
[INFO] |  |  |     +- (com.fasterxml.jackson.core:jackson-core:jar:2.15.2:runtime - omitted for duplicate)
[INFO] |  |  |     \- (com.fasterxml.jackson.core:jackson-databind:jar:2.15.2:runtime - omitted for duplicate)
[INFO] |  |  +- io.micrometer:micrometer-observation:jar:1.11.1:compile
[INFO] |  |  |  \- io.micrometer:micrometer-commons:jar:1.11.1:compile
[INFO] |  |  \- io.micrometer:micrometer-core:jar:1.11.1:compile
[INFO] |  |     +- (io.micrometer:micrometer-commons:jar:1.11.1:compile - omitted for duplicate)
[INFO] |  |     +- (io.micrometer:micrometer-observation:jar:1.11.1:compile - omitted for duplicate)
[INFO] |  |     +- org.hdrhistogram:HdrHistogram:jar:2.1.12:runtime
[INFO] |  |     \- org.latencyutils:LatencyUtils:jar:2.0.3:runtime
[INFO] |  +- org.springframework.boot:spring-boot-starter-data-redis:jar:3.1.1:compile
[INFO] |  |  +- (org.springframework.boot:spring-boot-starter:jar:3.1.1:compile - omitted for duplicate)
[INFO] |  |  \- org.springframework.data:spring-data-redis:jar:3.1.1:compile
[INFO] |  |     +- org.springframework.data:spring-data-keyvalue:jar:3.1.1:compile
[INFO] |  |     |  +- org.springframework.data:spring-data-commons:jar:3.1.1:compile
[INFO] |  |     |  |  +- (org.springframework:spring-core:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     |  |  +- (org.springframework:spring-beans:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     |  |  \- (org.slf4j:slf4j-api:jar:2.0.2:compile - omitted for conflict with 2.0.7)
[INFO] |  |     |  +- (org.springframework:spring-context:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     |  +- (org.springframework:spring-tx:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     |  \- (org.slf4j:slf4j-api:jar:2.0.2:compile - omitted for duplicate)
[INFO] |  |     +- org.springframework:spring-tx:jar:6.0.10:compile
[INFO] |  |     |  +- org.springframework:spring-beans:jar:6.0.10:compile
[INFO] |  |     |  |  \- (org.springframework:spring-core:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     |  \- (org.springframework:spring-core:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     +- org.springframework:spring-oxm:jar:6.0.10:compile
[INFO] |  |     |  +- (org.springframework:spring-beans:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     |  \- (org.springframework:spring-core:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     +- org.springframework:spring-aop:jar:6.0.10:compile
[INFO] |  |     |  +- (org.springframework:spring-beans:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     |  \- (org.springframework:spring-core:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     +- org.springframework:spring-context-support:jar:6.0.10:compile
[INFO] |  |     |  +- (org.springframework:spring-beans:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     |  +- (org.springframework:spring-context:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     |  \- (org.springframework:spring-core:jar:6.0.10:compile - omitted for duplicate)
[INFO] |  |     \- (org.slf4j:slf4j-api:jar:2.0.2:compile - omitted for conflict with 1.7.36)
[INFO] |  +- org.redisson:redisson:jar:3.23.4:compile
[INFO] |  |  +- io.netty:netty-common:jar:4.1.96.Final:compile
[INFO] |  |  +- io.netty:netty-codec:jar:4.1.96.Final:compile
[INFO] |  |  |  +- (io.netty:netty-common:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- (io.netty:netty-buffer:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  \- (io.netty:netty-transport:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  +- io.netty:netty-buffer:jar:4.1.96.Final:compile
[INFO] |  |  |  \- (io.netty:netty-common:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  +- io.netty:netty-transport:jar:4.1.96.Final:compile
[INFO] |  |  |  +- (io.netty:netty-common:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- (io.netty:netty-buffer:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  \- (io.netty:netty-resolver:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  +- io.netty:netty-resolver:jar:4.1.96.Final:compile
[INFO] |  |  |  \- (io.netty:netty-common:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  +- io.netty:netty-resolver-dns:jar:4.1.96.Final:compile
[INFO] |  |  |  +- (io.netty:netty-common:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- (io.netty:netty-buffer:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- (io.netty:netty-resolver:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- (io.netty:netty-transport:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- (io.netty:netty-codec:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- io.netty:netty-codec-dns:jar:4.1.96.Final:compile
[INFO] |  |  |  |  +- (io.netty:netty-common:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  |  +- (io.netty:netty-buffer:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  |  +- (io.netty:netty-transport:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  |  \- (io.netty:netty-codec:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  \- (io.netty:netty-handler:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  +- io.netty:netty-handler:jar:4.1.96.Final:compile
[INFO] |  |  |  +- (io.netty:netty-common:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- (io.netty:netty-resolver:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- (io.netty:netty-buffer:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- (io.netty:netty-transport:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  +- io.netty:netty-transport-native-unix-common:jar:4.1.96.Final:compile
[INFO] |  |  |  |  +- (io.netty:netty-common:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  |  +- (io.netty:netty-buffer:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  |  \- (io.netty:netty-transport:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  |  \- (io.netty:netty-codec:jar:4.1.96.Final:compile - omitted for duplicate)
[INFO] |  |  +- javax.cache:cache-api:jar:1.1.1:compile
[INFO] |  |  +- io.projectreactor:reactor-core:jar:3.5.3:compile
[INFO] |  |  |  \- (org.reactivestreams:reactive-streams:jar:1.0.4:compile - omitted for duplicate)
[INFO] |  |  +- org.reactivestreams:reactive-streams:jar:1.0.4:compile
[INFO] |  |  +- io.reactivex.rxjava3:rxjava:jar:3.1.6:compile
[INFO] |  |  |  \- (org.reactivestreams:reactive-streams:jar:1.0.4:compile - omitted for duplicate)
[INFO] |  |  +- org.jboss.marshalling:jboss-marshalling:jar:2.0.11.Final:compile
[INFO] |  |  +- org.jboss.marshalling:jboss-marshalling-river:jar:2.0.11.Final:compile
[INFO] |  |  |  \- (org.jboss.marshalling:jboss-marshalling:jar:2.0.11.Final:compile - omitted for duplicate)
[INFO] |  |  +- com.esotericsoftware:kryo:jar:5.5.0:compile
[INFO] |  |  |  +- com.esotericsoftware:reflectasm:jar:1.11.9:compile
[INFO] |  |  |  +- org.objenesis:objenesis:jar:3.3:compile
[INFO] |  |  |  \- com.esotericsoftware:minlog:jar:1.3.1:compile
[INFO] |  |  +- org.slf4j:slf4j-api:jar:1.7.36:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.15.2:compile
[INFO] |  |  +- com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:jar:2.15.2:compile
[INFO] |  |  |  +- (com.fasterxml.jackson.core:jackson-databind:jar:2.15.2:compile - omitted for duplicate)
[INFO] |  |  |  +- (org.yaml:snakeyaml:jar:2.0:compile - omitted for conflict with 1.33)
[INFO] |  |  |  \- (com.fasterxml.jackson.core:jackson-core:jar:2.15.2:compile - omitted for duplicate)
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-core:jar:2.15.2:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.15.2:compile
[INFO] |  |  |  +- (com.fasterxml.jackson.core:jackson-annotations:jar:2.15.2:compile - omitted for duplicate)
[INFO] |  |  |  \- (com.fasterxml.jackson.core:jackson-core:jar:2.15.2:compile - omitted for duplicate)
[INFO] |  |  +- net.bytebuddy:byte-buddy:jar:1.14.5:compile
[INFO] |  |  \- org.jodd:jodd-bean:jar:5.1.6:compile
[INFO] |  |     \- org.jodd:jodd-core:jar:5.1.6:compile
[INFO] |  \- org.redisson:redisson-spring-data-31:jar:3.23.4:compile
[INFO] |     +- (org.springframework.data:spring-data-redis:jar:3.1.0:compile - omitted for conflict with 3.1.1)
[INFO] |     \- (org.redisson:redisson:jar:3.23.4:compile - omitted for duplicate)
[INFO] \- cn.myphoenix:maven_10:jar:1.0-SNAPSHOT:compile
[INFO]    \- (junit:junit:jar:4.12:compile - omitted for conflict with 4.13)
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.846 s
[INFO] Finished at: 2024-12-12T20:04:27+08:00
[INFO] ------------------------------------------------------------------------
```

**2、查看指定依赖**

使用`-Dincludes`参数过滤依赖。

有时候可能只对特定的依赖感兴趣，可以使用`-Dincludes`参数来实现这一点。例如：

```Shell
# 只显示与 'org.springframework' 相关的依赖
mvn dependency:tree -Dincludes=org.springframework.*
```

**只显示与 'org.springframework' 相关的依赖：**

```Shell
(base) PS C:\...\maven_09> mvn dependency:tree -Dincludes="org.springframework.*"
[INFO] Scanning for projects...
[INFO] 
[INFO] -----------------------< cn.myphoenix:maven_09 >------------------------
[INFO] Building maven_09 1.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-dependency-plugin:2.8:tree (default-cli) @ maven_09 ---
[INFO] cn.myphoenix:maven_09:jar:1.0-SNAPSHOT
[INFO] \- org.redisson:redisson-spring-boot-starter:jar:3.23.4:compile
[INFO]    +- org.springframework.boot:spring-boot-starter-actuator:jar:3.1.1:compile
[INFO]    |  +- org.springframework.boot:spring-boot-starter:jar:3.1.1:compile
[INFO]    |  |  +- org.springframework.boot:spring-boot:jar:3.1.1:compile
[INFO]    |  |  +- org.springframework.boot:spring-boot-autoconfigure:jar:3.1.1:compile
[INFO]    |  |  \- org.springframework.boot:spring-boot-starter-logging:jar:3.1.1:compile
[INFO]    |  \- org.springframework.boot:spring-boot-actuator-autoconfigure:jar:3.1.1:compile
[INFO]    |     \- org.springframework.boot:spring-boot-actuator:jar:3.1.1:compile
[INFO]    \- org.springframework.boot:spring-boot-starter-data-redis:jar:3.1.1:compile
[INFO]       \- org.springframework.data:spring-data-redis:jar:3.1.1:compile
[INFO]          \- org.springframework.data:spring-data-keyvalue:jar:3.1.1:compile
[INFO]             \- org.springframework.data:spring-data-commons:jar:3.1.1:compile
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.060 s
[INFO] Finished at: 2024-12-12T20:16:13+08:00
[INFO] ------------------------------------------------------------------------
```

可以看到，显示的都是以 org.springframework 开头的依赖。

# 思维导图

![img](https://tw9tzjv8tr6.feishu.cn/space/api/box/stream/download/asynccode/?code=NjMzZWJjM2U5ZDMxOTU1YmNiZTI0Y2QzNmY3MjZmNzBfUTczeHpwTUJDd1pGSEpiZWo2VVRZTWxpd3UwUmpKVmpfVG9rZW46V3NOaWJhWWRjb21OQ294OHlLMWNOdlIwbnFoXzE3MzQxNjczNjY6MTczNDE3MDk2Nl9WNA)

代码仓库：https://github.com/Acura-bit/maven_dependency_conflict_demo.git