package com.rtn.fitnergy.education;



import com.rtn.fitnergy.R;

import java.util.ArrayList;

public class Article {
    int id;
    String title;
    String description;
    String link;


    String imgLink;


    int image;

    public ArrayList<Article> articleData = new ArrayList<>();

    public Article(int id, String title, String description,String imgLink, String link, int image) {
        this.id=id;
        this.imgLink=imgLink;
        this.title = title;
        this.description = description;
        this.link = link;
        this.image=image;


    }
    public Article(){

    }


    public void addArticle(int id,String title,String desc, String imgLink,String link){
        articleData.add(new Article(id,title,desc,imgLink,link,R.drawable.food1));
    }
    public void initArticle(){
        articleData.add(new Article(9, "Health & Nutrition Articles | Food Trends", "fefe", "nono", "123", R.drawable.food1));
        articleData.add(new Article(1, "Healthy food choices are happy food choices", "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).", "rerer", "123", R.drawable.food2));
        articleData.add(new Article(2, "Healthy Eating - HelpGuide.org", "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.", "nono", "123",R.drawable.food3));
        articleData.add(new Article(3, "How fast food affects the body", "fefe", "nono", "123",R.drawable.food4));
        articleData.add(new Article(4, "Food Articles - Joy Bauer", "fefe", "nono", "123",R.drawable.food5));
        articleData.add(new Article(5, "Articles - New Food Magazine", "fefe", "nono", "123",R.drawable.food1));
        articleData.add(new Article(6, "Why eating colourful food is good for you", "fefe", "nono", "123",R.drawable.food2));
        articleData.add(new Article(7, "How Does Food Impact Health?", "fefe", "nono", "123",R.drawable.food3));
        articleData.add(new Article(8, "Health & Nutrition Articles | Food Trends", "fefe", "nono", "123",R.drawable.food4));
    }


    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public int getImage() {
        return image;
    }
    public void setImage(int image){
        this.image=image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
