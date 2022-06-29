package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // JPA 스펙상 Entity나 임베디드 타입( @Embeddable )에서 기본 생성자를 protected로 설정하면 그나마 안전하다.
    protected Address() {

    }

    // value Type용도로 만든 Address 클래스는 imutable 해야하므로 모든값을 갖는 생성자를 만든다.
    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
