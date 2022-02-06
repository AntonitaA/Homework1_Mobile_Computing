package com.example.homework1_mobile_computing.ui.main.reminderEntries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LineStyle
import androidx.compose.material.icons.filled.ViewHeadline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homework1_mobile_computing.data.entity.Reminder
import com.google.accompanist.insets.systemBarsPadding
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReminderEntries (
    modifier: Modifier = Modifier
){
    val viewModel: ReminderEntriesViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()

    Column(modifier = modifier)
        {
        ReminderList(
            list = viewState.reminders
        )

    }
}

@Composable
private fun ReminderList(
    list: List<Reminder>
){
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ){
        items(list){ item ->
            ReminderLisItem(
                reminder = item,
                onClick = {},
                modifier = Modifier.fillParentMaxWidth(),
            )
        }
    }
}

@Composable
fun ReminderLisItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    ConstraintLayout(
        modifier = modifier.clickable { onClick() }
    ){
        val (divider, reminderTitle, icon, date, time) = createRefs()
        Divider(
            Modifier.constrainAs(divider){
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )
        // title
        Text(
            text = reminder.reminderTitle,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            fontSize = 17.sp,
            modifier = Modifier.constrainAs(reminderTitle){
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 20.dp,
                    endMargin = 20.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )
        // date
        Text(
            text = when{
                reminder.reminderDate != null -> {reminder.reminderDate.formatToString()}
                else -> Date().formatToString()
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(date){
            linkTo(
                start = reminderTitle.start,
                end = time.start,
                startMargin = 8.dp,
                endMargin = 16.dp,
                bias = 0f
            )
            centerVerticallyTo(reminderTitle)
            top.linkTo(reminderTitle.bottom, 6.dp)
            bottom.linkTo(parent.bottom, 10.dp)
            }
        )

        // time
        Text(
            text = reminder.reminderTime,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(time){
                linkTo(
                    start = date.end,
                    end = icon.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                //centerVerticallyTo(reminderTitle)
                //top.linkTo(reminderTitle.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )


        IconButton(
            onClick = {},
            modifier = Modifier
                .size(50.dp)
                .padding(10.dp)
                .constrainAs(icon){
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ){
            Icon(
                imageVector = Icons.Filled.ViewHeadline,
                contentDescription = "Check"        // extract resource from λαμπα
            )
        }
    }
}

private fun Date.formatToString(): String{
    return SimpleDateFormat("MMMM dd, yyy", Locale.getDefault()).format(this)
}