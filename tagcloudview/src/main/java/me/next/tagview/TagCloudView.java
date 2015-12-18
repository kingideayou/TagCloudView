package me.next.tagview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NeXT on 15-7-29.
 */
public class TagCloudView extends ViewGroup{

    private static final String TAG = TagCloudView.class.getSimpleName();
    private static final int TYPE_TEXT_NORMAL = 1;
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

    private int mTagResId;
    private int mRightImageResId;
    private boolean mSingleLine;
    private boolean mShowRightImage;
    private boolean mShowEndText;
    private boolean mCanTagClick;
    private String endTextString;

    private int imageWidth;
    private int imageHeight;
    private ImageView imageView = null;

    private int endTextWidth = 0;
    private int endTextHeight = 0;
    private TextView endText = null;

    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final int DEFAULT_TEXT_SIZE = 14;
    private static final int DEFAULT_TEXT_BACKGROUND = R.drawable.tag_background;
    private static final int DEFAULT_VIEW_BORDER = 6;
    private static final int DEFAULT_TEXT_BORDER_HORIZONTAL = 8;
    private static final int DEFAULT_TEXT_BORDER_VERTICAL = 5;

    private static final int DEFAULT_TAG_RESID = R.layout.item_tag;
    private static final int DEFAULT_RIGHT_IMAGE = R.drawable.arrow_right;
    private static final boolean DEFAULT_SINGLE_LINE = false;
    private static final boolean DEFAULT_SHOW_RIGHT_IMAGE = true;
    private static final boolean DEFAULT_SHOW_END_TEXT = true;
    private static final String DEFAULT_END_TEXT_STRING = " … ";
    private static final boolean DEFAULT_CAN_TAG_CLICK = true;

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

        mTagSize = a.getInteger(R.styleable.TagCloudView_tcvTextSize, DEFAULT_TEXT_SIZE);
        mTagColor = a.getColor(R.styleable.TagCloudView_tcvTextColor, DEFAULT_TEXT_COLOR);
        mBackground = a.getResourceId(R.styleable.TagCloudView_tcvBackground, DEFAULT_TEXT_BACKGROUND);
        mViewBorder = a.getDimensionPixelSize(R.styleable.TagCloudView_tcvBorder, DEFAULT_VIEW_BORDER);
        mTagBorderHor = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tcvItemBorderHorizontal, DEFAULT_TEXT_BORDER_HORIZONTAL);
        mTagBorderVer = a.getDimensionPixelSize(
                R.styleable.TagCloudView_tcvItemBorderVertical, DEFAULT_TEXT_BORDER_VERTICAL);
        mCanTagClick = a.getBoolean(R.styleable.TagCloudView_tcvCanTagClick, DEFAULT_CAN_TAG_CLICK);

        mRightImageResId = a.getResourceId(R.styleable.TagCloudView_tcvRightResId, DEFAULT_RIGHT_IMAGE);
        mSingleLine = a.getBoolean(R.styleable.TagCloudView_tcvSingleLine, DEFAULT_SINGLE_LINE);
        mShowRightImage = a.getBoolean(R.styleable.TagCloudView_tcvShowRightImg, DEFAULT_SHOW_RIGHT_IMAGE);
        mShowEndText = a.getBoolean(R.styleable.TagCloudView_tcvShowEndText, DEFAULT_SHOW_END_TEXT);
        endTextString = a.getString(R.styleable.TagCloudView_tcvEndText);

        mTagResId = a.getResourceId(R.styleable.TagCloudView_tcvTagResId, DEFAULT_TAG_RESID);

        a.recycle();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return (!mCanTagClick && mSingleLine) || super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /**
     * 计算 ChildView 宽高
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @SuppressLint("DrawAllocation")
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

        initSingleLineView(widthMeasureSpec, heightMeasureSpec);

        int totalWidth = 0;
        int totalHeight = mTagBorderVer;

        if (mSingleLine) {
            totalHeight = getSingleTotalHeight(totalWidth, totalHeight);
        } else {
            totalHeight = getMultiTotalHeight(totalWidth, totalHeight);
        }

        /**
         * 高度根据设置改变
         * 如果为 MATCH_PARENT 则充满父窗体，否则根据内容自定义高度
         */
        setMeasuredDimension(
                sizeWidth,
                (heightMode == MeasureSpec.EXACTLY ? sizeHeight : totalHeight));

    }

    /**
     * 初始化 singleLine 模式需要的视图
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    private void initSingleLineView(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mSingleLine) {
            return;
        }
        if (mShowRightImage) {
            imageView = new ImageView(getContext());
            imageView.setImageResource(mRightImageResId);
            imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            measureChild(imageView, widthMeasureSpec, heightMeasureSpec);
            imageWidth = imageView.getMeasuredWidth();
            imageHeight = imageView.getMeasuredHeight();
            addView(imageView);
        }

        if (mShowEndText) {
            endText = (TextView) mInflater.inflate(mTagResId, null);
            if (mTagResId == DEFAULT_TAG_RESID) {
                endText.setBackgroundResource(mBackground);
                endText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTagSize);
                endText.setTextColor(mTagColor);
            }
            @SuppressLint("DrawAllocation")
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            endText.setLayoutParams(layoutParams);
            endText.setText(endTextString == null || endTextString.equals("") ? DEFAULT_END_TEXT_STRING : endTextString);
            measureChild(endText, widthMeasureSpec, heightMeasureSpec);
            endTextHeight = endText.getMeasuredHeight();
            endTextWidth = endText.getMeasuredWidth();
            addView(endText);
            endText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onTagClickListener != null) {
                        onTagClickListener.onTagClick(-1);
                    }
                }
            });
        }
    }

    /**
     * 为 singleLine 模式布局，并计算视图高度
     * @param totalWidth
     * @param totalHeight
     * @return
     */
    private int getSingleTotalHeight(int totalWidth, int totalHeight) {
        int childWidth;
        int childHeight;

        totalWidth += mViewBorder;

        int textTotalWidth = getTextTotalWidth();
        if (textTotalWidth < sizeWidth - imageWidth) {
            endText = null;
            endTextWidth = 0;
        }

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();


            if (i == 0) {
                totalWidth += childWidth;
                totalHeight = childHeight + mViewBorder;
            } else {
                totalWidth += childWidth + mTagBorderHor;
            }

            if (child.getTag() != null && child.getTag() == TYPE_TEXT_NORMAL) {
                if (totalWidth + mTagBorderHor + mViewBorder + mViewBorder + endTextWidth + imageWidth < sizeWidth) {
                    child.layout(
                            totalWidth - childWidth + mTagBorderVer,
                            totalHeight - childHeight,
                            totalWidth + mTagBorderVer,
                            totalHeight);
                } else {
                    totalWidth -= childWidth + mViewBorder;
                    break;
                }
            }
        }

        if (endText != null) {
            endText.layout(
                    totalWidth + mViewBorder + mTagBorderVer,
                    totalHeight - endTextHeight,
                    totalWidth + mViewBorder + mTagBorderVer + endTextWidth,
                    totalHeight);
        }

        totalHeight += mViewBorder;

        if (imageView != null) {
            imageView.layout(
                    sizeWidth - imageWidth - mViewBorder,
                    (totalHeight - imageHeight) / 2,
                    sizeWidth - mViewBorder,
                    (totalHeight - imageHeight) / 2 + imageHeight);
        }

        return totalHeight;
    }

    /**
     * 为 multiLine 模式布局，并计算视图高度
     * @param totalWidth
     * @param totalHeight
     * @return
     */
    private int getMultiTotalHeight(int totalWidth, int totalHeight) {
        int childWidth;
        int childHeight;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();

            totalWidth += childWidth + mViewBorder;

            if (i == 0) {
                totalHeight = childHeight + mViewBorder;
            }
            // + marginLeft 保证最右侧与 ViewGroup 右边距有边界
            if (totalWidth + mTagBorderHor + mViewBorder> sizeWidth) {
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
        return totalHeight + mViewBorder;
    }

    private int getTextTotalWidth() {
        if (getChildCount() == 0) {
            return 0;
        }
        int totalChildWidth = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getTag() != null && (int)child.getTag() == TYPE_TEXT_NORMAL) {
                totalChildWidth += child.getMeasuredWidth() + mViewBorder;
            }
        }
        return totalChildWidth + mTagBorderHor * 2;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    public void setTags(List<String> tagList) {
        this.tags = tagList;
        this.removeAllViews();
        if (tags != null && tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                TextView tagView = (TextView) mInflater.inflate(mTagResId, null);
                if (mTagResId == DEFAULT_TAG_RESID) {
                    tagView.setBackgroundResource(mBackground);
                    tagView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTagSize);
                    tagView.setTextColor(mTagColor);
                }
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                tagView.setLayoutParams(layoutParams);
                tagView.setText(tags.get(i));
                tagView.setTag(TYPE_TEXT_NORMAL);
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

    public void singleLine(boolean mSingleLine) {
        this.mSingleLine = mSingleLine;
        this.setTags(tags);
//        requestLayout();
    }

    public void setOnTagClickListener(OnTagClickListener onTagClickListener) {
        this.onTagClickListener = onTagClickListener;
    }

    public interface OnTagClickListener{
        void onTagClick(int position);
    }

}
