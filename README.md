# TagCloudView
支持 SingleLine 模式的标签云效果，轻松实现知乎问题话题列表  
标签云效果，可以设置为 SingleLine 模式，丰富的自定义样式选择。  
只需要一行代码即可设置 SingleLine 模式：app:tcvSingleLine="true" true 为 SingleLine 模式，false 为默认标签云模式  
###Demo apk 下载：
<img src="https://raw.githubusercontent.com/kingideayou/TagCloudView/master/imgs/TagCloudView.png" width = "120" height = "120" alt="ListView 效果" align=center />  
[扫码或点我下载](https://github.com/kingideayou/TagCloudView/raw/master/apk/Demo.apk)  

# How to use 如何使用

##### Gradle  
使用 JCenter 在项目的 build.gradle 中添加如下代码
      
      compile 'com.github.kingideayou:tagcloudview:1.0.1'
      
#### Import 
首先下载 tagView，将 tagView 文件夹拷贝到项目的目录下面，然后在setting.gradle文件中增加文件夹名称

      include ":tagview"

然后在我们需要依赖这个模块的module中的build.gradle文件中加入如下代码:
    
      compile project(':tagview')
      
导入成功后，只需要在 XML 文件中添加如下视图

      <me.next.tagview.TagCloudView
            android:id="@+id/tag_cloud_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/background_tagcloudview_black_selector"
            app:tcvItemBorderHorizontal="8dp"
            app:tcvItemBorderVertical="6dp"
            app:tcvBorder="8dp"
            app:tcvTextColor="#123455"
            app:tcvSingleLine="true"
            app:tcvCanTagClick="true"
            />

在 Activity 中调用如下方法即可

      List<String> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add("标签" + i);
        }
        
      TagCloudView tagCloudView1 = (TagCloudView) findViewById(R.id.tag_cloud_view_1);
      tagCloudView1.setTags(tags);
      tagCloudView1.setOnTagClickListener(this);
      tagCloudView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });
        
       @Override
      public void onTagClick(int position) {
        if (position == -1) {
            Toast.makeText(getApplicationContext(), "点击末尾文字",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "点击 position : " + position,
                    Toast.LENGTH_SHORT).show();
        }
      }

# 效果图

<img src="https://raw.githubusercontent.com/kingideayou/TagCloudView/master/imgs/tagCloudView_1.png" width = "310" height = "560" alt="TagCloudView" align=center />
<img src="https://raw.githubusercontent.com/kingideayou/TagCloudView/master/imgs/tagCloudView_2.png" width = "310" height = "560" alt="TagCloudView" align=center />

### 动图展示:

<img src="https://raw.githubusercontent.com/kingideayou/TagCloudView/master/imgs/tagCloudView_3.gif" width = "380" height = "620" alt="TagCloudView" align=center />  
  
  
## License

    Copyright 2015 NeXT

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

