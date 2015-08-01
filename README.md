# TagCloudView
支持 SingleLine 模式的标签云效果，轻松实现知乎问题话题列表

标签云效果，可以设置为 SingleLine 模式，丰富的自定义样式选择 [Demo apk 下载](https://github.com/kingideayou/TagCloudView/raw/master/apk/Demo.apk)。

# How to user 如何使用
首先下载 tagView，将 tagView 文件夹拷贝到项目的目录下面，然后在setting.gradle文件中增加文件夹名称

      include ":tagview"

然后在我们需要依赖这个模块的module中的build.gradle文件中加入如下代码:
    
      compile project(':tagview')

# 效果图

<img src="https://raw.githubusercontent.com/kingideayou/TagCloudView/master/imgs/tagCloudView_1.png" width = "320" height = "580" alt="TagCloudView" align=center />
<img src="https://raw.githubusercontent.com/kingideayou/TagCloudView/master/imgs/tagCloudView_2.png" width = "320" height = "580" alt="TagCloudView" align=center />

动图展示:

<img src="https://raw.githubusercontent.com/kingideayou/TagCloudView/master/imgs/tagCloudView_3.gif" width = "380" height = "620" alt="TagCloudView" align=center />
