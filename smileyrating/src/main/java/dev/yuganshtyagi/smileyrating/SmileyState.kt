package dev.yuganshtyagi.smileyrating

internal sealed class SmileyState {
    object Sad : SmileyState()
    object Neutral : SmileyState()
    object Okay : SmileyState()
    object Happy : SmileyState()
    object Amazing : SmileyState()

    companion object {
        fun of(value: Int): SmileyState {
            return when (value) {
                0 -> Sad
                1 -> Neutral
                2 -> Okay
                3 -> Happy
                4 -> Amazing
                else -> Neutral
            }
        }
    }
}