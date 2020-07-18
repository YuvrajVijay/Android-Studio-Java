package ui;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.selfjournal.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Model.Journal;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Journal> journalList;

    public JournalRecyclerAdapter(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_row,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Journal journal =journalList.get(position);
        String imageUrl;

        holder.name.setText(journal.getUserName());
        holder.journalTitle.setText(journal.getTitle());
        holder.journalThought.setText(journal.getThoughts());

        imageUrl=journal.getImageUrl();
        //use Picasso Library to download and show image
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.you)
                .fit()
                .into(holder.journalImage);

        String timeago= (String) DateUtils.getRelativeTimeSpanString(journal
                .getTimeadded()
                .getSeconds() * 1000);
        holder.journalTimestamp.setText(timeago);

    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView journalTitle;
        public TextView journalThought;
        public TextView journalTimestamp;
        public ImageView journalImage;
        public CardView cardView;
        public TextView name;
        public ImageButton shareButton;

        String userId;
        String username;

        public ViewHolder(@NonNull View itemView,Context ctx) {
            super(itemView);
            journalTitle=itemView.findViewById(R.id.journal_title_list);
            journalThought=itemView.findViewById(R.id.journal_thought_list);
            journalTimestamp=itemView.findViewById(R.id.journal_timestamp_list);
            journalImage=itemView.findViewById(R.id.journal_image_list);
            cardView=itemView.findViewById(R.id.cardView);
            name=itemView.findViewById(R.id.journal_row_username);
            shareButton=itemView.findViewById(R.id.journal_row_share_button);

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //shareexp();
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO:edit on click
                }
            });
        }
    }
//    private void shareexp() {
//        Intent intent=new Intent(Intent.ACTION_SEND);
//        intent.setType("text/plain");
//        intent.putExtra(Intent.EXTRA_SUBJECT,journalList.get());
//        intent.putExtra(Intent.EXTRA_TEXT,"High Score: "+pref.getHighScore()+
//                "Current Score: "+score.getScore()+"Current Question: "
//                +questionList.get(currentquestionindex).getAnswer());
//        startActivity(intent);
//    }
}
