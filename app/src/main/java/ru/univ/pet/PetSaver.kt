package ru.univ.pet

import androidx.compose.runtime.saveable.listSaver

// Сохраняет Pet при повороте экрана / пересоздании Activity
val PetSaver = listSaver<Pet, Any>(
    save = { listOf(it.name, it.satiety, it.energy, it.happiness, it.health) },
    restore = {
        Pet(
            name = it[0] as String,
            satiety = it[1] as Int,
            energy = it[2] as Int,
            happiness = it[3] as Int,
            health = it[4] as Int,
        )
    },
)
