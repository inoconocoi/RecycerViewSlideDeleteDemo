package tielizi.com.recycerviewslidedeletedemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by 10840 on 2015/10/31.
 */
public class MyItemView extends HorizontalScrollView{

    private int windowWidth;//获取屏幕宽度
    private int windowHeight;//获取屏幕高度
    private float rightToBorderRatio;//删除View占屏幕比例
    private int deleteViewWidth;//删除View宽度
    private int contentWidth;//item内容宽度
    private ViewGroup deleteGroup;//delete容器
    private ViewGroup contentGroup;//item内容容器
    public boolean isOpen;//标记滑动状态
    public SlideListener slideListener;

    public void setSlideListener(SlideListener slideListener) {
        this.slideListener = slideListener;
    }

    public interface SlideListener{
        void slideState(boolean state);
    }

    public MyItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public MyItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyItemView(Context context) {
        super(context);
    }

    private void init(AttributeSet attributeSet){
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        windowWidth = outMetrics.widthPixels;
        windowHeight = outMetrics.heightPixels;
        TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.MyItemLayout);
        rightToBorderRatio = typedArray.getFloat(R.styleable.MyItemLayout_right_to_of_the_screen, 0.3f);
        deleteViewWidth = (int)(rightToBorderRatio*windowWidth);
        contentWidth = windowWidth;
        isOpen = false;
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LinearLayout parentLayout = (LinearLayout) getChildAt(0);
        deleteGroup = (ViewGroup) parentLayout.getChildAt(1);
        contentGroup = (ViewGroup) parentLayout.getChildAt(0);
        deleteGroup.getLayoutParams().width = deleteViewWidth;
        contentGroup.getLayoutParams().width = contentWidth;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    public void slideOpen(){
        this.smoothScrollTo(deleteViewWidth, 0);
        System.out.println("打开");
        isOpen = true;
        slideListener.slideState(isOpen);
    }

    public void slideClose(){
        this.smoothScrollTo(0, 0);
        isOpen = false;
        slideListener.slideState(isOpen);
        System.out.println("关闭");
    }
    public void slideCloseWithoutAnimator(){
        this.scrollTo(0, 0);
        isOpen = false;
        slideListener.slideState(isOpen);
        System.out.println("关闭");
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        Log.i("px","l = "+l+"\n t = "+t+"\n oldl = "+oldl + "\n oldt = "+oldt);
//        ViewHelper.setTranslationX(menuGroup, l);
//        changeRatio = l*1.0f/menuWidth;
//        float contentRatio = 0.7f+changeRatio*0.3f;
//        float menuRatio = 1.0f-0.7f*changeRatio;
//        ViewHelper.setPivotX(contentGroup, 0);
//        ViewHelper.setPivotY(contentGroup, contentGroup.getHeight()/2);
//        ViewHelper.setScaleX(contentGroup, contentRatio);
//        ViewHelper.setScaleY(contentGroup, contentRatio);
//        ViewHelper.setPivotX(menuGroup, 0);
//        ViewHelper.setPivotY(menuGroup, menuGroup.getHeight()/2);
//        ViewHelper.setScaleX(menuGroup, menuRatio);
//        ViewHelper.setScaleY(menuGroup, menuRatio);
//        ViewHelper.setAlpha(menuGroup, menuRatio);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if(ev.getAction()==MotionEvent.ACTION_UP){
            int scrollX = this.getScrollX();
            if(scrollX>deleteViewWidth/2){
                this.smoothScrollTo(deleteViewWidth, 0);
                System.out.println("打开");
                isOpen = true;
                slideListener.slideState(isOpen);
            }
            else{
                this.smoothScrollTo(0, 0);
                isOpen = false;
                slideListener.slideState(isOpen);
                System.out.println("关闭");
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }
}
