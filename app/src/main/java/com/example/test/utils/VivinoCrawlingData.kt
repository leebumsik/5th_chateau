package com.example.test.utils

import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.IOException
import java.text.ParseException


object vivinoCrawlingData {
    @Throws(ParseException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val URL = "https://www.vivino.com/chateau-mouton-rothschild-pauillac-premier-grand-cru-classe/w/1684223"
        val doc: Document
        try {
            doc = Jsoup.connect(URL).get()
            Log.d("test", "${doc}")
            val elem = doc.select(".date")
            Log.d("test", "${elem}")
            val str = elem.text().split(" ".toRegex()).toTypedArray()
            Log.d("test", "str :" + "${str}")
            val todaylist = doc.select(".new_totalinfo dl>dd")
            val juga = todaylist[3].text().split(" ".toRegex()).toTypedArray()[1]
            val DungRakrate = todaylist[3].text().split(" ".toRegex()).toTypedArray()[6]
            val siga = todaylist[5].text().split(" ".toRegex()).toTypedArray()[1]
            val goga = todaylist[6].text().split(" ".toRegex()).toTypedArray()[1]
            val zeoga = todaylist[8].text().split(" ".toRegex()).toTypedArray()[1]
            val georaeryang = todaylist[10].text().split(" ".toRegex()).toTypedArray()[1]
            val stype =
                todaylist[3].text().split(" ".toRegex()).toTypedArray()[3] //상한가,상승,보합,하한가,하락 구분
            val vsyesterday = todaylist[3].text().split(" ".toRegex()).toTypedArray()[4]
            println("삼성전자 주가------------------")
            println("주가:$juga")
            println("등락률:$DungRakrate")
            println("시가:$siga")
            println("고가:$goga")
            println("저가:$zeoga")
            println("거래량:$georaeryang")
            println("타입:$stype")
            println("전일대비:$vsyesterday")
            println("가져오는 시간:" + str[0] + str[1])
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }
}