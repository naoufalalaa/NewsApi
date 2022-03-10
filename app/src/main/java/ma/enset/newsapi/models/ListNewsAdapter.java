package ma.enset.newsapi.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



import java.util.List;

import ma.enset.newsapi.R;

public class ListNewsAdapter extends ArrayAdapter<News> {
    private int resource;
    public ListNewsAdapter(@NonNull Context context, int resource, @NonNull List<News> news) {
        super(context, resource, news);
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null){
          listItemView= LayoutInflater.from(getContext()).inflate(resource,parent,false);
        }

        TextView textViewDate = listItemView.findViewById(R.id.dateArticle);
        textViewDate.setText(String.valueOf(getItem(position).getPublishedAt()));
        TextView textViewTitle=listItemView.findViewById(R.id.textViewTitle);
        String content = getItem(position).getContent();
        String[] con = content.split(" ",10);
        TextView textViewContent=listItemView.findViewById(R.id.textViewContent);
        textViewContent.setText(con[0]);
        for (int i = 1 ; i < 10 ; i++) {
            textViewContent.setText(textViewContent.getText().toString()+" "+con[i]);
        }
        textViewContent.append("[...]");
        textViewTitle.setText(getItem(position).getTitle());

        return listItemView;
    }
}
