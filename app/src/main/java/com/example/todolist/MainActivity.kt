package com.example.todolist

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

// Realm 문서 링크 : https://realm.io/kr/docs/
class MainActivity : AppCompatActivity() {
    val realm = Realm.getDefaultInstance() // Realm 객체를 초기화

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // 새 할 일 추가
        fab.setOnClickListener {
            startActivity<EditActivity>()
        }

        // 전체 할 일 정보를 가져와서 날짜순으로 내림차순 정렬
        val realmResult = realm.where<Todo>().findAll().sort("date", Sort.DESCENDING)
        // 1. TodoListAdapter 클래스에 할 일 목록인 realmResult를 전달하여 어댑터 인스턴스를 생성합니다.
        val adapter = TodoListAdapter(realmResult)
        // 2.생성한 어댑터를 리스트 뷰에 설정합니다. 이것으로 할 일 목록이 리스트뷰에 표시됩니다.
        listView.adapter = adapter

        // Realm의 또 다른 장점은 데이터가 변경되는지 모니터링할 수 있다는 점입니다.
        // 3. addChangeListener를 구현하면 데이터가 변경될 때마다 어댑터에 알려줄 수 있습니다.
        // 어댑터에 notifyDataSetChanged() 메서드를 호출하면
        // 데이터 변경을 통지하여 리스트를 다시 표시하게 됩니다.
        // 데이터가 변경되면 어댑터에 적용
        realmResult.addChangeListener { _ -> adapter.notifyDataSetChanged() }

        // 리스트 뷰의 아이템을 클릭했을 때의 처리를 setOnItemClickListener 메서드에 구현합니다.
        listView.setOnItemClickListener {parent, view, position, id ->
            // 4. EditActivity에 선택한 아이템의 id 값을 전달합니다.
            // 이제 기존 id가 있는지 여부에 따라 새 할 일을 추가하거나 기존 할 일을 수정할 수 있습니다.
            // 할 일 수정
            startActivity<EditActivity>("id" to id)
        }

        // 새 할 일 추가
        fab.setOnClickListener {
            startActivity<EditActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // Realm 객체를 해제
    }
}
