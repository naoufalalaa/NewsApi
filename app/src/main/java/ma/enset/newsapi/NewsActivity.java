package ma.enset.newsapi;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;

import ma.enset.newsapi.models.News;

public class NewsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_page);

        Intent intent=getIntent();
        News news=(News) intent.getSerializableExtra("news");
        TextView title=findViewById(R.id.titre);
        TextView content=findViewById(R.id.contenu);
        TextView date=findViewById(R.id.datePub);
        TextView author=findViewById(R.id.author);
        TextView description=findViewById(R.id.description);
        ImageView imageView=findViewById(R.id.imageView);
        title.setText(news.getTitle());
        description.setText(news.getDescription());
        date.setText(String.valueOf(news.getPublishedAt()));
        content.setText(news.getContent());
        author.setText(news.getAuthor());

        Runnable thread= new Runnable(){
            @Override
            public void run() {
                try {
                    Log.i("info",news.getUrlToImage());
                    URL url=new URL(news.getUrlToImage());
                    Bitmap bitmap= BitmapFactory.decodeStream(url.openStream());
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t=new Thread(thread);
        t.start();



    }
}
