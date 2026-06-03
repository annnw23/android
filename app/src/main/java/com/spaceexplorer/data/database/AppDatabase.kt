package com.spaceexplorer.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.spaceexplorer.data.dao.*
import com.spaceexplorer.data.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, Lesson::class, Quiz::class, QuizQuestion::class, QuizResult::class],
    version = 8,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun lessonDao(): LessonDao
    abstract fun quizDao(): QuizDao
    abstract fun quizQuestionDao(): QuizQuestionDao
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "space_explorer_db"
                )
                    .addCallback(PrepopulateCallback(context.applicationContext))
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    private class PrepopulateCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            runPopulate()
        }

        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
            super.onDestructiveMigration(db)
            runPopulate()
        }

        private fun runPopulate() {
            CoroutineScope(Dispatchers.IO).launch {
                getInstance(context).prepopulate()
            }
        }
    }

    private suspend fun prepopulate() {
        val lesson1Id = lessonDao().insert(
            Lesson(
                title = "Układ Słoneczny",
                description = "Nasz układ słoneczny składa się ze Słońca oraz ośmiu planet: Merkurego, Wenus, Ziemi, Marsa, Jowisza, Saturna, Urana i Neptuna. Słońce zawiera 99,86% całej masy układu. Planety krążą wokół Słońca po eliptycznych orbitach. Jowisz jest największą planetą – jego masa jest większa niż wszystkich pozostałych planet razem wziętych. Poza planetami układ zawiera setki księżyców, miliony asteroid oraz komety.",
                // High-quality composite image from NASA
                imageRes = "images-assets.nasa.gov/image/PIA03604/PIA03604~orig.jpg"
            )
        ).toInt()

        val lesson2Id = lessonDao().insert(
            Lesson(
                title = "Czarne Dziury",
                description = "Czarne dziury to regiony przestrzeni, w których grawitacja jest tak silna, że nic – nawet światło – nie może ich opuścić. Powstają zazwyczaj po eksplozji supernowej masywnej gwiazdy. Granicę czarnej dziury nazywamy horyzontem zdarzeń. Istnienie czarnych dziur przewidział Albert Einstein w ramach ogólnej teorii względności. Stephen Hawking odkrył, że czarne dziury emitują promieniowanie termiczne (promieniowanie Hawkinga). W centrum naszej galaktyki Drogi Mlecznej znajduje się supermasywna czarna dziura Sagittarius A*.",
                imageRes = "https://images-assets.nasa.gov/image/PIA23122/PIA23122~medium.jpg"
            )
        ).toInt()

        val lesson3Id = lessonDao().insert(
            Lesson(
                title = "Księżyc",
                description = "Księżyc jest jedynym naturalnym satelitą Ziemi i piątym co do wielkości księżycem w Układzie Słonecznym. Odległość od Ziemi wynosi średnio 384 400 km. Księżyc wpływa na pływy morskie oraz stabilizuje oś obrotu Ziemi. Jeden pełny obieg wokół Ziemi trwa około 27,3 doby. Neil Armstrong jako pierwszy człowiek postawił stopę na Księżycu 20 lipca 1969 roku podczas misji Apollo 11. Księżyc nie ma atmosfery ani aktywności wulkanicznej.",
                imageRes = "https://images-assets.nasa.gov/image/as11-44-6551/as11-44-6551~medium.jpg"
            )
        ).toInt()

        val quiz1Id = quizDao().insert(
            Quiz(lessonId = lesson1Id, title = "Quiz: Układ Słoneczny", passingScore = 2, points = 100)
        ).toInt()

        val quiz2Id = quizDao().insert(
            Quiz(lessonId = lesson2Id, title = "Quiz: Czarne Dziury", passingScore = 2, points = 150)
        ).toInt()

        val quiz3Id = quizDao().insert(
            Quiz(lessonId = lesson3Id, title = "Quiz: Księżyc", passingScore = 2, points = 100)
        ).toInt()

        quizQuestionDao().insertAll(
            listOf(
                QuizQuestion(
                    quizId = quiz1Id,
                    questionText = "Ile planet liczy Układ Słoneczny?",
                    options = """["6","7","8","9"]""",
                    correctOption = 2
                ),
                QuizQuestion(
                    quizId = quiz1Id,
                    questionText = "Która planeta Układu Słonecznego jest największa?",
                    options = """["Saturn","Jowisz","Neptun","Mars"]""",
                    correctOption = 1
                ),
                QuizQuestion(
                    quizId = quiz1Id,
                    questionText = "Ile czasu zajmuje jeden obieg Ziemi wokół Słońca?",
                    options = """["365,25 dnia","24 godziny","30 dni","12 godzin"]""",
                    correctOption = 0
                ),
                QuizQuestion(
                    quizId = quiz2Id,
                    questionText = "Jak nazywa się granica czarnej dziury, za którą nic nie może uciec?",
                    options = """["Horyzont zdarzeń","Granica grawitacyjna","Strefa czarna","Bariera Hawkinga"]""",
                    correctOption = 0
                ),
                QuizQuestion(
                    quizId = quiz2Id,
                    questionText = "Kto przewidział istnienie czarnych dziur w ramach ogólnej teorii względności?",
                    options = """["Isaac Newton","Galileusz","Albert Einstein","Niels Bohr"]""",
                    correctOption = 2
                ),
                QuizQuestion(
                    quizId = quiz2Id,
                    questionText = "Jak nazywa się supermasywna czarna dziura w centrum Drogi Mlecznej?",
                    options = """["Sagittarius A*","Cygnus X-1","NGC 4261","M87*"]""",
                    correctOption = 0
                ),
                QuizQuestion(
                    quizId = quiz3Id,
                    questionText = "Jaka jest średnia odległość Księżyca od Ziemi?",
                    options = """["100 000 km","384 400 km","1 000 000 km","500 000 km"]""",
                    correctOption = 1
                ),
                QuizQuestion(
                    quizId = quiz3Id,
                    questionText = "Ile trwa jeden pełny obieg Księżyca wokół Ziemi?",
                    options = """["7 dni","14 dni","27,3 doby","365 dni"]""",
                    correctOption = 2
                ),
                QuizQuestion(
                    quizId = quiz3Id,
                    questionText = "Kto jako pierwszy stanął na Księżycu?",
                    options = """["Buzz Aldrin","Neil Armstrong","Yuri Gagarin","John Glenn"]""",
                    correctOption = 1
                )
            )
        )
    }
}
