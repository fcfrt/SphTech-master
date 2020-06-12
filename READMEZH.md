# SphTech-master
## 应用程序描述

- App启动后从[https://data.gov.sg/dataset/mobile-data-usage](https://data.gov.sg/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=5) 获取数据，按照一年4个季度显示列表；
- 该应用程序可以显示2008年至2019年新加坡移动网络发送的在线和离线数据量,如果一年中任何一个季度的数据量下降，则在条目中显示一个可点击的图像，点击后显示下降数据提示框；
- 支持数据本地缓存

## 应用下载
[点击下载](http://d.firim.info/sphtech)
## 功能和依赖库

1. [`litepal`](https://github.com/LitePalFramework/LitePal)
2. [`RxHttp`](https://github.com/liujingxing/okhttp-RxHttp)
3. [`rxhttp-compiler`](https://github.com/liujingxing/okhttp-RxHttp)
4. [`BaseRecyclerViewAdapterHelper`](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
5. [`Swiperefreshlayout`](https://developer.android.google.cn/jetpack/androidx/releases/swiperefreshlayout?hl=zh-cn)
6. [`ImmersionBar`](https://github.com/gyf-dev/ImmersionBar)

## TO LIST

- [ ]  基础框架
  - [x] 自己写的简单基础的MVP
  - [x] 页面基础加载、提示
  - [x] 接口结构化、非结构化数据处理、数据分组
- [ ] 页面开发
  - [x] 显示列表数据
  - [x] 显示可点击的图片
  - [x] 数据缓存，在没有网络的情况下显示离线数据
- [x] 测试
  - [x] 核心逻辑代码单元测试。ps：各个框架都有相应的单元测试用例，因此第三方库忽略测试
  - [x] UI即兼容测试
  - [x] 测试（网络错误、服务器内部错误）
  - [x] 非结构化数据处理、数据分组测试
## 
* **Email**: <AlanPaine@163.com>  