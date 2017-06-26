package sample.entity;

public class Product {

    String nameProd;
    String descriptionProd;
    double price;
    int idProd;


    public Product(String nameProd, String descriptionProd, Double price) {

        this.nameProd = nameProd;
        this.descriptionProd = descriptionProd;
        this.price = price;
    }

    public Product() {
    }

    public Product(String nameProd, String descriptionProd, Double price, Integer idProd) {
        this.nameProd = nameProd;
        this.descriptionProd = descriptionProd;
        this.price = price;
        this.idProd = idProd;
    }
    public String getNameProd() {
        return nameProd;
    }

    public void setNameProd(String nameProd) {
        this.nameProd = nameProd;
    }

    public String getDescriptionProd() {
        return descriptionProd;
    }

    public void setDescriptionProd(String descriptionProd) {
        this.descriptionProd = descriptionProd;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getIdProd() {
        return idProd;
    }

    public void setIdProd(int idProd) {
        this.idProd = idProd;
    }

    @Override
    public String toString() {
        return "Product{" +
                "nameProd='" + nameProd + '\'' +
                ", descriptionProd='" + descriptionProd + '\'' +
                ", price=" + price +
                '}';
    }
}
