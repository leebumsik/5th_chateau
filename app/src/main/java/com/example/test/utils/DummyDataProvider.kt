package com.example.randomuserlist.utils

import com.example.test.App
import com.example.test.R

data class RandomUser(
    // 아래 변수 3가지에 들어있는 값들은 디폴트값이고 파라메터 입력으로 변경 가능
    val name: String = "unit",
    val description: String = "unit",
    val profileImage: Int = R.drawable.quara,
    val grade : Double = 4.6,
    val price : String = "￦ 543,504"
)
val winename = mutableListOf("chateaumoutonrothschild", "chateaumargaux", "chateaulatour",
    "chateaulafiterothschild","chateauhautbrion")


object DummyDataProvider {

   var userList = winename.map { name ->
       val resource = App.context().resources.getIdentifier(
           name,
           "drawable",
           App.context().packageName)

       RandomUser(
           name,
           "와인의 생산국가는?",
           resource,
           grade = 4.6,
           price = "￦ 543,504"

       )
   }
}