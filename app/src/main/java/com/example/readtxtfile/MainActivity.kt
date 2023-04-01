package com.example.readtxtfile

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fileName = "enteral_high_way.txt"
        val searchString = "horizontalAccuracy"
        val doubleValues = findDoubleNextToString(this, fileName, searchString)

        if (doubleValues.isNotEmpty()) {
            val averageValue = doubleValues.average()
            println("Average value of double values next to '$searchString' in '$fileName': $averageValue")
        } else {
            println("No double values found next to '$searchString' in '$fileName'")
        }
    }
    private fun findDoubleNextToString(context: Context, fileName: String, searchString: String): List<Double> {
        val doubleValues = mutableListOf<Double>()
        try {
            val inputStream = context.assets.open(fileName)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val pattern = Pattern.compile("(?<=${Pattern.quote(searchString)})\\s*([-+]?\\d*\\.\\d+|\\d+)", Pattern.CASE_INSENSITIVE)
            reader.useLines { lines ->
                lines.forEach { line ->
                    val matcher = pattern.matcher(line)
                    while (matcher.find()) {
                        val doubleValue = matcher.group().trim().toDoubleOrNull()
                        if (doubleValue != null) {
                            doubleValues.add(doubleValue)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return doubleValues
    }


//    fun findStringOccurrences(context: Context, fileName: String, searchString: String): Int {
//        var count = 0
//
//        try {
//            val inputStream = context.assets.open(fileName)
//            val reader = BufferedReader(InputStreamReader(inputStream))
//            val pattern = Pattern.compile(Pattern.quote(searchString), Pattern.CASE_INSENSITIVE)
//
//            reader.useLines { lines ->
//                lines.forEach { line ->
//                    val matcher = pattern.matcher(line)
//                    while (matcher.find()) {
//                        count++
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return count
//    }
}