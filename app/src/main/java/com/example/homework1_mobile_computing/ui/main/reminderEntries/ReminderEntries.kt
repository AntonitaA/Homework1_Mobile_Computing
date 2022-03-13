package com.example.homework1_mobile_computing.ui.main.reminderEntries

import android.app.AlertDialog
import android.content.DialogInterface
import android.text.InputType
import android.widget.EditText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homework1_mobile_computing.R
import com.example.homework1_mobile_computing.data.entity.Reminder
import com.example.homework1_mobile_computing.ui.reminder.ReminderViewModel
import com.example.homework1_mobile_computing.ui.theme.Purple200
import com.example.homework1_mobile_computing.ui.theme.Purple700
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReminderEntries (
    modifier: Modifier = Modifier,
    reminderNavController: NavController
){
    val viewModel: ReminderEntriesViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()

    val latlang = reminderNavController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value



    Column(modifier = modifier)
    {
        val givenList = viewState.reminders
        val newList = mutableListOf<Reminder>()
        for (i in givenList.count()-1 downTo 0){
            if((givenList[i].dateAndTime) < Date().time){
                newList.add(givenList[i])
            }
        }

        val locationReminders = mutableListOf<Reminder>()
        for (reminder in givenList){
            if (latlang != null) {
                if(abs(reminder.longitude - latlang.longitude) <= 2 &&
                    abs(reminder.latitude - latlang.latitude) <= 2){
                    locationReminders.add(reminder)
                }
            }
        }

        if (latlang==null){

            ReminderList(
                //list = viewState.reminders,
                list = newList,
                reminderNavController
            )
        }
        else{
            ReminderList(
                //list = viewState.reminders,
                list = locationReminders,
                reminderNavController
            )

        }


        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                reminderNavController.navigate("AllRemindersScreen")
            },
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
        ) {
            Text(
                text = "Show all reminders",
                color = Color.White
            )
        }


        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
                reminderNavController.navigate("map/${"location"}")
            },
            enabled = true,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(backgroundColor = Purple700)
        ) {
            Text(
                text = "Virtual Location",
                color = Color.White
            )
        }
    }
}



@Composable
private fun ReminderList(
    list: List<Reminder>,
    reminderNavController: NavController
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
                reminderNavController
            )
        }
    }
}


@Composable
fun ReminderLisItem(
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
            text = reminder.title, /*longitude.toString()*/
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
            text = reminder.dateAndTime.toDateString(), /*latitude.toString()*/
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
                contentDescription = stringResource(R.string.edit),
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
                contentDescription = stringResource(R.string.delete),
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
                contentDescription = stringResource(R.string.more),
                tint = Color.White
            )
        }

    }
}


fun Long.toTimeString(): String{
    return SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date(this))
}

fun Long.toDateString(): String {
    return SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault()).format(Date(this))
}


private fun String.toDateString(): String{
    return SimpleDateFormat("dd, MMM yyyy", Locale.getDefault()).format((this))
}