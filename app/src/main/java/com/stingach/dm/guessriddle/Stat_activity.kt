package com.stingach.dm.guessriddle

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Stat_activity : AppCompatActivity() {

    // UI элементы для отображения статистики
    lateinit var riddleCount : TextView
    lateinit var correctCount : TextView
    lateinit var incorrectCount : TextView

    lateinit var btn : Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat)

        // Получаем данные из интента
        val CurrentRiddle = intent.getIntExtra("CurrentRiddle", 0)
        val CorrectAnswerCount = intent.getIntExtra("CorrectAnswerCount", 0)

        // Находим элементы интерфейса по их идентификаторам
        btn = findViewById(R.id.button2)
        btn.setOnClickListener {
            onBtnClick()
        }

        riddleCount = findViewById(R.id.textView4)
        correctCount = findViewById(R.id.textView5)
        incorrectCount = findViewById(R.id.textView6)

        // Устанавливаем текст элементов интерфейса
        riddleCount.setText("Количество решенных загадок: $CurrentRiddle")
        correctCount.setText("Правильно было решено загадок: $CorrectAnswerCount")
        incorrectCount.setText("Неправильно было решено загадок: ${CurrentRiddle - CorrectAnswerCount}")
    }

    // Обработчик нажатия кнопки "Назад" или "Закрыть"
    private fun onBtnClick()
    {
        // Закрываем текущую активность и возвращаемся к предыдущему экрану
        super.finish()
    }
}
