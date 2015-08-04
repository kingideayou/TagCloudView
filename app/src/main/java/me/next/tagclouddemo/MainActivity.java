package me.next.tagclouddemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.next.tagview.TagCloudView;


public class MainActivity extends AppCompatActivity implements TagCloudView.OnTagClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add("标签" + i);
        }

        TagCloudView tagCloudView0 = (TagCloudView) findViewById(R.id.tag_cloud_view_0);
        tagCloudView0.setTags(tags.subList(0, 4));
        tagCloudView0.setOnTagClickListener(this);
        tagCloudView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });

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

        TagCloudView tagCloudView2 = (TagCloudView) findViewById(R.id.tag_cloud_view_2);
        tagCloudView2.setTags(tags);
        tagCloudView2.setOnTagClickListener(this);
        tagCloudView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });

        TagCloudView tagCloudView3 = (TagCloudView) findViewById(R.id.tag_cloud_view_3);
        tagCloudView3.setTags(tags);
        tagCloudView3.setOnTagClickListener(this);
        tagCloudView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });

        TagCloudView tagCloudView4 = (TagCloudView) findViewById(R.id.tag_cloud_view_4);
        tagCloudView4.setTags(tags);
        tagCloudView4.setOnTagClickListener(this);
        tagCloudView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });

        TagCloudView tagCloudView5 = (TagCloudView) findViewById(R.id.tag_cloud_view_5);
        tagCloudView5.setTags(tags);
        tagCloudView5.setOnTagClickListener(this);
        tagCloudView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });

        TagCloudView tagCloudView6 = (TagCloudView) findViewById(R.id.tag_cloud_view_6);
        tagCloudView6.setTags(tags);
        tagCloudView6.setOnTagClickListener(this);
        tagCloudView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });

        TagCloudView tagCloudView7 = (TagCloudView) findViewById(R.id.tag_cloud_view_7);
        tagCloudView7.setTags(tags);
        tagCloudView7.setOnTagClickListener(this);
        tagCloudView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });

        TagCloudView tagCloudView8 = (TagCloudView) findViewById(R.id.tag_cloud_view_8);
        tagCloudView8.setTags(tags);
        tagCloudView8.setOnTagClickListener(this);
        tagCloudView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "TagView onClick",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }


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
}
