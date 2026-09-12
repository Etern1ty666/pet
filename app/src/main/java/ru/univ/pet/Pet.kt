package ru.univ.pet

// Все показатели от 0 до 100, «больше = лучше»
data class Pet(
    val name: String = "Капи",
    val satiety: Int = 70,     // сытость
    val energy: Int = 80,
    val happiness: Int = 60,
    val health: Int = 100,
) {
    // Настроение вычисляется из показателей — отдельно хранить не нужно
    val mood: String
        get() = when {
            health < 30 -> "Болеет"
            satiety < 30 -> "Голодный"
            energy < 25 -> "Хочет спать"
            happiness > 70 -> "Счастлив"
            else -> "Нормально"
        }

    val emoji: String
        get() = when (mood) {
            "Болеет" -> "🤒"
            "Голодный" -> "😫"
            "Хочет спать" -> "😴"
            "Счастлив" -> "😄"
            else -> "🙂"
        }
}

// Не даём значениям выйти за 0..100
private fun Int.clamp() = coerceIn(0, 100)

// Действия. Pet неизменяемый, поэтому каждое действие возвращает НОВОГО питомца через copy()
fun Pet.feed() = copy(
    satiety = (satiety + 30).clamp(),
    happiness = (happiness + 5).clamp(),
)

fun Pet.play() = copy(
    happiness = (happiness + 20).clamp(),
    energy = (energy - 15).clamp(),
    satiety = (satiety - 10).clamp(),
)

fun Pet.sleep() = copy(
    energy = (energy + 40).clamp(),
    satiety = (satiety - 10).clamp(),
)

// Один «тик» времени: питомец голодает, устаёт, скучает
fun Pet.tick(): Pet {
    val next = copy(
        satiety = (satiety - 5).clamp(),
        energy = (energy - 3).clamp(),
        happiness = (happiness - 4).clamp(),
    )
    val sick = next.satiety < 20 || next.energy < 10
    return next.copy(health = (if (sick) health - 5 else health + 1).clamp())
}
