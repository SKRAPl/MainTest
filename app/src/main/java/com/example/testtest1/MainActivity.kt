package com.example.testtest1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testtest1.ui.theme.TestTest1Theme

data class EventItem(val title: String, val text: String, val status: String)

data class TicketItem(val name: String, val Ceremony: String? = null)

enum class Screen {
    Events, Tickets, TicketsCreate, Records
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

                Screen.TicketsCreate -> {
                    Text("Tickets Create", fontSize = 24.sp, fontWeight = FontWeight.Bold)
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

            Screen.Tickets -> Tickets(Modifier.padding(innerPadding), onClick = {
                currentStep = Screen.TicketsCreate
            })

            Screen.Records -> Records(Modifier.padding(innerPadding))

            Screen.TicketsCreate -> TicketsCreate(Modifier.padding(innerPadding))
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
fun Tickets(modifier: Modifier = Modifier, onClick: () -> Unit) {

    val openingTickets = listOf(
        TicketItem("Jack", "A6 Row7 Column9"), TicketItem("Rose", "A7 Row8 Column6")
    )

    val nestedScrollInterop = rememberNestedScrollInteropConnection()

    val closingTickets = listOf(
        TicketItem("Jack", "A6 Row7 Column9"), TicketItem("Rose", "A7 Row8 Column6")
    )

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 80.dp, bottom = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick,
            shape = RoundedCornerShape(3.dp),
            modifier = Modifier.padding(top = 10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF5F5DC), contentColor = Color.Black
            ),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(text = "Create Ticket a new ticket", fontSize = 18.sp)
        }
        Spacer(Modifier.height(80.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(nestedScrollInterop),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Opening Ceremony Tickets",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Spacer(Modifier.height(8.dp))
            }
            items(openingTickets) { ticket ->
                TicketItemRow(ticket = ticket)
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                Spacer(modifier = Modifier.height(80.dp))
                Text(
                    text = "Closing Ceremony Tickets",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                )
                Spacer(Modifier.height(8.dp))
            }
            items(closingTickets) { ticket ->
                TicketItemRow(ticket = ticket)
                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}

@Composable
fun TicketItemRow(ticket: TicketItem) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .background(Color(0xFFF0F0F0), RoundedCornerShape(0.dp))
            .padding(end = 20.dp, bottom = 5.dp, top = 5.dp)


    ) {
        Text(text = ticket.name, fontWeight = FontWeight.Bold, fontSize = 23.sp)
        Spacer(Modifier.height(50.dp))
        if (ticket.Ceremony != null) {
            Text(
                text = ticket.Ceremony,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TestPreview() {
    TestTest1Theme {
        Tickets { }
    }
}

@Composable
fun TicketsCreate(modifier: Modifier = Modifier) {

    var expanded by remember { mutableStateOf(false) }
    var selectedCeremony by remember { mutableStateOf("Opening Ceremony") }
    var name by remember { mutableStateOf("") }



    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(modifier = Modifier.clickable { expanded = !expanded }) {
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .background(Color.White, RoundedCornerShape(3.dp))
                    .border(2.dp, Color.LightGray)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = selectedCeremony, color = Color.Black)
                Icon(
                    painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                    contentDescription = "Dropdown",
                    tint = Color.Gray
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                DropdownMenuItem(
                    text = { Text("Opening Ceremony") },
                    onClick = { selectedCeremony = "Opening Ceremony"; expanded = false })
                DropdownMenuItem(
                    text = { Text("Closing Ceremony") },
                    onClick = { selectedCeremony = "Closing Ceremony"; expanded = false })
            }
            OutlinedTextField(
                value = name,
                onValueChange = { newText -> name = newText },
                label = { Text(text = "Input your name") },
                modifier = Modifier.width(300.dp)
                )
        }
    }
}

@Preview (showBackground = true, showSystemUi = true)
@Composable
fun TicketCreatePreview(){
    TestTest1Theme {
        TicketsCreate()
    }
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

@Composable
fun ButtonScreen(icon: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onClick) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Button",
            )
        }
    }
}
