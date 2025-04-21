package com.example.testtest1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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

    Scaffold(
        topBar = {
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
        },
        bottomBar = {
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
                    icon = R.drawable.baseline_list_alt_24,
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


@Composable
fun Events(
    eventItems: List<EventItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        Modifier.fillMaxSize().padding(top = 60.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(eventItems) { item ->
            EventItemRow(item)
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun EventItemRow(item: EventItem) {
    Row(Modifier.fillMaxWidth()) {
        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Event Image",
            Modifier
                .size(100.dp)
                .background(Color.LightGray)
        )
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(text = item.title, fontWeight = FontWeight.Bold)
            Text(text = item.text)
            Spacer(Modifier.height(16.dp))
            Text(text = item.status, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EventPreview() {
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
