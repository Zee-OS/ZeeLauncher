package com.zeelauncher.core_common.text

import org.junit.Assert.assertEquals
import org.junit.Test

class AbbreviationTest {

    @Test
    fun multipleWordsTakeFirstLetters() {
        assertEquals("RDR", "Red Dead Redemption".toAbbreviation())
    }

    @Test
    fun moreThanThreeWordsAreLimitedToThreeLetters() {
        assertEquals("COD", "Call of Duty: Mobile".toAbbreviation())
    }

    @Test
    fun singleWordTakesTwoLetters() {
        assertEquals("MI", "Minecraft".toAbbreviation())
    }

    @Test
    fun camelCaseIsSplitIntoWords() {
        assertEquals("EF", "eFootball".toAbbreviation())
    }

    @Test
    fun symbolsAreTreatedAsSeparators() {
        assertEquals("A9L", "Asphalt 9: Legends".toAbbreviation())
    }

    @Test
    fun resultIsUppercase() {
        assertEquals("PM", "pubg mobile".toAbbreviation())
    }

    @Test
    fun emptyLabelGivesEmptyAbbreviation() {
        assertEquals("", "".toAbbreviation())
    }

    @Test
    fun symbolsOnlyGivesEmptyAbbreviation() {
        assertEquals("", "!!! ---".toAbbreviation())
    }
}
