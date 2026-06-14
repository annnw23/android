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
    version = 18,
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
        // --- LESSONS (12 TOTAL) ---

        val l1 = lessonDao().insert(Lesson(title = "Układ Słoneczny", description = "Nasz układ składa się ze Słońca i ośmiu planet krążących po orbitach. Słońce to centralna gwiazda, która grawitacyjnie wiąże wszystkie obiekty, od olbrzymich planet po małe ziarnka pyłu.", imageRes = "https://www.nasa.gov/wp-content/uploads/2023/03/solar-system.jpg")).toInt()
        val l2 = lessonDao().insert(Lesson(title = "Czarne Dziury", description = "Regiony czasoprzestrzeni, gdzie grawitacja jest tak silna, że nic nie może uciec, nawet światło. Powstają po zapadnięciu się masywnych gwiazd pod własnym ciężarem.", imageRes = "https://images-assets.nasa.gov/image/PIA23122/PIA23122~medium.jpg")).toInt()
        val l3 = lessonDao().insert(Lesson(title = "Księżyc", description = "Jedyny naturalny satelita Ziemi. Wpływa na pływy oceaniczne i stabilizuje nachylenie osi naszej planety, co ma kluczowe znaczenie dla klimatu.", imageRes = "https://images-assets.nasa.gov/image/as11-44-6551/as11-44-6551~medium.jpg")).toInt()
        val l4 = lessonDao().insert(Lesson(title = "Mars", description = "Czwarta planeta od Słońca, znana jako Czerwona Planeta z powodu tlenku żelaza na jej powierzchni. Posiada najwyższy wulkan w układzie - Olympus Mons oraz ogromne kaniony.", imageRes = "https://images-assets.nasa.gov/image/PIA02653/PIA02653~medium.jpg")).toInt()
        val l5 = lessonDao().insert(Lesson(title = "Galaktyki", description = "Gigantyczne skupiska miliardów gwiazd, pyłu i gazu. Nasza Galaktyka to Droga Mleczna, która jest spiralą z poprzeczką i zawiera setki miliardów układów planetarnych.", imageRes = "https://images-assets.nasa.gov/image/PIA08653/PIA08653~medium.jpg")).toInt()
        val l6 = lessonDao().insert(Lesson(title = "Słońce", description = "Gwiazda typu G2V, stanowiąca serce naszego układu. Zachodzą w niej reakcje termojądrowe przekształcające wodór w hel, uwalniając ogromne ilości energii podtrzymującej życie.", imageRes = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e001435/GSFC_20171208_Archive_e001435~medium.jpg")).toInt()
        val l7 = lessonDao().insert(Lesson(title = "Jowisz", description = "Największa planeta w układzie. Gazowy olbrzym z charakterystyczną Wielką Czerwona Plamą - gigantycznym antycyklonem szalejącym od stuleci.", imageRes = "https://images-assets.nasa.gov/image/PIA04866/PIA04866~medium.jpg")).toInt()
        val l8 = lessonDao().insert(Lesson(title = "Saturn", description = "Planeta słynąca z rozległych pierścieni zbudowanych z brył lodu i skał. Jest mniej gęsta niż woda, co oznacza, że mogłaby unosić się na jej powierzchni.", imageRes = "https://images-assets.nasa.gov/image/PIA08361/PIA08361~medium.jpg")).toInt()
        val l9 = lessonDao().insert(Lesson(title = "Ewolucja Gwiazd", description = "Cykl życia gwiazdy: od mgławicy, przez ciąg główny, po białego karła, gwiazdę neutronową lub czarną dziurę, zależnie od jej masy początkowej.", imageRes = "https://images-assets.nasa.gov/image/PIA15415/PIA15415~medium.jpg")).toInt()
        val l10 = lessonDao().insert(Lesson(title = "Komety", description = "Obiekty z lodu i pyłu krążące po wydłużonych orbitach. Zbliżając się do Słońca, lód paruje, tworząc charakterystyczny, świecący warkocz.", imageRes = "https://images-assets.nasa.gov/image/PIA00122/PIA00122~medium.jpg")).toInt()
        val l11 = lessonDao().insert(Lesson(title = "Droga Mleczna", description = "Nasz dom we wszechświecie. Zawiera około 200-400 miliardów gwiazd i ma średnicę około 100 000 lat świetlnych. My znajdujemy się w Ramieniu Oriona.", imageRes = "https://images-assets.nasa.gov/image/PIA12110/PIA12110~medium.jpg")).toInt()
        val l12 = lessonDao().insert(Lesson(title = "Teleskop Webba", description = "James Webb Space Telescope to najnowocześniejsze narzędzie do obserwacji kosmosu w podczerwieni, pozwalające widzieć pierwsze galaktyki uformowane po Wielkim Wybuchu.", imageRes = "https://images-assets.nasa.gov/image/GSFC_20171208_Archive_e000494/GSFC_20171208_Archive_e000494~medium.jpg")).toInt()

        // --- QUIZZES ---
        val q1 = quizDao().insert(Quiz(lessonId = l1, title = "Quiz: Układ Słoneczny", passingScore = 2, points = 100)).toInt()
        val q2 = quizDao().insert(Quiz(lessonId = l2, title = "Quiz: Czarne Dziury", passingScore = 2, points = 150)).toInt()
        val q3 = quizDao().insert(Quiz(lessonId = l3, title = "Quiz: Księżyc", passingScore = 2, points = 100)).toInt()
        val q4 = quizDao().insert(Quiz(lessonId = l4, title = "Quiz: Mars", passingScore = 2, points = 100)).toInt()
        val q5 = quizDao().insert(Quiz(lessonId = l5, title = "Quiz: Galaktyki", passingScore = 2, points = 120)).toInt()
        val q6 = quizDao().insert(Quiz(lessonId = l6, title = "Quiz: Słońce", passingScore = 2, points = 100)).toInt()
        val q7 = quizDao().insert(Quiz(lessonId = l7, title = "Quiz: Jowisz", passingScore = 2, points = 100)).toInt()
        val q8 = quizDao().insert(Quiz(lessonId = l8, title = "Quiz: Saturn", passingScore = 2, points = 100)).toInt()
        val q9 = quizDao().insert(Quiz(lessonId = l9, title = "Quiz: Ewolucja Gwiazd", passingScore = 2, points = 130)).toInt()
        val q10 = quizDao().insert(Quiz(lessonId = l10, title = "Quiz: Komety", passingScore = 2, points = 100)).toInt()
        val q11 = quizDao().insert(Quiz(lessonId = l11, title = "Quiz: Droga Mleczna", passingScore = 2, points = 120)).toInt()
        val q12 = quizDao().insert(Quiz(lessonId = l12, title = "Quiz: Teleskop Webba", passingScore = 2, points = 150)).toInt()

        // --- QUESTIONS ---
        quizQuestionDao().insertAll(listOf(
            QuizQuestion(quizId = q1, questionText = "Ile jest głównych planet w Układzie Słonecznym?", options = """["7","8","9","10"]""", correctOption = 1),
            QuizQuestion(quizId = q1, questionText = "Który obiekt znajduje się w centrum układu?", options = """["Ziemia","Księżyc","Słońce","Mars"]""", correctOption = 2),
            QuizQuestion(quizId = q1, questionText = "Która planeta jest największa?", options = """["Saturn","Jowisz","Neptun","Ziemia"]""", correctOption = 1),
            
            QuizQuestion(quizId = q2, questionText = "Co nie ucieknie z czarnej dziury?", options = """["Gaz","Pył","Światło","Tylko planety"]""", correctOption = 2),
            QuizQuestion(quizId = q2, questionText = "Kto przewidział istnienie czarnych dziur w teorii względności?", options = """["Newton","Einstein","Darwin","Tesla"]""", correctOption = 1),
            
            QuizQuestion(quizId = q3, questionText = "Kto był pierwszym człowiekiem na Księżycu?", options = """["Gagarin","Aldrin","Armstrong","Collins"]""", correctOption = 2),
            QuizQuestion(quizId = q3, questionText = "Czy Księżyc posiada gęstą atmosferę?", options = """["Tak","Nie","Tylko tlenową","Tylko azotową"]""", correctOption = 1),
            
            QuizQuestion(quizId = q4, questionText = "Dlaczego Mars ma czerwony kolor?", options = """["Z powodu rdzy","Z powodu lawy","Z powodu roślin","Z powodu zimna"]""", correctOption = 0),
            QuizQuestion(quizId = q4, questionText = "Jak nazywa się najwyższy wulkan na Marsie?", options = """["Etna","Olympus Mons","Vesuvius","Krakatau"]""", correctOption = 1),
            
            QuizQuestion(quizId = q5, questionText = "Jakim typem galaktyki jest Droga Mleczna?", options = """["Eliptyczna","Spiralna","Nieregularna","Kwadratowa"]""", correctOption = 1),
            QuizQuestion(quizId = q5, questionText = "Co trzyma miliardy gwiazd razem w galaktyce?", options = """["Magnetyzm","Grawitacja","Wiatr kosmiczny","Światło"]""", correctOption = 1),
            
            QuizQuestion(quizId = q6, questionText = "Czym w rzeczywistości jest Słońce?", options = """["Planetą","Gwiazdą","Księżycem","Asteroidą"]""", correctOption = 1),
            QuizQuestion(quizId = q6, questionText = "Jaki jest główny składnik Słońca?", options = """["Żelazo","Wodór","Węgiel","Tlen"]""", correctOption = 1),
            
            QuizQuestion(quizId = q7, questionText = "Czym jest Wielka Czerwona Plama na Jowiszu?", options = """["Wielką górą","Ogromną burzą","Wyschniętym morzem","Kraterem"]""", correctOption = 1),
            QuizQuestion(quizId = q7, questionText = "Do jakiego typu planet należy Jowisz?", options = """["Skalista","Gazowy olbrzym","Lodowa","Karłowata"]""", correctOption = 1),
            
            QuizQuestion(quizId = q8, questionText = "Z czego głównie zbudowane są pierścienie Saturna?", options = """["Z lodu i skał","Z gazu","Ze złota","Z piasku"]""", correctOption = 0),
            QuizQuestion(quizId = q8, questionText = "Czy gęstość Saturna jest mniejsza od gęstości wody?", options = """["Tak","Nie","Są równe","Zależy od pory roku"]""", correctOption = 0),
            
            QuizQuestion(quizId = q9, questionText = "Co pozostanie po Słońcu pod koniec jego ewolucji?", options = """["Czarna dziura","Biały karzeł","Supernowa","Nic nie zostanie"]""", correctOption = 1),
            QuizQuestion(quizId = q9, questionText = "Gdzie rodzą się nowe gwiazdy?", options = """["W próżni","W mgławicach","Na powierzchni planet","Wewnątrz komet"]""", correctOption = 1),
            
            QuizQuestion(quizId = q10, questionText = "Z czego składa się jądro komety?", options = """["Z metalu","Z lodu i pyłu","Z diamentu","Z czystego gazu"]""", correctOption = 1),
            QuizQuestion(quizId = q10, questionText = "Kiedy kometa wykształca warkocz?", options = """["Zawsze go ma","Gdy jest blisko Słońca","Tylko w nocy","Tylko zimą"]""", correctOption = 1),
            
            QuizQuestion(quizId = q11, questionText = "Ile mniej więcej gwiazd zawiera Droga Mleczna?", options = """["1 milion","1 miliard","Setki miliardów","Tysiące"]""", correctOption = 2),
            QuizQuestion(quizId = q11, questionText = "Gdzie w naszej galaktyce znajduje się Układ Słoneczny?", options = """["W samym centrum","W jednym z ramion spiralnych","Na zewnętrznym brzegu","Poza galaktyką"]""", correctOption = 1),
            
            QuizQuestion(quizId = q12, questionText = "W jakim zakresie światła głównie obserwuje JWST?", options = """["Widzialnym","Podczerwonym","Rentgenowskim","Ultrafioletowym"]""", correctOption = 1),
            QuizQuestion(quizId = q12, questionText = "Gdzie stacjonuje teleskop Webba?", options = """["Na orbicie Ziemi","W punkcie Lagrange'a L2","Na powierzchni Księżyca","Na orbicie Marsa"]""", correctOption = 1)
        ))
    }
}
