# AutoUtils
## 说明
   安卓屏幕适配方案，看了很多大神们的方案，最符合要求的是hongyang大神的方案，但是由于屏幕比例的问题（15：9，16：9，17：9等等），
导致显示会变形，本方案是只取一边长度进行适配，在zhengjingle的AutoUtils上进行修改。本方案只进行margin，padding，height，width，
testSize进行适配，自定义属性请在代码中实现。
## 优点
     1.不用考虑状态栏。
     2.对于不同比例屏幕（16:9,17:9等等）展示时不会变形：如图片等。
     3.简单，大大减少工作量，与传统适配方案相比，不需要写很多的适配文件，与百分比方案相比没了超大的计算量。
     4.兼容性强，安卓碎片化严重，而且还在不断增加碎片，新的碎片产生时并不需要去重新适配。
     5.体积小，就1个类。
## 缺点
     1.对于不同比例屏幕（15::9,16:9,17:9等等）展示时屏幕上展示效果不同：如在16：9屏幕布局正好占满屏幕，但在高比屏幕上底部会留白
       而低比屏幕则会跑出屏幕，但考虑到大多数页面都是滑动页面，所有影响不是很大，如果信息不是很多，可以考虑页面居中设计，信息较多
       可以考虑让页面滑动。
     2.耦合性强，当设计图纸改变后改的工作量大（例如原来图纸为720*1280，后面变为1080*1920），这个问题可能性还是很小的，除非人事
       变动，这时候你就要py交易了，当然你也可以在用到新图纸的地方重新设置设计尺寸或者写适配文件，将所有可能用到的px值列出来，样
       式改变时只要在适配文件中进行乘法或除法，不过这样会造成不好维护，建议在一个项目中还是使用统一样式。
## 使用
### 依赖
```
compile 'com.wuyifeng:AutoUtils:1.0.1'
```
### 代码
#### 初始化设计稿
在Application中的onCreate方法
```Java
AutoUtils.init(this, 1080, true);
```
第一个参数为Context，第二个参数为设计稿尺寸，第三个参数为是否为宽度。
#### 在xml布局中使用
```Html
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="540px"
    android:layout_height="600px">
    <ImageView
        android:id="@+id/iv1"
        android:layout_width="500px"
        android:layout_height="500px"
        android:src="@drawable/pic"
        android:layout_marginTop="30px"
        android:layout_centerHorizontal="true"/>

    <TextView
        android:id="@+id/tv1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/iv1"
        android:textSize="40px"
        android:layout_marginLeft="20px"
        android:text="啊啊啊啊啊啊啊啊啊啊啊啊"/>
</RelativeLayout>
```
#### 在Activity中使用
在setContentView后调用
```Java
AutoUtils.auto(this);
```
参数为Activity。
#### 在Fragment中使用
```Java
public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.xxx, container, false);
    AutoUtils.auto(v);
    return v;
}
```
参数为View。
#### 在ViewHolder中使用
```Java
class RvHolder extends RecyclerView.ViewHolder {
    public RvHolder(View itemView) {
        super(itemView);
        // 在ViewHolder中使用
        AutoUtils.auto(itemView);
    }
}
```
参数为View。
#### 自定义属性使用
下面举个类似的例子，其它可参考这个写
```Java
tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, AutoUtils.getTextSize(40));
ViewGroup.LayoutParams lp = tv.getLayoutParams();
lp.width = AutoUtils.getDisplayValue(1080);
tv.setLayoutParams(lp);
```
#### 在其它地方使用
除了以上的使用，在很多场合也能使用到，例如PopupWindow，Dialog等等，凡是出现View需要进行适配的地方都能使用，使用方法参考Fragment和ViewHolder。
## 展示
<table>
    <tr>
        <td><img width="270" height="480" src="https://github.com/shouzhong/AutoUtils/blob/master/Screenshots/1080_1920_3.jpg"/></td>
        <td><img width="270" height="480" src="https://github.com/shouzhong/AutoUtils/blob/master/Screenshots/1080_1920_4.jpg"/></td>
        <td><img width="270" height="480" src="https://github.com/shouzhong/AutoUtils/blob/master/Screenshots/1080_1920_5.jpg"/></td>
    </tr>
    <tr>
        <td><img width="270" height="450" src="https://github.com/shouzhong/AutoUtils/blob/master/Screenshots/480_800_3.png"/></td>
        <td><img width="270" height="450" src="https://github.com/shouzhong/AutoUtils/blob/master/Screenshots/480_800_4.png"/></td>
        <td><img width="270" height="450" src="https://github.com/shouzhong/AutoUtils/blob/master/Screenshots/480_800_5.png"/></td>
    </tr>
</table>
上边图为分辨率为1080*1920的屏幕，下边图为480*800的屏幕，可以看出，在2个屏幕上都能很好的显示，由于宽高比例不同，所有在y方向上显示效果可能不同（显示内容数量不同），但是不管文字还是图片显示效果都不错，没有发生变形或者效果不一样的错误。
</br>
