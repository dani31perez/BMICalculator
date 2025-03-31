package com.example.bmicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bmicalculator.ui.theme.BMICalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BMICalculatorTheme {
                BMIApp()
            }
        }
    }
}

@Composable
fun BMIApp() {
    var weight by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableFloatStateOf(0f) }

    BMIScreen(
        weight = weight,
        height = height
    )
}

@Composable
fun BMIScreen(
    weight: Float?,
    height: Float
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 20.dp)
    ) {
        Row  (
            modifier = Modifier
                .background(Color(0xFF3aacce))
                .fillMaxWidth()
                .padding(16.dp)
        ){
            Text("BMI Calculator", fontSize = 26.sp, color = Color.White)

        }
        OutlinedTextField(
            value = if(weight == 0f) "" else weight.toString(),
            onValueChange = {},
            label = { Text("Weight (lbs)") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )

        OutlinedTextField(
            value = if(height == 0f) "" else height.toString(),
            onValueChange = {},
            label = { Text("Height (cm)") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMICalculatorTheme {
        BMIScreen(
            weight = 0f,
            height = 0f
        )
    }
}