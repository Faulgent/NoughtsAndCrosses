package com.margaritasoft.noughtsandcrosses

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var scale: Scale? = null

    fun startNewGame(newScale: Scale) {
        setScale(newScale)
        createNewField()
    }

    private fun setScale(newScale: Scale) {
        scale = newScale
    }

    val field =  MutableLiveData<Array<Array<Symbol>>>()

    private fun createNewField() {
        field.postValue(Array(scale!!.size){Array(scale!!.size){Symbol.EMPTY}})
    }

    var next = Symbol.CROSS
    var win = MutableLiveData(false)

    fun pressed(x:Int, y:Int) {
        val oldValues = field.value!!
        val newValues = Array(scale!!.size) { row ->
            Array(scale!!.size) { column ->
                if (row == x && column == y) next else oldValues[row][column]
            }
        }
        win.value = newValues.checkForWin()
        changeNext()
        field.postValue(newValues)
    }

    private fun Array<Array<Symbol>>.checkForWin(): Boolean {
        for (row in this) {
            if (row.all { it == Symbol.NOUGHT } || row.all { it == Symbol.CROSS }) return true
        }
        for (columnIndex in 0 until size) {
            if (all { it[columnIndex] == Symbol.NOUGHT } || all { it[columnIndex] == Symbol.CROSS }) return true
        }
        var diagonalIndex = 0
        if (all { it[diagonalIndex++] == Symbol.NOUGHT }) return true
        diagonalIndex = 0
        if (all { it[diagonalIndex++] == Symbol.CROSS }) return true
        diagonalIndex = size - 1
        if (all { it[diagonalIndex--] == Symbol.NOUGHT }) return true
        diagonalIndex = size - 1
        if (all { it[diagonalIndex--] == Symbol.CROSS }) return true

        return false
    }

    private fun changeNext() {
        if(next == Symbol.CROSS){
            next = Symbol.NOUGHT
        } else {
            next = Symbol.CROSS
        }
    }

}