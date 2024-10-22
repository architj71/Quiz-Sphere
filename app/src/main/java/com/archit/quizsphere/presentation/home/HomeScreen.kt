package com.archit.quizsphere.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.archit.quizsphere.presentation.common.AppDropDownMenu
import com.archit.quizsphere.presentation.common.ButtonBox
import com.archit.quizsphere.presentation.nav_graph.Routes
import com.archit.quizsphere.presentation.util.Constants
import com.archit.quizsphere.presentation.util.Dimens


//@Preview
//@Composable
//private fun PrevHome() {
//    HomeScreen(
//        state = StateHomeScreen(),
//        event = { }
//        )
//}

@Composable
fun HomeScreen(
    state : StateHomeScreen,
    event: (EventHomeScreen) -> Unit,
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        HomeHeader()

        Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))
        AppDropDownMenu(menuName = "Number of Questions: ", menuList = Constants.numberAsString, text = state.numberOfQuiz.toString(),
            onDropDownClick = {event(EventHomeScreen.SetNumberOfQuizzes(it.toInt()))})

        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
        AppDropDownMenu(menuName = "Select Category: ", menuList = Constants.categories, text = state.category,
            onDropDownClick = {event(EventHomeScreen.SetQuizCategory(it))})

        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
        AppDropDownMenu(menuName = "Select Difficulty: ", menuList = Constants.difficulty, text = state.difficulty,
            onDropDownClick = {event(EventHomeScreen.SetQuizDifficulty(it))})

        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))
        AppDropDownMenu(menuName = "Select Type: ", menuList = Constants.type, text = state.type,
            onDropDownClick = {event(EventHomeScreen.SetQuizType(it))})

        Spacer(modifier = Modifier.height(Dimens.MediumSpacerHeight))
        ButtonBox(text = "Generate Quiz", padding = Dimens.MediumPadding){

            navController.navigate(
                route = Routes.QuizScreen.passQuizParams(state.numberOfQuiz, state.category, state.difficulty, state.type)
            )

        }

    }
}