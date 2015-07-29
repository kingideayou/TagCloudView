package me.next.tagcloud;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NeXT on 15-7-29.
 */
public class TagCloudView extends ViewGroup{

    private static final String TAG = TagCloudView.class.getSimpleName();
    private List<String> tags;

    private LayoutInflater mInflater;
    private OnTagClickListener onTagClickListener;

    private int sizeWidth;
    private int sizeHeight;

    public TagCloudView(Context context) {
        this(context, null);
    }

    public TagCloudView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagCloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        /*
        int childWidth;
        int childHeight;
        int marginLeft = 5;
        int marginTop = 8;
        int totalWidth = 0;
        int totalHeight = marginTop;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            totalWidth += childWidth + marginLeft;

            // + marginLeft 保证最右侧与 ViewGroup 右边距有边界
            if (totalWidth + marginLeft > sizeWidth) {
                totalWidth = 0;
                totalHeight += childHeight + marginTop;
                child.layout(
                        totalWidth + marginLeft,
                        totalHeight,
                        totalWidth + childWidth + marginLeft,
                        totalHeight + childHeight);
                totalWidth += childWidth;
            } else {
                child.layout(
                        totalWidth - childWidth + marginLeft,
                        totalHeight,
                        totalWidth + marginLeft,
                        totalHeight + childHeight);
            }
        }
        */
    }

    /**
     * 计算 ChildView 宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        /**
         * 计算 ViewGroup 上级容器为其推荐的宽高
         */
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        //计算 childView 宽高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int childWidth;
        int childHeight;
        int marginLeft = 5;
        int marginTop = 8;
        int totalWidth = 0;
        int totalHeight = marginTop;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            totalWidth += childWidth + marginLeft;

            if (i == 0) {
                totalHeight = childHeight + marginTop;
            }

            // + marginLeft 保证最右侧与 ViewGroup 右边距有边界
            if (totalWidth + marginLeft > sizeWidth) {
                totalWidth = 0;
                totalHeight += childHeight + marginTop;
                child.layout(
                        totalWidth + marginLeft,
                        totalHeight - childHeight,
                        totalWidth + childWidth + marginLeft,
                        totalHeight);
                totalWidth += childWidth;
            } else {
                child.layout(
                        totalWidth - childWidth + marginLeft,
                        totalHeight - childHeight,
                        totalWidth + marginLeft,
                        totalHeight);
            }
        }
        totalHeight += marginTop;

        /**
         * 高度根据设置改变
         * 如果为 MATCH_PARENT 则充满父窗体，否则根据内容自定义高度
         */
        setMeasuredDimension(
                sizeWidth,
                (heightMode == MeasureSpec.EXACTLY ? sizeHeight : totalHeight));

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    public void setTags(List<String> tagList) {
        this.tags = tagList;
        if (tags != null && tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                TextView tagView = (TextView) mInflater.inflate(R.layout.item_tag, null);
                tagView.setBackgroundResource(R.drawable.tag_background);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                tagView.setLayoutParams(layoutParams);
                tagView.setText(tags.get(i));
                final int finalI = i;
                tagView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onTagClickListener != null) {
                            onTagClickListener.onTagClick(finalI);
                        }
                    }
                });
                addView(tagView);
            }
        }
        postInvalidate();
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener{
        void onTagClick(int position);
    }

}
