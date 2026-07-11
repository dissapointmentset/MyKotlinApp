package com.example.myapplication.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    // Вставка теста. Возвращает Long — автоматически сгенерированный id теста.
    // Он нужен, чтобы привязать к тесту вопросы.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTest(test: Test): Long
    // Получение всех тестов как Flow.
    // Flow автоматически уведомляет UI при изменении данных в БД.
    @Query("SELECT * FROM Testi ORDER BY id DESC")
    fun getAllTests(): Flow<List<Test>>
    // Получение одного теста по ID.
    // suspend потому что это разовая операция чтения.
    @Query("SELECT * FROM Testi WHERE id = :testId")
    suspend fun getTestById(testId: Int): Test?
    // Удаление теста. Благодаря CASCADE в Question, все вопросы удалятся автоматически.
//    @Delete
//    suspend fun deleteTest(test: Test)
    // Вставка одного вопроса.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)
    // Массовая вставка списка вопросов (для сохранения всего теста за раз).
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestions(questions: List<Question>)

    // Получить тест вместе со всеми его вопросами.
    // @Transaction обязателен — он гарантирует, что тест и вопросы
    // прочитаются как единое целое, без риска "рассинхрона".
    @Transaction
    @Query("SELECT * FROM Testi WHERE id = :testId")
    suspend fun getTestWithQuestions(testId: Int): TestWithQuestions
    // Получить ВСЕ тесты с их вопросами.
    // Возвращает Flow, поэтому список автоматически обновится,
    // если в БД что-то изменится.
    @Transaction
    @Query("SELECT * FROM Testi ORDER BY id DESC")
    fun getAllTestsWithQuestions(): Flow<List<TestWithQuestions>>

    @Transaction
    @Query("""
    SELECT t.*, COUNT(q.id) as questionCount 
    FROM Testi t 
    LEFT JOIN Voprosi q ON t.id = q.testId 
    GROUP BY t.id 
    ORDER BY t.id DESC
""")
    fun getAllTestsInfo(): Flow<List<TestInfo>>

    @Transaction
    @Query("SELECT * FROM Testi WHERE id = :testId")
    suspend fun getTestWithQuestionsById(testId: Int): TestWithQuestions?

    // ✅ Обновление теста
    @Update
    suspend fun updateTest(test: Test)

    // ✅ Удаление всех вопросов теста
    @Query("DELETE FROM Voprosi WHERE testId = :testId")
    suspend fun deleteQuestionsByTestId(testId: Int)

    @Query("DELETE FROM Testi WHERE Id = :testId")
    suspend fun deleteTest(testId: Int)

}