package com.example.test

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.randomuserlist.utils.DummyDataProvider
import com.example.randomuserlist.utils.RandomUser
import com.example.test.ui.theme.Compose_fundamental_tutorialTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineScope

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_fundamental_tutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    WineExplanation()
                }
            }
        }
    }
}

//private fun setListener(){
//    binding.apply{
//        searchIcon.also{
//            it.isSunmitButtonEnabld = true
//            it.setOnQueryTextListener(SearchView.OnQueryTextListener())
//        }
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WineExplanation() {

    // DrawerValue.Closed : 서랍을 닫았을때의 상태
    //rememberDrawerState : 서랍의 상태가 닫였는지, 열렸는지 기억한다
    // => 즉 서랍이 닫힌 상태를 drawerState 변수로 저장한다
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    // 모달이 닫혀있는 상태로 기억을 하고 content 화면을 띄워주니 코루틴 스코프를 기억해야 한다?????
    val scope = rememberCoroutineScope()
    //
    var selectionWine by remember { mutableStateOf(RandomUser()) }
    // changeSelectionWine 변수는 RandomUser 를 입력으로 받아 Unit(비어있는) 출력이 없다
    // 처리는 위에서 입력받은 RandomUser 를 selectedWine 으로 받아 selectionWine 으로 바꿔 준다
    val changeSelectionWine: (RandomUser) -> Unit =
        {
                selectedWine ->
            selectionWine = selectedWine
        }



    ModalDrawer(
        // 스와이프 기능
        gesturesEnabled = true,
        drawerState = drawerState,
        // 모달창 화면 내용
        drawerContent = {
            if (selectionWine.name == "chateaumoutonrothschild")
            {
                Image(
                    modifier = Modifier.size(500.dp),
                    painter = painterResource(id = R.drawable.chateaumoutonrothschild),
                    contentDescription = "샤또 무똥 로칠드")
                Text(
                    "\n 이름 : 샤또 무똥 로칠드 " +
                            "\n 지역 : 뽀이약" +
                            "\n 특징 : 유명 미술품을 라벨에 프린트 적용" +
                            "\n 처음 등급을 제정할 당시는 2등급" +
                            "\n 이후 꾸준한 노력으로 1973년에 1등급")
            }

            if (selectionWine.name == "chateaulafiterothschild")
            {
                Image(
                    modifier = Modifier.size(500.dp),
                    painter = painterResource(id = R.drawable.chateaulafiterothschild),
                    contentDescription = "샤또 라피트 로칠드")
                Text(
                    "\n 이름 : 샤또 라피트 로칠드 " +
                            "\n 지역 : 뽀이약 " +
                            "\n 특징 : 베르사이유 궁의 공식 와인" +
                            "\n 루이 15세가 지방으로 다시 돌아온 총독에게 젊어 보이는 비결을 묻자" +
                            "\n 라피트라고 말하여 베으사이유 궁의 공식 와인이 됨")
            }


            if (selectionWine.name == "chateaulatour")
            {
                Image(
                    modifier = Modifier.size(500.dp),
                    painter = painterResource(id = R.drawable.chateaulatour),
                    contentDescription = "샤또 라뚜르")
                Text(
                    "\n 이름 : 샤또 라뚜르 " +
                            "\n 지역 : 뽀이약 " +
                            "\n 특징 : 강을 거슬러 올라오는 해적을 막기 위해 조정된 탑이 그려짐" +
                            "\n 프랑스어로 라뚜르는 탑이라는 뜻이다" +
                            "\n 장기 숙성형 와인으로 5대 샤또 중 가장 느리게 숙성")
            }

            if (selectionWine.name == "chateaumargaux")
            {
                Image(
                    modifier = Modifier.size(500.dp),
                    painter = painterResource(id = R.drawable.chateaumargaux),
                    contentDescription = "샤또 마고")
                Text(
                    "\n 이름 : 샤또 마고 " +
                            "\n 지역 : 마고 " +
                            "\n 특징 : 메독지역의 동남쪽에 위치한 마고마을은 맛이 우아하고 섬세" +
                            "\n 노인과 바다로 잘 알려진 헤미웨이가 사랑한 와인" +
                            "\n 그는 손녀를 헤밍웨이 마고라 불렀으며, 손녀와 마고를 사랑했다")
            }

            if (selectionWine.name == "chateauhautbrion")
            {
                Image(
                    modifier = Modifier.size(500.dp),
                    painter = painterResource(id = R.drawable.chateauhautbrion),
                    contentDescription = "샤또 오브리옹")
                Text(
                    "\n 이름 : 샤또 오브리옹 " +
                            "\n 지역 : 그라브의 페삭 " +
                            "\n 특징 : 5대 사또 중 유일하게 메독지역이 아닌 그라브페삭 지역" +
                            "\n 로빈슨 크루소로 잘 알려진 다니엘 디포가 사랑한 와인" +
                            "\n 걸리버 여행기로 잘 알려진 조나단 스위프트가 사랑한 와인")
            } },
        content = {
            CView(scope, drawerState, changeSelectionWine)
        }
    )
}

@Composable
fun MyAppBar(){
    TopAppBar(elevation = 10.dp,
        backgroundColor = MaterialTheme.colors.primary,
//        backgroundColor = MyGreen,
        modifier = Modifier.height(58.dp)
    ) {
        Text(
            text = stringResource(id = R.string.my_app_name),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
// scope : 변수명 , CoroutineScope : 파라메터 타입
fun CView(
    scope: CoroutineScope,
    drawerState: DrawerState,
    changeSelectionWine: (RandomUser) -> Unit )
{
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background)
    {
        Scaffold(backgroundColor = Color.White,
            topBar = { MyAppBar() })
        {
            RandomUserListView(randomUsers = DummyDataProvider.userList, scope, drawerState, changeSelectionWine)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RandomUserListView(
    randomUsers: List<RandomUser>,
    scope: CoroutineScope,
    drawerState: DrawerState,
    changeSelectionWine: (RandomUser) -> Unit
){
    // 메모리 관리가 들어간 LazyColumn
    val listState = rememberLazyListState()

    LazyColumn(state = listState,
        modifier = Modifier.simpleVerticalScrollbar(listState, 8.dp)){
        items(randomUsers){ aRandomUser ->
            RandomUserView(aRandomUser, scope, drawerState, changeSelectionWine) }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RandomUserView(
    randomUser: RandomUser,
    scope: CoroutineScope,
    drawerState: DrawerState,
    changeSelectionWine: (RandomUser) -> Unit,
) {
    val openDialog = remember { mutableStateOf(false)  }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val typography = MaterialTheme.typography
    Column() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp),
//                .size(200.dp),
            elevation = 10.dp,
            shape = RoundedCornerShape(15.dp)
        ) {
            Column() {
                Column() {
                    Text(
                        text = randomUser.name,
                        style = typography.subtitle1,
                        fontSize = 17.sp,
                        modifier = Modifier.padding(7.dp)
                    )

                    Row(
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        ProfileImg(
                            modifier = Modifier
                                .padding(0.dp)
                                .size(100.dp),
                            imgUrl = randomUser.profileImage
                        )
                        Column() {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Button(
                                    modifier = Modifier.size(88.dp, 40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Blue, contentColor = Color.White),
                                    onClick = {
                                        changeSelectionWine(randomUser)
                                        scope.launch { drawerState.open() }
                                    })
                                {
                                    Text(text = "상세정보")
                                }

                                Button(
                                    modifier = Modifier.size(70.dp, 40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Blue, contentColor = Color.White),
                                    onClick = {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                "${randomUser.grade}",
                                                "확인",
                                                SnackbarDuration.Short)}
                                    })
                                {
                                    Text(text = "평점")
                                }

                                Button(
                                    modifier = Modifier.size(70.dp, 40.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.Blue, contentColor = Color.White),
                                    onClick = {
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                "${randomUser.price}",
                                                "확인",
                                                SnackbarDuration.Short)}
                                    }
                                )
                                {
                                    Text(text = "가격")
                                }
                            }

                            // 스낵바가 보여지는 부분
                            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.size(260.dp, 70.dp))
                        }
                    }
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileImg(imgUrl: Int, modifier: Modifier = Modifier){
    // 이미지 비트맵
    val bitmap : MutableState<Bitmap?> = mutableStateOf(null)
    // 이미지 모디파이어
    val imageModifier = modifier
        .fillMaxSize()
//        .clip(RoundedCornerShape(10.dp))
//        .clip(CircleShape)

    // 이미지 사용가능하도록 하는 라이브러리 글라이드 사용
    Glide.with(LocalContext.current)
            // 비트맵을 이용하여
        .asBitmap()
            // imgUrl 파라메터를 사용하여 이미지를 띄워준다
        .load(imgUrl)
            //
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap.value = resource
            }
            override fun onLoadCleared(placeholder: Drawable?) { }
        })

    // 비트 맵이 있다면
    bitmap.value?.asImageBitmap()?.let { fetchedBitmap ->
        Image(bitmap = fetchedBitmap,
//            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = imageModifier
        )
    } ?: Image(painter = painterResource(id = R.drawable.ic_empty_user_img),
        contentScale = ContentScale.Fit,
        contentDescription = null,
        modifier = imageModifier

    )
}


@Composable
fun Modifier.simpleVerticalScrollbar(
    state: LazyListState,
    width: Dp
): Modifier {
    // 프론트엔드에서 알파는 투명도를 의미함
    // 스크롤이 작동을하는 중 이라면 1F를 받고 아니라면 0f를 받겠다
    // 투명도를 설정해 주기
    val targetAlpha = if (state.isScrollInProgress) 1f else 0f
    //스크롤이 작동을하는 중 이라면 150를 받고 아니라면 500를 받겠다
    //
    val duration = if (state.isScrollInProgress) 150 else 500

    val alpha by animateFloatAsState(
        targetValue = targetAlpha,
        animationSpec = tween(durationMillis = duration)
    )

    return drawWithContent {
        drawContent()

        // 처음 보이는 항목 인덱스 = 레이아웃에 보이는 아이템 정보(처음이거나 아니거나) 인덱스
        val firstVisibleElementIndex = state.layoutInfo.visibleItemsInfo.firstOrNull()?.index
        // 필수로 그려주는 스크롤바 = 스크롤이 움직이고 있거나 알파값이 0보다 클때
        val needDrawScrollbar = state.isScrollInProgress || alpha > 0.0f

        // Draw scrollbar if scrolling or if the animation is still running and lazy column has content
        // needDrawScrollbar,firstVisibleElementIndex 값이 둘다 null이 아닐때
        if (needDrawScrollbar && firstVisibleElementIndex != null) {
            val elementHeight = this.size.height / state.layoutInfo.totalItemsCount
            val scrollbarOffsetY = firstVisibleElementIndex * elementHeight
            val scrollbarHeight = state.layoutInfo.visibleItemsInfo.size * elementHeight

            drawRect(
                color = Color.Gray,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}

val winenameconuntry = mapOf("chateaumoutonrothschild" to "France", "chateaumargaux" to "France",
    "chateaulatour" to "France", "chateaulafiterothschild" to "France",
    "chateauhautbrion" to "France")


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DefaultPreview() {
    Compose_fundamental_tutorialTheme {

    }
}


