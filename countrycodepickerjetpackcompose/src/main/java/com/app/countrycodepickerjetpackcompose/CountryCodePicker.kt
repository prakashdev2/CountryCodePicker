package com.app.countrycodepickerjetpackcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryCodePicker(
    modifier : Modifier = Modifier,
    text : String,
    onValueChange : (String) -> Unit,
    place : String,
    colors : TextFieldColors,
    expand: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onClick: () -> Unit
   // iconClick : () -> Unit
) {

  //  val expand = remember{ mutableStateOf(false)}
    var image by remember{ mutableStateOf(coutryCodeList[1].countryImage)}

    if (expand.value){
        CountryDialog(
            expand = expand,
            onDismiss = onDismiss,
            onClick = onClick
        )
    }

    Column(modifier = Modifier.background(Color.White)) {
        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = place)
            },
            shape = RoundedCornerShape(16.dp) ,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            colors = colors,
            leadingIcon = {
                IconButton(
                    onClick = {
                        expand.value = true
                    }
                ) {
                    Image(
                        painter = painterResource( R.drawable.ch),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }

            }
        )
//        if (expand.value){
//            CategoryDropDown(
//                serviceList = coutryCodeList,
//                expand = expand,
//                onDismiss = {  },
//                dropDownClick = {
//                    image = it.countryImage
//                    expand.value = false
//                }
//            ) {
//
//            }
//        }
    }




    
}


@Composable
fun CountryDialog(
    expand : MutableState<Boolean>,
    onDismiss: () -> Unit,
    onClick: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {

        Column(modifier = Modifier.background(Color.White)) {

            LazyColumn( verticalArrangement = Arrangement.spacedBy(16.dp)){
                items(coutryCodeList){ country ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = country.countryImage),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp)
                        )
                        Text(
                            text = country.countryName,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(start = 12.dp)
                        )

                    }
                }
            }

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropDown(
    serviceList : List<CountryCodeData>,
    modifier: Modifier = Modifier,
    expand : MutableState<Boolean>,
    onDismiss : () -> Unit,
    dropDownClick : (CountryCodeData) -> Unit,
    onClick: () -> Unit

) {

    DropdownMenu(
        expanded = expand.value,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .background(Color(0XFF2D2D2D))
    ) {
        serviceList.forEach { label ->
            DropdownMenuItem(
                colors = MenuDefaults.itemColors(
                    textColor = Color.White.copy(0.5f)
                ),
                onClick = { dropDownClick(label) },
                leadingIcon = {
                      Image(
                          painter = painterResource(id = label.countryImage),
                          contentDescription = null
                      )
                },
                text = {
                    Text(
                        text = label.countryName,
                        fontSize = 14.sp,
                        color = Color.White.copy(0.5f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }

//    Card(
//        modifier = modifier
//            .height(52.dp),
//        onClick = onClick,
//        colors = CardDefaults.cardColors(
//            containerColor =    Color(0XFF2D2D2D)
//        ),
//        border = BorderStroke(1.dp,Color.White.copy(0.35f))
//    ) {
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(48.dp),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//            Text(
//                text = category,
//                modifier = Modifier
//                    .weight(1f)
//                    .padding(start = 16.dp),
//                fontSize = 14.sp,
//                color = Color.White.copy(0.5f)
//            )
//
//            Icon(
//                imageVector = icons,
//                contentDescription = null,
//                modifier = Modifier
//                    .weight(0.3f)
//                    .size(24.dp),
//                tint = Color(0XFFF8C950)
//            )
//        }
//
//
//
//    }

}

data class CountryCodeData(
    val id : Int,
    val countryName : String,
    val countryImage : Int
)

val coutryCodeList = listOf(
    CountryCodeData(1,"Australia",R.drawable.ge),
    CountryCodeData(1,"India",R.drawable.hn),
    CountryCodeData(1,"America",R.drawable.ng),
    CountryCodeData(1,"China",R.drawable.cg),
    CountryCodeData(1,"Japan",R.drawable.ch),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
    CountryCodeData(1,"South Africa",R.drawable.de),
)


