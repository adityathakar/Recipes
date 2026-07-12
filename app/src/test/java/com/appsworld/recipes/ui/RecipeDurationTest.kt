package com.appsworld.recipes.ui

import org.junit.Assert.assertEquals
import org.junit.Test

class RecipeDurationTest {

    @Test
    fun `under an hour shows minutes only`() {
        assertEquals("45m", 45.formatDuration())
        assertEquals("59m", 59.formatDuration())
    }

    @Test
    fun `exactly an hour drops the minutes`() {
        assertEquals("1h", 60.formatDuration())
        assertEquals("2h", 120.formatDuration())
    }

    @Test
    fun `over an hour shows both parts`() {
        assertEquals("1h 1m", 61.formatDuration())
        assertEquals("4h 30m", 270.formatDuration())
    }

    @Test
    fun `zero displays as zero minutes`() {
        assertEquals("0m", 0.formatDuration())
    }

    @Test
    fun `spoken duration spells out the units`() {
        assertEquals("45 minutes", 45.spokenDuration())
        assertEquals("1 hour 30 minutes", 90.spokenDuration())
        assertEquals("4 hours 30 minutes", 270.spokenDuration())
    }

    @Test
    fun `spoken duration keeps single units singular`() {
        assertEquals("1 minute", 1.spokenDuration())
        assertEquals("1 hour", 60.spokenDuration())
        assertEquals("1 hour 1 minute", 61.spokenDuration())
    }

    @Test
    fun `spoken zero reads as zero minutes`() {
        assertEquals("0 minutes", 0.spokenDuration())
    }
}
