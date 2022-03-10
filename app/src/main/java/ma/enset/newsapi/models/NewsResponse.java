package ma.enset.newsapi.models;

import java.util.List;

public class NewsResponse {
    private String status;
    private Long totalResults;
    private List<News> articles;

    public NewsResponse() {
    }

    public NewsResponse(String status, Long totalResults, List<News> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public List<News> getArticles() {
        return articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }
}
