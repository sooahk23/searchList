package com.example.daumsearch.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.TimeZone

object DateTimeUtils {

    fun formatIsoDateTime(dateTime: String): String {
        val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'XXX yyyy", Locale.ENGLISH)
        // 출력 형식 정의
        val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        // 문자열을 Date 객체로 파싱
        val date = inputFormat.parse(dateTime)
        // Date 객체를 새로운 형식으로 포맷
        return outputFormat.format(date)
    }
}