package com.example.project;

        import android.os.Bundle;
        import android.util.Log;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import org.jetbrains.annotations.NotNull;

        import java.util.ArrayList;

public class Board extends AppCompatActivity {

    private ArrayList<BoardItem> arrayList;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_layout);

        arrayList = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.rv);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("board");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot datasnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot : datasnapshot.getChildren()){
                    BoardItem boardItem = snapshot.getValue(BoardItem.class); // BoardItem 객체에 데이터를 담는다.
                    arrayList.add(boardItem);// 담은 데이터들을 배열리스트에 넣고 리사이클러뷰에 보내기 위해 arrayList에 저장.
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                //디비 가져오던중 에러발생
                Log.e("error",String.valueOf(error.toException()));
            }
        });

        adapter = new BoardAdapter(arrayList);
        recyclerView.setAdapter(adapter); //리사이클러 뷰에 연결

    }
}
