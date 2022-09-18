package dev.yuganshtyagi.smileyrating

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import androidx.core.content.ContextCompat
import com.yugansh.tyagi.smileyrating.R
import dev.yuganshtyagi.smileyrating.SmileyState.*

internal class SmileyViewConfig(
    private val context: Context,
    private val attributeSet: AttributeSet?
) {

    //paint
    val paint: Paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 10F.toDp()
    }

    //colors
    var faceColor = 0
    var eyesColor = 0
    var mouthColor = 0
    var tongueColor = 0

    //rect
    var faceBgRect = RectF()
    var sadFaceRect = RectF()
    var neutralFaceRect = RectF()
    var okayFaceRect = RectF()
    var happyFaceRect = RectF()
    var amazingFaceRect = RectF()
    var tongueRect = RectF()
    val eyeRadius = 10F.toDp()

    //eyesPos
    var currEyeLX = 0
    var currEyeRX = 0
    var currEyeY = 0

    var defaultRating = 0

    private var viewWidth = 0
    private var viewHeight = 0
    private var widthCenter = 0
    private var heightCenter = 0
    private var centerOffset = 0

    init {
        initAttributeValues()
    }

    private fun initAttributeValues() {
        var typedArray: TypedArray? = null
        try {
            typedArray = context.obtainStyledAttributes(
                attributeSet, R.styleable.SmileyRatingView
            )
            faceColor = typedArray.getColor(
                R.styleable.SmileyRatingView_face_color,
                ContextCompat.getColor(context, R.color.faceColor)
            )

            eyesColor = typedArray.getColor(
                R.styleable.SmileyRatingView_eyes_color,
                ContextCompat.getColor(context, R.color.eyesColor)
            )

            mouthColor = typedArray.getColor(
                R.styleable.SmileyRatingView_mouth_color,
                ContextCompat.getColor(context, R.color.mouthColor)
            )

            tongueColor = typedArray.getColor(
                R.styleable.SmileyRatingView_tongue_color,
                ContextCompat.getColor(context, R.color.tongueColor)
            )

            defaultRating = typedArray.getInteger(
                R.styleable.SmileyRatingView_default_rating, 2
            )
        } catch (exception: Exception) {
            Log.e("Smiley Rating", exception.localizedMessage, exception)
        } finally {
            typedArray?.recycle()
        }
    }

    fun onMeasure(width: Int, height: Int) {
        viewWidth = width
        viewHeight = height
        widthCenter = viewWidth / 2
        heightCenter = viewHeight / 2
        centerOffset = 120.toDp()
        initRectValues()
        updateCurrentEyePos()

    }

    private fun updateCurrentEyePos() {
        val position = getEyePosForState(SmileyState.of(defaultRating))
        currEyeLX = position.leftEyeX
        currEyeRX = position.rightEyeX
        currEyeY = position.eyesY
    }

    private fun initRectValues() {
        faceBgRect.set(
            -centerOffset.toFloat(),
            -viewHeight.toFloat(),
            viewWidth + centerOffset.toFloat(),
            viewHeight.toFloat()
        )
        sadFaceRect.set(
            widthCenter - 90F.toDp(),
            viewHeight - 180F.toDp(),
            widthCenter + 90F.toDp(),
            viewHeight - 20F.toDp()


        )
        neutralFaceRect.set(
            widthCenter - 120F.toDp(),
            viewHeight - 110F.toDp(),
            widthCenter + 120F.toDp(),
            viewHeight - 110F.toDp()
        )
        okayFaceRect.set(
            widthCenter - 110F.toDp(),
            viewHeight - 250F.toDp(),
            widthCenter + 110F.toDp(),
            viewHeight - 70F.toDp()
        )
        happyFaceRect.set(
            widthCenter - 130F.toDp(),
            viewHeight - 330F.toDp(),
            widthCenter + 130F.toDp(),
            viewHeight - 70F.toDp()
        )
        amazingFaceRect.set(
            widthCenter - 132F.toDp(),
            viewHeight - 330F.toDp(),
            widthCenter + 132F.toDp(),
            viewHeight - 50F.toDp()
        )
        tongueRect.set(
            widthCenter - 70F.toDp(),
            viewHeight - 220F.toDp(),
            widthCenter + 70F.toDp(),
            viewHeight - 75F.toDp()
        )
    }



    //****************** Singleton ****************************

     object EyePos {
         var leftEyeX: Int = 0
         var rightEyeX: Int = 0
         var eyesY: Int = 0


     }


    private fun eyePosSad() {
        with(EyePos){
            leftEyeX = widthCenter - 90.toDp()
            rightEyeX = widthCenter + 90.toDp()
            eyesY = 80.toDp()
        }
    }
    private fun eyePosNeutral() {
        with(EyePos){
            leftEyeX = widthCenter - 120.toDp()
            rightEyeX = widthCenter + 100.toDp()
            eyesY = 80.toDp()
        }
    }

    private fun eyePosOkay() {
        with(EyePos){
            leftEyeX = widthCenter - 20.toDp()
            rightEyeX = widthCenter + 20.toDp()
            eyesY = 300.toDp()
        }
    }

    private fun eyePosHappy() {
        with(EyePos){
            leftEyeX = widthCenter - 20.toDp()
            rightEyeX = widthCenter + 20.toDp()
            eyesY = 82.toDp()
        }
    }

    private fun eyePosAmazing(){
        with(EyePos) {
            leftEyeX = widthCenter - 120.toDp()
            rightEyeX = widthCenter + 120.toDp()
            eyesY = 72.toDp()
        }
    }

    fun getEyePosForState(state: SmileyState): EyePos {
        return when (state) {
            Sad -> {
                eyePosSad()
                EyePos
            }
            Neutral -> {
                eyePosNeutral()
                EyePos
            }
            Okay -> {
                eyePosOkay()
                EyePos
            }
            Happy -> {
                eyePosHappy()
                EyePos
            }
            Amazing -> {
                eyePosAmazing()
                EyePos
            }
        }
    }

}