package com.example.demo.user.basic.data

import com.example.demo.common.page.Pageable

//원래는 findUserList랑 findUserDetail을 기준으로 인터페이스를 구성해 묶을려고 했음.
//근데, 이걸 묶기에는 dto를 추상화 해야하는데, 응답 데이터를 추상화 할 수 있나? Any로도 할 수 있긴 하지만, 좋지 않은 방법임.
//사실상 잘 활용하는 방법이 해당 인터페이스를 구성하고, List<인터페이스>로 받아와 support할경우 반환하는 방식임. - 다형성 이용
//응답의 추상화 때문에 음...

//13:15 - 굳이 억지로 다형성을 사용하지 말고, 필요한 기능을 묶는 용도로 사용하면 좋을듯.
//SOLID원칙중 I를 준수할 수 있음.

//List<DataFetcher>로는 사용할 수 없지만, 그래도 인터페이스를 구현함으로써 해당 구조를 준수할 수 있음.
//I : ItemDTO - 해당 페이지의 리스트 아이템을 구성하는 DTO
//D : DetailDTO - 상세한 정보를 담고 있는 DTO
interface DataFetcher<I, D> {

    suspend fun getList(page : Int, amount : Int) : Pageable<I>

    suspend fun getDetail(id : Long) : D
}