# store-resarvation
매장 테이블 예약 서비스 구현(스프링 부트를 활용한 서버 API 기반)

>과제 구현을 하면서 최대한 좋은 코드에 대한 설계에 대해서 고민을 많이 했으며, 해당 고민은 아래와 같은 결과를 낳게 되었습니다.

## 개요

필자는 아직 신입 개발자로서 항상 좋은 코드 작성법에 대해서 고민이 많았다. 이러한 고민을 가지고 [블로그](https://velog.io/@dkwktm45/%EA%B0%9D%EC%B2%B4%EC%A7%80%ED%96%A5-%EA%B0%9C%EB%B0%9C-5%EB%8C%80-%EC%9B%90%EB%A6%AC%EB%A5%BC-%ED%8C%8C%ED%97%A4%EC%B3%90-%EB%B3%B4%EC%9E%90)도 작성을 해봤고, **하지만 여전히 백문이 불어 일견 실전과 같은 코드 작성이 아니니 나에게는 해소가 되지 않는 부분이 있었다.**

그렇기에 이번에 제로베이스 백엔드 과제를 수행하면서 최대한 좋은 코드 작성법을 적용해 보면서 아 이렇게 작성하면 좋구나! 라는 것을 느꼈고 그에 대한 지극히 나만의 이야기를 작성하려고 한다.

### 비지니스 로직의 추상화를 높이자.
```java
  public Reservation getReservationValidById(Long reservationId) {
    Reservation reservation = reservationRepository.findById(reservationId)
        .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
    
    // 수락된 예약인지를 판단
    if (!reservation.getStatus().equals(REFUSE)) {
      throw new CustomException(RESERVATION_NOT_FOUND);
    }
    // code가 일치한지를 판단
    if (!reservation.getReservationCode().equals("123")) { // 임의의 123
      throw new CustomException(RESERVATION_NOT_VALID);
    }

    // 규정 내의 시간안에 도착하는지를 판단
    if (reservation.getReservationTime().isBefore(now())) {
      throw new CustomException(RESERVATION_NOT_TIME);
    }
    return reservation;
  }
  ```

<br>

만약 이러한 코드가 Service Layer에 있다고 가정을 하자. 얼핏 본다면 정말 괜찮은 코드일 수 있다. 그리고 **<span style="color:green">이러한 코드는 매우 흔한코드이고 그렇게까지 나쁘지 않은 코드라고 본다.</span>**

그리고 이것의 코드를 읽는데도 큰 불편함은 없다. 

<br>

📌 하지만 아래의 경우는 어떤가??
<br>

```java
  /**
   * reservationId 매개변수를 통해서 해당 Object를 반환하는 메소드
   * */
  public Reservation getById(Long reservationId) {
    return reservationRepository.findById(reservationId)
        .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
  }

  /**
   * 해당 예약이 적절한지를 판단하는 메소드
   * */
  public void validReservation(String code, Reservation reservation) {
    // 수락된 예약인지를 판단
    if (!reservation.getStatus().equals(REFUSE)) {
      throw new CustomException(RESERVATION_NOT_FOUND);
    }
    // code가 일치한지를 판단
    if (!reservation.getReservationCode().equals(code)) {
      throw new CustomException(RESERVATION_NOT_VALID);
    }

    // 규정 내의 시간안에 도착하는지를 판단
    if (reservation.getReservationTime().isBefore(now())) {
      throw new CustomException(RESERVATION_NOT_TIME);
    }
  }
```

<br>

여기서 필자는 기존의 Service 계층 전에 하나의 Application 계층을 하나 더 많들어서 Service 계층에 위와 같은 메소드를 지정을 해줬다. 그렇다면 위와 같이 나눠줬다면 Application 계층에서는 아래와 같이 표현이 된다.

<br>

```java
public void getReservationValidById(Long id, String code){
	Reservation reservation = reservationService.getById(id);
    
    reservationService.validReservation(code, reservation);
    // ...
}
```
기존에 getReservationValidById 봤던 메소드에서 객체를 가지고 오고, 그 해당 객체를 가지고 검증하는 것을 세세하게 볼 수 있었다.

<br>

그렇다면 바로 위의 코드는 어떠한가? 좀 깔끔해지고 알 수 있는 것은 객체를 가지고 검증하고 있다!! 이다. **<span style="color:green">즉 해당 코드는 좀 더 해당 메소드가 하는 흐름을 한눈에 파악이 가능하다.</span>**

또한 이러한 추상화 레벨을 높이니 이러한 장점도 나에게는 자연스럽게 활용이 가능했다.
![](https://velog.velcdn.com/images/dkwktm45/post/bdda5b53-3183-4deb-8539-d747a596aea9/image.png)

해당 활용은 getById 에 대한 내용이다. **<span style="color:green">결국 이러한 추상화 레벨을 높게 가져간 코드는 재사용성까지 활용할 수 있게 된 격이 된 것이다.</span>**


### 네이밍에 신경을 쓰자!
필자는 개발자는 네이밍에 신경을 많이 쓴다. 그리고 그 네이밍에 관해서 시간을 많이 쏟는다!! 는 것까지 이해하고 있었다. 하지만 당연하다고 생각했지만, 나에게는 Entity에 해당하는 내용이라는 것만을 인지하고 있었다.

하지만 위에서 봤을 때 내가 추상화 레벨을 높이면서 해당 비즈니스 로직에 관한 흐름을 알기 위해서는 정말 정말 네이밍에 관해서 신경을 써야겠다는 생각을 많이 하게 됐다. 그러니 모두 필자처럼 생각하고 있다면 해당 글을 통해서 중요성을 다시 한번 인지하고 갔으면 한다.

[좋은 코드를 위한 자바 메서드 네이밍](https://tecoble.techcourse.co.kr/post/2020-04-26-Method-Naming/) 네이밍은 해당 사이트를 참고 하면 좋을거 같다.

## 마치면서
나는 이번에 제로베이스 과제를 하면서 최대한 좋은 설계가 있을까? 라는 고민으로 과제에 임했으며, 그로 인해 위와 같은 지극히 개인적인 생각을 얻게 됐다.

하지만 결코 이러한 추상화 레벨을 높이는 것은 정답은 아니다. 왜냐하면 우리에게 기존에 처음 언급했던 코드 또한 문제가 되지 않을뿐더러, 레거시 코드라고 불릴 요소도 아니기 때문이다. 그렇다고 이러한 추상화 레벨을 세분화 장점 또한 있기에 적절히 쓰는 것은 좋다고 생각한다.

그래도 이렇게 결과물을 내고 항상 멘토님께 리뷰를 받는다 리뷰에 대해서는 확실히 좋은 반응을 받았기에 조금은 뿌듯한 감이 없잖아 있다!! 화이팅하자!

<br>

![image](https://github.com/dkwktm45/store-resarvation/assets/48014869/78f40448-c603-4e8d-8840-c7e6be8a790f)
