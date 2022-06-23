package com.example.randomuserlist.utils

import android.os.AsyncTask
import android.util.Log
import com.example.test.App
import com.example.test.R
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

data class RandomUser(
    // 아래 변수 3가지에 들어있는 값들은 디폴트값이고 파라메터 입력으로 변경 가능
    val name: String = "chateaumoutonrothschild",
    val description: String = "샤또 무똥 로칠드",
    val profileImage: Int = R.drawable.chateaumoutonrothschild,
    val url: String = "https://www.vivino.com/chateau-mouton-rothschild-pauillac-premier-grand-cru-classe/w/1684223",
    val rating: String = "4.6",
    val price: String = "￦ 543,504",
    val id: String = "3361520"
)

// =======================================================================================================

fun fetchLastResult(url: String): String {
    Log.d("test", "url : ${url}")
    val task = JsoupAsyncTask(url)
    task.execute(url)
    val file: Document = task.get()

    val rating = file.toString().indexOf("ratings_average")
    val rating2 = file.toString().substring(rating + 17, rating + 20)
    Log.d("test", "rating2 (평점정보) : ${rating2}")

    return rating2
}


fun fetchLastResult2(id: String): String {
    Log.d("test", "id : ${id}")
    val url = "https://www.vivino.com/api/checkout_prices?vintage_id=${id}&language=en"
    val task = JsoupAsyncTask2(url)
    task.execute(url)
    val file: Document = task.get()
    Log.d("test", "file (vivino - http - API : price 정보) : ${file}")

    val rating = file.toString().indexOf("\"amount\":")
    val rating2 = file.toString().indexOf(",\"type\":")
    val rating3 = file.toString().substring(rating + "\"amount\":".length, rating2)
    Log.d("test", "rating3 (가격정보) : ${rating3}")

    return rating3
}

private class JsoupAsyncTask(val url: String) : AsyncTask<Any, Int, Document>() {
    override fun doInBackground(vararg p0: Any?): Document {
        return Jsoup.connect(url)
            .get()
    }
}
private class JsoupAsyncTask2(val url: String) : AsyncTask<Any, Int, Document>() {
    override fun doInBackground(vararg p0: Any?): Document {
        return Jsoup.connect(url)
            .ignoreContentType(true)
            .header("Accept", "application/json")
            .get()
    }
}

//=======================================================================================================

fun createWineObject(
    name: String,
    url: String = "https://www.vivino.com/wines/3361520",
    id: String = "3361520",
    description: String = "와인의 생산국가는?",
    rating: String = "4.6",
    price: String = "￦ 543,504 원"
): RandomUser {
    val resource = App.context().resources.getIdentifier(
        name,
        "drawable",
        App.context().packageName
    )

    return RandomUser(
        name,
        description,
        resource,
        url,
        rating,
        price,
        id
    )
}

// 싱글톤 : 파라메타 넘기지 않아도 해당 코드내에서 어디든 사용 가능
object DummyDataProvider {

    var userList: List<RandomUser> = listOf(
        createWineObject(
            "chateaumoutonrothschild",
            id = "3361520"),
        createWineObject(
            "chateaumargaux",
            "https://www.vivino.com/wines/1510407",
            id = "1510407"),
        createWineObject(
            "chateaulatour",
            "https://www.vivino.com/wines/2688418",
            id = "2688418"),
        createWineObject(
            "chateaulafiterothschild",
            "https://www.vivino.com/wines/1526555",
            id = "1526555"),
        createWineObject(
            "chateauhautbrion",
            "https://www.vivino.com/wines/1488311",
            id = "1488311"),
    )

}

