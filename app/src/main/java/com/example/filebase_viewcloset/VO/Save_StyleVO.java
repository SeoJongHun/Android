package com.example.filebase_viewcloset.VO;

public class Save_StyleVO {

    private int s_style_img1;
    private int s_style_img2;
    private String s_style_color1;
    private String s_style_color2;

    public Save_StyleVO(int s_style_img1, int s_style_img2, String s_style_color1, String s_style_color2) {
        this.s_style_img1 = s_style_img1;
        this.s_style_img2 = s_style_img2;
        this.s_style_color1 = s_style_color1;
        this.s_style_color2 = s_style_color2;
    }

    public int getS_style_img1() {
        return s_style_img1;
    }

    public void setS_style_img1(int s_style_img1) {
        this.s_style_img1 = s_style_img1;
    }

    public int getS_style_img2() {
        return s_style_img2;
    }

    public void setS_style_img2(int s_style_img2) {
        this.s_style_img2 = s_style_img2;
    }

    public String getS_style_color1() {
        return s_style_color1;
    }

    public void setS_style_color1(String s_style_color1) {
        this.s_style_color1 = s_style_color1;
    }

    public String getS_style_color2() {
        return s_style_color2;
    }

    public void setS_style_color2(String s_style_color2) {
        this.s_style_color2 = s_style_color2;
    }
}
