package jp.kai.forcelayout.properties

import android.graphics.Color

/**
 * Created by kai on 2017/05/14.
 */

object GraphStyle {
    /** node */
    var isImgDraw: Boolean = true
    var nodesWidth: Int = 150
    var roundSize: Int = 5
    var nodeDefColor: Int = Color.BLACK
    var nodeSelectedColor: Int = Color.CYAN

    /** link */
    var linkWidth: Float = 5.0f
    var linkColor: Int = Color.BLACK

    /** label */
    var fontSize: Float = 30.0f
    var fontColor: Int = Color.BLACK
    var fontSelectedColor: Int = Color.CYAN

    val defaultColor: Int = Color.BLACK
}