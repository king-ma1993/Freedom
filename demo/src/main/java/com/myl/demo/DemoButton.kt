package com.myl.demo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button

class DemoButton : Button {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {

    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        Log.d("DemoButton", "context:$context")
    }
}