package chapter6

import java.awt.Color
import java.awt.Frame
import java.awt.Graphics2D
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JPanel

class Canvas(
    private val canvasWidth: Int = 640,
    private val canvasHeight: Int = 480,
    xRange: ClosedRange<Double>,
    yRange: ClosedRange<Double>,
    blankFrame: Double = 0.1,
) {

    private val xFrame = (xRange.endInclusive - xRange.start) * blankFrame
    private val xFramed = (xRange.start - xFrame).rangeTo(xRange.endInclusive + xFrame)
    private val xFramedDelta = xFramed.endInclusive - xFramed.start

    private val yFrame = (yRange.endInclusive - yRange.start) * blankFrame
    private val yFramed = (yRange.start - yFrame).rangeTo(yRange.endInclusive + yFrame)
    private val yFramedDelta = yFramed.endInclusive - yFramed.start

    private val frame = Frame().apply {
        setSize(canvasWidth, canvasHeight)
        title = "AWT frame"
        layout = null
        isVisible = true
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                dispose()
            }
        })
        add(JPanel())
    }

    private val graphics2D = frame.graphics.create() as Graphics2D

    fun paintPoint(x: Double, y: Double, color: Color = Color.MAGENTA) {
        graphics2D.color = color
        graphics2D.fillOval(getXPosition(x), getYPosition(y), 10, 10)
    }

    fun paintCentroid(x: Double, y: Double, color: Color = Color.MAGENTA) {
        graphics2D.color = color
        graphics2D.fillRect(getXPosition(x), getYPosition(y), 20, 20)
    }

    private fun getXPosition(x: Double): Int {
        return ((x - xFramed.start) / xFramedDelta * canvasWidth).toInt()
    }

    private fun getYPosition(y: Double): Int {
        return ((y - yFramed.start) / yFramedDelta * canvasHeight).toInt()
    }
}
