package me.next.tagcloud;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TagCloudView.OnTagClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TagCloudView tagCloudView = (TagCloudView) findViewById(R.id.tag_cloud_view);
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add("标签" + i);
        }
        tagCloudView.setTags(tags);
        tagCloudView.setOnTagClickListener(this);
    }

    @Override
    public void onTagClick(int position) {
        Toast.makeText(getApplicationContext(), "position : " + position,
                Toast.LENGTH_SHORT).show();
    }
}
