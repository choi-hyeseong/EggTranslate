package com.example.demo.user.basic.data

import com.example.demo.common.page.Pageable

//List<DataFetcher>로는 사용할 수 없지만, 그래도 인터페이스를 구현함으로써 해당 구조를 준수할 수 있음.
//I : ItemDTO - 해당 페이지의 리스트 아이템을 구성하는 DTO
//D : DetailDTO - 상세한 정보를 담고 있는 DTO
interface DataFetcher<I, D> {

    suspend fun getList(page : Int, amount : Int) : Pageable<I>

    suspend fun getDetail(id : Long) : D
}