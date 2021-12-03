package com.margaritasoft.noughtsandcrosses

import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val scale = intent.getSerializableExtra("scale")
        val viewModel = GameViewModel()
        viewModel.startNewGame(scale as Scale)
        viewModel.field.observe(this, Observer { drawLayout(it, viewModel::pressed) })
        viewModel.win.observe(this, Observer { displayWinWindow(viewModel) })
        viewModel.impWin.observe(this, { displayNoWinWindow(viewModel) })

    }

    fun displayWinWindow(viewModel: GameViewModel) {
        if (viewModel.win.value == true) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Congratulations!")
                .setMessage("You won!")
                .setPositiveButton("Start new game") { _: DialogInterface, i: Int -> finish() }
                .show()
        }
    }

    fun displayNoWinWindow(viewModel: GameViewModel) {
        if (viewModel.impWin.value == true) {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Ups!")
                .setMessage("The field is filled and nobody can win!")
                .setPositiveButton("Start new game") { _: DialogInterface, i: Int -> finish() }
                .show()
        }
    }

    private fun drawLayout(
        field: Array<Array<Symbol>>,
        onPressed: (x: Int, y: Int) -> Unit
    ) {
        val layout = createLayout()
        val scale = field.size

        for (y in 0 until scale) {
            layout.addView(createRow(y, scale, field, onPressed))
        }
        setContentView(layout)
    }

    private fun createRow(
        rowIndex: Int,
        size: Int,
        field: Array<Array<Symbol>>,
        onPressed: (x: Int, y: Int) -> Unit
    ): LinearLayout {
        val row = LinearLayout(this)
        row.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        for (x in 0 until size) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(0, 250).apply {
                weight = 1f
                marginStart = 5
                marginEnd = 5
                topMargin = 5
                bottomMargin = 5
            }
            button.setBackgroundResource(R.color.beige_dark)



            when (field[x][rowIndex]) {
                Symbol.CROSS -> button.setBackgroundResource(R.drawable.ic_cross)
                Symbol.NOUGHT -> button.setBackgroundResource(R.drawable.ic_circle)
                Symbol.EMPTY -> button.setOnClickListener {
                    onPressed(x, rowIndex)
                }
            }

            row.addView(button)
            row.gravity = Gravity.CENTER
        }
        return row
    }

    private fun createLayout(): LinearLayout {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setBackgroundResource(R.color.beige_light)
        return layout
    }
}