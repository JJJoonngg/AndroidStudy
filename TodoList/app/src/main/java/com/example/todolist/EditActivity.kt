package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class EditActivity : AppCompatActivity() {

    val realm = Realm.getDefaultInstance() // 인스턴스 얻기

    val calendar: Calendar = Calendar.getInstance() //날짜를 다룰 캘린더 객체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // 인스턴스 해제
    }

    private fun insertTodo() {
        realm.beginTransaction() // Transaction Start

        val newItem = realm.createObject<Todo>(nextId()) // 새 객체 생성

        // 값 설정
        newItem.title = todoEditText.text.toString()
        newItem.date = calendar.timeInMillis

        realm.commitTransaction() // 트랜잭션 종료 반영

        // 다이얼로그 표시
        alert ("내용이 추가되었습니다."){
            yesButton { finish() }
        }.show()
    }

    //다음 id를 반환
    private fun nextId():Int{
        val maxId = realm.where<Todo>().max("id")
        if(maxId !=null){
            return maxId.toInt() + 1
        }
        return 0
    }
}
