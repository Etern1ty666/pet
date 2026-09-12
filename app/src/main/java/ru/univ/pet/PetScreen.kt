package ru.univ.pet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.univ.pet.ui.theme.PetTheme

// Раз в сколько секунд питомец «живёт» (тик времени)
private const val TICK_SECONDS = 5L

@Composable
fun PetScreen(modifier: Modifier = Modifier) {
    // Состояние экрана — один неизменяемый Pet. Любое действие заменяет его новым.
    var pet by rememberSaveable(stateSaver = PetSaver) { mutableStateOf(Pet()) }

    // Тикаем по таймеру, пока экран на виду
    LaunchedEffect(Unit) {
        while (true) {
            delay(TICK_SECONDS * 1000)
            pet = pet.tick()
        }
    }

    PetContent(
        pet = pet,
        onFeed = { pet = pet.feed() },
        onPlay = { pet = pet.play() },
        onSleep = { pet = pet.sleep() },
        modifier = modifier,
    )
}

@Composable
fun PetContent(
    pet: Pet,
    onFeed: () -> Unit,
    onPlay: () -> Unit,
    onSleep: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = pet.emoji, fontSize = 96.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            text = pet.name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = pet.mood,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )

        Spacer(Modifier.height(32.dp))

        StatBar("Сытость", pet.satiety)
        StatBar("Энергия", pet.energy)
        StatBar("Счастье", pet.happiness)
        StatBar("Здоровье", pet.health)

        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
        ) {
            Button(onClick = onFeed) { Text("🍎 Кормить") }
            Button(onClick = onPlay) { Text("🎾 Играть") }
            Button(onClick = onSleep) { Text("💤 Спать") }
        }
    }
}

@Composable
private fun StatBar(label: String, value: Int) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
            Text("$value", style = MaterialTheme.typography.bodyLarge)
        }
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { value / 100f },
            modifier = Modifier.fillMaxWidth().height(10.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PetContentPreview() {
    PetTheme {
        PetContent(pet = Pet(), onFeed = {}, onPlay = {}, onSleep = {})
    }
}
