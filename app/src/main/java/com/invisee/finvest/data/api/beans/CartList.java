package com.invisee.finvest.data.api.beans;

import com.invisee.finvest.data.api.responses.CartListResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by glenrynaldi on 3/16/16.
 */
public class CartList implements Serializable {

    private List<CartListResponse> cartList;

    public CartList() {

    }

    public CartList(List<CartListResponse> cartList) {
        this.cartList = cartList;
    }

    public List<CartListResponse> getCartList() {
        return cartList;
    }

    public void setCartList(List<CartListResponse> cartList) {
        this.cartList = cartList;
    }
}
