# SphTech-master
## [中文描述](https://github.com/fcfrt/SphTech-master/blob/master/READMEZH.md "Editor.md")
## Application description

- The App starts from[https://data.gov.sg/dataset/mobile-data-usage](https://data.gov.sg/api/action/datastore_search?resource_id=a807b7ab-6cad-4aa6-87d0-e283a7353a0f&limit=5) Obtain data and display the list according to 4 quarters in a year;
- The application can display the amount of online and offline data sent by Singapore mobile network from 2008 to 2019. If the amount of data drops in any quarter of the year, a clickable image is displayed in the entry, and a drop data prompt box is displayed after clicking;
- Supports local caching of data

## Functionality and dependency libraries

1. [`litepal`](https://github.com/LitePalFramework/LitePal)
2. [`RxHttp`](https://github.com/liujingxing/okhttp-RxHttp)
3. [`rxhttp-compiler`](https://github.com/liujingxing/okhttp-RxHttp)
4. [`BaseRecyclerViewAdapterHelper`](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
5. [`Swiperefreshlayout`](https://developer.android.google.cn/jetpack/androidx/releases/swiperefreshlayout?hl=zh-cn)
6. [`ImmersionBar`](https://github.com/gyf-dev/ImmersionBar)

## TO LIST

- [ ]  Basic framework
  - [x] self - written simple base MVP
  - [x] age base load, prompt
  - [x] Interface structuring, unstructured data processing, data grouping
- [ ] Page development
  - [x] displays list data
  - [x] displays clickable images
  - [x] Data cache to display offline data without a network
- [x] Testing
  - [x] Core logic code unit tests. Ps: Each framework has a corresponding unit test case, so third-party libraries ignore tests
  - [x] UI is compatibility testing
  - [x] Testing (network error, server internal error)
  - [x] Unstructured data processing, data grouping testing
## 
* **Email**: <AlanPaine@163.com>  