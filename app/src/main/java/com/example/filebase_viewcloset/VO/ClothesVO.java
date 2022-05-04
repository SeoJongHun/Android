package com.example.filebase_viewcloset.VO;

public class ClothesVO {

    //Clothes에 필요한 VO
    // 이미지 = img
    // 색깔 = color
    // 태그값(위치) = location

    private int clothes_img;
    private String clothes_color;
    private String clothes_location;
    private String clothes_type;


    public ClothesVO(int clothes_img, String clothes_color, String clothes_location,String clothes_type) {
        this.clothes_img = clothes_img;
        this.clothes_color = clothes_color;
        this.clothes_location = clothes_location;
        this.clothes_type = clothes_type;
    }

    public ClothesVO(String clothes_color, String clothes_location, String clothes_type) {
        this.clothes_color = clothes_color;
        this.clothes_location = clothes_location;
        this.clothes_type = clothes_type;
    }

    public int getClothes_img() {
        return clothes_img;
    }

    public String getClothes_type() {
        return clothes_type;
    }

    public void setClothes_type(String clothes_type) {
        this.clothes_type = clothes_type;
    }

    public void setClothes_img(int clothes_img) {
        this.clothes_img = clothes_img;
    }

    public String getClothes_color() {
        return clothes_color;
    }

    public void setClothes_color(String clothes_color) {
        this.clothes_color = clothes_color;
    }

    public String getClothes_location() {
        return clothes_location;
    }

    public void setClothes_location(String clothes_location) {
        this.clothes_location = clothes_location;
    }
}
