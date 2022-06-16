package com.example.test

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.util.Log
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ContentView
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutLinearInEasing
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
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
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
import androidx.compose.ui.input.key.Key.Companion.F
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
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
import java.nio.file.Files.size
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import android.widget.TextView
import com.example.randomuserlist.utils.DummyDataProvider.userList
import com.example.randomuserlist.utils.winename
import java.util.Locale.filter

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_fundamental_tutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                    Container()
//                    BoxContainer()
//                    BoxWithConstraintContainer()
//                    BoxWithConstraintItem(Modifier.size(300.dp))
//                    VerticalContainer()
//                    ButtonsContainer()
//                    CheckBoxContainer()
//                    ShapeContainer()
//                    MySnackbar()
//                    TextFieldTest()
//                    NavigationGraph()
//                    TextContainer()
                    CView()
                }
            }
        }
    }
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
fun CView(){
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(backgroundColor = Color.White,
            topBar = { MyAppBar() }
        ) {
            RandomUserListView(randomUsers = DummyDataProvider.userList)
//            RandomUserListView(DummyDataProvider.userList)
//            아래 이건 실패함 : 레이지 칼럼 관련 문제로 예상됨
//            DummyDataProvider.userList
//            단순 레이지 칼럼의 문제는 아님 확인
//            LazyColumn(){DummyDataProvider.userList}
//            Type mismatch.
//            LazyColumn(){items(DummyDataProvider.userList)}
//            LazyColumn(){items(List<DummyDataProvider.userList>)}
//            Column() { DummyDataProvider.userList }
//            Column() { items(DummyDataProvider.userList) }
//            Column() { items(List<DummyDataProvider.userList>) }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RandomUserListView(randomUsers: List<RandomUser>){
    // 메모리 관리가 들어간 LazyColumn
    val listState = rememberLazyListState()

    // Provide it to LazyColumn
    LazyColumn(state = listState,
        modifier = Modifier.simpleVerticalScrollbar(listState, 8.dp)){
        items(randomUsers){ aRandomUser ->
            RandomUserView(aRandomUser) }
//        items(randomUsers){ RandomUserView(it) }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WineExpvlanation(navHostController: NavHostController, routeAction:RouteAction, randomUser: RandomUser) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        // 모달창 화면 내용
        drawerContent = {
            Column {
                if (randomUser.name == "chateaumoutonrothschild") Text("1샤또")
            }
        },
        content = {
            CView()
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RandomUserView(randomUser: RandomUser){
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
                Row(modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                // 이미지 원클릭 시도1 : 실패
//                Box(modifier =
//                    Modifier
//                        .size(width = 60.dp, height = 60.dp)
//                        .clip(CircleShape)
//                        .background(Color.Red)
//                )

                     // 이미지 원클릭 시도2 : 실패
                    Button(
                        modifier = Modifier.size(70.dp, 100.dp).background(color = Color.Black),
                        onClick = {
                            scope.launch { drawerState.open() }
                        }
                            )
                    {
                        ProfileImg(modifier = Modifier,
                            imgUrl = randomUser.profileImage)
                    }

                    // 이미지 원클릭 시도3 : 실패
//                    ProfileImg(imgUrl = randomUser.profileImage,
//                        onClick = {
//                        scope.launch {
//                            drawerState.open()
//                        }
//                    })

//                    ProfileImg(imgUrl = randomUser.profileImage)

                    Column(modifier = Modifier
                        .padding(1.dp)
//                        .align(Alignment.Top)
//                        .offset(y = 20.dp)
                        )
                    {
                        Text(text = randomUser.name,
                            style = typography.subtitle1,
                            fontSize = 12.sp)
                        Text(text = randomUser.description,
                            style = typography.body1) }
//            Column {
//                Box(modifier = Modifier.size(80.dp))
//                {
//                    Button(
//                        modifier = Modifier.clickable { "A" }
//                            .fillMaxWidth()
////                        Text(text = "힌트")
//                    )
//                }
//                     }
//            MySnackbar()
                    ButtonsContainer(randomUser)

                }
                TextFieldTest(randomUser)
            }

        }
//        TextFieldTest()
    }

}



@SuppressLint("UnrememberedMutableState")
@Composable
fun ProfileImg(imgUrl: Int, modifier: Modifier = Modifier){
    // 이미지 비트맵
    val bitmap : MutableState<Bitmap?> = mutableStateOf(null)
    // clickable 개선 필요 : 1차 시도 실패 최종 목적은 이미지 줌인
//    val context = LocalContext.current
    // 이미지 모디파이어
    val imageModifier = modifier
        .size(100.dp, 100.dp)
        // clickable 개선 필요 : 1차 시도 실패 최종 목적은 이미지 줌인
//        .clickable { Toast.makeText(
//                context,
//                "Second image clicked",
//                Toast.100).show()
//        }
        .clip(RoundedCornerShape(10.dp))
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
            contentScale = ContentScale.Fit,
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
                color = Color.Black,
                topLeft = Offset(this.size.width - width.toPx(), scrollbarOffsetY),
                size = Size(width.toPx(), scrollbarHeight),
                alpha = alpha
            )
        }
    }
}

// 네비게이션 라우트 이넘 (값을 가지는 이넘)
enum class NAV_ROUTE(val routeName: String, val description: String, val btnColor: Color){
    MAIN("MAIN", "메인 화면", Color(0xFF3949AB)),
    LOGIN("LOGIN", "로그인 화면", Color(0xFF5E35B1)),
    REGISTER("REGISTER", "회원가입 화면", Color(0xFFD81B60)),
    USER_PROFILE("USER_PROFILE", "유저 프로필 화면", Color(0xFF00897B)),
    SETTING("SETTING", "설정 화면", Color(0xFFF4511E))
}

// 네비게이션 라우트 액션
class RouteAction(navHostController: NavHostController){

    // 특정 라우트로 이동
    val navTo: (NAV_ROUTE) -> Unit = { route ->
        navHostController.navigate(route.routeName)
    }

    // 뒤로가기 이동
    val goBack: () -> Unit = {
        navHostController.navigateUp()
    }
}

@Composable
fun NavigationGraph(startRoute: NAV_ROUTE = NAV_ROUTE.MAIN) {

    // 네비게이션 컨트롤러
    val navController = rememberNavController()

    // 네비게이션 라우트 액션
    val routeAction = remember(navController) { RouteAction(navController) }

    // NavHost 로 네비게이션 결정
    // 네비게이션 연결할 녀석들을 설정한다
    NavHost(navController, startRoute.routeName) {

        // 라우트 이름 = 화면의 키
        composable(NAV_ROUTE.MAIN.routeName){
            // 화면 = 값
            MainScreen(routeAction = routeAction)
        }
        // 라우트 이름 = 화면의 키
        composable(NAV_ROUTE.LOGIN.routeName){
            // 화면 = 값
            LoginScreen(routeAction = routeAction)
        }
        // 라우트 이름 = 화면의 키
        composable(NAV_ROUTE.REGISTER.routeName){
            // 화면 = 값
            RegisterScreen(routeAction = routeAction)
        }
        // 라우트 이름 = 화면의 키
        composable(NAV_ROUTE.USER_PROFILE.routeName){
            // 화면 = 값
            UserProfileScreen(routeAction = routeAction)
        }
        // 라우트 이름 = 화면의 키
        composable(NAV_ROUTE.SETTING.routeName){
            // 화면 = 값
            SettingScreen(routeAction = routeAction)
        }
    }

}

// 메인 화면
@Composable
fun MainScreen(routeAction: RouteAction){
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.padding(16.dp)) {
            NavButton(route = NAV_ROUTE.LOGIN, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.REGISTER, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.USER_PROFILE, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.SETTING, routeAction = routeAction)
        }
    }
}

// 로그인 화면
@Composable
fun LoginScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center){
            Text(text = "로그인 화면", style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            // 뒤로가기 버튼
            Button(onClick  = routeAction.goBack,
                modifier = Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)) {
                Text("뒤로가기")
            }
        }
    }
}

// 회원가입 화면
@Composable
fun RegisterScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center){
            Text(text = "회원가입 화면", style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            // 뒤로가기 버튼
            Button(onClick = routeAction.goBack,
                modifier = Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)) {
                Text("뒤로가기")
            }
        }
    }
}

// 유저 프로필 화면
@Composable
fun UserProfileScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center){
            Text(text = "유저 프로필 화면", style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            // 뒤로가기 버튼
            Button(onClick = routeAction.goBack,
                modifier = Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)) {
                Text("뒤로가기")
            }
        }
    }
}

// 설정 화면
@Composable
fun SettingScreen(routeAction: RouteAction){
    Surface(Modifier.fillMaxSize()) {
        Box(Modifier.padding(8.dp), Alignment.Center){
            Text(text = "설정 화면", style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
            // 뒤로가기 버튼
            Button(onClick = routeAction.goBack,
                modifier = Modifier
                    .padding(16.dp)
                    .offset(y = 100.dp)) {
                Text("뒤로가기")
            }
        }
    }
}

// 콜럼에 있는 네비게이션 버튼
@Composable
fun ColumnScope.NavButton(route: NAV_ROUTE, routeAction: RouteAction){
    Button(onClick = {
        routeAction.navTo(route)
    },colors = ButtonDefaults.buttonColors(backgroundColor = route.btnColor),
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Text(text = route.description,
            style = TextStyle(Color.White, 22.sp, FontWeight.Medium)
        )
    }
}

val winenameconuntry = mapOf("chateaumoutonrothschild" to "France", "chateaumargaux" to "France",
    "chateaulatour" to "France", "chateaulafiterothschild" to "France",
    "chateauhautbrion" to "France")


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TextFieldTest(randomUser: RandomUser){

//    val winenameconuntry = mapOf("chateaumoutonrothschild" to "F", "chateaumargaux" to "France",
//        "chateaulatour" to "France", "chateaulafiterothschild" to "France",
//        "chateauhautbrion" to "France")

    var userInput by remember { mutableStateOf(String()) }

    var userInputsave by remember { mutableStateOf(String()) }

    var inputCheck by remember { mutableStateOf(String()) }

    val shouldShowuserInput = remember { mutableStateOf(false) }

//    val checkAnswer = TextField ( value = "Argentina", enabled = true,onValueChange = { newValue -> newValue })

    val userInputResource: (Boolean) -> Int = {
        // if 안에 it 은 : it == true 와 같은 뜻
        if(it) {
            R.drawable.ic_baseline_check_24
        // 디폴트값으로 false를 가지고 있어서 맨 처음 화면은 - 이런 아이콘 표시
        } else {
            R.drawable.ic_baseline_receipt_24
        }
    }

    var phoneNumberInput by remember { mutableStateOf(TextFieldValue()) }

    var emailInput by remember { mutableStateOf(TextFieldValue()) }

    val shouldShowPassword = remember { mutableStateOf(false) }

    var passwordInput by remember { mutableStateOf(TextFieldValue()) }

    val passwordResource: (Boolean) -> Int = {
        if(it) {
            R.drawable.ic_baseline_visibility_24
        } else {
            R.drawable.ic_baseline_visibility_off_24
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
//

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
            }
            )
                {
                // 아이콘은 shouldShowuserInput.value 값을 userInputResource 변수를 이용하여 불리안값으로 변경
                // 위 불리안값을 이용하여 아이콘 변경
                Icon(painter = painterResource(id = userInputResource(shouldShowuserInput.value)),
                    contentDescription = null
                    )
                }
                            },
            // 화면 표시
//            visualTransformation = if (shouldShowPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
//            visualTransformation = if (userInput.text == "Argentina") {Text("정답")} else {Text("오답")},
//            visualTransformation = if (userInput.toString() == "Argentina") {Text("정답")} else {Text("오답")},

//            ??????????
            // 여기 수정 작업 필요 정답 입력 후 확인 가능 버튼 구현
//            trailingIcon = { IconButton(onClick = {
//                userInput.
//            }) {
////                if (TextField.value == "Argentina" "정답"  else "땡"
////            }},
//
//
//        )


//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = phoneNumberInput,
//            singleLine = true,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//            onValueChange = { newValue -> phoneNumberInput = newValue },
//            label = { Text("전화번호") },
//            placeholder = { Text("010-1234-1234") }
//        )
//
//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = emailInput,
//            singleLine = true,
        // 화면 왼쪽 아이콘
//            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
        // 화면 오른쪽 아이콘
////            trailingIcon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null) },
//            trailingIcon = { IconButton(onClick = { Log.d("test", "TextFieldTest: 체크버튼 클릭") }) {
//                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
//            }
//            },
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//            onValueChange = { newValue -> emailInput = newValue },
//            label = { Text("이메일 주소") },
//            placeholder = { Text("이메일 주소를 입력해 주세요") }
//        )
//
//        OutlinedTextField(
//            modifier = Modifier.fillMaxWidth(),
//            value = passwordInput,
//            singleLine = true,
//            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
////            trailingIcon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null) },
//            trailingIcon = { IconButton(onClick = {
//                Log.d("test", "TextFieldTest: 비밀번호 visible 버튼 클릭")
//                shouldShowPassword.value = !shouldShowPassword.value
//            }) {
//                Icon(painter = painterResource(id = passwordResource(shouldShowPassword.value)), contentDescription = null)
//            }
//            },
//            visualTransformation = if (shouldShowPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            onValueChange = { newValue -> passwordInput = newValue },
//            label = { Text("비밀번호") },
//            placeholder = { Text("비밀번호를 입력해주세요") }
        )
    }
}



@Composable
fun MySnackbar(){

    // 스넥바를 관리하는
    val snackbarHostState = remember { SnackbarHostState() }
    // 스넥바는 코루틴 상태를 기억하고 있음
    val coroutineScope = rememberCoroutineScope()
    // 버튼 제목
    val buttonTitle : (SnackbarData?) -> String = { snackbarData ->
        if (snackbarData != null) {
            "닫기"
        } else {
            "힌트"
        }
    }

    val buttonColor : (SnackbarData?) -> Color = { snackbarData ->
        if (snackbarData != null) {
            Color.Black
        } else {
            Color.Blue
        }
    }

    Box(
        modifier = Modifier
            .padding(20.dp)
            .size(80.dp),
        contentAlignment = Alignment.TopCenter
    )
    {
        Button(
            // 버튼컬러를 사용하는 패턴에 따라 백그라운드 컬러로 사용: 컬러 설정이 여러개라서 컬러스 예상
            colors = ButtonDefaults.buttonColors(
                // 버튼컬러를 사용하는 패턴에 따라 백그라운드 컬러로 사용
                // 버튼 상태를 보고 컬러가 바뀌도록
                backgroundColor = buttonColor(snackbarHostState.currentSnackbarData),
                // 글자색
                contentColor = Color.White
            ),
            onClick = {
                Log.d("test", "MySnackbar: 스낵바 버튼 클릭")
                // 버튼 상태가 널이아니면 = 버튼을 눌렸으면
                if (snackbarHostState.currentSnackbarData != null) {
                    Log.d("test", "MySnackbar: 이미 스낵바가 있다.")
                    // dismiss 는 시간이 초과되거나 사용자가 강제 종료시 스넥바를 닫음
                    snackbarHostState.currentSnackbarData?.dismiss()
                    return@Button
                }
                //코루틴 스코프를 가동 시킴 : 스넥바는 코루틴 스코프안에서 사용되도록 설계됨
                coroutineScope.launch {
                    // 스넥바 메세지 띄워 주기
                    snackbarHostState.showSnackbar(
//                        "오늘도 빡코딩?! 🔥👨‍💻",
                        "A",
                        "",
//                        SnackbarDuration.Short
                    ).let {
                        // 리턴값을 받아서 처리해주는 조건문
                        when(it) {
                            SnackbarResult.Dismissed -> Log.d("test", "MySnackbar: 스낵바 닫아짐")
                            // 확인 버튼이 눌러 졌을때
                            SnackbarResult.ActionPerformed -> Log.d("test", "MySnackbar: 스낵바 확인 버튼 클릭")
                        }
                    }
                } // coroutineScope
            }) {
            Text(buttonTitle(snackbarHostState.currentSnackbarData))
        }

        // 스낵바가 보여지는 부분
//        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
        SnackbarHost(hostState = snackbarHostState, modifier = Modifier.fillMaxSize())

    }
}



@Composable
fun Container(){
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}


@Composable
fun BoxContainer(){
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false
    ) {
        DummyBox(modifier = Modifier.size(200.dp), color = Color.Green)
        DummyBox(modifier = Modifier.size(150.dp), color = Color.Yellow)
        DummyBox(color = Color.Blue)
    }
}

@Composable
fun BoxWithConstraintContainer(){
    BoxWithConstraints(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false
    ) {

        if (this.minHeight > 400.dp){
            DummyBox(modifier = Modifier.size(200.dp), color = Color.Green)
        } else {
            DummyBox(modifier = Modifier.size(200.dp), color = Color.Yellow)
        }
        Text(text = "minHeight: ${this.minHeight}")
//        Column() {
//            BoxWithConstraintItem(modifier = Modifier
//                .size(200.dp)
//                .background(Color.Yellow)
//            )
//            BoxWithConstraintItem(modifier = Modifier
//                .size(300.dp)
//                .background(Color.Green)
//            )
//        }

//        DummyBox(modifier = Modifier.size(200.dp), color = Color.Green)
//        DummyBox(modifier = Modifier.size(150.dp), color = Color.Yellow)
//        DummyBox(color = Color.Blue)
    }
}

@Composable
fun BoxWithConstraintItem(modifier: Modifier = Modifier){
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center,
        propagateMinConstraints = false
    ) {
        if (this.minWidth > 200.dp) {
            Text(text = "이것은 큰 상자이다", color = Color.Black, fontWeight = FontWeight.Bold)
        } else {
            Text(text = "이것은 작은 상자이다")
        }
    }
}

@Composable
fun VerticalContainer(){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.End
    ) {
        DummyBox()
        DummyBox()
        DummyBox()
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
    val pressStatusTitle = if (isPressed) "${winenameconuntry.getValue("${(randomUser.name).substring(0)}")}+lower" else "힌트 버튼 꾹~"
    Log.d("test", "${winenameconuntry.getValue("${randomUser.name.substring(0)}")}+lower")
    // isPressed 함수도 동일한거 두번째 하나더 추가
    val interactionSourceForSecondBtn = remember { MutableInteractionSource() }
    val isPressedForSecondBtn by interactionSourceForSecondBtn.collectIsPressedAsState()
    // 버튼 눌렸을때 반경.?.. 암튼 위 글자 바뀌는거랑 동일한 방법으로 그림자 살리고 죽이고
    val pressedBtnRadius = if (isPressed) 0.dp else 5.dp
    // 버튼 눌렸을때 애니 추가 : Dp에 바로 적용 타겟값을 pressedBtnRadius 의 조건과 동일하게
    val pressedBtnRadiusWithAnim: Dp by animateDpAsState( if (isPressed) 0.dp else 5.dp )

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        if (isPressed) {
//            Text(text = "A")
//        } else {
//            Text(text = "힌트 버튼 클릭")
//        }
        // 위 if 조건문을 변수를 이용하여 한줄로 작성
        // ?????????????????????????????????????????????????? 클릭시 타이틀 변경 안됨.. 몰겠음

        // pressStatusTitle 함수 사용하여 아래 Text를 변경하여 진행
        Text(text = "$pressStatusTitle", modifier = Modifier.padding(top=5.dp))
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
            modifier = Modifier.drawColoredShadow(
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
            Text(text = "힌트",
                // 폰트 스타일 1값은 기울기 0은 보통 그리고 옵션 없음
                style = TextStyle(fontStyle = FontStyle(1),
                    // 폰트 웨이트 600은 SemiBold 그외 여러가지 옵션 있음
                    fontWeight = FontWeight(600)))
        }
//        Button(
//            elevation = ButtonDefaults.elevation(
//                defaultElevation = 10.dp,
//                pressedElevation = 0.dp,
//                disabledElevation = 0.dp
//            ),
//            enabled = true,
//            onClick = {
//                Log.d("test", "ButtonsContainer: 버튼 2 클릭")
//            }) {
//            Text(text = "버튼 2")
//        }
//        Button(
//            elevation = ButtonDefaults.elevation(
//                defaultElevation = 10.dp,
//                pressedElevation = 0.dp,
//                disabledElevation = 0.dp
//            ),
//            enabled = true,
//            shape = CircleShape,
//            onClick = {
//                Log.d("test", "ButtonsContainer: 버튼 3 클릭")
//            }) {
//            Text(text = "버튼 3")
//        }
//        Button(
//            elevation = ButtonDefaults.elevation(
//                defaultElevation = 10.dp,
//                pressedElevation = 0.dp,
//                disabledElevation = 0.dp
//            ),
//            enabled = true,
//            shape = RoundedCornerShape(10.dp),
//            border = BorderStroke(4.dp, Color.Red),
//            contentPadding = PaddingValues(top = 20.dp, bottom = 10.dp, start = 20.dp, end = 20.dp),
//            onClick = {
//                Log.d("test", "ButtonsContainer: 버튼 4 클릭")
//            }) {
//            Text(text = "버튼 4")
//        }
//        Button(
//            elevation = ButtonDefaults.elevation(
//                defaultElevation = 10.dp,
//                pressedElevation = 0.dp,
//                disabledElevation = 0.dp
//            ),
//            enabled = true,
//            shape = RoundedCornerShape(10.dp),
//            border = BorderStroke(4.dp, buttonBorderGradient),
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = Color.Black,
//                disabledBackgroundColor = Color.LightGray
//            ),
//            interactionSource = interactionSource,
//            onClick = {
//                Log.d("test", "ButtonsContainer: 버튼 5 클릭")
//            }) {
//            Text(text = "버튼 5", color = Color.White)
//        }

        // 변수로 별로 빼서 사용
//        if (isPressed) {
//            Text(text = "버튼을 누르고 있다.")
//        } else {
//            Text(text = "버튼에서 손을 뗐다.")
//        }



//        Button(
//            elevation = ButtonDefaults.elevation(
//                defaultElevation = 0.dp,
//                pressedElevation = 0.dp,
//                disabledElevation = 0.dp
//            ),
//            enabled = true,
//            shape = RoundedCornerShape(10.dp),
//            border = BorderStroke(4.dp, buttonBorderGradient),
//            colors = ButtonDefaults.buttonColors(
//                backgroundColor = Color.Black,
//                disabledBackgroundColor = Color.LightGray
//            ),
//            interactionSource = interactionSourceForSecondBtn,
//            modifier = Modifier.drawColoredShadow(
//                color = Color.Blue,
//                alpha = 0.5f,
//                borderRadius = 10.dp,
//                shadowRadius = pressedBtnRadiusWithAnim,
//                offsetY = 0.dp,
//                offsetX = 0.dp,
//            ),
//            onClick = {
//                Log.d("test", "ButtonsContainer: 버튼 5 클릭")
//            }) {
//            Text(text = "버튼 5", color = Color.White)
//        }

    }

}

@Composable
fun TextContainer() {
    val name = "gfyhealth"

    val scrollState = rememberScrollState()

    var words = stringResource(id = R.string.dummy_short_text)
    var wordsArray = words.split(" ")


    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        Text(text = "안녕하세요? $name",
            style = TextStyle(
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(text = "안녕하세요?  $name",
            style = TextStyle(
                textAlign = TextAlign.Start
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(text = "안녕하세요?  $name",
            style = TextStyle(
                textAlign = TextAlign.End
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(text = stringResource(id = R.string.dummy_short_text),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.Justify,
                // 덱스트 효과 여러개 만들고 한번에
                textDecoration = TextDecoration.combine(
                    listOf(
                        TextDecoration.LineThrough,
                        TextDecoration.Underline,
                    )
                )
            ),
            fontWeight = FontWeight.W200,
            fontSize = 20.sp,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(text = stringResource(id = R.string.dummy_short_text),
            style = TextStyle(
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(Font(R.font.cafe24, weight = FontWeight.ExtraBold)),
                lineHeight = 40.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(text = buildAnnotatedString {
            append("안녕하세요?")

            withStyle(style = SpanStyle(color = Color.Blue,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            ) {
                append("gfyhealth 입니다!")
            }
            withStyle(style = SpanStyle(color = Color.Red)
            ) {
                append("빡!코딩")
            }
        })

        Text(text = buildAnnotatedString {
            wordsArray.forEach{
                if (it.contains("심장")){
                    withStyle(style = SpanStyle(color = Color.Blue,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    ) {
                        append("$it ")
                    }
                } else {
                    append("$it ")
                }
            }
        })

        ClickableText(text = AnnotatedString("클릭미!"), onClick = {
            Log.d("test", "TextContainer: 클릭미가 클릭되었다!")
        })

        Text(text = stringResource(id = R.string.dummy_long_text),
            style = TextStyle(lineHeight = 20.sp)
        )

    }
}


@Composable
fun DummyBox(modifier: Modifier = Modifier, color: Color? = null){
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)
    // color 가 값이 있으면 해당 값을 넣어주고 값이 없다면 랜덤 값을 넣어주기
    val randomColor = color?.let { it } ?: Color(red, green, blue)
    Box(modifier = modifier
        .size(100.dp)
        .background(randomColor))
}

@Composable
fun ShapeContainer(){

    var polySides by remember { mutableStateOf(3) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        DummyBox(modifier = Modifier.clip(RectangleShape))
        DummyBox(modifier = Modifier.clip(CircleShape))
        DummyBox(modifier = Modifier.clip(RoundedCornerShape(20.dp)))
        DummyBox(modifier = Modifier.clip(CutCornerShape(20.dp)))
        DummyBox(modifier = Modifier.clip(TriangleShape()))
        DummyBox(modifier = Modifier.clip(PolyShape(polySides, 100f)))

        Text(text = "polySides: $polySides")
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                polySides = polySides + 1
            }) {
                Text(text = "polySides + 1")
            }
            Button(onClick = {
                polySides = 3
            }) {
                Text(text = "초기화")
            }
        }

    }
}

class TriangleShape(): Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path = path)
    }
}

class PolyShape(private val sides: Int, private val radius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = Path().apply { this.polygon(sides, radius, size.center) })
    }
}


fun Path.polygon(sides: Int, radius: Float, center: Offset) {
    val angle = 2.0 * Math.PI / sides
    moveTo(
        x = center.x + (radius * cos(0.0)).toFloat(),
        y = center.y + (radius * sin(0.0)).toFloat()
    )
    for (i in 1 until sides) {
        lineTo(
            x = center.x + (radius * cos(angle * i)).toFloat(),
            y = center.y + (radius * sin(angle * i)).toFloat()
        )
    }
    close()
}

@Composable
fun CheckBoxContainer(){

    val checkedStatusForFirst = remember { mutableStateOf(false) }
    val checkedStatusForSecond = remember { mutableStateOf(false) }
    val checkedStatusForThird = remember { mutableStateOf(false) }
//    val checkedStatusForForth = remember { mutableStateOf(false) }

    val checkedStatesArray = listOf(
        checkedStatusForFirst,
        checkedStatusForSecond,
        checkedStatusForThird,
    )

    val allBoxChecked: (Boolean) -> Unit = { isAllBoxChecked ->
        Log.d("test", "CheckBoxContainer: isAllBoxChecked : $isAllBoxChecked")
        checkedStatesArray.forEach { it.value = isAllBoxChecked }
    }

//    val checkedStatusForForth : Boolean = checkedStatesArray.all { it.value == true }
    val checkedStatusForForth : Boolean = checkedStatesArray.all { it.value }

//    var checkedStatusForSecond by remember { mutableStateOf(false) }
//
//    var (checkedStatusForThird, setCheckedStatusForThird) = remember { mutableStateOf(false) }

    var (checkedStatusForFourth, setCheckedStatusForFourth) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CheckBoxWithTitle("1번 확인사항", checkedStatusForFirst)
        CheckBoxWithTitle("2번 확인사항", checkedStatusForSecond)
        CheckBoxWithTitle("3번 확인사항", checkedStatusForThird)

//        Checkbox(
//            enabled = true,
//            checked = checkedStatusForSecond,
//            onCheckedChange = { isChecked ->
//                Log.d("test", "CheckBoxContainer: isChecked: $isChecked")
//                checkedStatusForSecond = isChecked
//            })
//        Checkbox(
//            enabled = true,
//            checked = checkedStatusForThird,
//            onCheckedChange = {
//                Log.d("test", "CheckBoxContainer: isChecked: $it")
//                setCheckedStatusForThird.invoke(it)
//            })
        Spacer(modifier = Modifier.height(10.dp))
        AllAgreeCheckBox("모두 동의하십니까?", checkedStatusForForth, allBoxChecked)
        Spacer(modifier = Modifier.height(10.dp))
        MyCustomCheckBox(title = "커스텀 체크박스 리플 O", withRipple = true)
        MyCustomCheckBox(title = "커스텀 체크박스 리플 X", withRipple = false)
//        Checkbox(
//            enabled = true,
//            checked = checkedStatusForFourth,
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color.Red,
//                uncheckedColor = Color(0xFFEF9A9A),
//                checkmarkColor = Color.Black,
//                disabledColor = Color(0xFF90CAF9)
//            ),
//            onCheckedChange = {
//                Log.d("test", "CheckBoxContainer: isChecked: $it")
//                setCheckedStatusForFourth.invoke(it)
//            })
    }
}

@Composable
fun CheckBoxWithTitle(title: String, isCheckedState: MutableState<Boolean>) {
    Row(
        modifier = Modifier
//            .background(Color.Yellow)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            enabled = true,
            checked = isCheckedState.value,
            onCheckedChange = { isChecked ->
                Log.d("test", "CheckBoxContainer: isChecked: $isChecked")
                isCheckedState.value = isChecked
            })
        Text(text = title)
    }
}

@Composable
fun AllAgreeCheckBox(title: String,
                     shouldChecked: Boolean ,
                     allBoxChecked: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
//            .background(Color.Yellow)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Checkbox(
            enabled = true,
            checked = shouldChecked,
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red,
                uncheckedColor = Color(0xFFEF9A9A),
                checkmarkColor = Color.White,
                disabledColor = Color(0xFF90CAF9)
            ),
            onCheckedChange = { isChecked ->
                Log.d("test", "CheckBoxContainer: isChecked: $isChecked")
//                isCheckedState.value = isChecked
                allBoxChecked(isChecked)
            })
        Text(text = title)
    }
}

@Composable
fun MyCustomCheckBox(title: String, withRipple: Boolean = false){

//    var isCheckedState by remember { mutableStateOf(false) }
//    var isChecked = remember { mutableStateOf(false) }
    var (isChecked, setIsChecked) = remember { mutableStateOf(false) }

    var togglePainter = if (isChecked == true) R.drawable.ic_checked else R.drawable.ic_unchecked

    var checkedInfoString = if (isChecked) "체크됨" else "체크안됨"

    var rippleEffect = if (withRipple) rememberRipple(
        radius = 30.dp,
        bounded = false,
        color = Color.Blue
    ) else null

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
//            .background(Color.Yellow)
            .padding(horizontal = 30.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(60.dp)
//            .background(Color.Yellow)
                .clickable(
                    indication = rippleEffect,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    setIsChecked.invoke(!isChecked)
                    Log.d("test", "MyCustomCheckBox: 클릭이 되었다! / $isChecked")
                }){
            Image(
                painter = painterResource(id = togglePainter),
                contentDescription = null
            )
        }
        Text(text = "$title / $checkedInfoString")
    }
}

//bounded: Boolean = true,
//radius: Dp = Dp.Unspecified,
//color: Color = Color.Unspecified


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Compose_fundamental_tutorialTheme {
        CheckBoxContainer()
//        Container()
//        Greeting("Android")
    }
}

