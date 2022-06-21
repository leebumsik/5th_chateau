package com.example.test

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.randomuserlist.utils.DummyDataProvider
import com.example.randomuserlist.utils.RandomUser
import com.example.test.ui.theme.Compose_fundamental_tutorialTheme
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
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
    changeSelectionWine: (RandomUser) -> Unit
) {
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
                        fontSize = 12.sp,
                        modifier = Modifier.padding(7.dp)
                    )

                    Row(
                        modifier = Modifier.padding(0.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ProfileImg(
                            modifier = Modifier
                                .padding(0.dp)
                                .size(100.dp),
                            imgUrl = randomUser.profileImage
                        )

                        Button(
                            modifier = Modifier.size(88.dp, 40.dp),
                            onClick = {
                                changeSelectionWine(randomUser)
                                scope.launch { drawerState.open() }
                            })
                        {
                            Text(text = "상세정보")
                        }

                        Button(
                            modifier = Modifier.size(70.dp, 40.dp),
                            onClick = {
                            })
                        {
                            Text(text = "평점")
                        }

                        Button(
                            modifier = Modifier.size(70.dp, 40.dp),
                            onClick = {
                            })
                        {
                            Text(text = "가격")
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
fun TextFieldTest(randomUser: RandomUser){

    var userInput by remember { mutableStateOf(String()) }

    var userInputsave by remember { mutableStateOf(String()) }

    var inputCheck by remember { mutableStateOf(String()) }

    val shouldShowuserInput = remember { mutableStateOf(false) }

    val userInputResource: (Boolean) -> Int = {
        // if 안에 it 은 : it == true 와 같은 뜻
        if(it) {
            R.drawable.ic_baseline_check_24
            // 디폴트값으로 false를 가지고 있어서 맨 처음 화면은 - 이런 아이콘 표시
        } else {
            R.drawable.ic_baseline_receipt_24
        }
    }

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        TextField(
            modifier = Modifier.fillMaxWidth(),
            // 값은 사용자가 입력한 값
            value = userInput,
            // false라서 입력란을 한줄이상 사용 가능, true는 한줄만 사용
            singleLine = false,
            // 최대 2줄 입력 가능
            maxLines = 2,
            // 키보드..타입 알지?
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            // 키보드를 통해 입력된 새로운값을(newValue) userInput 변수에 넣어준다
            onValueChange = { newValue -> userInput = newValue },
            //
            label = { Text("정답 입력") },
            //
            placeholder = { Text(if (inputCheck == "") {"작성해 주세요"} else {inputCheck}) },
            // 화면 왼쪽 아이콘
            leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null)},
            // 화면 오른쪽 아이콘
//            trailingIcon = { Icon(imageVector = Icons.Default.Check, contentDescription = null)},

//            참조 수정
            trailingIcon = { IconButton(onClick = {
                // 아이콘을 클릭하면 로그를 찍음
                Log.d("test", "TextFieldTest: 비밀번호 visible 버튼 클릭")
                // 아이콘을 클릭하면 shouldShowuserInput.value 값을 반대로 변경 해줌
                shouldShowuserInput.value = !shouldShowuserInput.value
                if (shouldShowuserInput.value == true) {
                    Log.d("test", "userInput"+userInput)
//                    if (userInput == "Argentina")
                    if (userInput == "${winenameconuntry.getValue("${randomUser.name}")}")
                    {
                        userInputsave = userInput
                        userInput = ""
                        inputCheck = "정답입니다"
                    }
                    else {
                        userInputsave = userInput
                        userInput = ""
                        inputCheck = "오답입니다"
                    }
                }
                else {
                    userInput = userInputsave
                }
            })
            {
                // 아이콘은 shouldShowuserInput.value 값을 userInputResource 변수를 이용하여 불리안값으로 변경
                // 위 불리안값을 이용하여 아이콘 변경
                Icon(painter = painterResource(id = userInputResource(shouldShowuserInput.value)),
                    contentDescription = null
                )
            }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ButtonsContainer(randomUser: RandomUser){
    //
    val buttonBorderGradient = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red))
    // 클릭이나 드래그 등의 상태를 반영하는 interactionSource 의 상태를 기억 : 사용자의 상태
    val interactionSource = remember { MutableInteractionSource() }
    // 위 사용자의 상태에서 클릭된 상태를 isPressed 변수에 담기
    val isPressed by interactionSource.collectIsPressedAsState()
    // pressStatusTitle(눌림 상태 제목) 변수는 위의 인터렉션소스 변수를 통해 현재 상태를 가져와서
    // 클릭 상태일때는 버튼을 누르고 있다, 그외는 버튼에서 손을 뗏다 라고 제목을 변경해 준다
    // 밸류값 앞글자만 가져온다 : 실패~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    val pressStatusTitle = if (isPressed) "${winenameconuntry.getValue("${(randomUser.name)}")[0]}+lower" else "힌트 버튼 꾹~"
    // isPressed 함수도 동일한거 두번째 하나더 추가
    val interactionSourceForSecondBtn = remember { MutableInteractionSource() }
    val isPressedForSecondBtn by interactionSourceForSecondBtn.collectIsPressedAsState()
    // 버튼 눌렸을때 반경.?.. 암튼 위 글자 바뀌는거랑 동일한 방법으로 그림자 살리고 죽이고
    val pressedBtnRadius = if (isPressed) 0.dp else 5.dp
    // 버튼 눌렸을때 애니 추가 : Dp에 바로 적용 타겟값을 pressedBtnRadius 의 조건과 동일하게
    val pressedBtnRadiusWithAnim: Dp by animateDpAsState( if (isPressed) 0.dp else 5.dp )

    Column(
        modifier = Modifier
//            .background(Color.Black)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // pressStatusTitle 함수 사용하여 아래 Text를 변경하여 진행
        Text(text = "$pressStatusTitle", modifier = Modifier.padding(top=5.dp),)
        Button(
            // ButtonDefaults.elevation 3가지 설정 가능
            elevation = ButtonDefaults.elevation(
                // 디폴트는 기본 상태
                defaultElevation = 10.dp,
                // 버튼을 눌렀을때
                pressedElevation = 0.dp,
                // 버튼 클릭이 불가능 할때 : enabled = false
                disabledElevation = 0.dp
            ),
            // 버튼 클릭이 가능하도록 투루로 설정
            enabled = true,
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xff5FD068),
                disabledBackgroundColor = Color.Gray),
            border = BorderStroke(2.dp, Color.Black),
            contentPadding = PaddingValues(horizontal = 1.dp, vertical = 1.dp),
            interactionSource = interactionSource,
            modifier = Modifier
                .size(60.dp, 100.dp)
                .drawColoredShadow(
                    color = Color.Green,
                    alpha = 0.5f,
                    borderRadius = 10.dp,
                    shadowRadius = pressedBtnRadiusWithAnim,
                    offsetX = 5.dp,
                    offsetY = 5.dp
                ),
            onClick = {
                Log.d("test", "ButtonsContainer: 힌트 버튼 클릭")
            }) {
            // 의문점 : 한번 클릭 바꿀러면..
            interactionSource.collectIsPressedAsState() != interactionSource.collectIsPressedAsState()
            Text(text = "힌트",
                // 폰트 스타일 1값은 기울기 0은 보통 그리고 옵션 없음
                style = TextStyle(fontStyle = FontStyle(1),
                    // 폰트 웨이트 600은 SemiBold 그외 여러가지 옵션 있음
                    fontWeight = FontWeight(600)))
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_fundamental_tutorialTheme {
        WineExplanation()
    }
}

