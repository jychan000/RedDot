package com.jychan.reddot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

import static com.jychan.reddot.RedDotImageView.PointMode.NO_POINT;

/**
 *
 * 画红点，可带数字
 *
 * Created by chenjinying on 2016/11/26.
 * Email: 415683089@qq.com
 */

public class RedDotImageView extends ImageView {

    public enum PointMode {
        NO_POINT,       //不画
        POINT_DOT,      //画点
        POINT_NUMBER    //画数字
    }

    public PointMode pointMode = NO_POINT;   //默认为不画图模式
    private int number = 0;                   //默认数字为0

    private Paint paint;            //画笔
    private TextPaint paintText;    //画消息条数

    public RedDotImageView(Context context) {
        super(context);
        init();
    }

    public RedDotImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);//实心
        paint.setColor(0xffff0000);//红色
        paint.setAntiAlias(true);//抗锯齿

        paintText= new TextPaint();
        paintText.setColor(0xffffffff);//白色
        paintText.setTextSize(25);//设置显示条数的文本大小
        paintText.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);//实心
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setPointMode(PointMode pointMode) {
        this.pointMode = pointMode;
    }

    public void refresh() {
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (NO_POINT == pointMode)
            return;

        int width = getWidth();
        int height = getHeight();
        int cx = width * 3 / 4;
        int cy = height / 4;
        int radius = (width >= height) ? height / 8 : width / 8;

        switch (pointMode) {
            case POINT_DOT:
                canvas.drawCircle(cx, cy, radius, paint);
                break;
            case POINT_NUMBER:
                radius *= 1.5;
                canvas.drawCircle(cx, cy, radius, paint);
                String showText="";
                if(number>0 && number<100){
                    showText = number+"";
                }else if(number >= 100){
                    showText = "+99";
                }
                float textWidth = paintText.measureText(showText);
                float x = cx - textWidth / 2;
                float y = (float) (cy + paintText.getFontMetrics().bottom * 1.5);
                canvas.drawText(showText, x, y, paintText);
                break;
        }
    }
}
