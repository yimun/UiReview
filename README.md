# UiReview - 设计师Review工具

豆瓣实习期间开发的Android视图信息可视化工具，专用于豆瓣设计师review应用界面


* 2015.08.24 **1.0.0** - 基本功能完成，实现TextView扫描器，发布1.0.0正式版

## 概览
![enter image description here](static/1.png)


## 基本结构

1. `UiReview.java`
主要文件，采用定时遍历的方式递归遍历当前界面的视图树，采用观察者模式，分发给所有注册的`Scanner`

2.  `TextViewScanner.java`
抛砖引玉，实现了`UiReview.Scanner`接口的一个扫描器实例，完成对界面上所有`TextView`的处理操作，以`overlay`的方式显示了字体，颜色等信息

## 使用指南
### 注册：
```java
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UiReview.getInstance().register(
                new TextViewScanner());
        ...
}
```

### 开关：
在应用所有Activity的基类，比如`BaseActivity`中，回调`resume`和`pause`方法
```java
public BaseActivity extends Activity{
    @Override
    protected void onResume() {
        super.onResume();
        if(reviewMode){
	        UiReview.getInstance().resume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(reviewMode){
	        UiReview.getInstance().pause();
        }
    }
}
```

## 其他
- 由于使用到了Overlay，只支持API-18以上版本（Android4.3）
- 目前只实现了`TextView`信息的处理，后续可以增加更多的扫描器，比如`ImageView`的信息等


## License

    Copyright 2015 Linwei Zhang

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

