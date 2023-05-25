package com.app.countrycodepickerjetpackcompose.component

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.app.countrycodepickerjetpackcompose.R
import com.app.countrycodepickerjetpackcompose.data.CountryData
import com.app.countrycodepickerjetpackcompose.utils.searchCountry
import com.app.countrycodepickerjetpackcompose.utils.utils.getCountryName
import com.app.countrycodepickerjetpackcompose.utils.utils.getFlags
import com.app.countrycodepickerjetpackcompose.utils.utils.getLibCountries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TogiCodeDialog(
    modifier: Modifier = Modifier,
    padding: Dp = 15.dp,
    defaultSelectedCountry: CountryData = getLibCountries.first(),
    showCountryCode: Boolean = true,
    pickedCountry: (CountryData) -> Unit,
    showFlag: Boolean = true,
    showCountryName: Boolean = false,

    ) {
    val context = LocalContext.current

    val countryList: List<CountryData> = getLibCountries
    var isPickCountry by remember {
        mutableStateOf(defaultSelectedCountry)
    }
    var isOpenDialog by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Column(modifier = modifier
        .padding(padding)
        .clickable(
            interactionSource = interactionSource,
            indication = null,
        ) {
            isOpenDialog = true
        }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showFlag) {
                Image(
                    modifier = modifier.width(34.dp),
                    painter = painterResource(
                        id = getFlags(
                            isPickCountry.countryCode
                        )
                    ), contentDescription = null
                )
            }
            if (showCountryCode) {
                Text(
                    text = isPickCountry.countryPhoneCode,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            if (showCountryName) {
                Text(
                    text = stringResource(id = getCountryName(isPickCountry.countryCode.lowercase())),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 6.dp),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }


        if (isOpenDialog) {
            CountryDialog(
                countryList = countryList,
                onDismissRequest = { isOpenDialog = false },
                context = context,
                dialogStatus = isOpenDialog,
                onSelected = { countryItem ->
                    pickedCountry(countryItem)
                    isPickCountry = countryItem
                    isOpenDialog = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDialog(
    modifier: Modifier = Modifier,
    countryList: List<CountryData>,
    onDismissRequest: () -> Unit,
    onSelected: (item: CountryData) -> Unit,
    context: Context,
    dialogStatus: Boolean,
) {
    var searchValue by remember { mutableStateOf("") }
    if (!dialogStatus) searchValue = ""

    Dialog(
        onDismissRequest = onDismissRequest,
        content = {
            Surface(
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(25.dp))
            ) {
                Scaffold { scaffold ->
                    scaffold.calculateBottomPadding()
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = "Select Country Code",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            textAlign = TextAlign.Center
                        )
                        SearchTextField(
                            value = searchValue,
                            colors = TextFieldDefaults.textFieldColors(
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                containerColor = Color.Transparent
                            ),
                            placeHolder = stringResource(id = R.string.search),
                            onValueChange = {
                                searchValue = it
                                            },
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        LazyColumn {
                            items(
                                if (searchValue.isEmpty()) countryList else countryList.searchCountry(
                                    searchValue,
                                    context
                                )
                            ) { countryItem ->
                                Row(
                                    Modifier
                                        .padding(18.dp)
                                        .fillMaxWidth()
                                        .clickable(onClick = { onSelected(countryItem) }),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Image(
                                            modifier = modifier.width(30.dp),
                                            painter = painterResource(
                                                id = getFlags(
                                                    countryItem.countryCode
                                                )
                                            ), contentDescription = null
                                        )
                                        Text(
                                            stringResource(id = getCountryName(countryItem.countryCode.lowercase())),
                                            Modifier.padding(horizontal = 18.dp),
                                            fontSize = 14.sp,
                                            fontFamily = FontFamily.Serif,
                                        )
                                    }

                                    Text(
                                        text = countryItem.countryPhoneCode,
                                        Modifier
                                            .padding(horizontal = 18.dp,)
                                        ,
                                        fontSize = 14.sp,
                                        fontFamily = FontFamily.Serif,
                                    )
                                }
                            }
                        }
                    }

                }

            }
        },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    colors : TextFieldColors,
    placeHolder : String,
    onValueChange: (String) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )

            }
        },
        placeholder = {
                      Text(
                          text = placeHolder
                      )
        },
        colors = colors

    )
}