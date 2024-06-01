package com.stingach.dm.guessriddle

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import java.util.Random

class MainActivity : AppCompatActivity() {
    // Список загадок и ответов
    var riddles = listOf(
        Pair("Идёт сегодня дождик, бери с собою …!", "зонтик"), // Загадка о зонте
        Pair("За спиной у нас висит, много сможет он вместить!", "рюкзак"), // Загадка о рюкзаке
        Pair("На пол снова убежало с моей кровати …", "одеяло"), // Загадка о одеяле
        Pair("Только на небо приплыли, тут же солнце собой скрыли.", "облака"), // Загадка о облаках
        Pair("Будем чистыми с тобой, Смоем пеной всё густой!", "мыло"), // Загадка о мыле
        Pair("В них прошли мы по дороге, Не промокли наши ноги!", "сапоги"), // Загадка о сапогах
        Pair("Она сидит на голове, Чтоб тепло было тебе!", "шапка"), // Загадка о шапке
        Pair("Она красивая игрушка, Танюше верная подружка.", "кукла"), // Загадка о кукле
        Pair("Как по ткани он пройдёт, Складочки все соберёт", "утюг"), // Загадка о утюге
        Pair("На небо туча приплыла, Его с собой к нам привела.", "дождь"), // Загадка о дожде
        Pair("Её руками обниму И сразу сладко так усну.", "подушка"), // Загадка о подушке
        Pair("Её сначала разверни, А потом уж в рот клади.", "конфета"), // Загадка о конфете
        Pair("Суп в тарелку мы нальём, Рукой затем её возьмём!", "ложка"), // Загадка о ложке
        Pair("По утрам у тёти Маши, Ем вкуснейшую я …", "кашу"), // Загадка о каше
        Pair("Он разрежет всё подряд Булку, фрукты и салат!", "нож"), // Загадка о ноже
    )

    // UI элементы для отображения информации и управления игрой
    lateinit var name : TextView
    lateinit var riddleText : TextView
    lateinit var correctOrNo : TextView

    lateinit var riddle : Button
    lateinit var answer : Button
    lateinit var statistic : Button
    lateinit var newGame : Button

    lateinit var statText : TextView

    // Переменные для отслеживания статистики игры
    var correctAnswerCount = 0
    var currentRiddle = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Находим элементы интерфейса по их идентификаторам
        name = findViewById(R.id.textView)
        riddleText = findViewById(R.id.textView2)
        correctOrNo = findViewById(R.id.textView3)
        statText = findViewById(R.id.textView22)

        riddle = findViewById(R.id.button)
        riddle.setOnClickListener {
            onRiddleClick()
        }

        answer = findViewById(R.id.button1)
        answer.setOnClickListener {
            onAnswerClick()
        }
        answer.isEnabled = false

        statistic = findViewById(R.id.button3)
        statistic.setOnClickListener {
            onStatisticClick()
        }
        statistic.isEnabled = false

        newGame = findViewById(R.id.button11)
        newGame.setOnClickListener {
            onNewGameClick()
        }

        // Перемешиваем список загадок перед началом игры
        riddles = riddles.shuffled(Random())
    }

    // Обработчик нажатия кнопки "Загадка"
    private fun onRiddleClick()
    {
        answer.isEnabled = true
        riddle.isEnabled = false
        correctOrNo.text = ""

        // Отображаем текущую загадку
        riddleText.text = riddles[currentRiddle].first
    }

    // Обработчик нажатия кнопки "Ответить"
    private fun onAnswerClick()
    {
        val builder = AlertDialog.Builder(this)
        val dialogLayout = layoutInflater.inflate(R.layout.answering_layout, null)

        builder.setView(dialogLayout)
            .setPositiveButton("Ответить") { dialog, which ->
                val ans = dialogLayout.findViewById<TextInputLayout>(R.id.editTextAnswer)

                // Проверяем правильность ответа
                if (ans.editText?.text.toString() == riddles[currentRiddle].second)
                {
                    correctOrNo.text = "Правильно!"
                    correctAnswerCount++
                }
                else
                {
                    correctOrNo.text = "Неправильно!"
                }
                answer.isEnabled = false
                riddle.isEnabled = true
                currentRiddle++
                statText.text = "Решено: ${currentRiddle}/10"

                // Если решено 10 загадок, включаем статистику
                if (currentRiddle == 10)
                {
                    statistic.isEnabled = true
                    answer.isEnabled = false
                    riddle.isEnabled = false
                }
            }

        builder.create().show()
    }

    // Обработчик нажатия кнопки "Статистика"
    private fun onStatisticClick()
    {
        val intent = Intent(this, Stat_activity::class.java)
        intent.putExtra("CorrectAnswerCount", correctAnswerCount)
        intent.putExtra("CurrentRiddle", currentRiddle)

        startActivity(intent)
    }

    // Обработчик нажатия кнопки "Новая игра"
    private fun onNewGameClick()
    {
        // Перезапускаем игру
        riddles = riddles.shuffled(Random())
        currentRiddle = 0
        riddleText.text = ""

        riddle.isEnabled = true
        answer.isEnabled = false
        statistic.isEnabled = false

        correctOrNo.text = ""
        statText.text = "Решено: ${currentRiddle}/10"
    }
}
