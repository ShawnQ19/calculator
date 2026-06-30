package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorScreen()
        }
    }
}

private fun formatNumber(value: Float): String {
    return if (value == value.toLong().toFloat()) {
        value.toLong().toString()
    } else {
        String.format("%.1f", value)
    }
}

@Composable
fun CalculatorScreen() {
    var currentValue by remember { mutableFloatStateOf(100f) }
    var actionText by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF1A1A2E), Color(0xFF16213E), Color(0xFF0F3460))
                )
            )
            .padding(horizontal = 20.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // 标题
        Text(
            text = "统分计算器",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Light,
            letterSpacing = 6.sp
        )

        // 显示区
        DisplayCard(
            currentValue = currentValue,
            actionText = actionText
        )

        // 按钮区
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 第一行：0.5, 1, 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CalcButton(label = "-0.5", color = Color(0xFFB388EE), weight = 1f) {
                    currentValue = (currentValue - 0.5f).coerceAtLeast(0f)
                    actionText = 1
                }
                CalcButton(label = "-1",   color = Color(0xFF4ECDC4), weight = 1f) {
                    currentValue = (currentValue - 1f).coerceAtLeast(0f)
                    actionText = 1
                }
                CalcButton(label = "-2",   color = Color(0xFF45B7D1), weight = 1f) {
                    currentValue = (currentValue - 2f).coerceAtLeast(0f)
                    actionText = 1
                }
            }

            // 第二行：5, 10
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CalcButton(label = "-5",  color = Color(0xFFF7DC6F), weight = 1f) {
                    currentValue = (currentValue - 5f).coerceAtLeast(0f)
                    actionText = 1
                }
                CalcButton(label = "-10", color = Color(0xFFE94560), weight = 1f) {
                    currentValue = (currentValue - 10f).coerceAtLeast(0f)
                    actionText = 1
                }
                // AC 重置按钮
                Button(
                    onClick = {
                        currentValue = 100f
                        actionText = 2
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(72.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6C5CE7)
                    )
                ) {
                    Text(
                        text = "AC 重置",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 2.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayCard(currentValue: Float, actionText: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF252540).copy(alpha = 0.85f))
            .padding(horizontal = 28.dp, vertical = 36.dp)
    ) {
        // 上一条操作提示
        when (actionText) {
            1 -> Text(
                text = "已减去",
                color = Color(0xFFE94560),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            2 -> Text(
                text = "已重置为 100",
                color = Color(0xFF00D9FF),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            else -> Text(
                text = "起始值 100",
                color = Color.White.copy(alpha = 0.35f),
                fontSize = 22.sp,
                fontWeight = FontWeight.Light
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // 当前数值
        Text(
            text = formatNumber(currentValue),
            color = Color.White,
            fontSize = 80.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 剩余进度条
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(Color.White.copy(alpha = 0.08f))
        ) {
            val ratio = (currentValue / 100f).coerceIn(0f, 1f)
            Box(
                modifier = Modifier
                    .fillMaxWidth(ratio)
                    .height(5.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(Color(0xFF00D9FF), Color(0xFF00FF88))
                        )
                    )
            )
        }
    }
}

@Composable
fun CalcButton(
    label: String,
    color: Color,
    weight: Float,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(weight)
            .height(72.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = color.copy(alpha = 0.18f),
            contentColor = color
        )
    ) {
        Text(
            text = label,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
