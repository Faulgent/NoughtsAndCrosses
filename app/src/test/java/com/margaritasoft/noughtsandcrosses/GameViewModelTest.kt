package com.margaritasoft.noughtsandcrosses

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import junit.framework.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized


@RunWith(Parameterized::class)
class GameViewModelTest(
    val scale: Scale
) {

    @Rule @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    val viewModel = GameViewModel()

    @Test
    fun `GIVEN viewModel is just initialized THEN field is null`() {
        assertNull(viewModel.field.value)
    }

    @Test
    fun `GIVEN viewModel is just initialized WHEN scale is passed THEN field exists`(){
        viewModel.startNewGame(scale)

        assertNotNull(viewModel.field.value)
    }

    @Test
    fun `GIVEN viewModel is just initialized WHEN scale is passed THEN field is created with correct size`() {
        viewModel.startNewGame(scale)

        assertEquals(scale.size, viewModel.field.value!!.size)
    }

    @Test
    fun `GIVEN viewModel is just initialized WHEN field is created THEN field is filled with EMPTY`() {
        viewModel.startNewGame(scale)

        viewModel.field.value!!.forEach {
            it.forEach {
                assertEquals(Symbol.EMPTY, it)
            }
        }
    }

    @Test
    fun `GIVEN field is just created WHEN pressed 0, 0 THEN field contains CROSS in 0,0`() {
        viewModel.startNewGame(scale)
        viewModel.pressed(0, 0)

        assertEquals(Symbol.CROSS, viewModel.field.value!![0][0])
    }

    @Test
    fun `GIVEN field contains CROSS in position 0,1 WHEN pressed 0, 0 THEN field contains NOUGHT in 0,0`() {
        viewModel.startNewGame(scale)
        viewModel.pressed(0, 1)
        viewModel.pressed(0,0)

        assertEquals(Symbol.NOUGHT, viewModel.field.value!![0][0])
    }



    companion object {
        @Parameterized.Parameters @JvmStatic
        fun params(): Array<Array<Any>> =
            arrayOf(
                arrayOf(Scale.SMALL),
                arrayOf(Scale.MEDIUM),
                arrayOf(Scale.LARGE)
            )
    }
}