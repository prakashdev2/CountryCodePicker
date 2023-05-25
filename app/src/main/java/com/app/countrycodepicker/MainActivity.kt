package com.app.countrycodepicker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.app.countrycodepicker.ui.theme.CountryCodePickerTheme
import com.app.countrycodepickerjetpackcompose.CountryCodePicker
import com.app.countrycodepickerjetpackcompose.component.TogiCountryCodePicker

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryCodePickerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val phoneNumber = rememberSaveable { mutableStateOf("") }
                    val fullPhoneNumber = rememberSaveable { mutableStateOf("") }
                    val onlyPhoneNumber = rememberSaveable { mutableStateOf("") }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        TogiCountryCodePicker(
                            text = phoneNumber.value,
                            onValueChange = {
                                phoneNumber.value = it
                            },
                            placeHolder = "Phone Number",
                            color1 = TextFieldDefaults.outlinedTextFieldColors(
                                cursorColor = MaterialTheme.colorScheme.primary,
                                unfocusedBorderColor = Color.Gray.copy(0.75f),
                                focusedBorderColor = MaterialTheme.colorScheme.primary,
                            )

                        )

                        }
                    }

                }
            }
        }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Demo() {

    var text by remember{ mutableStateOf("")}
    val expand = remember{ mutableStateOf(false)}
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        CountryCodePicker(
            text = text,
            onValueChange = {
                text = it
            },
            place = "",
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black
            ),
            expand = expand,
            onDismiss = {expand.value = false},
            onClick = {
                expand.value = false
            }
        )
    }



}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CountryCodePickerTheme {
        Greeting("Android")
    }
}