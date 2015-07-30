package me.next.tagview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
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

    private float mTagSize;
    private int mTagColor;
    private int mBackground;
    private int mViewBorder;
    private int mTagBorderHor;
    private int mTagBorderVer;

    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_SIZE = 14;
    private static final int DEFAULT_TEXT_BACKGROUND = R.drawable.tag_background;
    private static final int DEFAULT_VIEW_BORDER = 6;
    private static final int DEFAULT_TEXT_BORDER_HORIZONTAL = 5;
    private static final int DEFAULT_TEXT_BORDER_VERTICAL = 5;

    public TagCloudView(Context context) {
        this(context, null);
    }

    public TagCloudView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagCloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TagCloudView,
                defStyleAttr,
                defStyleAttr
        );

        mTagSize = a.getDimension(R.styleable.TagCloudView_tcvTextSize, DEFAULT_TEXT_SIZE);
        mTagColor = a.getColor(R.styleable.TagCloudView_tcvTextColor, DEFAULT_TEXT_COLOR);
        mBackground = a.getResourceId(R.styleable.TagCloudView_tcvBackground, DEFAULT_TEXT_BACKGROUND);
        mViewBorder = a.getDimensionPixelSize(R.styleable.TagCloudView_tcvBorder, DEFAULT_VIEW_BORDER);
        mTagBorderHor = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tcvItemBorderHorizontal, DEFAULT_TEXT_BORDER_HORIZONTAL);
        mTagBorderVer = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tcvItemBorderVertical, DEFAULT_TEXT_BORDER_VERTICAL);

        a.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /**
     * 计算 ChildView 宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
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
//        int marginLeft = 5;
//        int marginTop = 8;
        int totalWidth = 0;
        int totalHeight = mTagBorderVer;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            totalWidth += childWidth + mViewBorder;

            if (i == 0) {
                totalHeight = childHeight + mViewBorder;
            }
            // + marginLeft 保证最右侧与 ViewGroup 右边距有边界
            if (totalWidth + mTagBorderHor > sizeWidth) {
                totalWidth = mViewBorder;
                totalHeight += childHeight + mTagBorderVer;
                child.layout(
                        totalWidth + mTagBorderHor,
                        totalHeight - childHeight,
                        totalWidth + childWidth + mTagBorderHor,
                        totalHeight);
                totalWidth += childWidth;
            } else {
                child.layout(
                        totalWidth - childWidth + mTagBorderHor,
                        totalHeight - childHeight,
                        totalWidth + mTagBorderHor,
                        totalHeight);
            }
        }
        totalHeight += mViewBorder;

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
                tagView.setBackgroundResource(mBackground);
                tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTagSize);
                tagView.setTextColor(mTagColor);
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
