package ru.univ.pet

import org.junit.Assert.assertEquals
import org.junit.Test

class PetTest {
    @Test
    fun feedIncreasesSatietyAndClamps() {
        val pet = Pet(satiety = 90).feed()
        assertEquals(100, pet.satiety)
    }

    @Test
    fun tickMakesPetSickWhenStarving() {
        val pet = Pet(satiety = 20, health = 50).tick()
        assertEquals(15, pet.satiety)
        assertEquals(45, pet.health)
    }

    @Test
    fun moodDependsOnStats() {
        assertEquals("Голодный", Pet(satiety = 10).mood)
        assertEquals("Счастлив", Pet(happiness = 90).mood)
        assertEquals("Болеет", Pet(health = 10, satiety = 10).mood)
    }
}
