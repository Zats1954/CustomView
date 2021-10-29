package ru.netology.statsview.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import ru.netology.statsview.R
import kotlin.math.min
import kotlin.random.Random

class StatsView @JvmOverloads constructor(context: Context,
attrs: AttributeSet? = null) : View(context,attrs) {
    private var center = PointF(0F, 0F)
    private var radius = 0F
    private var linedWidth = 0
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }


    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply{
        style = Paint.Style.STROKE
        textAlign = Paint.Align.CENTER
    }
    private var circle = RectF(0F, 0F,0F,0F)
    var data: List<Float> = emptyList()
       set(value) {
          field = value
          invalidate()
    }

private var colors = emptyList<Int>()
init{
    context.withStyledAttributes(attrs, R.styleable.StatsView){
        textPaint.textSize = getDimension(R.styleable.StatsView_textSize,
            AndroidUtils.dp(context, 40F).toFloat())
        linedWidth = getDimensionPixelSize(R.styleable.StatsView_lineWidth,
            AndroidUtils.dp(context, 15F))
        paint.strokeWidth = linedWidth.toFloat()
        colors = listOf(
            getColor(R.styleable.StatsView_color1, getRandomColor()),
            getColor(R.styleable.StatsView_color2, getRandomColor()),
            getColor(R.styleable.StatsView_color3, getRandomColor()),
            )

    }
}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        center = PointF(w/2F, h/2F)
        radius = min(w,h)/2F - linedWidth/2F
        circle = RectF(
               center.x - radius,
               center.y - radius,
              center.x + radius,
            center.y + radius
        )
    }

    override fun onDraw(canvas: Canvas) {
        if(data.isEmpty())
            return
        var startAngle = -90F
        data.forEachIndexed { index,item ->
            val angle = item * 360F
            paint.color = colors.getOrElse(index) {getRandomColor()}
            canvas.drawArc(circle, startAngle, angle,false,paint)
            startAngle += angle
        }
        canvas.drawText(
            "%.2f%%".format(data.sum()*100),
            center.x,
            center.y - textPaint.textSize / 4,
            textPaint
        )
    }

    private fun getRandomColor() = Random.nextInt(0xFF000000.toInt(), 0xFFFFFFFF.toInt())
}