package com.example.randomuserlist.utils

import com.example.randomuserlist.utils.DummyDataProvider.userList
import com.example.test.App
import com.example.test.R

data class RandomUser(
    // 아래 변수 3가지에 들어있는 값들은 디폴트값이고 파라메터 입력으로 변경 가능
    val name: String = "quara",
    val description: String = "와인의 생산국가는?",
    val profileImage: Int = R.drawable.quara
)
val winename = mutableListOf("chateaumoutonrothschild", "chateaumargaux", "chateaulatour",
    "chateaulafiterothschild","chateauhautbrion")


object DummyDataProvider {
//    val userList = List<RandomUser>(1){ RandomUser("네임","생산국가",R.drawable.quara) }
//    val userList = listOf(RandomUser("쿠아라","생산국가",R.drawable.quara)



   var userList = winename.map { name ->
       val resource = App.context().resources.getIdentifier(
           name,
           "drawable",
           App.context().packageName)

       RandomUser(
           name,
           "와인의 생산국가는?",
           resource
       )
   }

}

//class DummyDataProviderRe {
//    for (i in 1..5){
//        DummyDataProvider
//}
//
//}