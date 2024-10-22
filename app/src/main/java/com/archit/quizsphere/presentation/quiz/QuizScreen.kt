package com.archit.quizsphere.presentation.quiz

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.archit.quizsphere.R
import com.archit.quizsphere.presentation.common.ButtonBox
import com.archit.quizsphere.presentation.common.QuizAppBar
import com.archit.quizsphere.presentation.nav_graph.Routes
import com.archit.quizsphere.presentation.quiz.component.QuizInterface
import com.archit.quizsphere.presentation.quiz.component.ShimmerEffectQuizInterface
import com.archit.quizsphere.presentation.util.Constants
import com.archit.quizsphere.presentation.util.Dimens
import kotlinx.coroutines.launch

//@Preview
//@Composable
//private fun Prevquiz() {
//    QuizScreen(numOfQuiz = 12, quizCategory = "GK", quizDifficulty = "Easy",
//        quizType = "easy",
//        event = {},
//        state = StateQuizScreen()
//        )
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizScreen(
    numOfQuiz: Int,
    quizCategory: String,
    quizDifficulty: String,
    quizType: String,
    event: (EventQuizScreen) -> Unit,
    state: StateQuizScreen,
    navController: NavController
) {
    BackHandler {
        navController.navigate(Routes.HomeScreen.route){
            popUpTo(Routes.HomeScreen.route){inclusive = true}
        }
    }
    
    LaunchedEffect(key1 = Unit) {
        //difficulty is of Medium, Hard but we want to pass small letters to the api. checkout below
        val difficulty = when(quizDifficulty){
            "Medium" -> "medium"
            "Hard" -> "hard"
            else -> "easy"
        }
        val type = when(quizType){
            "Multiple Choice" -> "multiple"
            else -> "boolean"
        }

        event(EventQuizScreen.GetQuizzes(numOfQuiz, Constants.categoriesMap[quizCategory]!!, difficulty, type))
    }


    Column(modifier = Modifier.fillMaxSize()) {
        QuizAppBar(quizCategory = quizCategory) {
            navController.navigate(Routes.HomeScreen.route){
                popUpTo(Routes.HomeScreen.route){inclusive = true}
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.VerySmallPadding)
        ) {
            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Questions : $numOfQuiz",
                    color = colorResource(id = R.color.blue_grey)
                )
                Text(
                    text = quizDifficulty,
                    color = colorResource(id = R.color.blue_grey)
                )

            }

            Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.VerySmallViewHeight)
                    .clip(RoundedCornerShape(Dimens.MediumCornerRadius))
                    .background(colorResource(id = R.color.blue_grey))
            )
            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

            //quiz interface
            if (quizFetched(state)){


                val pagerState = rememberPagerState { state.quizState.size }

                HorizontalPager(state = pagerState) {index->
                    QuizInterface(
                        modifier = Modifier.weight(1f),
                        quizState = state.quizState[index],
                        onOptionSelected = {selectedIndex ->
                            event(EventQuizScreen.SetOptionSelected(index, selectedIndex))
                        },
                        qNumber = index+1
                    )
                }

                val buttonText by remember {
                    derivedStateOf {
                        when (pagerState.currentPage){
                            0 ->{
                                listOf("","Next")
                            }
                            state.quizState.size - 1 ->{
                                listOf("Previous", "Submit")
                            }
                            else ->{
                                listOf("Previous", "Next")
                            }
                        }
                    }
                }

                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimens.MediumPadding)
                        .navigationBarsPadding()
                ){

                    val scope = rememberCoroutineScope()
                    if (buttonText[0].isNotEmpty()){
                        ButtonBox(
                            text = "Previous",
                            padding = Dimens.SmallPadding,
                            fraction = 0.43f,
                            fontSize = Dimens.SmallTextSize
                        ) {
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                        }
                    }
                    else{
                        ButtonBox(
                            text = "",
                            fraction = 0.43f,
                            fontSize = Dimens.SmallTextSize,
                            borderColor = colorResource(id = R.color.mid_night_blue),
                            containerColor = colorResource(id = R.color.mid_night_blue)
                        ) {

                        }
                    }


                    ButtonBox(
                        text = buttonText[1],
                        padding = Dimens.SmallPadding,
                        borderColor = colorResource(id = R.color.orange),
                        containerColor =
                        if (pagerState.currentPage == state.quizState.size -1 ) colorResource(id = R.color.orange)
                        else colorResource(id = R.color.mid_night_blue),
                        fraction = 1f,
                        textColor = colorResource(id = R.color.white),
                        fontSize = Dimens.SmallTextSize
                    ) {

                        if (pagerState.currentPage == state.quizState.size - 1){
                            navController.navigate(Routes.ScoreScreen.passNumOfQuestionsAndCorrectAns(state.quizState.size, state.score))
                        }
                        else{
                            scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                        }
                    }


                }
            }


        }
    }
    
}

@Composable
fun quizFetched(state: StateQuizScreen): Boolean {

    return when{
        state.isLoading ->{
            ShimmerEffectQuizInterface()
            false
        }

        state.quizState.isNotEmpty() ->{
            true
        }
        else ->{
            Text(text = state.error.toString(), color = colorResource(id = R.color.white))
            false
        }

    }
    

}
