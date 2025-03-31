package com.example.bmicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
    val context = LocalContext.current
    var weight by remember { mutableFloatStateOf(0f) }
    var height by remember { mutableFloatStateOf(0f) }
    var BMI by remember { mutableStateOf<Float?>(null) }
    var category by remember { mutableStateOf("") }
    var color by remember { mutableStateOf<Color>(Color.Unspecified) }

    BMIScreen(
        weight = weight,
        height = height,
        onWeightChange = {value -> try{
            if(value == "")
                weight = 0f
            else{
                val numValue = value.toFloat()
                if(numValue <= 800f)
                    weight = value.toFloat()
                else
                    Toast.makeText(context, "Weight out of range: must be less than 800 lbs", Toast.LENGTH_SHORT).show()
            }
        }catch (e: NumberFormatException ){
            Toast.makeText(context, "You cannot add text in this input", Toast.LENGTH_SHORT).show()
        }},
        onHeightChange = {value -> try{
            if(value == "")
                height = 0f
            else{
                val numValue = value.toFloat()
                if(numValue <= 250f)
                    height = value.toFloat()
                else
                    Toast.makeText(context, "Height out of range: must be less than 250 cm", Toast.LENGTH_SHORT).show()
            }
        }catch (e: NumberFormatException ){
            Toast.makeText(context, "You cannot add text in this input", Toast.LENGTH_SHORT).show()
        }},
        onClick = {
            if(weight < 30f)
                Toast.makeText(context, "Weight must be greater than 30 lbs", Toast.LENGTH_SHORT).show()
            else
                if(height < 50f)
                    Toast.makeText(context, "Height must be greater than 50 cm", Toast.LENGTH_SHORT).show()
                else{
                    BMI = (weight/(height*height))*703
                    if(BMI!! < 18.5f) {
                        category = "Underweight"
                        color = Color.Blue
                    }else if (BMI!! in 18.5f..24.9f){
                        category = "Normal weight"
                        color = Color.Green
                    }
                    else if (BMI!! in 25f..29.9f ){
                        category = "Overweight"
                        color = Color.Yellow
                    }
                    else{
                        category = "Obese"
                        color = Color.Red
                    }
                }
        },
        BMI = BMI,
        category = category,
        color = color
    )
}

@Composable
fun BMIScreen(
    weight: Float,
    height: Float,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onClick: () -> Unit,
    BMI: Float?,
    category: String,
    color: Color
){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFC9EAF1), Color(0xFF1e81b0))
                )
            )
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
            onValueChange = {onWeightChange(it)},
            label = { Text("Weight (lbs)") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        OutlinedTextField(
            value = if(height == 0f) "" else height.toString(),
            onValueChange = {onHeightChange(it)},
            label = { Text("Height (cm)") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {onClick()},
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(16.dp),
        ) {
            Text("Calculate BMI", fontSize = 20.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ){
            BMI?.let {
                index ->
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(25.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(50.dp,50.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        "Your BMI is:",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        index.toString(),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = Color.Green,
                            fontSize = 32.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Text(
                        category,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 16.dp)
                    )

                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BMICalculatorTheme {
        BMIScreen(
            weight = 0f,
            height = 0f,
            onWeightChange = {},
            onHeightChange = {},
            onClick = {},
            BMI = 0f,
            category = "",
            color = Color.Unspecified
        )
    }
}