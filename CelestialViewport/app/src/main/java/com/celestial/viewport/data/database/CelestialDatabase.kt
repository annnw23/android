package com.celestial.viewport.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.celestial.viewport.data.dao.*
import com.celestial.viewport.data.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [User::class, Lesson::class, Quiz::class, QuizQuestion::class, QuizResult::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(OptionsConverter::class)
abstract class CelestialDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun lessonDao(): LessonDao
    abstract fun quizDao(): QuizDao
    abstract fun quizQuestionDao(): QuizQuestionDao
    abstract fun quizResultDao(): QuizResultDao

    companion object {
        @Volatile private var INSTANCE: CelestialDatabase? = null

        fun getDatabase(context: Context): CelestialDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CelestialDatabase::class.java,
                    "celestial_viewport.db"
                )
                    .addCallback(SeedCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class SeedCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    seedDatabase(database)
                }
            }
        }

        private suspend fun seedDatabase(db: CelestialDatabase) {
            // Seed current user
            db.userDao().insertUser(User(id = 1, username = "Star_Struck_User", xpTotal = 21450, rank = "Cadet Explorer"))

            // Seed leaderboard users
            db.userDao().insertUser(User(id = 2, username = "Solaris_X", xpTotal = 51200, rank = "Commander"))
            db.userDao().insertUser(User(id = 3, username = "Nova_99", xpTotal = 42800, rank = "Silver Explorer"))
            db.userDao().insertUser(User(id = 4, username = "Vortex_01", xpTotal = 39100, rank = "Bronze Explorer"))
            db.userDao().insertUser(User(id = 5, username = "Aria_Skywalker", xpTotal = 34200, rank = "Pilot Class II"))
            db.userDao().insertUser(User(id = 6, username = "Zenith_Void", xpTotal = 19800, rank = "Surveyor"))
            db.userDao().insertUser(User(id = 7, username = "Kael_The_Swift", xpTotal = 18550, rank = "Surveyor"))

            // Seed lessons
            val lessons = listOf(
                Lesson(id = 1, title = "The Rings of Saturn", description = "A complex ballet of ice and rock orbiting the jewel of the solar system.", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c7/Saturn_during_Equinox.jpg/1200px-Saturn_during_Equinox.jpg", category = "Planets", missionNumber = 42),
                Lesson(id = 2, title = "The Red Giant: Mars", description = "Explore the dusty plains and towering volcanoes of the Red Planet.", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/02/OSIRIS_Mars_true_color.jpg", category = "Planets", missionNumber = 43),
                Lesson(id = 3, title = "Black Holes Explained", description = "Unravel the mysteries of the most extreme objects in the universe.", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/Black_hole_-_Messier_87_crop_max_res.jpg/1200px-Black_hole_-_Messier_87_crop_max_res.jpg", category = "Deep Space", missionNumber = 44),
                Lesson(id = 4, title = "The Life Cycle of Stars", description = "From nebulae to supernovae – how stars are born, live, and die.", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/a7/Pillars_of_creation_2014_HST_WFC3-UVIS_full-res_denoised.jpg/1200px-Pillars_of_creation_2014_HST_WFC3-UVIS_full-res_denoised.jpg", category = "Stars", missionNumber = 45),
                Lesson(id = 5, title = "Dark Matter & Dark Energy", description = "What makes up 95% of our universe that we cannot directly observe?", imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/ESO_-_Barred_Spiral_Galaxy_%28by%29.jpg/1200px-ESO_-_Barred_Spiral_Galaxy_%28by%29.jpg", category = "Deep Space", missionNumber = 46),
            )
            db.lessonDao().insertLessons(lessons)

            // Seed quizzes
            val quizzes = listOf(
                Quiz(id = 1, lessonId = 1, title = "Saturnian Navigator", passingScore = 60, points = 500),
                Quiz(id = 2, lessonId = 2, title = "Mars Rover Challenge", passingScore = 60, points = 500),
                Quiz(id = 3, lessonId = 3, title = "Event Horizon Test", passingScore = 70, points = 750),
                Quiz(id = 4, lessonId = 4, title = "Stellar Evolution Exam", passingScore = 70, points = 750),
                Quiz(id = 5, lessonId = 5, title = "Dark Matter Mystery", passingScore = 80, points = 1000),
            )
            db.quizDao().insertQuizzes(quizzes)

            // Seed questions for Saturn quiz
            val saturnQuestions = listOf(
                QuizQuestion(quizId = 1, questionText = "What is Saturn's ring system primarily composed of?", options = listOf("Metallic dust and gas", "Water ice and rock particles", "Liquid methane", "Compressed hydrogen"), correctOption = 1),
                QuizQuestion(quizId = 1, questionText = "How thick are Saturn's main rings on average?", options = listOf("~10 meters", "~10 kilometers", "~1,000 kilometers", "~100 meters"), correctOption = 0),
                QuizQuestion(quizId = 1, questionText = "What is the Roche Limit?", options = listOf("The distance from which Saturn is visible", "The boundary of Saturn's magnetic field", "The orbital distance within which a moon is torn apart by tidal forces", "The maximum size of ring particles"), correctOption = 2),
                QuizQuestion(quizId = 1, questionText = "How many distinct ring bands does Saturn have?", options = listOf("3", "5", "7", "12"), correctOption = 2),
                QuizQuestion(quizId = 1, questionText = "Which spacecraft provided most of our detailed knowledge of Saturn's rings?", options = listOf("Voyager 1", "Cassini", "New Horizons", "Hubble"), correctOption = 1),
            )
            db.quizQuestionDao().insertQuestions(saturnQuestions)

            // Seed questions for Mars quiz
            val marsQuestions = listOf(
                QuizQuestion(quizId = 2, questionText = "What gives Mars its reddish color?", options = listOf("Sulfur deposits", "Iron oxide (rust)", "Red algae", "Volcanic rock"), correctOption = 1),
                QuizQuestion(quizId = 2, questionText = "What is the name of the largest volcano on Mars?", options = listOf("Mount Elysium", "Olympus Mons", "Arsia Mons", "Pavonis Mons"), correctOption = 1),
                QuizQuestion(quizId = 2, questionText = "How long is a day on Mars compared to Earth?", options = listOf("About the same – 24 hours 37 minutes", "Half as long – 12 hours", "Twice as long – 48 hours", "Much shorter – 10 hours"), correctOption = 0),
                QuizQuestion(quizId = 2, questionText = "What are the two moons of Mars called?", options = listOf("Titan and Europa", "Deimos and Phobos", "Charon and Nix", "Io and Ganymede"), correctOption = 1),
                QuizQuestion(quizId = 2, questionText = "What is the Valles Marineris?", options = listOf("A crater chain", "A large volcano", "A vast canyon system", "A polar ice cap"), correctOption = 2),
            )
            db.quizQuestionDao().insertQuestions(marsQuestions)
        }
    }
}
