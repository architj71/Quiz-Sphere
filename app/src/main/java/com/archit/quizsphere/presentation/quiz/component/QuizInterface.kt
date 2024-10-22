package com.archit.quizsphere.presentation.quiz.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.archit.quizsphere.R
import com.archit.quizsphere.presentation.quiz.QuizState
import com.archit.quizsphere.presentation.util.Dimens

//@Preview
//@Composable
//private fun Prev() {
//    QuizInterface(onOptionSelected = {}, qNumber = 1)
//}

@Composable
fun QuizInterface(
    onOptionSelected: (Int) -> Unit,
    qNumber: Int,
    quizState: QuizState,
    modifier: Modifier = Modifier
) {

    val question = quizState.quiz?.question!!.replace(oldValue = "&quot;" , newValue = "\"" ).replace(oldValue = "&#039", newValue = "\'")

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.wrapContentHeight()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    modifier = Modifier.weight(1f),
                    text = "$qNumber.",
                    color = colorResource(id = R.color.blue_grey),
                    fontSize = Dimens.SmallTextSize
                )
                Text(
                    modifier = Modifier.weight(9f),
                    text = question,
                    color = colorResource(id = R.color.blue_grey),
                    fontSize = Dimens.SmallTextSize
                )
            }

            Spacer(modifier = Modifier.height(Dimens.LargeSpacerHeight))

            Column(
                modifier = Modifier.padding(horizontal = 15.dp)
            ) {
                val options = listOf(
                    "A" to quizState.shuffledOption[0].replace(oldValue = "&quot;" , newValue = "\"" ).replace(oldValue = "&#039", newValue = "\'"),
                    "B" to quizState.shuffledOption[1].replace(oldValue = "&quot;" , newValue = "\"" ).replace(oldValue = "&#039", newValue = "\'"),
                    "C" to quizState.shuffledOption[2].replace(oldValue = "&quot;" , newValue = "\"" ).replace(oldValue = "&#039", newValue = "\'"),
                    "D" to quizState.shuffledOption[3].replace(oldValue = "&quot;" , newValue = "\"" ).replace(oldValue = "&#039", newValue = "\'")
                )

                Column {
                    options.forEachIndexed{ index, (optionNumber, optionText) ->
                        if (optionText.isNotEmpty()){
                            QuizOption(
                                optionNumber = optionNumber,
                                options = optionText,
                                onOptionClick = {onOptionSelected(index) },
                                selected = quizState.selectedOptions == index,
                                onUnselectOption = {onOptionSelected(-1)}
                            )
                        }

                        Spacer(modifier = Modifier.height(Dimens.SmallSpacerHeight))

                    }
                }

                Spacer(modifier = Modifier.height(Dimens.ExtraLargeSpacerHeight))

            }


        }

    }
    
}