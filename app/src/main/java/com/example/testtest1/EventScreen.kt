package com.example.testtest1

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testtest1.ui.theme.TestTest1Theme

val f = listOf(
    EventItem("Title1", "1", "Read"),
    EventItem("Title2", "2", "Unread"),
    EventItem("Title3", "3", "Unread"),
    EventItem("Title4", "4", "Unread"),
)


@Composable
fun Events(
    eventItems: List<EventItem>, modifier: Modifier = Modifier
) {

    var filterStatus by remember { mutableStateOf<String?>(null) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FilterButtons(
            selectedFilter = filterStatus,
            onFilterChange = { status ->
                filterStatus = status
            })
    }

    val filteredEvents = when (filterStatus) {
        "Unread" -> eventItems.filter { it.status == "Unread" }
        "Read" -> eventItems.filter { it.status == "Read" }
        else -> eventItems
    }

    LazyColumn(
        Modifier.Companion
            .fillMaxSize()
            .padding(top = 120.dp), contentPadding = PaddingValues(16.dp)
    ) {
        items(filteredEvents) { item ->
            EventItemRow(item)
            Spacer(Modifier.Companion.height(16.dp))
        }
    }
}

@Composable
fun EventItemRow(item: EventItem) {
    Row(Modifier.Companion.fillMaxWidth()) {
        Image(
            painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Event Image",
            Modifier.Companion
                .size(100.dp)
                .background(Color.Companion.LightGray)
        )
        Spacer(Modifier.Companion.width(16.dp))
        Column(Modifier.Companion.weight(1f)) {
            Text(text = item.title, fontWeight = FontWeight.Companion.Bold)
            Text(text = item.text)
            Spacer(Modifier.Companion.height(16.dp))
            Text(text = item.status, color = Color.Companion.Gray)
        }
    }
}

@Composable
fun FilterButtons(selectedFilter: String?, onFilterChange: (String?) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        FilterButton(
            text = "All",
            isSelected = selectedFilter == null,
            onClick = { onFilterChange(null) }
        )
        Text(
            "/",
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        FilterButton(
            text = "Unread",
            isSelected = selectedFilter == "Unread",
            onClick = { onFilterChange("Unread") }
        )
        Text(
            "/",
            color = Color.Black,
            fontSize = 25.sp,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        FilterButton(
            text = "Read",
            isSelected = selectedFilter == "Read",
            onClick = { onFilterChange("Read") }
        )
    }
}

@Composable
fun FilterButton(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val textColor = if (isSelected) Color.Red else Color.Black
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
        Text(text, color = textColor, fontSize = 20.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EventPreview() {
    TestTest1Theme {
        Events(f)
    }
}