package es.source.code.activity;

import java.io.Serializable;

/**
 * Created by adam on 2016/6/27.
 */
public class FoodsOnService implements Serializable{
    String foodName;//菜名
    int foodNum;//库存数量
    int foodPrice;
    String type;//种类

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }



    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getFoodNum() {
        return foodNum;
    }

    public void setFoodNum(int foodNum) {
        this.foodNum = foodNum;
    }


}
