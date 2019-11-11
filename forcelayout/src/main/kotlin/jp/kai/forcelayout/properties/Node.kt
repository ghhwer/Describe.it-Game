package jp.kai.forcelayout.properties

import jp.kai.forcelayout.properties.GraphStyle.nodeDefColor

/**
 * Created by kai on 2017/05/03.
 * Node Class
 */

internal class Node{
    var nodename: String = ""

    var x: Double = 0.toDouble()
    var y: Double = 0.toDouble()
    var width: Double = 0.toDouble()
    var height: Double = 0.toDouble()
    var color: Int = nodeDefColor

    var dx: Double = 0.toDouble()
    var dy: Double = 0.toDouble()
}