//package com.example.diploma;
//
//import android.view.LayoutInflater;
//import android.view.View;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import java.util.List;
//
//public class NoteDataAdapter extends RecyclerView.Adapter<NoteDataAdapter.PlayerViewHolder> {
//    public List<Note> notes;
//
//    public class PlayerViewHolder extends RecyclerView.ViewHolder {
//
//        TextView title;
//        TextView changeDate;
//        ImageView type;
//
//        public PlayerViewHolder(View view) {
//            super(view);
//
//            title = (TextView) view.findViewById(R.id.item_title);
//            changeDate = (TextView) view.findViewById(R.id.item_changeDate);
//            type = view.findViewById(R.id.item_type);
//        }
//    }
//
//    public NoteDataAdapter(List<Note> notes) {
//        this.notes = notes;
//    }
//
//
//    @Override
//    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_item, parent, false);
//
//        return new PlayerViewHolder(itemView);
//    }
//
//
//
//    @Override
//    public void onBindViewHolder(PlayerViewHolder holder, int position) {
//        Note note = notes.get(position);
//        holder.title.setText(note.getTitle());
//        holder.changeDate.setText(note.getChangeDate());
//       // holder.type.setText(note.getType());
//
//        if (note.type.equals("text"))
//            holder.type.setBackgroundResource(R.mipmap.ic_textnote);
//        if (note.type.equals("photo"))
//            holder.type.setBackgroundResource(R.mipmap.ic_photonote);
//        if (note.type.equals("list"))
//            holder.type.setBackgroundResource(R.mipmap.ic_listnote);
//        if (note.type.equals("sound"))
//            holder.type.setBackgroundResource(R.mipmap.ic_soundnote);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return notes.size();
//    }
//
//}
