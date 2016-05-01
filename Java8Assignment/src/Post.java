import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Deepak on 1/5/16.
 */
public class Post {

    Date date;
    String content;
    int noOfLikes;
    int noOfRetweets;

    Post(Date date, String content, int noOfLikes, int noOfRetweets) {
        this.date = date;
        this.content = content;
        this.noOfLikes = noOfLikes;
        this.noOfRetweets = noOfRetweets;
    }

    public static void printPosts(List<Post> posts) {
        for (Post post : posts) {

            printTweets(post);

        }
    }

    public static void printTweets(Post post){
        System.out.print("\n\ntweets{" +
                " Name -> '" + post.content + "''\n" +
                ",Retweet -> '" + post.noOfRetweets  + "'\n" +
                ",Likes -> " + post.noOfLikes + "'\n" +
                "}");
    }

    public Date getDate() {
        return date;
    }

    public int getNoOfLikes() {
        return noOfLikes;
    }

    public int getNoOfRetweets() {
        return noOfRetweets;
    }


}

class Timeline {

    public static void main(String args[]) {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("w50kgzcAMCqstAjPNtKCCUAZb")
                .setOAuthConsumerSecret("744k6xvIhC0RxTvLcFNDbd4jkcUl47w8UJDAFmlLwylsMWzIKz")
                .setOAuthAccessToken("104384669-LlkKw0dcZgbQ3GeLHvzz6kl6id1IelFmkvFRUZzl")
                .setOAuthAccessTokenSecret("ruuVvE26GsaHRMMgrHEO4koIujvNZgAFIjs2gX71h2tL2");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        List<Post> posts = new ArrayList<>();
        Comparator<Post> byDate = (Post p1, Post p2) -> p1.getDate().compareTo(p2.getDate());
        Comparator<Post> byLikes = (Post p1, Post p2) -> p1.getNoOfLikes() > p2.getNoOfLikes() ? 1 : p1.getNoOfLikes() == p2.getNoOfLikes() ? 0 : -1;
        Comparator<Post> byRetweets = (Post p1, Post p2) -> p1.getNoOfRetweets() > p2.getNoOfRetweets() ? 1 : p1.getNoOfRetweets() == p2.getNoOfRetweets() ? 0 : -1;

        try {
            ResponseList<Status> resList = twitter.getHomeTimeline();
            for (Status responseList : resList) {
                Date date = responseList.getCreatedAt();
                String content = responseList.getText();
                int nLikes = responseList.getFavoriteCount();
                int nRetweets = responseList.getRetweetCount();
                posts.add(new Post(date, content, nLikes, nRetweets));
            }
            System.out.println("Posts sorted by Date: ");
            posts.sort(byDate.reversed());
            Post.printPosts(posts);
            System.out.println("\n\nPosts sorted by Likes : ");
            posts.sort(byLikes.reversed());
            Post.printPosts(posts);
            System.out.println("\n\nPosts sorted by Retweets : ");
            posts.sort(byRetweets.reversed());
            Post.printPosts(posts);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
