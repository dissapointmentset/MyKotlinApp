package com.example.myapplication

import androidx.compose.ui.state.ToggleableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.myapplication.data.BasaDanih
import com.example.myapplication.data.Question
import com.example.myapplication.data.QuestionType
import com.example.myapplication.data.Test
import com.example.myapplication.data.TestWithQuestions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.SharingStarted

class MainView(private val dataBase: BasaDanih) : ViewModel() {

    // Получаем DAO из базы данных.
    private val testDao = dataBase.testDao()
    // Flow автоматически обновляется при любом изменении в БД.
    val allTests: StateFlow<List<TestWithQuestions>> = testDao.getAllTestsWithQuestions()
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList<TestWithQuestions>()
        )
    // Название теста, который пользователь создаёт прямо сейчас
    private val _draftTestName = MutableStateFlow("Новый тест")
    val draftTestName: StateFlow<String> = _draftTestName.asStateFlow()
    // Список вопросов черновика (пока не сохранён в БД)
    private val _draftQuestions = MutableStateFlow<List<Question>>(emptyList())
    val draftQuestions: StateFlow<List<Question>> = _draftQuestions.asStateFlow()
    // Изменить название теста
    fun updateDraftTestName(name: String) {
        _draftTestName.value = name
    }
    // Добавить новый вопрос в черновик
    fun addDraftQuestion(question: Question) {
        _draftQuestions.value = _draftQuestions.value + question
    }
    // Обновить вопрос в черновике по индексу
    fun updateDraftQuestion(index: Int, question: Question) {
        _draftQuestions.value = _draftQuestions.value.toMutableList().apply {
            this[index] = question
        }
    }
    // Удалить вопрос из черновика
    fun removeDraftQuestion(index: Int) {
        _draftQuestions.value = _draftQuestions.value.toMutableList().apply {
            removeAt(index)
        }
    }
    // Сохранить текущий черновик теста в БД
    // функция связывает Test и Question
    fun saveTest() {
        viewModelScope.launch {
            //Создаём объект Test
            val test = Test(name = _draftTestName.value)
            //Сохраняем тест и получаем его сгенерированный ID
            val testId = testDao.insertTest(test).toInt()
            //Привязываем все вопросы к этому тесту через testId
            val questionsWithTestId = _draftQuestions.value.map { question ->
                question.copy(testId = testId)
            }
            //Сохраняем все вопросы разом
            testDao.insertQuestions(questionsWithTestId)
            //Очищаем черновик
            _draftTestName.value = "Новый тест"
            _draftQuestions.value = emptyList()
        }
    }

    fun QuestionState.toQuestion(testId: Int): Question {
        val questionType = when (state) {
            ToggleableState.On -> QuestionType.SINGLE_CHOICE
            ToggleableState.Indeterminate -> QuestionType.MULTIPLE_CHOICE
            ToggleableState.Off -> QuestionType.TEXT_INPUT
            else -> QuestionType.MULTIPLE_CHOICE
        }

        val correctIndices = when (questionType) {
            QuestionType.SINGLE_CHOICE -> {
                // Один правильный ответ
                selectedOptionIndex?.let { listOf(it) } ?: emptyList()
            }
            QuestionType.MULTIPLE_CHOICE -> {
                // Несколько правильных ответов — индексы отмеченных чекбоксов
                options.mapIndexedNotNull { index, option ->
                    if (option.isChecked) index else null
                }
            }
            QuestionType.TEXT_INPUT -> emptyList()
        }

        return Question(
            testId = testId,
            vopros = task,
            questionType = questionType,
            varianti = options.map { it.text },
            otvet = locval,
            indexiOtveta = correctIndices
        )
    }

    fun saveTestFromUI(testName: String, questions: List<QuestionState>, onComplete: () -> Unit = {}) {
        viewModelScope.launch {
            // 1. Создаём и сохраняем тест
            val test = Test(name = testName)
            val testId = testDao.insertTest(test).toInt()

            // 2. Преобразуем QuestionState (UI) в Question (Room Entity)
            val questionsToSave = questions.map { state ->
                state.toQuestion(testId = testId)
            }

            // 3. Сохраняем все вопросы в БД
            testDao.insertQuestions(questionsToSave)

            // 4. Вызываем callback (например, для закрытия экрана)
            onComplete()
        }
    }

    // Удалить тест из БД
    fun deleteTest(test: Test) {
        viewModelScope.launch {
            testDao.deleteTest(test)
        }
    }
    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val dataBase = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return MainView(dataBase) as T
            }
        }
    }
}