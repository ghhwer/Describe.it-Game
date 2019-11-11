package jp.kai.forcelayout.properties

import java.lang.Integer.parseInt

/**
 * Created by kai on 2017/05/14.
 */

class NodeProperty {
    internal fun prepare(): NodeProperty {
        return this
    }

    fun size(width: Int): NodeProperty{
        GraphStyle.nodesWidth = width

        return this
    }

    fun style(color: String): NodeProperty {
        GraphStyle.nodeDefColor = parseInt(color, 16)

        return this
    }

    fun style(color: Int): NodeProperty {
        GraphStyle.nodeDefColor = color

        return this
    }
}