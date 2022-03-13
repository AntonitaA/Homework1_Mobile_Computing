package com.example.homework1_mobile_computing.ui.main.reminderEntries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ViewHeadline
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homework1_mobile_computing.data.entity.Reminder
import com.example.homework1_mobile_computing.ui.reminder.ReminderViewModel
import com.example.homework1_mobile_computing.ui.theme.Purple200
import com.example.homework1_mobile_computing.ui.theme.Purple700
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AllReminders (
    modifier: Modifier = Modifier,
    reminderNavController: NavController
){
    val viewModel: ReminderEntriesViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()


    Column(modifier = modifier)
    {
        val givenList = viewState.reminders
        val newList = mutableListOf<Reminder>()
        for (i in givenList.count()-1 downTo 0){
            if((givenList[i].dateAndTime) < Date().time){
                newList.add(givenList[i])
            }
        }

        for (reminder in newList){
            println(reminder.title)
        }

        AllRemindersList(
            list = viewState.reminders,
            reminderNavController
        )



    }
}

@Composable
private fun AllRemindersList(
    list: List<Reminder>,
    reminderNavController: NavController
){
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ){
        items(list){ item ->
            AllRemindersListItem(
                reminder = item,
                onClick = {},
                modifier = Modifier.fillParentMaxWidth(),
                reminderNavController
            )
        }
    }
}

@Composable
fun AllRemindersListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    reminderNavController: NavController,
    viewModel: ReminderViewModel = viewModel(),

    ) {
    val viewState by viewModel.state.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val selectedReminder: Reminder = reminder

    val title = rememberSaveable { mutableStateOf("") }
    val date = rememberSaveable { mutableStateOf("") }



    ConstraintLayout(
        modifier = modifier.clickable { onClick() }
    ) {
        val (divider, reminderTitle, reminderDate, reminderTime, editIcon, moreIcon, deleteIcon) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )
        // title
        Text(
            text = reminder.title,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            fontSize = 17.sp,
            modifier = Modifier.constrainAs(reminderTitle) {
                linkTo(
                    start = parent.start,
                    end = moreIcon.start,
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
            text = reminder.dateAndTime.toDateString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(reminderDate) {
                linkTo(
                    start = reminderTitle.start,
                    end = reminderTime.start,
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
            text = reminder.dateAndTime.toTimeString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(reminderTime) {
                linkTo(
                    start = reminderDate.end,
                    end = editIcon.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )
// edit button
        FloatingActionButton(
            onClick = {
                reminderNavController.navigate("EditReminderScreen/${(reminder.id).toString()}") /*(reminder.id).toString()*/
            },
            modifier = Modifier
                .size(50.dp)
                .padding(5.dp)
                .constrainAs(editIcon) {
                    top.linkTo(parent.top, 5.dp)
                    bottom.linkTo(parent.bottom, 5.dp)
                    end.linkTo(parent.end)
                    linkTo(
                        start = reminderTime.end,
                        end = deleteIcon.start,
                        startMargin = 20.dp,
                        endMargin = 1.dp,
                        bias = 0f
                    )
                },
            backgroundColor = Purple200
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit",        // extract resource from λαμπα
                tint = Color.White
            )
        }
        FloatingActionButton(
            onClick = {
                coroutineScope.launch {
                    viewModel.deleteReminder(reminder)

                }
            },
            modifier = Modifier
                .size(50.dp)
                .padding(5.dp)
                .constrainAs(deleteIcon) {
                    top.linkTo(parent.top, 5.dp)
                    bottom.linkTo(parent.bottom, 5.dp)
                    end.linkTo(parent.end)
                    linkTo(
                        start = editIcon.end,
                        end = moreIcon.start,
                        startMargin = 1.dp,
                        endMargin = 1.dp,
                        bias = 0f
                    )
                },
            backgroundColor = Purple200
        ){
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",        // extract resource from λαμπα
                tint = Color.White

            )
        }

        FloatingActionButton(
            onClick = {},
            modifier = Modifier
                .size(50.dp)
                .padding(5.dp)
                .constrainAs(moreIcon) {
                    top.linkTo(parent.top, 5.dp)
                    bottom.linkTo(parent.bottom, 5.dp)
                    end.linkTo(parent.end)

                },
            backgroundColor = Purple200
        ){
            Icon(
                imageVector = Icons.Filled.ViewHeadline,
                contentDescription = "More",        // extract resource from λαμπα
                tint = Color.White
            )
        }

    }
}


