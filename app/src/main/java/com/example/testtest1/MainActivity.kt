package com.example.testtest1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testtest1.ui.theme.TestTest1Theme

data class EventItem(val title: String, val text: String, val status: String)

enum class Screen {
    Events, Tickets, Records
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestTest1Theme {
                CompositionLocalProvider() {
                    MainScreen()
                }
            }
        }
    }
}


@Composable
fun MainScreen() {

    val eventItems = listOf(
        EventItem("Title1", "1", "Read"),
        EventItem("Title2", "2", "Unread"),
        EventItem("Title3", "3", "Unread"),
        EventItem("Title4", "4", "Unread"),
    )

    var currentStep by remember { mutableStateOf(Screen.Events) }

    Scaffold(topBar = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (currentStep) {
                Screen.Events -> {
                    Text("Events List", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }

                Screen.Tickets -> {
                    Text("Tickets List", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }

                else -> {
                    Text("Records List", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }, bottomBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(1.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomBarItem(
                name = "Events",
                isSelected = currentStep == Screen.Events,
                icon = R.drawable.baseline_event_24,
                onClick = { currentStep = Screen.Events })
            BottomBarItem(
                name = "Tickets",
                isSelected = currentStep == Screen.Tickets,
                icon = R.drawable.baseline_confirmation_number_24,
                onClick = { currentStep = Screen.Tickets })
            BottomBarItem(
                name = "Records",
                isSelected = currentStep == Screen.Records,
                icon = R.drawable.baseline_record_voice_over_24,
                onClick = { currentStep = Screen.Records })
        }
    }) { innerPadding ->
        when (currentStep) {
            Screen.Events -> Events(eventItems, Modifier.padding(innerPadding))
            Screen.Tickets -> Tickets(Modifier.padding(innerPadding))
            Screen.Records -> Records(Modifier.padding(innerPadding))
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainPreview() {
    TestTest1Theme {
        MainScreen()
    }
}


@Composable
fun Tickets(modifier: Modifier = Modifier) {

}

@Composable
fun Records(modifier: Modifier = Modifier) {

}

@Composable
fun BottomBarItem(name: String, isSelected: Boolean, icon: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Bottom Bar Icon",
                tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
            )
        }
        Text(
            text = name,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
        )
    }
}
