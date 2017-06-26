package sample.utils;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.entity.Product;
import sample.entity.User;

public class Context {

    User user;
    Product product;
    ObservableList<String> chek = FXCollections.observableArrayList();
    Double total;

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public ObservableList<String> getChek() {
        return chek;
    }

    public void setChek(ObservableList<String> chek) {
        this.chek = chek;
    }

    static Context instance;

    private Context() {
    }

    public static Context getInstance(){
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static void setInstance(Context instance) {
        Context.instance = instance;
    }

}
