package com.example.todolist

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class TodoListAdapter(realmResult: OrderedRealmCollection<Todo>)
    : RealmBaseAdapter<Todo>(realmResult) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val view: View

        // 1. convertView가 null이면 레이아웃을 작성합니다.
        if (convertView == null) {
            // 2. LayoutInflater 클래스는 XML 레이아웃 파일을 코드로 불러오는 기능을 제공합니다.
            // LayoutInflater.from(parent?.context) 메서드로 객체를 얻고
            // inflate() 메서드로 XML 레이아웃 파일을 읽어서 뷰로 반환하여 view 변수에 할당합니다.
            view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_todo, parent, false)

            // 3. 뷰 홀더 객체를 초기화합니다.
            // 뷰 홀더 클래스는 15와 같이 별도의 클래스로 먼저 작성합니다.
            // 뷰 홀더 클래스는 전달받은 view에서 text1과 text2 아이디를 가진 텍스트 뷰들의 참조를 저장하는 역할을 합니다.
            vh = ViewHolder(view)
            // 4. 뷰 홀더 객체는 tag 프로퍼티로 view에 저장됩니다. tag 프로퍼티에는 Any 형으로 어떠한 객체도 저장할 수 있습니다.
            view.tag = vh
        }
        // 5. convertView가 null이 아니라면
        else {
            // 6. 이전에 작성했던 convertVIew를 재사용합니다.
            view = convertView
            // 7. 뷰 홀더 객체를 tag 프로퍼티에서 꺼냅니다. 반환되는 데이터형이 Any이므로 ViewHolder형으로 형변환을 합니다.
            vh = view.tag as ViewHolder
        }

        // RealmBaseAdapter는 adapterData 프로퍼티를 제공합니다. 여기서 데이터에 접근할 수 있습니다.
        // 8. 데이터에 값이 있다면
        if (adapterData != null) {
            // 9. 해당 위치의 데이터를 item 변수에 담습니다.
            val item = adapterData!![position]
            // 10. 할 일 텍스트와
            vh.textTextView.text = item.title
            // 11. 날짜를 각각 텍스트 뷰에 표시합니다.
            // DateFormat.format() 메서드는 지정한 형식으로 Long형 시간 데이터를 변환합니다.
            // DateFormat 클래스는 android.text.format.DateFormat을 임포트하는 것에 주의합니다.
            vh.dateTextView.text = DateFormat.format("yyyy/MM/dd", item.date)
        }

        // 12. 완성된 view 변수를 반환합니다. 이 뷰는 다음 번에 호출되면 convertView로 재사용됩니다.
        return view
    }

    // 13. getItemId() 메서드를 오버라이드합니다.
    // 리스트 뷰를 클릭하여 이벤트를 처리할 때 인자로 position, id 등이 넘어오게 되는데
    // 이 때 넘어오는 id값을 결정합니다.
    // 데이터베이스를 다룰 때 레코드마다 고유한 아이디를 가지고 있는데 그것을 반환하도록 정의합니다.
    override fun getItemId(position: Int): Long {
        // adapterView가 Realm 데이터를 가지고 있으면
        if (adapterData != null) {
            // 14. 요청한 해당 위치에 있는 데이터의 id값을 반환하도록 합니다.
            return adapterData!![position].id
        }
        return super.getItemId(position)
    }
}

// 15
class ViewHolder(view: View) {
    val dateTextView: TextView = view.findViewById(R.id.text1)
    val textTextView: TextView = view.findViewById(R.id.text2)
}