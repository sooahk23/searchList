package com.example.daumsearch.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class DateTimeUtils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatIsoDateTime(dateTime: String): String {
        // ISO 8601 형식의 날짜 시간 파서
        val parser = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        // 원하는 출력 형식
        val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분", Locale.getDefault())

        // 문자열을 LocalDateTime 객체로 파싱한 다음 새로운 형식으로 포맷합니다.
        val parsedDate = LocalDateTime.parse(dateTime, parser)
        return formatter.format(parsedDate)
    }

}